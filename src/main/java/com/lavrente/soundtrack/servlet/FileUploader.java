package com.lavrente.soundtrack.servlet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;

/**
 * Created by 123 on 22.01.2017.
 */
public class FileUploader {
    static final Logger LOG= LogManager.getLogger();
    private static final String SAVE_DIR = "uploadTracks";
    private static final String PATH="path";

    public boolean uploadFile(HttpServletRequest request, SessionRequestContent sessionRequestContent) {
        try {
            String appPath = request.getServletContext().getRealPath("");
            String savePath = appPath + File.separator + SAVE_DIR;

            File fileSaveDir = new File(savePath);
            if (!fileSaveDir.exists()) {
                fileSaveDir.mkdir();
                LOG.info("Directory "+savePath+ "was created");
            }

            for (Part part : request.getParts()) {
                if(PATH.equals(part.getName())) {
                    String fileName = extractFileName(part);
                    // refines the fileName in case it is an absolute path
                    fileName = new File(fileName).getName();
                    String path=savePath + File.separator + fileName;
                    part.write(path);
                    sessionRequestContent.setRequestAttribute(PATH,path);
                }
            }
            LOG.info("Upload has been done successfully!");
            return true;
        } catch (ServletException |IOException e) {
            LOG.error("Exception during uploading file to server", e);
            return false;
        }
    }

    private String extractFileName(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        String[] items = contentDisp.split(";");
        for (String s : items) {
            if (s.trim().startsWith("filename")) {
                return s.substring(s.indexOf("=") + 2, s.length() - 1);
            }
        }
        return "";
    }
}

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

    public boolean uploadFile(HttpServletRequest request) {
        try {
            // gets absolute path of the web application
            String appPath = request.getServletContext().getRealPath("");
            // constructs path of the directory to save uploaded file
            String savePath = appPath + File.separator + SAVE_DIR;

            // creates the save directory if it does not exists
            File fileSaveDir = new File(savePath);
            if (!fileSaveDir.exists()) {
                fileSaveDir.mkdir();
                LOG.info("Directory "+savePath+ "was created");
            }

            for (Part part : request.getParts()) {
                String fileName = extractFileName(part);
                // refines the fileName in case it is an absolute path
                fileName = new File(fileName).getName();
                part.write(savePath + File.separator + fileName);
            }
            LOG.info("Upload has been done successfully!");
            return true;
        } catch (ServletException |IOException e) {
            LOG.error("Error during uploading file to server", e);
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

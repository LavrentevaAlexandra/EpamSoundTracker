package com.lavrente.soundtrack.servlet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by 123 on 26.01.2017.
 */
public class FileDownloader {
    private static final Logger LOG = LogManager.getLogger();
    private final String MIME_TYPE = "application/octet-stream";
    private final String HEADER_KEY = "Content-Disposition";
    private final String ATTACHMENT = "attachment; filename=\"%s\"";
    private final int END = -1;
    private final int BUFFER_LENGTH = 4096;
    private final int START_OFFSET = 0;

    boolean downloadTrack(String filePath, HttpServletResponse response, ServletContext context) {
        try {
            File downloadFile = new File(filePath);
            if (downloadFile.exists() && downloadFile.isFile()) {
                FileInputStream inStream = new FileInputStream(downloadFile);
                String mimeType = context.getMimeType(filePath);
                if (mimeType == null) {
                    mimeType = MIME_TYPE;
                }
                response.setContentType(mimeType);
                response.setContentLength((int) downloadFile.length());
                String headerValue = String.format(ATTACHMENT, downloadFile.getName());
                response.setHeader(HEADER_KEY, headerValue);
                OutputStream outStream = response.getOutputStream();
                byte[] buffer = new byte[BUFFER_LENGTH];
                int bytesRead;
                while ((bytesRead = inStream.read(buffer)) != END) {
                    outStream.write(buffer, START_OFFSET, bytesRead);
                }
                return true;
            }
        } catch (IOException e) {
            LOG.error("Exception during file downloading");
        }
        return false;
    }
}

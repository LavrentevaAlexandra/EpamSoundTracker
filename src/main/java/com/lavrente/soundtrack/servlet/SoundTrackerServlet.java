package com.lavrente.soundtrack.servlet;

import com.lavrente.soundtrack.command.AbstractCommand;
import com.lavrente.soundtrack.command.CommandCreator;
import com.lavrente.soundtrack.command.admin.AddTrackCommand;
import com.lavrente.soundtrack.command.user.CommentCommand;
import com.lavrente.soundtrack.pool.ConnectionPool;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by 123 on 27.12.2016.
 */

@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 20,      // 20MB
        maxRequestSize = 1024 * 1024 * 50)   // 50MB
@WebServlet("/controller")
public class SoundTrackerServlet extends HttpServlet implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        ConnectionPool.getInstance().terminatePool();
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        SessionRequestContent sessionRequestContent = new SessionRequestContent();
        sessionRequestContent.extractValues(request);

        CommandCreator client = new CommandCreator();
        AbstractCommand command = client.defineCommand(sessionRequestContent);

        if (command instanceof AddTrackCommand) {
            FileUploader uploader = new FileUploader();
            boolean res = uploader.uploadFile(request, sessionRequestContent);
            sessionRequestContent.setRequestAttribute("result", res);
        }

        String page = command.execute(sessionRequestContent);
        if (command instanceof CommentCommand) {
            response.sendRedirect(page);
        } else {
            sessionRequestContent.insertAttributes(request);
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(page);
            dispatcher.forward(request, response);
        }
    }
}

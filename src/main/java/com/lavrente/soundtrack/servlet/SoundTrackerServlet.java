package com.lavrente.soundtrack.servlet;

import com.lavrente.soundtrack.command.AbstractCommand;
import com.lavrente.soundtrack.command.CommandCreator;
import com.lavrente.soundtrack.command.DownloadErrorCommand;
import com.lavrente.soundtrack.command.admin.AddTrackCommand;
import com.lavrente.soundtrack.command.user.CommentCommand;
import com.lavrente.soundtrack.command.user.DownloadCommand;
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

    /**
     * Context initialized.
     *
     * @param servletContextEvent the servlet context event
     */
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
    }

    /**
     * Context destroyed.
     *
     * @param servletContextEvent the servlet context event
     */
    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        ConnectionPool.getInstance().terminatePool();
    }


    /**
     * Do get.
     *
     * @param request the request
     * @param response the response
     * @throws ServletException the servlet exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Do post.
     *
     * @param request the request
     * @param response the response
     * @throws ServletException the servlet exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Process request.
     *
     * @param request the request
     * @param response the response
     * @throws ServletException the servlet exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        SessionRequestContent sessionRequestContent = new SessionRequestContent();
        sessionRequestContent.extractValues(request);

        CommandCreator client = new CommandCreator();
        AbstractCommand command = client.defineCommand(sessionRequestContent);
        String page;

        if (command instanceof DownloadCommand) {
            String filePath = command.execute(sessionRequestContent);
            FileDownloader downloader = new FileDownloader();
            if (!downloader.downloadTrack(filePath, response, getServletContext())) {
                DownloadErrorCommand errorCommand = new DownloadErrorCommand();
                page = errorCommand.execute(sessionRequestContent);
                sessionRequestContent.insertAttributes(request);
                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(page);
                dispatcher.forward(request, response);
            }
        } else {
            if (command instanceof AddTrackCommand) {
                FileUploader uploader = new FileUploader();
                boolean res = uploader.uploadFile(request, sessionRequestContent);
                sessionRequestContent.setRequestAttribute("result", res);
                sessionRequestContent.setRequestAttribute("realPath", request.getServletContext().getContextPath());
            }
            page = command.execute(sessionRequestContent);
            sessionRequestContent.insertAttributes(request);
            if (command instanceof CommentCommand) {
                response.sendRedirect(request.getServletContext().getContextPath() + page);
            } else {
                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(page);
                dispatcher.forward(request, response);
            }
        }
    }
}

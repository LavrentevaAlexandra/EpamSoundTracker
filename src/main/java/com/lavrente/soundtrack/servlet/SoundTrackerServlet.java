package com.lavrente.soundtrack.servlet;

import com.lavrente.soundtrack.command.AbstractCommand;
import com.lavrente.soundtrack.command.CommandCreator;
import org.apache.logging.log4j.LogManager;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by 123 on 27.12.2016.
 */
@WebServlet("/controller")
public class SoundTrackerServlet extends HttpServlet {
    private static final org.apache.logging.log4j.Logger LOG = LogManager.getLogger();

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
        String page = null;
        SessionRequestContent sessionRequestContent = new SessionRequestContent();
        sessionRequestContent.extractValues(request);

        CommandCreator client = new CommandCreator();
        AbstractCommand command = client.defineCommand(sessionRequestContent);

        page = command.execute(sessionRequestContent);
        sessionRequestContent.insertAttributes(request);
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(page);
        dispatcher.forward(request, response);
    }
}

package com.lavrente.soundtrack.command;

import com.lavrente.soundtrack.entity.User;
import com.lavrente.soundtrack.exception.LogicException;
import com.lavrente.soundtrack.logic.LoginLogic;
import com.lavrente.soundtrack.logic.UserLogic;
import com.lavrente.soundtrack.manager.ConfigurationManager;
import com.lavrente.soundtrack.manager.MessageManager;
import com.lavrente.soundtrack.servlet.SessionRequestContent;

/**
 * Created by 123 on 28.12.2016.
 */
public class LogInCommand extends AbstractCommand {
    private static final String PARAM_LOGIN = "login";
    private static final String PARAM_PASSWORD = "password";
    private static final String LOGIN_SUCCESS="loginSuccess";
    private static final String USER_ATTRIBUTE="user";



    @Override
    public String execute(SessionRequestContent sessionRequestContent) {
        String page = null;
        String login = sessionRequestContent.getRequestParameter(PARAM_LOGIN);
        String password = sessionRequestContent.getRequestParameter(PARAM_PASSWORD);
        try {
            if (new LoginLogic().checkLogin(login, password)) {
                sessionRequestContent.setSessionAttribute(LOGIN_SUCCESS, "true");
                UserLogic userLogic = new UserLogic();
                User user = userLogic.findUser(login);
                sessionRequestContent.setSessionAttribute(USER_ATTRIBUTE, user);
                page = ConfigurationManager.getProperty(ConfigurationManager.HOME_PATH);
            } else {
                sessionRequestContent.setRequestAttribute(PARAM_LOGIN, login);
                sessionRequestContent.setRequestAttribute(PARAM_PASSWORD, password);
                sessionRequestContent.setRequestAttribute(ERROR, messageManager.getProperty(MessageManager.LOGIN_ERROR));
                page = ConfigurationManager.getProperty(ConfigurationManager.LOGIN_PATH);
            }
        } catch (LogicException e) {
            LOG.error("Error during login command",e);
            sessionRequestContent.setRequestAttribute(ERROR,e);
            page= ConfigurationManager.getProperty(ConfigurationManager.ERROR_PATH);
        }
            return page;
    }
}

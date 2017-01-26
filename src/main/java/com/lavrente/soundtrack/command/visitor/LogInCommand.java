package com.lavrente.soundtrack.command.visitor;

import com.lavrente.soundtrack.command.AbstractCommand;
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

    /** The Constant PARAM_LOGIN. */
    private static final String PARAM_LOGIN = "login";

    /** The Constant PARAM_PASSWORD. */
    private static final String PARAM_PASSWORD = "password";

    /** The Constant TRUE. */
    private static final String TRUE="true";

    /**
     * Execute.
     *
     * @param sessionRequestContent the session request content
     * @return the string
     */
    @Override
    public String execute(SessionRequestContent sessionRequestContent) {
        String page;
        String login = sessionRequestContent.getRequestParameter(PARAM_LOGIN);
        String password = sessionRequestContent.getRequestParameter(PARAM_PASSWORD);
        try {
            if (new LoginLogic().checkLogin(login, password)) {
                sessionRequestContent.setSessionAttribute(IS_LOGIN, TRUE);
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
            LOG.error("Exception during login command", e);
            page= redirectToErrorPage(sessionRequestContent,e);
        }
        return page;
    }
}

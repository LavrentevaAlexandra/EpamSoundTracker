package com.lavrente.soundtrack.command.visitor;

import com.lavrente.soundtrack.command.AbstractCommand;
import com.lavrente.soundtrack.entity.User;
import com.lavrente.soundtrack.exception.LogicException;
import com.lavrente.soundtrack.logic.UserLogic;
import com.lavrente.soundtrack.manager.ConfigurationManager;
import com.lavrente.soundtrack.servlet.SessionRequestContent;

/**
 * Created by 123 on 05.01.2017.
 */
public class SingUpCommand extends AbstractCommand {

    /** The Constant PARAM_LOGIN. */
    private static final String PARAM_LOGIN = "login";

    /** The Constant PARAM_PASSWORD. */
    private static final String PARAM_PASSWORD = "password";

    /** The Constant PARAM_CONF_PASS. */
    private static final String PARAM_CONF_PASS = "password2";

    /** The Constant PARAM_EMAIL. */
    private static final String PARAM_EMAIL = "email";

    /** The Constant PARAM_CARD_NUMBER. */
    private static final String PARAM_CARD_NUMBER = "card";

    /** The Constant ROLE_ATTRIBUTE. */
    private static final String ROLE_ATTRIBUTE = "role";

    /** The Constant IS_LOGIN. */
    private static final String IS_LOGIN = "is_login";

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
        String confPassword = sessionRequestContent.getRequestParameter(PARAM_CONF_PASS);
        String email = sessionRequestContent.getRequestParameter(PARAM_EMAIL);
        String cardNumber = sessionRequestContent.getRequestParameter(PARAM_CARD_NUMBER);
        UserLogic userLogic = new UserLogic();
        try {
            String res = userLogic.singUp(login, password, confPassword, email, cardNumber);
            if (SUCCESS.equals(res)) {
                User user=userLogic.findUser(login);
                sessionRequestContent.setSessionAttribute(IS_LOGIN, "true");
                sessionRequestContent.setSessionAttribute(USER_ATTRIBUTE, user);
                sessionRequestContent.setSessionAttribute(ROLE_ATTRIBUTE, user.getRole());
                page = ConfigurationManager.getProperty(ConfigurationManager.HOME_PATH);
            }else{
                sessionRequestContent.setRequestAttribute(PARAM_LOGIN, login);
                sessionRequestContent.setRequestAttribute(PARAM_PASSWORD, password);
                sessionRequestContent.setRequestAttribute(PARAM_CONF_PASS, confPassword);
                sessionRequestContent.setRequestAttribute(PARAM_EMAIL, email);
                sessionRequestContent.setRequestAttribute(PARAM_CARD_NUMBER, cardNumber);
                sessionRequestContent.setRequestAttribute(ERROR, res);
                page = ConfigurationManager.getProperty(ConfigurationManager.SIGNUP_PATH);
            }
        } catch (LogicException e) {
            LOG.error("Exception during sign up command", e);
            page = redirectToErrorPage(sessionRequestContent,e);
        }
        return page;
    }
}

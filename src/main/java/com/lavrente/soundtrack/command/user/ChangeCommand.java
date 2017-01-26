package com.lavrente.soundtrack.command.user;

import com.lavrente.soundtrack.command.AbstractCommand;
import com.lavrente.soundtrack.entity.User;
import com.lavrente.soundtrack.exception.LogicException;
import com.lavrente.soundtrack.logic.UserLogic;
import com.lavrente.soundtrack.manager.ConfigurationManager;
import com.lavrente.soundtrack.manager.MessageManager;
import com.lavrente.soundtrack.servlet.SessionRequestContent;

/**
 * Created by 123 on 06.01.2017.
 */
public class ChangeCommand extends AbstractCommand {

    /** The Constant USER_ATTR. */
    private static final String USER_ATTR = "user";

    /** The Constant PARAM_LOGIN. */
    private static final String PARAM_LOGIN = "login";

    /** The Constant PARAM_EMAIL. */
    private static final String PARAM_EMAIL = "email";

    /** The Constant PARAM_CARD_NUMBER. */
    private static final String PARAM_CARD_NUMBER = "card";


    /**
     * Execute.
     *
     * @param sessionRequestContent the session request content
     * @return the string
     */
    @Override
    public String execute(SessionRequestContent sessionRequestContent) {
        String page;
        String logined = (String) sessionRequestContent.getSessionAttribute(IS_LOGIN);
        if (logined != null && Boolean.valueOf(logined)) {
            User user = (User) sessionRequestContent.getSessionAttribute(USER_ATTR);
            String login = sessionRequestContent.getRequestParameter(PARAM_LOGIN);
            String email = sessionRequestContent.getRequestParameter(PARAM_EMAIL);
            String cardNumber = sessionRequestContent.getRequestParameter(PARAM_CARD_NUMBER);
            UserLogic userLogic = new UserLogic();
            String res;
            try {
                if (!login.equals(user.getLogin())) {
                    res = userLogic.changeLogin(user.getId(), login);
                    if (SUCCESS.equals(res)) {
                        user.setLogin(login);
                    } else {
                        sessionRequestContent.setRequestAttribute(ERROR, res);
                        return ConfigurationManager.getProperty(ConfigurationManager.CHANGE_PATH);
                    }
                }
                if (!email.equals(user.getEmail())) {
                    res = userLogic.changeEmail(user.getId(), email);
                    if (SUCCESS.equals(res)) {
                        user.setEmail(email);
                    } else {
                        sessionRequestContent.setRequestAttribute(ERROR, res);
                        return ConfigurationManager.getProperty(ConfigurationManager.CHANGE_PATH);
                    }
                }
                if (!cardNumber.equals(user.getCardNumber())) {
                    res = userLogic.changeCardNumber(user.getId(), cardNumber);
                    if (SUCCESS.equals(res)) {
                        user.setCardNumber(cardNumber);
                    } else {
                        sessionRequestContent.setRequestAttribute(ERROR, res);
                        return ConfigurationManager.getProperty(ConfigurationManager.CHANGE_PATH);
                    }
                }
                sessionRequestContent.setSessionAttribute(USER_ATTR, user);
                sessionRequestContent.setRequestAttribute(SUCCESS, messageManager.getProperty(MessageManager.CHANGE_SUCCESS));
                page = ConfigurationManager.getProperty(ConfigurationManager.PROFILE_PATH);
            } catch (LogicException e) {
                LOG.error("Exception during change command", e);
                page = redirectToErrorPage(sessionRequestContent, e);
            }
        } else {
            page = ConfigurationManager.getProperty(ConfigurationManager.HOME_PATH);
        }
        return page;
    }
}

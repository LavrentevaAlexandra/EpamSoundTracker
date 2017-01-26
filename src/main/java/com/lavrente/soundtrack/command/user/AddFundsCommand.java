package com.lavrente.soundtrack.command.user;

import com.lavrente.soundtrack.command.AbstractCommand;
import com.lavrente.soundtrack.entity.User;
import com.lavrente.soundtrack.exception.LogicException;
import com.lavrente.soundtrack.logic.UserLogic;
import com.lavrente.soundtrack.manager.ConfigurationManager;
import com.lavrente.soundtrack.manager.MessageManager;
import com.lavrente.soundtrack.servlet.SessionRequestContent;

/**
 * Created by 123 on 07.01.2017.
 */
public class AddFundsCommand extends AbstractCommand {
    private static final String PARAM_CARD = "card";

    /** The Constant PARAM_CASH. */
    private static final String PARAM_CASH = "cash";


    @Override
    public String execute(SessionRequestContent sessionRequestContent) {
        String page;
        String logined = (String) sessionRequestContent.getSessionAttribute(IS_LOGIN);
        if (logined != null && Boolean.valueOf(logined)) {
            User user = (User) sessionRequestContent.getSessionAttribute(USER_ATTRIBUTE);
            String card_number = sessionRequestContent.getRequestParameter(PARAM_CARD);
            UserLogic userLogic = new UserLogic();
            if (!card_number.isEmpty() && card_number.equals(user.getCardNumber())) {
                String cash = sessionRequestContent.getRequestParameter(PARAM_CASH);
                try {
                    userLogic.addFunds(user, cash);
                    sessionRequestContent.setRequestAttribute(SUCCESS, messageManager.getProperty(MessageManager.CHANGE_SUCCESS));
                    sessionRequestContent.setSessionAttribute(USER_ATTRIBUTE, user);
                    page = ConfigurationManager.getProperty(ConfigurationManager.PROFILE_PATH);
                } catch (LogicException e) {
                    LOG.error("Exception funds addition command", e);
                    page = redirectToErrorPage(sessionRequestContent, e);
                }
            } else {
                sessionRequestContent.setRequestAttribute(ERROR, messageManager.getProperty(MessageManager.CHANGE_CASH_CARD_ERROR));
                page = ConfigurationManager.getProperty(ConfigurationManager.MONEY_PATH);
            }
        } else {
            page = ConfigurationManager.getProperty(ConfigurationManager.HOME_PATH);
        }
        return page;
    }
}

package com.lavrente.soundtrack.command;

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
    private static final String PARAM_CASH = "cash";


    @Override
    public String execute(SessionRequestContent sessionRequestContent) {
        String page;
        User user = (User) sessionRequestContent.getSessionAttribute(USER_ATTRIBUTE);
        String card_number = sessionRequestContent.getRequestParameter(PARAM_CARD);
        UserLogic userLogic = new UserLogic();
        if(!card_number.isEmpty() && card_number.equals(user.getCardNumber())){
            String cash= sessionRequestContent.getRequestParameter(PARAM_CASH);
            try{
                userLogic.addFunds(user, cash);
                sessionRequestContent.setRequestAttribute(SUCCESS, messageManager.getProperty(MessageManager.CHANGE_SUCCESS));
                sessionRequestContent.setSessionAttribute(USER_ATTRIBUTE, user);
                page = ConfigurationManager.getProperty(ConfigurationManager.PROFILE_PATH);
            }catch (LogicException e){
                LOG.error("Error funds addition command", e);
                sessionRequestContent.setRequestAttribute(ERROR, e);
                page = ConfigurationManager.getProperty(ConfigurationManager.ERROR_PATH);
            }
        }else {
            sessionRequestContent.setRequestAttribute(ERROR, messageManager.getProperty(MessageManager.CHANGE_CASH_CARD_ERROR));
            return ConfigurationManager.getProperty(ConfigurationManager.MONEY_PATH);
        }
        return page;
    }
}

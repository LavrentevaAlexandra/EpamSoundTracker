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
    private static final String USER_ATTR = "user";
    private static final String PARAM_CARD = "card";
    private static final String PARAM_CASH = "cash";


    @Override
    public String execute(SessionRequestContent sessionRequestContent) {
        String page = null;
        User user = (User) sessionRequestContent.getSessionAttribute(USER_ATTR);
        String card_number = sessionRequestContent.getRequestParameter(PARAM_CARD);
        UserLogic userLogic = new UserLogic();
        if(card_number.equals(user.getCardNumber())){
            Double cash = Double.valueOf(sessionRequestContent.getRequestParameter(PARAM_CASH));
            try{
                userLogic.addFunds(user.getId(),user.getCash(),cash);
                user.setCash(cash+user.getCash());
                sessionRequestContent.setRequestAttribute(SUCCESS, messageManager.getProperty(MessageManager.CHANGE_SUCCESS));
                sessionRequestContent.setSessionAttribute(USER_ATTR, user);
                page = ConfigurationManager.getProperty(ConfigurationManager.PROFILE_PATH);
            }catch (LogicException e){
                LOG.error("Error adding funds command", e);
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

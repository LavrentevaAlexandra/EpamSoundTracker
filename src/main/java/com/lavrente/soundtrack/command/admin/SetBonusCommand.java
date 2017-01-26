package com.lavrente.soundtrack.command.admin;

import com.lavrente.soundtrack.command.AbstractCommand;
import com.lavrente.soundtrack.exception.LogicException;
import com.lavrente.soundtrack.logic.UserLogic;
import com.lavrente.soundtrack.manager.ConfigurationManager;
import com.lavrente.soundtrack.manager.MessageManager;
import com.lavrente.soundtrack.servlet.SessionRequestContent;

/**
 * Created by 123 on 26.01.2017.
 */
public class SetBonusCommand extends AbstractCommand{
    private final String USER_ID_ATTR="user_id";
    private final String BONUS_ATTR="bonus";


    @Override
    public String execute(SessionRequestContent sessionRequestContent) {
        String page;
        String userIdStr=sessionRequestContent.getRequestParameter(USER_ID_ATTR);
        String bonus=sessionRequestContent.getRequestParameter(BONUS_ATTR+userIdStr);
        Integer userId=Integer.valueOf(userIdStr);
        UserLogic userLogic=new UserLogic();
        try{
            String res=userLogic.setBonus(userId,bonus);
            if(SUCCESS.equals(res)){
                sessionRequestContent.setRequestAttribute(SUCCESS,messageManager.getProperty(MessageManager.SET_BONUS_SUCCESS));
                page= ConfigurationManager.getProperty(ConfigurationManager.SHOW_USERS_PATH);
            }else {
                sessionRequestContent.setRequestAttribute(ERROR,res);
                page= ConfigurationManager.getProperty(ConfigurationManager.SHOW_USERS_PATH);
            }
        }catch (LogicException e){
            LOG.error("Exception during bonus setting",e);
            page=redirectToErrorPage(sessionRequestContent,e);
        }
        return page;
    }
}

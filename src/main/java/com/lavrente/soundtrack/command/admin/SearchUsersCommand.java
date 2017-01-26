package com.lavrente.soundtrack.command.admin;

import com.lavrente.soundtrack.command.AbstractCommand;
import com.lavrente.soundtrack.entity.User;
import com.lavrente.soundtrack.exception.LogicException;
import com.lavrente.soundtrack.logic.UserLogic;
import com.lavrente.soundtrack.manager.ConfigurationManager;
import com.lavrente.soundtrack.servlet.SessionRequestContent;

import java.util.List;

/**
 * Created by 123 on 26.01.2017.
 */
public class SearchUsersCommand extends AbstractCommand {
    private final String FIND_PARAMETER = "find";
    private final String USER_LIST_ATTR="users";


    @Override
    public String execute(SessionRequestContent sessionRequestContent) {
        String page;
        UserLogic userLogic = new UserLogic();
        try {
            String str = sessionRequestContent.getRequestParameter(FIND_PARAMETER);
            List<User> userList = userLogic.findSuitableUsers(str);
            sessionRequestContent.setSessionAttribute(USER_LIST_ATTR, userList);
            page = ConfigurationManager.getProperty(ConfigurationManager.SET_BONUS_PATH);
        } catch (LogicException e) {
            LOG.error("Exception during users search", e);
            page = redirectToErrorPage(sessionRequestContent, e);
        }
        return page;
    }
}

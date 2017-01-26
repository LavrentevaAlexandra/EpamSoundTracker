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
public class ShowUsersCommand extends AbstractCommand {
    private final String USER_LIST_ATTR = "users";

    /**
     * Execute.
     *
     * @param sessionRequestContent the session request content
     * @return the string
     */
    @Override
    public String execute(SessionRequestContent sessionRequestContent) {
        String page;
        User user = (User) sessionRequestContent.getSessionAttribute(USER_ATTRIBUTE);
        if (user != null && user.getRole() == 1) {
            UserLogic userLogic = new UserLogic();
            try {
                List<User> userList = userLogic.findClients();
                sessionRequestContent.setSessionAttribute(USER_LIST_ATTR, userList);
                page = ConfigurationManager.getProperty(ConfigurationManager.SET_BONUS_PATH);
            } catch (LogicException e) {
                LOG.error("Exception during clients search", e);
                page = redirectToErrorPage(sessionRequestContent, e);
            }
        } else {
            page = ConfigurationManager.getProperty(ConfigurationManager.HOME_PATH);
        }
        return page;
    }
}

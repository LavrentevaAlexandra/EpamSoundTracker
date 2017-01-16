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
public class ChangePasswordCommand extends AbstractCommand {
    private static final String USER_ATTR = "user";
    private static final String PARAM_PASSWORD = "password";
    private static final String PARAM_CONF_PASS = "password2";
    private static final String PARAM_NEW_PASS = "new_pass";


    @Override
    public String execute(SessionRequestContent sessionRequestContent) {
        String page;
        User user = (User) sessionRequestContent.getSessionAttribute(USER_ATTR);
        String password = sessionRequestContent.getRequestParameter(PARAM_PASSWORD);
        String newPassword = sessionRequestContent.getRequestParameter(PARAM_NEW_PASS);
        String confPassword = sessionRequestContent.getRequestParameter(PARAM_CONF_PASS);
        UserLogic userLogic = new UserLogic();
        String res;
        try {
            res = userLogic.changePass(user.getId(),user.getPassword(), password, newPassword, confPassword);
            if (SUCCESS.equals(res)) {
                user.setPassword(newPassword);
                sessionRequestContent.setRequestAttribute(SUCCESS, messageManager.getProperty(MessageManager.CHANGE_SUCCESS));
                sessionRequestContent.setSessionAttribute(USER_ATTR, user);
                page = ConfigurationManager.getProperty(ConfigurationManager.PROFILE_PATH);
            } else {
                    sessionRequestContent.setRequestAttribute(ERROR, res);
                    return ConfigurationManager.getProperty(ConfigurationManager.CHANGE_PASS_PATH);
            }
        } catch (LogicException e) {
            LOG.error("Error during change password command", e);
            sessionRequestContent.setRequestAttribute(ERROR, e);
            page = ConfigurationManager.getProperty(ConfigurationManager.ERROR_PATH);
        }
        return page;
    }
}

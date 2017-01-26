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
public class ChangePasswordCommand extends AbstractCommand {

    /** The Constant PARAM_PASSWORD. */
    private static final String PARAM_PASSWORD = "password";

    /** The Constant PARAM_CONF_PASS. */
    private static final String PARAM_CONF_PASS = "password2";

    /** The Constant PARAM_NEW_PASS. */
    private static final String PARAM_NEW_PASS = "new_pass";


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
            User user = (User) sessionRequestContent.getSessionAttribute(USER_ATTRIBUTE);
            String password = sessionRequestContent.getRequestParameter(PARAM_PASSWORD);
            String newPassword = sessionRequestContent.getRequestParameter(PARAM_NEW_PASS);
            String confPassword = sessionRequestContent.getRequestParameter(PARAM_CONF_PASS);
            UserLogic userLogic = new UserLogic();
            String res;
            try {
                res = userLogic.changePass(user.getId(), user.getPassword(), password, newPassword, confPassword);
                if (SUCCESS.equals(res)) {
                    user.setPassword(newPassword);
                    sessionRequestContent.setRequestAttribute(SUCCESS, messageManager.getProperty(MessageManager.CHANGE_SUCCESS));
                    sessionRequestContent.setSessionAttribute(USER_ATTRIBUTE, user);
                    page = ConfigurationManager.getProperty(ConfigurationManager.PROFILE_PATH);
                } else {
                    sessionRequestContent.setRequestAttribute(ERROR, res);
                    return ConfigurationManager.getProperty(ConfigurationManager.CHANGE_PASS_PATH);
                }
            } catch (LogicException e) {
                LOG.error("Exception during change password command", e);
                page = redirectToErrorPage(sessionRequestContent, e);
            }
        } else {
            page = ConfigurationManager.getProperty(ConfigurationManager.HOME_PATH);
        }
        return page;
    }
}

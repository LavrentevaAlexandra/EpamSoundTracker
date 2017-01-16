package com.lavrente.soundtrack.command;

import com.lavrente.soundtrack.manager.ConfigurationManager;
import com.lavrente.soundtrack.servlet.SessionRequestContent;

/**
 * Created by 123 on 28.12.2016.
 */
public class LogOutCommand extends AbstractCommand {
    private static final String LOGIN_SUCCESS="loginSuccess";
    private static final String USER_ATTRIBUTE="user";


    @Override
    public String execute(SessionRequestContent sessionRequestContent) {
        sessionRequestContent.setSessionAttribute(LOGIN_SUCCESS, null);
        sessionRequestContent.setSessionAttribute(USER_ATTRIBUTE, null);
        return ConfigurationManager.getProperty(ConfigurationManager.HOME_PATH);
    }
}

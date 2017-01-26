package com.lavrente.soundtrack.command.user;

import com.lavrente.soundtrack.command.AbstractCommand;
import com.lavrente.soundtrack.manager.ConfigurationManager;
import com.lavrente.soundtrack.servlet.SessionRequestContent;

/**
 * Created by 123 on 28.12.2016.
 */
public class LogOutCommand extends AbstractCommand {

    /** The Constant IS_LOGIN. */
    private static final String IS_LOGIN="is_login";

    /**
     * Execute.
     *
     * @param sessionRequestContent the session request content
     * @return the string
     */
    @Override
    public String execute(SessionRequestContent sessionRequestContent) {
        sessionRequestContent.setSessionAttribute(IS_LOGIN, null);
        sessionRequestContent.setSessionAttribute(USER_ATTRIBUTE, null);
        return ConfigurationManager.getProperty(ConfigurationManager.HOME_PATH);
    }
}

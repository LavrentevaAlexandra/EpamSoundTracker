package com.lavrente.soundtrack.command;

import com.lavrente.soundtrack.manager.ConfigurationManager;
import com.lavrente.soundtrack.servlet.SessionRequestContent;

/**
 * Created by 123 on 30.11.2016.
 */

public class EmptyCommand extends AbstractCommand {
    private static final String DEFAULT_LOCALE = "ru_RU";

    @Override
    public String execute(SessionRequestContent sessionRequestContent) {
        String page = ConfigurationManager.getProperty(ConfigurationManager.HOME_PATH);
        sessionRequestContent.setSessionAttribute(CUR_PAGE_ATTR,ConfigurationManager.HOME_PATH);
        sessionRequestContent.setSessionAttribute(LOCALE_ATTRIBUTE, DEFAULT_LOCALE );
        return page;
    }
}

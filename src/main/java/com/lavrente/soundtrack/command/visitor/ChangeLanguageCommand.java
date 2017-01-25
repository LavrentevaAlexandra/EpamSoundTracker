package com.lavrente.soundtrack.command.visitor;

import com.lavrente.soundtrack.command.AbstractCommand;
import com.lavrente.soundtrack.manager.ConfigurationManager;
import com.lavrente.soundtrack.servlet.SessionRequestContent;

/**
 * Created by 123 on 29.11.2016.
 */
public class ChangeLanguageCommand extends AbstractCommand {
    private static final String LANGUAGE_ATTRIBUTE = "lang";

    @Override
    public String execute(SessionRequestContent sessionRequestContent) {
        String page;
        Object str = sessionRequestContent.getRequestParameter(LANGUAGE_ATTRIBUTE);
        if (str != null) {
            messageManager.setCurrentLocale(str.toString());
            sessionRequestContent.setSessionAttribute(LOCALE_ATTRIBUTE, str.toString());
        }
        Object property = sessionRequestContent.getSessionAttribute(CUR_PAGE_ATTR);
        if (property == null) {
            page = ConfigurationManager.getProperty(ConfigurationManager.HOME_PATH);
        } else {
            page = ConfigurationManager.getProperty(property.toString());
        }
        return page;
    }
}

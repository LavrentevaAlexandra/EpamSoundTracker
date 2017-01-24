package com.lavrente.soundtrack.command;

import com.lavrente.soundtrack.manager.ConfigurationManager;
import com.lavrente.soundtrack.manager.Messenger;
import com.lavrente.soundtrack.servlet.SessionRequestContent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by 123 on 29.11.2016.
 */
public abstract class AbstractCommand implements Messenger{
    protected static final Logger LOG= LogManager.getLogger();
    static final String LOCALE_ATTRIBUTE = "locale";
    static final String CUR_PAGE_ATTR = "page";
    protected static final String ERROR = "error";
    protected static final String SUCCESS= "success";
    protected static final String USER_ATTRIBUTE="user";

    public abstract String execute(SessionRequestContent sessionRequestContent);

    public String redirectToErrorPage(SessionRequestContent sessionRequestContent, Exception e) {
        sessionRequestContent.setRequestAttribute(ERROR, e);
        return ConfigurationManager.getProperty(ConfigurationManager.ERROR_PATH);
    }

}

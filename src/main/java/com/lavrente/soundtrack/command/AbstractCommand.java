package com.lavrente.soundtrack.command;

import com.lavrente.soundtrack.manager.ConfigurationManager;
import com.lavrente.soundtrack.manager.Messenger;
import com.lavrente.soundtrack.servlet.SessionRequestContent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The Class AbstractCommand.
 */
public abstract class AbstractCommand implements Messenger{

    /** The Constant LOG. */
    protected static final Logger LOG= LogManager.getLogger();

    /** The Constant LOCALE_ATTRIBUTE. */
    protected static final String LOCALE_ATTRIBUTE = "locale";

    /** The Constant CUR_PAGE_ATTR. */
    protected static final String CUR_PAGE_ATTR = "page";

    /** The Constant ERROR. */
    protected static final String ERROR = "error";

    /** The Constant SUCCESS. */
    protected static final String SUCCESS= "success";

    /** The Constant USER_ATTRIBUTE. */
    protected static final String USER_ATTRIBUTE="user";

    /** The Constant IS_LOGIN. */
    protected static final String IS_LOGIN = "is_login";

    /** The Constant NUM_PAGE. */
    protected static final String NUM_PAGE = "num_page";

    /**
     * Execute.
     *
     * @param sessionRequestContent the session request content
     * @return the string
     */
    public abstract String execute(SessionRequestContent sessionRequestContent);

    /**
     * Redirect to error page.
     *
     * @param sessionRequestContent the session request content
     * @param e the e
     * @return the string
     */
    public String redirectToErrorPage(SessionRequestContent sessionRequestContent, Exception e) {
        sessionRequestContent.setRequestAttribute(ERROR, e);
        return ConfigurationManager.getProperty(ConfigurationManager.ERROR_PATH);
    }

}

package com.lavrente.soundtrack.command;

import com.lavrente.soundtrack.manager.MessageManager;
import com.lavrente.soundtrack.manager.Messenger;
import com.lavrente.soundtrack.servlet.SessionRequestContent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by 123 on 29.11.2016.
 */
public abstract class AbstractCommand implements Messenger{
    static final Logger LOG= LogManager.getLogger();
    static final String LOCALE_ATTRIBUTE = "locale";
    static final String CUR_PAGE_ATTR = "page";
    static final String ERROR = "error";
    static final String SUCCESS= "success";

    public abstract String execute(SessionRequestContent sessionRequestContent);
}

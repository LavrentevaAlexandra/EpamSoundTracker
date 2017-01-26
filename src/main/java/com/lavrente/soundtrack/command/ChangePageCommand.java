package com.lavrente.soundtrack.command;

import com.lavrente.soundtrack.manager.ConfigurationManager;
import com.lavrente.soundtrack.servlet.SessionRequestContent;

import java.util.Objects;

/**
 * Created by 123 on 26.01.2017.
 */
public class ChangePageCommand extends AbstractCommand {
    private final String PAGE_PARAM = "page";
    private final String NUM_OF_PAGES = "number_of_pages";


    @Override
    public String execute(SessionRequestContent sessionRequestContent) {
        Object pageObj = sessionRequestContent.getRequestParameter(PAGE_PARAM);
        Object allPagesObj = sessionRequestContent.getRequestParameter(NUM_OF_PAGES);
        int newPageNum;
        if (pageObj != null) {
            newPageNum = Integer.valueOf(pageObj.toString());
            if (allPagesObj == null || newPageNum > Integer.valueOf(pageObj.toString())){
                newPageNum = 0;
            }
        } else {
            newPageNum = 0;
        }
        sessionRequestContent.setSessionAttribute(NUM_PAGE, newPageNum);
        return ConfigurationManager.getProperty(sessionRequestContent.getSessionAttribute(CUR_PAGE_ATTR).toString());
    }
}

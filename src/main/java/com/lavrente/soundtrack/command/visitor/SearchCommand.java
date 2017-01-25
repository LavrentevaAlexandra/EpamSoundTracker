package com.lavrente.soundtrack.command.visitor;

import com.lavrente.soundtrack.command.AbstractCommand;
import com.lavrente.soundtrack.entity.Track;
import com.lavrente.soundtrack.exception.LogicException;
import com.lavrente.soundtrack.logic.TrackLogic;
import com.lavrente.soundtrack.manager.ConfigurationManager;
import com.lavrente.soundtrack.servlet.SessionRequestContent;

import java.util.List;

/**
 * Created by 123 on 25.01.2017.
 */
public class SearchCommand extends AbstractCommand {
    private final String FIND_PARAMETER = "find";
    private final String SEARCH_ATTR = "search";
    private final String TRACK_LIST_ATTR="track_list";
    private final String IS_DELETED = "is_deleted";

    @Override
    public String execute(SessionRequestContent sessionRequestContent) {
        String page;
        TrackLogic trackLogic = new TrackLogic();
        try {
            String str = sessionRequestContent.getRequestParameter(FIND_PARAMETER);
            List<Track> tracks = trackLogic.findSuitableTracks(str);
            sessionRequestContent.setSessionAttribute(IS_DELETED, false);
            sessionRequestContent.setRequestAttribute(SEARCH_ATTR, true);
            sessionRequestContent.setSessionAttribute(TRACK_LIST_ATTR, tracks);
            page = ConfigurationManager.getProperty(ConfigurationManager.MAIN_PATH);
        } catch (LogicException e) {
            LOG.error("Exception during tracks search", e);
            page = redirectToErrorPage(sessionRequestContent, e);
        }
        return page;
    }
}

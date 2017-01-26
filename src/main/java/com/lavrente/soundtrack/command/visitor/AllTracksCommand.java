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
public class AllTracksCommand extends AbstractCommand{

    /** The track list attr. */
    private final String TRACK_LIST_ATTR = "track_list";

    /** The is deleted. */
    private final String IS_DELETED = "is_deleted";

    /** The all attr. */
    private final String ALL_ATTR="all";

    /**
     * Execute.
     *
     * @param sessionRequestContent the session request content
     * @return the string
     */
    @Override
    public String execute(SessionRequestContent sessionRequestContent) {
        String page;
        TrackLogic trackLogic = new TrackLogic();
        try {
            List<Track> trackList = trackLogic.findAllTracks();
            sessionRequestContent.setSessionAttribute(TRACK_LIST_ATTR, trackList);
            sessionRequestContent.setSessionAttribute(IS_DELETED, false);
            sessionRequestContent.setRequestAttribute(ALL_ATTR,true);
            page = ConfigurationManager.getProperty(ConfigurationManager.MAIN_PATH);
        } catch (LogicException e) {
            LOG.error("Exception during all tracks search",e);
            page = redirectToErrorPage(sessionRequestContent, e);
        }
        return page;
    }
}

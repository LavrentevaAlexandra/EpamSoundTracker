package com.lavrente.soundtrack.command.admin;

import com.lavrente.soundtrack.command.AbstractCommand;
import com.lavrente.soundtrack.entity.Track;
import com.lavrente.soundtrack.exception.LogicException;
import com.lavrente.soundtrack.logic.TrackLogic;
import com.lavrente.soundtrack.manager.ConfigurationManager;
import com.lavrente.soundtrack.servlet.SessionRequestContent;

import java.util.List;

/**
 * Created by 123 on 24.01.2017.
 */
public class ShowDeletedCommand extends AbstractCommand {
    private static final String TRACK_LIST_ATTR = "track_list";
    private static final String IS_DELETED = "is_deleted";

    @Override
    public String execute(SessionRequestContent sessionRequestContent) {
        String page;
        List<Track> deletedTracks;
        TrackLogic trackLogic = new TrackLogic();
        try {
            deletedTracks = trackLogic.findDeletedTracks();
            sessionRequestContent.setSessionAttribute(TRACK_LIST_ATTR, deletedTracks);
            sessionRequestContent.setSessionAttribute(IS_DELETED, true);
            page = ConfigurationManager.getProperty(ConfigurationManager.TRACK_RECOVER_PATH);
        } catch (LogicException e) {
            LOG.error("Exception during deleted tracks search",e);
            page = redirectToErrorPage(sessionRequestContent, e);
        }
        return page;
    }
}

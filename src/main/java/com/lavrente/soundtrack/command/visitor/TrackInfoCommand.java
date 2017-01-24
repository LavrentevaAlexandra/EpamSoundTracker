package com.lavrente.soundtrack.command.visitor;

import com.lavrente.soundtrack.command.AbstractCommand;
import com.lavrente.soundtrack.entity.Comment;
import com.lavrente.soundtrack.entity.Track;
import com.lavrente.soundtrack.exception.LogicException;
import com.lavrente.soundtrack.logic.TrackLogic;
import com.lavrente.soundtrack.manager.ConfigurationManager;
import com.lavrente.soundtrack.servlet.SessionRequestContent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 123 on 19.01.2017.
 */
public class TrackInfoCommand extends AbstractCommand {
    private final String TRACK_ID = "track_id";
    private final String TRACK_ATTRIBUTE = "track";
    private final String COMMENTS_ATTRIBUTE = "comments";

    @Override
    public String execute(SessionRequestContent sessionRequestContent) {
        String page;
        int trackId = Integer.valueOf(sessionRequestContent.getRequestParameter(TRACK_ID));
        TrackLogic trackLogic = new TrackLogic();
        try {
            List<Comment> comments = trackLogic.findTrackComments(trackId);
            Track track = trackLogic.findTrackById(trackId);
            sessionRequestContent.setSessionAttribute(TRACK_ATTRIBUTE, track);
            sessionRequestContent.setSessionAttribute(COMMENTS_ATTRIBUTE, comments);
            page = ConfigurationManager.getProperty(ConfigurationManager.TRACK_INFO_PATH);
        }catch (LogicException e){
            LOG.error("Exception during command about track info",e);
            page = redirectToErrorPage(sessionRequestContent, e);
        }
        return page;
    }
}

package com.lavrente.soundtrack.command;

import com.lavrente.soundtrack.entity.Comment;
import com.lavrente.soundtrack.entity.Track;
import com.lavrente.soundtrack.exception.LogicException;
import com.lavrente.soundtrack.logic.TrackLogic;
import com.lavrente.soundtrack.manager.ConfigurationManager;
import com.lavrente.soundtrack.servlet.SessionRequestContent;

import java.util.ArrayList;

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
            ArrayList<Comment> comments = trackLogic.findTrackComments(trackId);
            //Track track = trackLogic.findTrackById(trackId);
            //sessionRequestContent.setRequestAttribute(TRACK_ATTRIBUTE, track);
            sessionRequestContent.setRequestAttribute(COMMENTS_ATTRIBUTE, comments);
            page = ConfigurationManager.getProperty(ConfigurationManager.TRACK_INFO_PATH);
        }catch (LogicException e){
            LOG.error(e);
            sessionRequestContent.setRequestAttribute(ERROR, e.getMessage());
            page = ConfigurationManager.getProperty(ConfigurationManager.ERROR_PATH);
        }
        return page;
    }
}

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
public class ShowGenreCommand extends AbstractCommand {
    private final String TRACK_LIST_ATTR = "track_list";
    private final String IS_DELETED = "is_deleted";
    private final String GENRE_PARAMETER="genre";
    private final String IS_GENRE="is_genre";

    @Override
    public String execute(SessionRequestContent sessionRequestContent) {
        String page;
        String genre= sessionRequestContent.getRequestParameter(GENRE_PARAMETER);
        if(genre==null){
            genre= sessionRequestContent.getSessionAttribute(GENRE_PARAMETER).toString();
        }
        TrackLogic trackLogic = new TrackLogic();
        try {
            List<Track> trackList = trackLogic.findTracksByGenre(genre);
            sessionRequestContent.setSessionAttribute(GENRE_PARAMETER,genre);
            sessionRequestContent.setSessionAttribute(TRACK_LIST_ATTR, trackList);
            sessionRequestContent.setSessionAttribute(IS_DELETED, false);
            sessionRequestContent.setRequestAttribute(IS_GENRE,true);
            page = ConfigurationManager.getProperty(ConfigurationManager.MAIN_PATH);
        } catch (LogicException e) {
            LOG.error("Exception during tracks by genre search",e);
            page = redirectToErrorPage(sessionRequestContent, e);
        }
        return page;
    }
}

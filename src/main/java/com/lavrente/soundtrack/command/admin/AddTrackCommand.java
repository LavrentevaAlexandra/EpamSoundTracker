package com.lavrente.soundtrack.command.admin;

import com.lavrente.soundtrack.command.AbstractCommand;
import com.lavrente.soundtrack.entity.User;
import com.lavrente.soundtrack.exception.LogicException;
import com.lavrente.soundtrack.logic.GenreLogic;
import com.lavrente.soundtrack.logic.TrackLogic;
import com.lavrente.soundtrack.manager.ConfigurationManager;
import com.lavrente.soundtrack.manager.MessageManager;
import com.lavrente.soundtrack.servlet.SessionRequestContent;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 123 on 11.01.2017.
 */
public class AddTrackCommand extends AbstractCommand {

    /** The Constant RESULT_ATTR. */
    private static final String RESULT_ATTR = "result";

    /** The real path attribute. */
    private final String REAL_PATH_ATTRIBUTE = "path";

    /** The name param. */
    private final String NAME_PARAM = "name";

    /** The artist param. */
    private final String ARTIST_PARAM = "artist";

    /** The genre param. */
    private final String GENRE_PARAM = "genre";

    /** The genre attribute. */
    private final String GENRES_ATTR = "genres";


    /** The price param. */
    private final String PRICE_PARAM = "price";

    /**
     * Execute.
     *
     * @param sessionRequestContent the session request content
     * @return the string
     */
    @Override
    public String execute(SessionRequestContent sessionRequestContent) {
        String page;
        User user = (User) sessionRequestContent.getSessionAttribute(USER_ATTRIBUTE);
        if (user != null && user.getRole() == 1) {
            boolean result = Boolean.valueOf(sessionRequestContent.getRequestAttribute(RESULT_ATTR).toString());
            if (result) {
                TrackLogic trackLogic = new TrackLogic();
                String name = sessionRequestContent.getRequestParameter(NAME_PARAM);
                String artist = sessionRequestContent.getRequestParameter(ARTIST_PARAM);
                String genre = sessionRequestContent.getRequestParameter(GENRE_PARAM);
                String price = sessionRequestContent.getRequestParameter(PRICE_PARAM);
                String realPath = sessionRequestContent.getRequestAttribute(REAL_PATH_ATTRIBUTE).toString();
                try {
                    String res = trackLogic.addTrack(name, artist, price, genre, realPath);
                    if (SUCCESS.equals(res)) {
                        GenreLogic genreLogic=new GenreLogic();
                        List<String> genreList=new ArrayList<>();
                        genreList= genreLogic.findGenres();
                        sessionRequestContent.setSessionAttribute(GENRES_ATTR, genreList);
                        sessionRequestContent.setRequestAttribute(SUCCESS, messageManager.getProperty(MessageManager.ADD_TRACK_SUCCESS));
                        page = ConfigurationManager.getProperty(ConfigurationManager.HOME_PATH);
                    } else {
                        sessionRequestContent.setRequestAttribute(ERROR, res);
                        page = ConfigurationManager.getProperty(ConfigurationManager.HOME_PATH);
                    }
                } catch (LogicException e) {
                    LOG.error("Exception during track addition command", e);
                    sessionRequestContent.setRequestAttribute(ERROR, messageManager.getProperty(MessageManager.ADD_TRACK_ERROR));
                    page = ConfigurationManager.getProperty(ConfigurationManager.HOME_PATH);
                }
            } else {
                sessionRequestContent.setRequestAttribute(ERROR, messageManager.getProperty(MessageManager.ADD_TRACK_ERROR));
                page = ConfigurationManager.getProperty(ConfigurationManager.HOME_PATH);
            }
        } else {
            page = ConfigurationManager.getProperty(ConfigurationManager.HOME_PATH);
        }
        return page;
    }
}

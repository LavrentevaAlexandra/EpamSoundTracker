package com.lavrente.soundtrack.command.admin;

import com.lavrente.soundtrack.command.AbstractCommand;
import com.lavrente.soundtrack.entity.User;
import com.lavrente.soundtrack.exception.LogicException;
import com.lavrente.soundtrack.logic.TrackLogic;
import com.lavrente.soundtrack.manager.ConfigurationManager;
import com.lavrente.soundtrack.manager.MessageManager;
import com.lavrente.soundtrack.servlet.SessionRequestContent;

import java.io.File;

/**
 * Created by 123 on 11.01.2017.
 */
public class AddTrackCommand extends AbstractCommand {
    private static final String RESULT_ATTR = "result";
    private final String DATA_FOLDER = "uploadTracks";
    private final String PATH_ATTRIBUTE = "path";
    private final String REAL_PATH_ATTRIBUTE = "path";
    private final String NAME_PARAM = "name";
    private final String ARTIST_PARAM = "artist";
    private final String GENRE_PARAM = "genre";
    private final String PRICE_PARAM = "price";

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

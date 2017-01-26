package com.lavrente.soundtrack.command.admin;

import com.lavrente.soundtrack.command.AbstractCommand;
import com.lavrente.soundtrack.entity.Comment;
import com.lavrente.soundtrack.entity.Track;
import com.lavrente.soundtrack.entity.User;
import com.lavrente.soundtrack.exception.LogicException;
import com.lavrente.soundtrack.logic.TrackLogic;
import com.lavrente.soundtrack.manager.ConfigurationManager;
import com.lavrente.soundtrack.manager.MessageManager;
import com.lavrente.soundtrack.servlet.SessionRequestContent;

import java.util.List;

/**
 * Created by 123 on 25.01.2017.
 */
public class EditTrackCommand extends AbstractCommand {

    /** The track attr. */
    private final String TRACK_ATTR = "track";

    /** The comments attribute. */
    private final String COMMENTS_ATTRIBUTE = "comments";

    /** The name param. */
    private final String NAME_PARAM = "name";

    /** The artist param. */
    private final String ARTIST_PARAM = "artist";

    /** The genre param. */
    private final String GENRE_PARAM = "genre";

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
            Track track = (Track) sessionRequestContent.getSessionAttribute(TRACK_ATTR);
            String name = sessionRequestContent.getRequestParameter(NAME_PARAM);
            String artist = sessionRequestContent.getRequestParameter(ARTIST_PARAM);
            String price = sessionRequestContent.getRequestParameter(PRICE_PARAM);
            String genre = sessionRequestContent.getRequestParameter(GENRE_PARAM);
            TrackLogic trackLogic = new TrackLogic();
            String res;
            try {
                if (!name.equals(track.getName())) {
                    res = trackLogic.changeName(track.getId(), name);
                    if (SUCCESS.equals(res)) {
                        track.setName(name);
                    } else {
                        sessionRequestContent.setRequestAttribute(ERROR, res);
                        return ConfigurationManager.getProperty(ConfigurationManager.TRACK_EDIT_PATH);
                    }
                }
                if (!artist.equals(track.getArtist())) {
                    res = trackLogic.changeArtist(track.getId(), artist);
                    if (SUCCESS.equals(res)) {
                        track.setArtist(artist);
                    } else {
                        sessionRequestContent.setRequestAttribute(ERROR, res);
                        return ConfigurationManager.getProperty(ConfigurationManager.TRACK_EDIT_PATH);
                    }
                }
                if (!genre.equals(track.getGenre())) {
                    res = trackLogic.changeGenre(track.getId(), genre);
                    if (SUCCESS.equals(res)) {
                        track.setGenre(genre.toUpperCase());
                    } else {
                        sessionRequestContent.setRequestAttribute(ERROR, res);
                        return ConfigurationManager.getProperty(ConfigurationManager.TRACK_EDIT_PATH);
                    }
                }
                res = trackLogic.changePrice(track.getId(), track.getPrice(), price);
                if (SUCCESS.equals(res)) {
                    track.setPrice(Double.valueOf(price));
                } else {
                    sessionRequestContent.setRequestAttribute(ERROR, res);
                    return ConfigurationManager.getProperty(ConfigurationManager.TRACK_EDIT_PATH);
                }
                List<Comment> comments = trackLogic.findTrackComments(track.getId());
                sessionRequestContent.setSessionAttribute(COMMENTS_ATTRIBUTE, comments);
                sessionRequestContent.setSessionAttribute(TRACK_ATTR, track);
                sessionRequestContent.setRequestAttribute(SUCCESS, messageManager.getProperty(MessageManager.CHANGE_SUCCESS));
                page = ConfigurationManager.getProperty(ConfigurationManager.TRACK_INFO_PATH);
            } catch (LogicException e) {
                LOG.error("Exception during change command", e);
                page = redirectToErrorPage(sessionRequestContent, e);
            }
        } else {
            page = ConfigurationManager.getProperty(ConfigurationManager.HOME_PATH);
        }

        return page;
    }
}

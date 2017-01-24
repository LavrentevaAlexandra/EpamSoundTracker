package com.lavrente.soundtrack.command.user;

import com.lavrente.soundtrack.command.AbstractCommand;
import com.lavrente.soundtrack.entity.Comment;
import com.lavrente.soundtrack.entity.Track;
import com.lavrente.soundtrack.entity.User;
import com.lavrente.soundtrack.exception.LogicException;
import com.lavrente.soundtrack.logic.TrackLogic;
import com.lavrente.soundtrack.logic.UserLogic;
import com.lavrente.soundtrack.manager.ConfigurationManager;
import com.lavrente.soundtrack.manager.MessageManager;
import com.lavrente.soundtrack.servlet.SessionRequestContent;

import java.util.List;

/**
 * Created by 123 on 21.01.2017.
 */
@SuppressWarnings("Duplicates")
public class CommentCommand extends AbstractCommand {
    private final String TRACK_ID_PARAM = "track_id";
    private final String COMMENT_PARAM = "comment_area";
    private final String TRACK_ATTRIBUTE = "track";
    private final String COMMENTS_ATTRIBUTE = "comments";

    @Override
    public String execute(SessionRequestContent sessionRequestContent) {
        String page;
        User user = (User) sessionRequestContent.getSessionAttribute(USER_ATTRIBUTE);
        int trackId = Integer.valueOf(sessionRequestContent.getRequestParameter(TRACK_ID_PARAM));
        String commentText = sessionRequestContent.getRequestParameter(COMMENT_PARAM);
        UserLogic userLogic = new UserLogic();
        TrackLogic trackLogic = new TrackLogic();
        if (!commentText.isEmpty()) {
            try {
                String res = userLogic.addComment(user, commentText, trackId);
                if (SUCCESS.equals(res)) {
                    List<Comment> comments = trackLogic.findTrackComments(trackId);
                    sessionRequestContent.setSessionAttribute(COMMENTS_ATTRIBUTE, comments);
                    page = ConfigurationManager.getProperty(ConfigurationManager.TRACK_INFO_PATH);
                }else{
                    sessionRequestContent.setRequestAttribute(ERROR, res);
                    return ConfigurationManager.getProperty(ConfigurationManager.TRACK_INFO_PATH);
                }
            } catch (LogicException e) {
                LOG.error("Exception during comment addition command", e);
                page = redirectToErrorPage(sessionRequestContent, e);
            }
        } else {
            sessionRequestContent.setRequestAttribute(ERROR, messageManager.getProperty(MessageManager.ADD_COMMENT_EMPTY));
            return ConfigurationManager.getProperty(ConfigurationManager.TRACK_INFO_PATH);
        }
        return page;
    }
}

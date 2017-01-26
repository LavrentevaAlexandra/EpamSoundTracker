package com.lavrente.soundtrack.command.admin;

import com.lavrente.soundtrack.command.AbstractCommand;
import com.lavrente.soundtrack.entity.User;
import com.lavrente.soundtrack.exception.LogicException;
import com.lavrente.soundtrack.logic.TrackLogic;
import com.lavrente.soundtrack.manager.ConfigurationManager;
import com.lavrente.soundtrack.manager.MessageManager;
import com.lavrente.soundtrack.servlet.SessionRequestContent;

/**
 * Created by 123 on 23.01.2017.
 */
public class DeleteTrackCommand extends AbstractCommand {
    private final String TRACK_ID_PARAMETER = "track_id";

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
            int trackId = Integer.valueOf(sessionRequestContent.getRequestParameter(TRACK_ID_PARAMETER));
            TrackLogic trackLogic = new TrackLogic();
            try {
                trackLogic.deleteTrackById(trackId);
                sessionRequestContent.setRequestAttribute(SUCCESS, messageManager.getProperty(MessageManager.DELETE_TRACK_SUCCESS));
                page = ConfigurationManager.getProperty(ConfigurationManager.HOME_PATH);
            } catch (LogicException e) {
                LOG.error("Exception during track delete command", e);
                page = redirectToErrorPage(sessionRequestContent, e);
            }
        } else {
            page = ConfigurationManager.getProperty(ConfigurationManager.HOME_PATH);
        }
        return page;
    }
}

package com.lavrente.soundtrack.command;

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

    @Override
    public String execute(SessionRequestContent sessionRequestContent) {
        String page;
        int trackId = Integer.valueOf(sessionRequestContent.getRequestParameter(TRACK_ID_PARAMETER));
        TrackLogic trackLogic = new TrackLogic();
        try {
            trackLogic.deleteTrackById(trackId);
            sessionRequestContent.setRequestAttribute(SUCCESS, messageManager.getProperty(MessageManager.DELETE_TRACK_SUCCESS));
            page = ConfigurationManager.getProperty(ConfigurationManager.HOME_PATH);
        } catch (LogicException e) {
            LOG.error("Error during track delete command", e);
            page=redirectToErrorPage(sessionRequestContent,e);
        }
        return page;
    }
}

package com.lavrente.soundtrack.command.user;

import com.lavrente.soundtrack.command.AbstractCommand;
import com.lavrente.soundtrack.exception.LogicException;
import com.lavrente.soundtrack.logic.TrackLogic;
import com.lavrente.soundtrack.servlet.SessionRequestContent;

/**
 * Created by 123 on 26.01.2017.
 */
public class DownloadCommand extends AbstractCommand {
    private final String TRACK_ID_ATTR = "track_id";

    @Override
    public String execute(SessionRequestContent sessionRequestContent) {
        String filePath;
        int trackId = Integer.valueOf(sessionRequestContent.getRequestParameter(TRACK_ID_ATTR));
        TrackLogic trackLogic = new TrackLogic();
        try {
            filePath = trackLogic.findTrackPath(trackId);
        } catch (LogicException e) {
            LOG.error("Exception during track path search",e);
            filePath = "";
        }
        return filePath;
    }
}

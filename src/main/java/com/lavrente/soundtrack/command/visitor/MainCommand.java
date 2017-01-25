package com.lavrente.soundtrack.command.visitor;

import com.lavrente.soundtrack.command.AbstractCommand;
import com.lavrente.soundtrack.entity.Track;
import com.lavrente.soundtrack.exception.LogicException;
import com.lavrente.soundtrack.logic.TrackLogic;
import com.lavrente.soundtrack.manager.ConfigurationManager;
import com.lavrente.soundtrack.servlet.SessionRequestContent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 123 on 14.01.2017.
 */
public class MainCommand extends AbstractCommand {
    private final String TRACK_LIST_ATTR = "track_list";
    private final String IS_DELETED = "is_deleted";

    @Override
    public String execute(SessionRequestContent sessionRequestContent) {
        TrackLogic trackLogic=new TrackLogic();
        List<Track> trackList=new ArrayList<>();
        try{
            trackList=trackLogic.lastTracks();
        }catch (LogicException e){
            LOG.error("Exception during last ordered tracks search",e);
        }
        sessionRequestContent.setSessionAttribute(TRACK_LIST_ATTR, trackList);
        sessionRequestContent.setSessionAttribute(IS_DELETED, false);
        return ConfigurationManager.getProperty(ConfigurationManager.MAIN_PATH);
    }
}

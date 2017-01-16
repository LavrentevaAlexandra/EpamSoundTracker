package com.lavrente.soundtrack.command;

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
    private static final String TRACK_LIST_ATTR = "track_list";


    @Override
    public String execute(SessionRequestContent sessionRequestContent) {
        TrackLogic trackLogic=new TrackLogic();
        List<Track> trackList=new ArrayList<>();
        try{
            trackList=trackLogic.lastTracks();
        }catch (LogicException e){
            LOG.error("Can't find last ordered tracks",e);
        }
        sessionRequestContent.setRequestAttribute(TRACK_LIST_ATTR, trackList);
        return ConfigurationManager.getProperty(ConfigurationManager.MAIN_PATH);
    }
}

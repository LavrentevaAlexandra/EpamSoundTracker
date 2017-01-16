package com.lavrente.soundtrack.command;

import com.lavrente.soundtrack.entity.Track;
import com.lavrente.soundtrack.exception.LogicException;
import com.lavrente.soundtrack.logic.TrackLogic;
import com.lavrente.soundtrack.manager.ConfigurationManager;
import com.lavrente.soundtrack.servlet.SessionRequestContent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 123 on 31.12.2016.
 */
public class IndexCommand extends AbstractCommand {
    private static final Logger LOG = LogManager.getLogger();
    private static final String DEFAULT_LOCALE = "ru_RU";
    private static final String TRACK_LIST_ATTR = "track_list";
    private static final String GENRES_ATTR = "genres";

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
        sessionRequestContent.setSessionAttribute(LOCALE_ATTRIBUTE, DEFAULT_LOCALE);
        return ConfigurationManager.getProperty(ConfigurationManager.HOME_PATH);
    }
}

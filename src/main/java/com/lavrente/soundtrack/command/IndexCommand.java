package com.lavrente.soundtrack.command;

import com.lavrente.soundtrack.exception.LogicException;
import com.lavrente.soundtrack.logic.GenreLogic;
import com.lavrente.soundtrack.manager.ConfigurationManager;
import com.lavrente.soundtrack.servlet.SessionRequestContent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 123 on 31.12.2016.
 */
public class IndexCommand extends AbstractCommand {

    /** The Constant DEFAULT_LOCALE. */
    private static final String DEFAULT_LOCALE = "ru_RU";
    private static final String GENRES_ATTR = "genres";

    @Override
    public String execute(SessionRequestContent sessionRequestContent) {
        GenreLogic genreLogic=new GenreLogic();
        List<String> genreList=new ArrayList<>();
        try {
            genreList= genreLogic.findGenres();
        }catch (LogicException e){
            LOG.error("Exception during genres search",e);
        }
        sessionRequestContent.setSessionAttribute(GENRES_ATTR, genreList);
        sessionRequestContent.setSessionAttribute(LOCALE_ATTRIBUTE, DEFAULT_LOCALE);
        return ConfigurationManager.getProperty(ConfigurationManager.HOME_PATH);
    }
}

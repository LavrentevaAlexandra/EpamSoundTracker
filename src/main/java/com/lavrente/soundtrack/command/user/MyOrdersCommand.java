package com.lavrente.soundtrack.command.user;

import com.lavrente.soundtrack.command.AbstractCommand;
import com.lavrente.soundtrack.entity.Track;
import com.lavrente.soundtrack.entity.User;
import com.lavrente.soundtrack.exception.LogicException;
import com.lavrente.soundtrack.logic.OrderLogic;
import com.lavrente.soundtrack.logic.TrackLogic;
import com.lavrente.soundtrack.manager.ConfigurationManager;
import com.lavrente.soundtrack.servlet.SessionRequestContent;

import java.util.List;

/**
 * Created by 123 on 26.01.2017.
 */
public class MyOrdersCommand extends AbstractCommand {
    private static final String TRACK_LIST_ATTR = "track_list";
    private static final String IS_MY_ORDERS = "is_my_orders";

    /**
     * Execute.
     *
     * @param sessionRequestContent the session request content
     * @return the string
     */
    @Override
    public String execute(SessionRequestContent sessionRequestContent) {
        String page;
        String logined = (String) sessionRequestContent.getSessionAttribute(IS_LOGIN);
        if (logined != null && Boolean.valueOf(logined)) {
            List<Track> myTracks;
            User user = (User) sessionRequestContent.getSessionAttribute(USER_ATTRIBUTE);
            OrderLogic orderLogic = new OrderLogic();
            try {
                myTracks = orderLogic.findMyTracks(user.getId());
                sessionRequestContent.setSessionAttribute(TRACK_LIST_ATTR, myTracks);
                sessionRequestContent.setRequestAttribute(IS_MY_ORDERS, true);
                page = ConfigurationManager.getProperty(ConfigurationManager.MY_ORDERS_PATH);
            } catch (LogicException e) {
                LOG.error("Exception during my tracks search", e);
                page = redirectToErrorPage(sessionRequestContent, e);
            }
        } else {
            page = ConfigurationManager.getProperty(ConfigurationManager.HOME_PATH);
        }
        return page;
    }
}

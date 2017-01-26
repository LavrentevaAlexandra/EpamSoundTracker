package com.lavrente.soundtrack.command.user;

import com.lavrente.soundtrack.command.AbstractCommand;
import com.lavrente.soundtrack.entity.User;
import com.lavrente.soundtrack.exception.LogicException;
import com.lavrente.soundtrack.logic.OrderLogic;
import com.lavrente.soundtrack.logic.UserLogic;
import com.lavrente.soundtrack.manager.ConfigurationManager;
import com.lavrente.soundtrack.manager.MessageManager;
import com.lavrente.soundtrack.servlet.SessionRequestContent;

/**
 * Created by 123 on 26.01.2017.
 */
public class BuyTrackCommand extends AbstractCommand {
    private final String TRACK_ID_ATTR = "track_id";
    private final String PRICE_ATTR = "price";

    @Override
    public String execute(SessionRequestContent sessionRequestContent) {
        String page;
        String logined = (String) sessionRequestContent.getSessionAttribute(IS_LOGIN);
        if (logined != null && Boolean.valueOf(logined)) {
            User user = (User) sessionRequestContent.getSessionAttribute(USER_ATTRIBUTE);
            int trackId = Integer.valueOf(sessionRequestContent.getRequestParameter(TRACK_ID_ATTR));
            double price = Double.valueOf(sessionRequestContent.getRequestParameter(PRICE_ATTR));
            price-=price*user.getDiscount()/100;
            OrderLogic orderLogic = new OrderLogic();
            try {
                if (!orderLogic.isOrdered(user.getId(), trackId)) {
                    if (user.getCash() - price >= 0) {
                        orderLogic.addOrder(trackId, price, user);
                        user.setCash(user.getCash() - price);
                        sessionRequestContent.setSessionAttribute(USER_ATTRIBUTE, user);
                        sessionRequestContent.setRequestAttribute(SUCCESS, messageManager.getProperty(MessageManager.ORDER_SUCCESS));
                        page = ConfigurationManager.getProperty(ConfigurationManager.SHOW_MY_ORDERS_PATH);

                    } else {
                        sessionRequestContent.setRequestAttribute(ERROR, messageManager.getProperty(MessageManager.ORDER_ERROR));
                        page = ConfigurationManager.getProperty((String) sessionRequestContent.getSessionAttribute(CUR_PAGE_ATTR));
                    }
                } else {
                    sessionRequestContent.setRequestAttribute(SUCCESS, messageManager.getProperty(MessageManager.ODER_DOWNLOAD));
                    page = ConfigurationManager.getProperty(ConfigurationManager.SHOW_MY_ORDERS_PATH);
                }
            } catch (LogicException e) {
                LOG.error("Exception during track purchase");
                page = redirectToErrorPage(sessionRequestContent, e);
            }
        } else {
            page = ConfigurationManager.getProperty(ConfigurationManager.HOME_PATH);
        }
        return page;
    }
}

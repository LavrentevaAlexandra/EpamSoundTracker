package com.lavrente.soundtrack.manager;

import java.util.ResourceBundle;

/**
 * Created by 123 on 02.01.2017.
 */
public class MessageManager {

    public final static String ADD_COMMENT_EMPTY="message.add.comment.empty";
    public final static String ADD_COMMENT_ERROR="message.add.comment";
    public final static String ADD_TRACK_DATA_ERROR="message.add.track.data.error";
    public final static String ADD_TRACK_ERROR="message.add.track.error";
    public final static String ADD_TRACK_SUCCESS="message.add.track.success";
    public final static String CHANGE_CASH_CARD_ERROR="message.change.cash.card";
    public final static String CHANGE_CASH_ERROR="message.change.cash";
    public final static String CHANGE_CARD_ERROR="message.change.card.error";
    public final static String CHANGE_EMAIL_ERROR="message.change.email.error";
    public final static String CHANGE_LOGIN_ERROR="message.change.login.error";
    public final static String CHANGE_PASS_EQUAL_ERROR="message.change.pass.equal";
    public final static String CHANGE_PASS_EQUAL_NEW_ERROR="message.change.pass.equal.new";
    public final static String CHANGE_PASS_NEW_ERROR="message.change.pass.new";
    public final static String CHANGE_SUCCESS="message.change.success";
    public final static String CHANGE_TRACK_ARTIST_ERROR="message.change.track.artist.error";
    public final static String CHANGE_TRACK_GENRE_ERROR="message.change.track.genre.error";
    public final static String CHANGE_TRACK_NAME_ERROR="message.change.track.name.error";
    public final static String CHANGE_TRACK_PRICE_ERROR="message.change.track.price.error";
    public final static String DELETE_TRACK_SUCCESS="message.delete.track.success";
    public final static String LOGIN_ERROR="message.login.error";
    public final static String NOT_UNIQUE_EMAIL="message.signup.unique.email";
    public final static String NOT_UNIQUE_LOGIN="message.signup.unique.login";
    public final static String ODER_DOWNLOAD="message.order.download";
    public final static String ORDER_ERROR="message.order.money";
    public final static String ORDER_SUCCESS="message.order.success";
    public final static String SET_BONUS_ERROR="message.bonus.error";
    public final static String SET_BONUS_SUCCESS="message.bonus.success";
    public final static String SIGNUP_ERROR="message.signup.error";
    public final static String TRACK_RECOVER_SUCCESS="message.track.recover.success";

    private  ResourceBundle resourceBundle ;

    public MessageManager() {
       resourceBundle=ResourceBundleType.RU_RU.getResourceBundle();
    }

    public String getProperty(String key) {
        return resourceBundle.getString(key);
    }

    public void setCurrentLocale(String language) {
        if("en_US".equals(language)){
            resourceBundle= ResourceBundleType.EN_US.getResourceBundle();
        }else {
            resourceBundle= ResourceBundleType.RU_RU.getResourceBundle();
        }
    }
}

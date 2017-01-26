package com.lavrente.soundtrack.manager;

import java.util.ResourceBundle;

/**
 * Created by 123 on 02.01.2017.
 */
public class MessageManager {

    /** The Constant ADD_COMMENT_EMPTY. */
    public final static String ADD_COMMENT_EMPTY="message.add.comment.empty";

    /** The Constant ADD_COMMENT_ERROR. */
    public final static String ADD_COMMENT_ERROR="message.add.comment";

    /** The Constant ADD_TRACK_DATA_ERROR. */
    public final static String ADD_TRACK_DATA_ERROR="message.add.track.data.error";

    /** The Constant ADD_TRACK_ERROR. */
    public final static String ADD_TRACK_ERROR="message.add.track.error";

    /** The Constant ADD_TRACK_SUCCESS. */
    public final static String ADD_TRACK_SUCCESS="message.add.track.success";

    /** The Constant CHANGE_CASH_CARD_ERROR. */
    public final static String CHANGE_CASH_CARD_ERROR="message.change.cash.card";

    /** The Constant CHANGE_CASH_ERROR. */
    public final static String CHANGE_CASH_ERROR="message.change.cash";

    /** The Constant CHANGE_CARD_ERROR. */
    public final static String CHANGE_CARD_ERROR="message.change.card.error";

    /** The Constant CHANGE_EMAIL_ERROR. */
    public final static String CHANGE_EMAIL_ERROR="message.change.email.error";

    /** The Constant CHANGE_LOGIN_ERROR. */
    public final static String CHANGE_LOGIN_ERROR="message.change.login.error";

    /** The Constant CHANGE_PASS_EQUAL_ERROR. */
    public final static String CHANGE_PASS_EQUAL_ERROR="message.change.pass.equal";

    /** The Constant CHANGE_PASS_EQUAL_NEW_ERROR. */
    public final static String CHANGE_PASS_EQUAL_NEW_ERROR="message.change.pass.equal.new";

    /** The Constant CHANGE_PASS_NEW_ERROR. */
    public final static String CHANGE_PASS_NEW_ERROR="message.change.pass.new";

    /** The Constant CHANGE_SUCCESS. */
    public final static String CHANGE_SUCCESS="message.change.success";

    /** The Constant CHANGE_TRACK_ARTIST_ERROR. */
    public final static String CHANGE_TRACK_ARTIST_ERROR="message.change.track.artist.error";

    /** The Constant CHANGE_TRACK_GENRE_ERROR. */
    public final static String CHANGE_TRACK_GENRE_ERROR="message.change.track.genre.error";

    /** The Constant CHANGE_TRACK_NAME_ERROR. */
    public final static String CHANGE_TRACK_NAME_ERROR="message.change.track.name.error";

    /** The Constant CHANGE_TRACK_PRICE_ERROR. */
    public final static String CHANGE_TRACK_PRICE_ERROR="message.change.track.price.error";

    /** The Constant DELETE_TRACK_SUCCESS. */
    public final static String DELETE_TRACK_SUCCESS="message.delete.track.success";

    /** The Constant DOWNLOAD_ERROR. */
    public final static String DOWNLOAD_ERROR="message.download.error";

    /** The Constant LOGIN_ERROR. */
    public final static String LOGIN_ERROR="message.login.error";

    /** The Constant NOT_UNIQUE_EMAIL. */
    public final static String NOT_UNIQUE_EMAIL="message.signup.unique.email";

    /** The Constant NOT_UNIQUE_LOGIN. */
    public final static String NOT_UNIQUE_LOGIN="message.signup.unique.login";

    /** The Constant ODER_DOWNLOAD. */
    public final static String ODER_DOWNLOAD="message.order.download";

    /** The Constant ORDER_ERROR. */
    public final static String ORDER_ERROR="message.order.money";

    /** The Constant ORDER_SUCCESS. */
    public final static String ORDER_SUCCESS="message.order.success";

    /** The Constant SET_BONUS_ERROR. */
    public final static String SET_BONUS_ERROR="message.bonus.error";

    /** The Constant SET_BONUS_SUCCESS. */
    public final static String SET_BONUS_SUCCESS="message.bonus.success";

    /** The Constant SIGNUP_ERROR. */
    public final static String SIGNUP_ERROR="message.signup.error";

    /** The Constant TRACK_RECOVER_SUCCESS. */
    public final static String TRACK_RECOVER_SUCCESS="message.track.recover.success";

    /** The resource bundle. */
    private  ResourceBundle resourceBundle ;

    /**
     * Instantiates a new message manager.
     */
    public MessageManager() {
        resourceBundle=ResourceBundleType.RU_RU.getResourceBundle();
    }

    /**
     * Gets the property.
     *
     * @param key the key
     * @return the property
     */
    public String getProperty(String key) {
        return resourceBundle.getString(key);
    }

    /**
     * Sets the current locale.
     *
     * @param language the new current locale
     */
    public void setCurrentLocale(String language) {
        if("en_US".equals(language)){
            resourceBundle= ResourceBundleType.EN_US.getResourceBundle();
        }else {
            resourceBundle= ResourceBundleType.RU_RU.getResourceBundle();
        }
    }
}

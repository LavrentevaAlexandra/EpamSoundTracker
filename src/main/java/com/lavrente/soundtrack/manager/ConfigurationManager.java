package com.lavrente.soundtrack.manager;

import java.util.ResourceBundle;

/**
 * Created by 123 on 30.11.2016.
 */
public class ConfigurationManager {

    /** The Constant ADD_TRACK_PATH. */
    public static final String ADD_TRACK_PATH = "path.page.add.track";

    /** The Constant CHANGE_PATH. */
    public static final String CHANGE_PATH = "path.page.change";

    /** The Constant CHANGE_PASS_PATH. */
    public static final String CHANGE_PASS_PATH = "path.page.pass";

    /** The Constant ERROR_PATH. */
    public static final String ERROR_PATH = "path.page.error";

    /** The Constant HOME_PATH. */
    public static final String HOME_PATH = "path.page.home";

    /** The Constant LOGIN_PATH. */
    public static final String LOGIN_PATH = "path.page.login";

    /** The Constant MAIN_PATH. */
    public static final String MAIN_PATH = "path.page.main";

    /** The Constant MONEY_PATH. */
    public static final String MONEY_PATH = "path.page.add.funds";

    /** The Constant MY_ORDERS_PATH. */
    public static final String MY_ORDERS_PATH = "path.page.my_orders";

    /** The Constant SHOW_MY_ORDERS_PATH. */
    public static final String SHOW_MY_ORDERS_PATH = "path.page.orders";

    /** The Constant PROFILE_PATH. */
    public static final String PROFILE_PATH = "path.page.profile";

    /** The Constant SET_BONUS_PATH. */
    public static final String SET_BONUS_PATH = "path.page.bonus";

    /** The Constant SHOW_USERS_PATH. */
    public static final String SHOW_USERS_PATH = "path.page.show_users";

    /** The Constant SIGNUP_PATH. */
    public static final String SIGNUP_PATH = "path.page.signup";

    /** The Constant TRACK_INFO_PATH. */
    public static final String TRACK_INFO_PATH = "path.page.track_info";

    /** The Constant TRACK_DELETED_PATH. */
    public static final String TRACK_DELETED_PATH = "path.page.deleted";

    /** The Constant TRACK_EDIT_PATH. */
    public static final String TRACK_EDIT_PATH = "path.page.track_edit";

    /** The Constant TRACK_RECOVER_PATH. */
    public static final String TRACK_RECOVER_PATH = "path.page.recover";

    /** The Constant resourceBundle. */
    private final static ResourceBundle resourceBundle = ResourceBundle.getBundle("config");

    /**
     * Gets the property.
     *
     * @param key the key
     * @return the property
     */
    public static String getProperty(String key) {
        return resourceBundle.getString(key);
    }
}

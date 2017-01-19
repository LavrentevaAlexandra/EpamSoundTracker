package com.lavrente.soundtrack.manager;

import java.util.ResourceBundle;

/**
 * Created by 123 on 30.11.2016.
 */
public class ConfigurationManager {
    public static final String HOME_PATH = "path.page.home";
    public static final String MAIN_PATH = "path.page.main";
    public static final String LOGIN_PATH = "path.page.login";
    public static final String ERROR_PATH = "path.page.error";
    public static final String SIGNUP_PATH = "path.page.signup";
    public static final String CHANGE_PATH = "path.page.change";
    public static final String CHANGE_PASS_PATH = "path.page.pass";
    public static final String PROFILE_PATH = "path.page.profile";
    public static final String MONEY_PATH = "path.page.add.funds";
    public static final String ADD_TRACK_PATH = "path.page.add.track";
    public static final String TRACK_INFO_PATH = "path.page.track_info";

    private final static ResourceBundle resourceBundle = ResourceBundle.getBundle("config");

    public static String getProperty(String key) {
        return resourceBundle.getString(key);
    }
}

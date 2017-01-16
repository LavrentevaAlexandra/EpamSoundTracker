package com.lavrente.soundtrack.manager;

import java.util.ResourceBundle;

/**
 * Created by 123 on 02.01.2017.
 */
public class DBManager {
    public static final String DB_POOL_SIZE = "db.poolsize";
    public static final String DB_URL = "db.url";
    public static final String DB_USER = "db.user";
    public static final String DB_PASSWORD = "db.password";

    private final static ResourceBundle resourceBundle = ResourceBundle.getBundle("database");

    public static String getProperty(String key) {
        return resourceBundle.getString(key);
    }
}

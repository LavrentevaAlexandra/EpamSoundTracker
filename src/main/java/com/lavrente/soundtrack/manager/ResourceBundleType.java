package com.lavrente.soundtrack.manager;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Created by 123 on 14.01.2017.
 */
public enum ResourceBundleType {
    EN_US("en","US"),
    RU_RU("ru","RU");

    private ResourceBundle resourceBundle;

    ResourceBundleType(String curlocale, String country) {
        resourceBundle=ResourceBundle.getBundle("content", new Locale(curlocale,country));
    }

    public ResourceBundle getResourceBundle(){
        return resourceBundle;
    }

    public static void main(String[] args) {
        ResourceBundle resourceBundle22= ResourceBundleType.EN_US.getResourceBundle();
        String s=resourceBundle22.getString("menu.brand");
        ResourceBundle resourceBundle23= ResourceBundleType.RU_RU.getResourceBundle();
    }
}

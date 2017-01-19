package com.lavrente.soundtrack.entity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by 123 on 02.01.2017.
 */
public class Comment {
    private String dateTime;
    private int userId;
    private String userLogin;
    private String text;

    public Comment( int userId, String text) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime now=LocalDateTime.now();
        this.dateTime = now.format(formatter);
        this.userId = userId;
        this.text = text;
    }

    public Comment( String userLogin,String dateTime, String text) {
        this.dateTime = dateTime;
        this.userLogin = userLogin;
        this.text = text;
    }
    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public int getUserId() {
        return userId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}

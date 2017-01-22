package com.lavrente.soundtrack.entity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by 123 on 02.01.2017.
 */
public class Comment extends Entity{
    private String dateTime;
    private int userId;
    private int audioTrackId;
    private String userLogin;
    private String text;


    public Comment( int userId,int audioTrackId, String text) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime now=LocalDateTime.now();
        this.dateTime = now.format(formatter);
        this.userId = userId;
        this.text = text;
        this.audioTrackId=audioTrackId;
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

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getUserId() {
        return userId;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getAudioTrackId() {
        return audioTrackId;
    }

    public void setAudioTrackId(int audioTrackId) {
        this.audioTrackId = audioTrackId;
    }
}

package com.lavrente.soundtrack.entity;

import java.time.LocalDateTime;

/**
 * Created by 123 on 02.01.2017.
 */
public class Comment {
    private LocalDateTime dateTime;
    private int userId;
    private int trackId;
    private String text;

    public Comment( int userId, int trackId, String text) {
        this.dateTime = LocalDateTime.now();
        this.userId = userId;
        this.trackId = trackId;
        this.text = text;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public int getUserId() {
        return userId;
    }

    public int getTrackId() {
        return trackId;
    }

    public void setTrackId(int trackId) {
        this.trackId = trackId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}

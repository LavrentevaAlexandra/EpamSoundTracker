package com.lavrente.soundtrack.entity;

/**
 * Created by 123 on 02.01.2017.
 */
public class Track extends Entity {
    private int id;
    private String name;
    private String artist;
    private double price;
    private String genre;

    public Track(int id, String name, String artist, double price, String genre) {
        this.id = id;
        this.name = name;
        this.artist = artist;
        this.price = price;
        this.genre = genre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }
}

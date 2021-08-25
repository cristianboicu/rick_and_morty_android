package com.example.rickandmortyapi;

import java.io.Serializable;

public class Character implements Serializable {
    private int id;
    private String name;
    private String origin;
    private String location;
    private String episode;
    private String status;
    private String imgUrl;

    public Character(int id, String name, String origin, String location, String episode, String status, String imgUrl) {
        this.id = id;
        this.name = name;
        this.origin = origin;
        this.episode = episode;
        this.imgUrl = imgUrl;
        this.location = location;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public String getStatus() {
        return status;
    }

    public String getOrigin() {
        return origin;
    }

    public String getEpisode() {
        return episode;
    }

    public String getImgUrl() {
        return imgUrl;
    }
}

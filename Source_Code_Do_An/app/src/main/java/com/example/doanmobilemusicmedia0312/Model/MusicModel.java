package com.example.doanmobilemusicmedia0312.Model;

import java.io.Serializable;

public class MusicModel implements Serializable {
    private String id;
    private String imageUrl;
    private String dateRelease;
    private String genre;
    private String length;
    private String songName;
    private String singer;
    private String sourceUrl;
    private long views;


    public MusicModel(){

    }

    public MusicModel(String id,String imageUrl, String dateRelease, String genre, String length, String songName, String singer, String sourceUrl, int views) {
        this.id = id;
        this.imageUrl = imageUrl;
        this.dateRelease = dateRelease;
        this.genre = genre;
        this.length = length;
        this.songName = songName;
        this.singer = singer;
        this.sourceUrl = sourceUrl;
        this.views = views;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDateRelease() {
        return dateRelease;
    }

    public void setDateRelease(String dateRelease) {
        this.dateRelease = dateRelease;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public String getSinger() {
        return singer;
    }

    public void setSinger(String singer) {
        this.singer = singer;
    }

    public String getSourceUrl() {
        return sourceUrl;
    }

    public void setSourceUrl(String sourceUrl) {
        this.sourceUrl = sourceUrl;
    }

    public long getViews() {
        return views;
    }

    public void setViews(long views) {
        this.views = views;
    }
}

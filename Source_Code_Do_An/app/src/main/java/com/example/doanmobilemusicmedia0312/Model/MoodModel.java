package com.example.doanmobilemusicmedia0312.Model;

public class MoodModel {
    String musicName, musicSinger;
    String img, Id;

    public String getMusicName(){
        return musicName;
    }

    public void setMusicName(String musicName){
        this.musicName = musicName;
    }

    public String getMusicSinger(){
        return musicSinger;
    }

    public void setMusicSinger(String musicSinger){
        this.musicSinger = musicSinger;
    }

    public String getImg(){
        return img;
    }
    public String getId() {return Id;}

    public void setId(String id) {this.Id = id;}

    public void setImg(String img){
        this.img = img;
    }
}

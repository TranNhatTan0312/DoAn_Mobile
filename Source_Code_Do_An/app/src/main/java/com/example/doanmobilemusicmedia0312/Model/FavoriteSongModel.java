package com.example.doanmobilemusicmedia0312.Model;

public class FavoriteSongModel {
    String musicName, musicSinger, songId, img , length;


    public String getSongId() {
        return songId;
    }

    public void setSongId(String songId) {
        this.songId = songId;
    }

    public String getMusicName(){
        return musicName;
    }

    public void setMusicName(String musicName){
        this.musicName = musicName;
    }
    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }
    public String getMusicSinger(){
        return musicSinger;
    }

    public void setMusicSinger(String musicSinger){
        this.musicSinger = musicSinger;
    }

    public String getImg(){
        return this.img;
    }

    public void setImg(String img){
        this.img = img;
    }

}

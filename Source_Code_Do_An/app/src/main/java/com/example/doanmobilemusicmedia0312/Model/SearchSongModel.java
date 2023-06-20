package com.example.doanmobilemusicmedia0312.Model;

public class SearchSongModel {
    String musicName, musicNum, songId, img;


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

    public String getMusicNum(){
        return musicNum;
    }

    public void setMusicNum(String musicNum){
        this.musicNum = musicNum;
    }

    public String getImg(){
        return this.img;
    }

    public void setImg(String img){
        this.img = img;
    }
}


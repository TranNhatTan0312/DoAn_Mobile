package com.example.doanmobilemusicmedia0312.Model;

public class SearchSongModel {
    String musicName, musicNum, songId;
    int img;

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

    public int getImg(){
        return img;
    }

    public void setImg(int img){
        this.img = img;
    }
}


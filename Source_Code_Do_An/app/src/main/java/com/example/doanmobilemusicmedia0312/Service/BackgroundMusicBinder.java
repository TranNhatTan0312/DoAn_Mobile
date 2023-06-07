package com.example.doanmobilemusicmedia0312.Service;

import android.os.Binder;

import com.example.doanmobilemusicmedia0312.Model.MusicModel;

import java.util.List;

public class BackgroundMusicBinder extends Binder {
    private BackgroundMusicService service;

    public BackgroundMusicBinder(BackgroundMusicService service) {
        this.service = service;
    }

    public BackgroundMusicService getService() {
        return service;
    }

//    public void play() {
//        service.play();
//    }
//
//    public void pause() {
//        service.pause();
//    }
//
//    public void stop() {
//        service.stop();
//    }
//
//    public boolean isPlaying() {
//        return service.isPlaying();
//    }
//
//    public void setSong(int index) {
//        service.setSong(index);
//    }
//
//    public int getCurrentSongIndex() {
//        return service.getCurrentSongIndex();
//    }
//
//    public List<MusicModel> getSongList() {
//        return service.getSongList();
//    }
}

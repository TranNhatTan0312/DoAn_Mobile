package com.example.doanmobilemusicmedia0312.Service;

import static android.content.Intent.getIntent;

import android.app.ActivityManager;
import android.app.Service;
import android.content.Intent;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.example.doanmobilemusicmedia0312.BroadcastReceiver.MusicBroadcastReceiver;
import com.example.doanmobilemusicmedia0312.Model.MusicModel;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class BackgroundMusicService extends Service implements MediaPlayer.OnCompletionListener {
    public static final String ACTION_PLAY = "ACTION_PLAY";
    public static final String ACTION_STOP = "ACTION_STOP";
    public static final String ACTION_PAUSED = "ACTION_PAUSED";
    public static final String ACTION_LOOP = "ACTION_LOOP";
    public static final String ACTION_NEXT = "ACTION_NEXT";
    public static final String ACTION_PREVIOUS  = "ACTION_PREVIOUS";
    public static final String ACTION_RANDOM = "ACTION_RANDOM";
    public static final String EXTRA_SONG_LIST = "com.example.musicplayer.extra.SONG_LIST";
    public static final String EXTRA_SONG_INDEX = "com.example.musicplayer.extra.SONG_INDEX";

    public static final String ACTION_UPDATE_PROGRESS = "com.example.musicplayer.action.UPDATE_PROGRESS";
    public static final String EXTRA_CURRENT_POSITION = "com.example.musicplayer.extra.CURRENT_POSITION";

    private MediaPlayer mediaPlayer;
    private List<MusicModel> songList;
    private int songIndex = -1;
    private boolean isLooping = false;
    private final IBinder binder = new BackgroundMusicBinder(this);
    private final Handler handler = new Handler();
//    public static String songUrl;
//
//    String url = "";
//    private static BackgroundMusicService service = null;
//
//
//
//    public static BackgroundMusicService getInstance() {
//        if (service == null) {
//            service = new BackgroundMusicService();
//        }
//        return service;
//    }
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }
    @Override
    public void onCreate() {
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setOnCompletionListener(this);
//        mediaPlayer.setAudioAttributes(
//                new AudioAttributes.Builder()
//                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
//                        .setUsage(AudioAttributes.USAGE_MEDIA)
//                        .build()
//        );

    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(intent != null && intent.getAction() != null){
            switch (intent.getAction()){
                case ACTION_PLAY:
                    handlePlayAction(intent);
                    break;
                case ACTION_PAUSED:
                    handlePauseAction();
                    break;
                case ACTION_NEXT:
                    handleNextAction();
                    break;
                case ACTION_PREVIOUS:
                    handlePreviousAction();
                    break;
                case ACTION_STOP:
                    handleStopAction();
                    break;
                case ACTION_LOOP:
                    handleLoopAction();
                    break;
            }
        }
        super.onStartCommand(intent, flags,startId);

         return START_STICKY;
    }
//    private void updateProgress() {
//        if (mediaPlayer.isPlaying()) {
//            int currentPosition = mediaPlayer.getCurrentPosition();
//            Intent intent = new Intent(ACTION_UPDATE_PROGRESS);
//            intent.putExtra(EXTRA_CURRENT_POSITION, currentPosition);
//            sendBroadcast(intent);
//            handler.postDelayed(this::updateProgress, 1000);
//        }
//    }
    private void handleStopAction() {
        mediaPlayer.stop();
        stopSelf();
    }

    private void handleLoopAction() {
        isLooping = !isLooping;
    }

    private void handlePreviousAction() {
        if(songIndex > 0){
            songIndex--;
            playSong();
        }else{
            songIndex = songList.size()-1;
            playSong();
        }
    }

    private void handleNextAction() {
        if(songList != null){
            if(songIndex < songList.size()-1){
                songIndex++;
                playSong();
            }else{
                songIndex = 0;
                playSong();
            }
        }

    }

    private void playSong() {
        MusicModel song = songList.get(songIndex);
        mediaPlayer.reset();
        try{
            mediaPlayer.setDataSource(song.getSourceUrl());
            mediaPlayer.prepare();
            mediaPlayer.start();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    private void handlePauseAction() {
        if(mediaPlayer.isPlaying()){
            mediaPlayer.pause();
        }
    }

    private void handlePlayAction(Intent intent) {
        List<MusicModel> songList = (List<MusicModel>)intent.getSerializableExtra(EXTRA_SONG_LIST);
        int songIndex = intent.getIntExtra(EXTRA_SONG_INDEX, -1);
        if (songList != null && songIndex >= 0 && songIndex < songList.size()) {
            this.songList = songList;
            this.songIndex = songIndex;

            setBroadcastUpdateUI();

            playSong();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mediaPlayer.release();
        mediaPlayer = null;

    }


    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {

        if(songList != null){
            if(isLooping){
                mediaPlayer.seekTo(0);
                mediaPlayer.start();
            }else{
                if(songList.size() > 1){
                    handleNextAction();
                    setBroadcastUpdateUI();
                }else{
                    mediaPlayer.stop();
                }
            }
        }

    }
    // Public methods for controlling playback
    public void pause() {
        handlePauseAction();
    }

    public void previous() {
        handlePreviousAction();
    }

    public void next() {
        handleNextAction();
    }

    public void stop() {
        handleStopAction();
    }
    public void resume() {
        if (mediaPlayer != null && !mediaPlayer.isPlaying()) {
            mediaPlayer.start();
        }
    }
    public void setLooping(boolean isLooping) {
        this.isLooping = isLooping;
    }

    public boolean isPlaying() {
        return mediaPlayer.isPlaying();
    }

    public int getCurrentPosition() {
        return mediaPlayer.getCurrentPosition();
    }

    public int getDuration() {
        return mediaPlayer.getDuration();
    }
    public void seekTo(int position) {
        if(mediaPlayer.isPlaying())
            mediaPlayer.seekTo(position);
    }

    public MusicModel getCurrentSong() {
        return songList.get(songIndex);
    }

    public void setBroadcastUpdateUI(){
        if(songList != null){
            Intent broadcastIntent = new Intent(MusicBroadcastReceiver.ACTION_SONG_CHANGED);
            broadcastIntent.putExtra(MusicBroadcastReceiver.EXTRA_SONG, songList.get(songIndex));
            sendBroadcast(broadcastIntent);
        }

    }
//    public void play() {
//
//    }
//
//    public void pause() {
//    }
//
//    public void stop() {
//    }
//
//    public boolean isPlaying() {
//    }
//
//    public int getCurrentSongIndex() {
//    }
//
//    public void setSong(int index) {
//    }
//
//    public List<MusicModel> getSongList() {
//    }
}

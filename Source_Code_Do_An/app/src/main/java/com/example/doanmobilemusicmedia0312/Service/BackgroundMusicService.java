package com.example.doanmobilemusicmedia0312.Service;

import static android.content.Intent.getIntent;

import android.app.ActivityManager;
import android.app.Service;
import android.content.Intent;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class BackgroundMusicService extends Service {
    public static MediaPlayer mediaPlayer;
    public static String songUrl;

    String url = "";
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
        return null;
    }
    @Override
    public void onCreate() {
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioAttributes(
                new AudioAttributes.Builder()
                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                        .setUsage(AudioAttributes.USAGE_MEDIA)
                        .build()
        );

    }

    public void setLoop(boolean value){
        mediaPlayer.setLooping(value);
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        try{
            songUrl = intent.getStringExtra("sourceUrl");
            boolean loop = intent.getBooleanExtra("loop", false);
            mediaPlayer.stop();
            mediaPlayer.reset();

            mediaPlayer.setDataSource(songUrl);
            mediaPlayer.setLooping(loop);
            mediaPlayer.prepare();
            mediaPlayer.start();
        }catch (Exception ex){

        }
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        mediaPlayer.stop();
        mediaPlayer.release();

    }


}

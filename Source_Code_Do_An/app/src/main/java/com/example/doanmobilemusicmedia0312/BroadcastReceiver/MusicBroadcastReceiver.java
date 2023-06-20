package com.example.doanmobilemusicmedia0312.BroadcastReceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import com.example.doanmobilemusicmedia0312.Interface.IMusicActivity;
import com.example.doanmobilemusicmedia0312.Model.MusicModel;
import com.example.doanmobilemusicmedia0312.Model.SearchSongModel;

public class MusicBroadcastReceiver extends BroadcastReceiver {
    public static final String ACTION_SONG_CHANGED = "SONG_CHANGED";
    public static final String EXTRA_SONG = "EXTRA_SONG";
    private IMusicActivity activity;

    public MusicBroadcastReceiver(IMusicActivity activity) {
        this.activity = activity;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (ACTION_SONG_CHANGED.equals(intent.getAction())) {
            MusicModel song = (MusicModel) intent.getSerializableExtra(EXTRA_SONG);
            activity.updateUI(song);
        }
    }

}
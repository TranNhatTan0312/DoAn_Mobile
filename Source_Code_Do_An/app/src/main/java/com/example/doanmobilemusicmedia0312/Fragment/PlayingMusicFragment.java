package com.example.doanmobilemusicmedia0312.Fragment;

import static android.content.ContentValues.TAG;
import static android.content.Context.ACTIVITY_SERVICE;
import static androidx.core.content.ContextCompat.getSystemService;

import static com.google.android.gms.tasks.Tasks.await;

import android.app.ActivityManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.IBinder;
import android.util.Log;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;

import com.example.doanmobilemusicmedia0312.Adapter.PlayMusicAdapter;
import com.example.doanmobilemusicmedia0312.Interface.IToolbarHandler;
import com.example.doanmobilemusicmedia0312.Model.MusicModel;
import com.example.doanmobilemusicmedia0312.R;
import com.example.doanmobilemusicmedia0312.Service.BackgroundMusicService;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.IOException;
import java.net.URL;


public class PlayingMusicFragment extends Fragment {
    private static final String SHARED_PREFERENCES_NAME = "song_pref";
    private ImageView moreOption, back, playIcon, logoImage, loopMusic;
    private SeekBar musicTimerSeekBar;
    boolean isLoop;
    MediaPlayer mediaPlayer;
    MusicModel music;
    String song_id;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    DocumentReference docRef;
    SharedPreferences pref;
    SharedPreferences.Editor pref_editor;

    private static IToolbarHandler toolbarListener;
    public PlayingMusicFragment(String song_id) {

        this.song_id = song_id;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        music = new MusicModel();
        pref = getContext().getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        isLoop = pref.getBoolean("loop", false);

    }

    public void setSongId(String song_id){
        this.song_id = song_id;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_playing_music, container, false);

        startMusic();
        addControls(view);
        addEvents();
        // Inflate the layout for this fragment
        return view;
    }

    private void addControls(View view) {

        moreOption = (ImageView)view.findViewById(R.id.btnOption);
        back = (ImageView)view.findViewById(R.id.btnBack);
        playIcon = (ImageView) view.findViewById(R.id.playIcon);
        logoImage = (ImageView) view.findViewById(R.id.imgMusicBackground);
        loopMusic = (ImageView) view.findViewById(R.id.loopMusic);
        musicTimerSeekBar = (SeekBar) view.findViewById(R.id.musicTimerSeekBar);

        setInitValue();

    }

    private void setInitValue() {
        if(isLoop){ loopMusic.setImageResource(R.drawable.reload_icon_2);}
        else{loopMusic.setImageResource(R.drawable.reload_icon);}

    }

    private void addEvents() {

        moreOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toolbarListener.onMoreOptionSongClick();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toolbarListener.onBackButtonClick();
            }
        });

        playIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer = BackgroundMusicService.mediaPlayer;
                if(mediaPlayer.isPlaying()){
                    mediaPlayer.pause();
                    playIcon.setImageResource(R.drawable.icon_play_music);
                }else{
                    mediaPlayer.start();
                    playIcon.setImageResource(R.drawable.playing_icon);
                }
            }
        });

        loopMusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isLoop){
                    isLoop = false;
                    loopMusic.setImageResource(R.drawable.reload_icon);
                    pref_editor = pref.edit();
                    pref_editor.putBoolean("loop",false);
                    pref_editor.apply();
                }
                else{
                    isLoop = true;
                    loopMusic.setImageResource(R.drawable.reload_icon_2);
                    pref_editor = pref.edit();
                    pref_editor.putBoolean("loop",true);
                    pref_editor.apply();
                }
                BackgroundMusicService.mediaPlayer.setLooping(isLoop);

            }
        });

    }

    private void startMusic() {
        docRef = db.collection("songs").document(song_id);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot result = task.getResult();
                    if(result != null){
                        music.setSinger(result.getString("singer"));
                        music.setGenre(result.getString("genre"));
                        music.setLength(result.getString("length"));
                        music.setSongName(result.getString("name"));
                        music.setImageUrl(result.getString("cover_image"));
                        music.setDateRelease(result.getString("date_release"));
                        music.setSourceUrl(result.getString("url"));
                        music.setViews(result.getLong("views"));

                        updateMusicData();

                    }else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "Error getting document: " + task.getException());
                }
            }
        });



    }

    private void updateMusicData() {
        Intent intent = new Intent(getContext(), BackgroundMusicService.class);
        intent.putExtra("sourceUrl",music.getSourceUrl());
        intent.putExtra("loop",isLoop);

        getContext().startService(intent);


    }

    public void setToolbarListener(IToolbarHandler listener){
        this.toolbarListener = listener;
    }




}
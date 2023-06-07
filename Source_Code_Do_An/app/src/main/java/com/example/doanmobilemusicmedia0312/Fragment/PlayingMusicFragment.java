package com.example.doanmobilemusicmedia0312.Fragment;

import static android.content.ContentValues.TAG;
import static android.content.Context.ACTIVITY_SERVICE;
import static androidx.core.content.ContextCompat.getSystemService;

import static com.google.android.gms.tasks.Tasks.await;

import static java.lang.Thread.sleep;

import android.app.ActivityManager;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.media.AudioAttributes;
import android.media.Image;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.doanmobilemusicmedia0312.Adapter.PlayMusicAdapter;
import com.example.doanmobilemusicmedia0312.Interface.IToolbarHandler;
import com.example.doanmobilemusicmedia0312.Model.MusicModel;
import com.example.doanmobilemusicmedia0312.R;
import com.example.doanmobilemusicmedia0312.Service.BackgroundMusicBinder;
import com.example.doanmobilemusicmedia0312.Service.BackgroundMusicService;
import com.example.doanmobilemusicmedia0312.Utils.SongTimeDisplay;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;


public class PlayingMusicFragment extends Fragment {
    private static final String SHARED_PREFERENCES_NAME = "song_pref";
    private ImageView moreOption, back, playIcon, logoImage, loopMusic, preMusic, nextMusic;
    private TextView txtCurrentTime, txtLengthTime;
    private SeekBar musicTimerSeekBar;
    boolean isLoop;
    Thread updateSeekBarThread;
    private BackgroundMusicService musicService;
    private ServiceConnection serviceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            BackgroundMusicBinder binder = (BackgroundMusicBinder) iBinder;
            musicService = binder.getService();

            Handler mHandler = new Handler();
            Runnable mRunnable = new Runnable() {
                @Override
                public void run() {
                    if (musicService != null && isPlaying()) {
                        int currentPosition = musicService.getCurrentPosition();
                        musicTimerSeekBar.setProgress(currentPosition);
                        txtCurrentTime.setText(SongTimeDisplay.display(currentPosition));
                    }
                    mHandler.postDelayed(this, 1000);
                }
            };

// ...

            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    int duration = musicService.getDuration();
                    if (duration > 0) {
                        musicTimerSeekBar.setMax(duration);
                        txtLengthTime.setText(SongTimeDisplay.display(duration));
                        mHandler.postDelayed(mRunnable, 1000);
                    }
                }
            });

        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            musicService = null;
        }
    };

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
//        Intent intent = new Intent(getContext(), BackgroundMusicService.class);

        addControls(view);
        if(!getServiceRunning("com.example.doanmobilemusicmedia0312.Service.BackgroundMusicService", getActivity())){
            startMusic();
        }else{
            Intent intent = new Intent(getContext(), BackgroundMusicService.class);
            getContext().bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);

        }
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
        preMusic = (ImageView) view.findViewById(R.id.preMusic);
        nextMusic = (ImageView)view.findViewById(R.id.nextMusic);
        txtCurrentTime = (TextView)view.findViewById(R.id.txtCurrentTime);
        txtLengthTime = (TextView)view.findViewById(R.id.txtLengthTime);




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

                if(isPlaying()){
                    pauseSong();
                    playIcon.setImageResource(R.drawable.icon_play_music);
                }else{
                    continueSong();
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
                setLooping(isLoop);
            }
        });
        preMusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nextSong();
            }
        });

        nextMusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                previousSong();
            }
        });

        musicTimerSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser && isPlaying()) {
                    setSeekTo(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });



    }

    private void startMusic() {
        docRef = db.collection("songs").document(song_id);
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists()){
                    music.setSinger(documentSnapshot.getString("singer"));
                    music.setGenre(documentSnapshot.getString("genre"));
                    music.setLength(documentSnapshot.getString("length"));
                    music.setSongName(documentSnapshot.getString("name"));
                    music.setImageUrl(documentSnapshot.getString("cover_image"));
                    music.setDateRelease(documentSnapshot.getString("date_release"));
                    music.setSourceUrl(documentSnapshot.getString("url"));
                    music.setViews(documentSnapshot.getLong("views"));

                    playSong(new ArrayList<MusicModel>(Arrays.asList(music)),0);


                }else {
                    Log.d(TAG, "No such document");
                }
            }
        });



    }

//    private void updateMusicData() {
//        Intent intent = new Intent(getContext(), BackgroundMusicService.class);
//        intent.putExtra("sourceUrl",music.getSourceUrl());
//        intent.putExtra("loop",isLoop);
//
//        getContext().startService(intent);
//
//
//    }

    public void setToolbarListener(IToolbarHandler listener){
        this.toolbarListener = listener;
    }

    public void playSong(List<MusicModel> songList, int songIndex) {
        Intent intent = new Intent(getContext(), BackgroundMusicService.class);
        intent.setAction(BackgroundMusicService.ACTION_PLAY);
        intent.putExtra(BackgroundMusicService.EXTRA_SONG_LIST, (ArrayList<MusicModel>) songList);
        intent.putExtra(BackgroundMusicService.EXTRA_SONG_INDEX, songIndex);
        getContext().startService(intent);
        getContext().bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);




    }
    private boolean getServiceRunning(String className, Context context) {
        ActivityManager manager = (ActivityManager) getContext().getSystemService(Context.ACTIVITY_SERVICE);
        ArrayList<ActivityManager.RunningServiceInfo> runningServiceInfo = (ArrayList<ActivityManager.RunningServiceInfo>) manager.getRunningServices(30);
        for (int i = 0; i < runningServiceInfo.size(); i++){
            if (runningServiceInfo.get(i).service.getClassName().toString().equals(className)) {
               return true;
            }
        }
        return false;
    }

    public void pauseSong() {
        musicService.pause();
    }
    public void continueSong() {
        musicService.resume();
    }

    public void previousSong() {
        musicService.previous();
    }

    public void nextSong() {
        musicService.next();
    }

    public void stopSong() {
        musicService.stop();
    }

    public void setLooping(boolean isLooping) {
        musicService.setLooping(isLooping);
    }

    public void setSeekTo(int position){
        musicService.seekTo(position);
    }

    public boolean isPlaying() {
        return musicService.isPlaying();
    }

    public int getCurrentPosition() {
        return musicService.getCurrentPosition();
    }

    public int getDuration() {
        return musicService.getDuration();
    }

    public MusicModel getCurrentSong() {
        return musicService.getCurrentSong();
    }


}
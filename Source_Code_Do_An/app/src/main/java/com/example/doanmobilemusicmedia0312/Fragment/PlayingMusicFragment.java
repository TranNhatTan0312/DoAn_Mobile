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
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.media.AudioAttributes;
import android.media.Image;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
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
import com.example.doanmobilemusicmedia0312.BroadcastReceiver.MusicBroadcastReceiver;
import com.example.doanmobilemusicmedia0312.Interface.IMusicActivity;
import com.example.doanmobilemusicmedia0312.Interface.IToolbarHandler;
import com.example.doanmobilemusicmedia0312.Model.MusicModel;
import com.example.doanmobilemusicmedia0312.Model.SearchSongModel;
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
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;


public class PlayingMusicFragment extends Fragment implements IMusicActivity {
    private static final String SHARED_PREFERENCES_NAME = "song_pref";
    private ImageView moreOption, back, playIcon, logoImage, loopMusic, preMusic, nextMusic;
    private TextView txtCurrentTime, txtLengthTime, txtMusicName, txtSinger;
    private SeekBar musicTimerSeekBar;
    private MusicBroadcastReceiver receiver;

    @Override
    public void onResume() {
        super.onResume();
        receiver = new MusicBroadcastReceiver(this);
        IntentFilter filter = new IntentFilter(MusicBroadcastReceiver.ACTION_SONG_CHANGED);
        getActivity().registerReceiver(receiver, filter);
    }
    @Override
    public void updateUI(MusicModel song) {
        Picasso.get().load(song.getImageUrl()).into(logoImage);
        txtMusicName.setText(song.getSongName());
        txtSinger.setText(song.getSinger());
    }

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
                        playIcon.setImageResource(R.drawable.playing_icon);
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
    boolean isNewSong;
    MusicModel song;
    DocumentReference docRef;
    SharedPreferences pref;
    SharedPreferences.Editor pref_editor;

    private static IToolbarHandler toolbarListener;
    public PlayingMusicFragment(MusicModel song, boolean isNewSong) {
        this.isNewSong = isNewSong;
        this.song = song;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        pref = getContext().getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        isLoop = pref.getBoolean("loop", false);

    }

    public void setSongId(MusicModel song){
        this.song = song;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_playing_music, container, false);
//        Intent intent = new Intent(getContext(), BackgroundMusicService.class);

        addControls(view);


        if(isNewSong == true){
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
        txtMusicName = view.findViewById(R.id.txtMusicName);
        txtSinger = view.findViewById(R.id.txtMusicSinger);

        if(this.song != null){
            updateUI(this.song);
        }

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

        playSong(new ArrayList<MusicModel>(Arrays.asList(song)),0);


    }

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


    public void pauseSong() {
        try{
            musicService.pause();
        }catch (Exception ex){

        }
    }
    public void continueSong() {
        try{
            musicService.resume();
        }catch (Exception ex){

        }
    }

    public void previousSong() {
        try{
            musicService.previous();
        }catch (Exception ex){

        }
    }

    public void nextSong() {
        try{
            musicService.next();
        }catch (Exception ex){

        }
    }

    public void stopSong() {
        try{
            musicService.stop();
        }catch (Exception ex){

        }
    }

    public void setLooping(boolean isLooping) {
        try{
            musicService.setLooping(isLooping);
        }catch (Exception ex){

        }
    }

    public void setSeekTo(int position){
        try{
            musicService.seekTo(position);
        }catch (Exception ex){

        }
    }

    public boolean isPlaying() {
        try{
            return musicService.isPlaying();

        }catch (Exception ex){

        }
        return false;
    }

    public int getCurrentPosition() {
        try{
            return musicService.getCurrentPosition();

        }catch (Exception ex){

        }
        return 0;
    }

    public int getDuration() {
        try{
            return musicService.getDuration();
        }catch (Exception ex){

        }
        return 0;
    }

    public MusicModel getCurrentSong() {
        try{
            return musicService.getCurrentSong();
        }catch (Exception ex){

        }
        return null;
    }



}
package com.example.doanmobilemusicmedia0312;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.doanmobilemusicmedia0312.BroadcastReceiver.MusicBroadcastReceiver;
import com.example.doanmobilemusicmedia0312.Fragment.FavoriteFragment;
import com.example.doanmobilemusicmedia0312.Fragment.HomeFragment;
import com.example.doanmobilemusicmedia0312.Fragment.ProfileFragment;
import com.example.doanmobilemusicmedia0312.Fragment.SearchFragment;
import com.example.doanmobilemusicmedia0312.Interface.IMusicActivity;
import com.example.doanmobilemusicmedia0312.Model.MusicModel;
import com.example.doanmobilemusicmedia0312.Service.BackgroundMusicBinder;
import com.example.doanmobilemusicmedia0312.Service.BackgroundMusicService;
import com.example.doanmobilemusicmedia0312.Utils.SongTimeDisplay;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements IMusicActivity {

    private BottomNavigationView bottomNav;
    private ImageView open_setting, play_stop_music_button;
    private BackgroundMusicService musicService;

    private LinearLayout music_status_bar;
    private MusicBroadcastReceiver receiver;

    private TextView music_status_bar_song_name, music_status_bar_singer;
    private ImageView  music_status_bar_image;
    private boolean isServiceConnected = false;


    private ServiceConnection serviceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            BackgroundMusicBinder binder = (BackgroundMusicBinder) iBinder;
            musicService = binder.getService();

            if(musicService.isPlaying())
                play_stop_music_button.setImageResource(R.drawable.arrow_play_mini_icon_2);
            else
                play_stop_music_button.setImageResource(R.drawable.arrow_play_mini_icon);


        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            musicService = null;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        receiver = new MusicBroadcastReceiver(this);
        IntentFilter filter = new IntentFilter(MusicBroadcastReceiver.ACTION_SONG_CHANGED);
        registerReceiver(receiver, filter);


        addBottomNavigation();
        addControls();
        addEvents();


    }

    @Override
    protected void onResume() {
        super.onResume();
        if(!getServiceRunning("com.example.doanmobilemusicmedia0312.Service.BackgroundMusicService")){
            music_status_bar.setVisibility(View.GONE);
        }else{
            Intent intent = new Intent(this, BackgroundMusicService.class);
            bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
            try{
                music_status_bar.setVisibility(View.VISIBLE);
                if(musicService != null){
                    if(musicService.isPlaying())
                        play_stop_music_button.setImageResource(R.drawable.arrow_play_mini_icon_2);
                    else
                        play_stop_music_button.setImageResource(R.drawable.arrow_play_mini_icon);
                }

            }catch(Exception ex){
                music_status_bar.setVisibility(View.GONE);
            }
        }
    }

    private void addEvents() {
        open_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),SettingActivity.class);
                startActivity(intent);
            }
        });

        music_status_bar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, PlayMusicActivity.class);
                Bundle bundle = new Bundle();

                bundle.putBoolean("NEWSONG", false);
                bundle.putSerializable("OLDSONG", musicService.getCurrentSong());

                intent.putExtra("data",bundle);
                startActivity(intent);



            }
        });

        play_stop_music_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(musicService.isPlaying()){
                    play_stop_music_button.setImageResource(R.drawable.arrow_play_mini_icon);
                    musicService.pause();
                }else{
                    play_stop_music_button.setImageResource(R.drawable.arrow_play_mini_icon_2);
                    musicService.resume();
                }
            }
        });
    }

    private boolean getServiceRunning(String className) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        ArrayList<ActivityManager.RunningServiceInfo> runningServiceInfo = (ArrayList<ActivityManager.RunningServiceInfo>) manager.getRunningServices(30);
        for (int i = 0; i < runningServiceInfo.size(); i++){
            if (runningServiceInfo.get(i).service.getClassName().toString().equals(className)) {
                return true;
            }
        }
        return false;
    }

    private void addControls() {
        open_setting = findViewById(R.id.open_setting);
        music_status_bar = findViewById(R.id.music_status_bar);
        play_stop_music_button = findViewById(R.id.play_stop_music_button);
        music_status_bar_image = findViewById(R.id.music_status_bar_image);
        music_status_bar_song_name = findViewById(R.id.music_status_bar_title);
        music_status_bar_singer = findViewById(R.id.music_status_bar_singer);
    }

    private void addBottomNavigation() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container,HomeFragment.class,null);
        fragmentTransaction.commit();

        bottomNav = findViewById(R.id.bottom_nav);
        bottomNav.setBackground(null);
        bottomNav.setItemIconTintList(null);
        bottomNav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                handleChangeFragment(item.getItemId());
                return true;
            }
        });
    }

    private void handleChangeFragment(int itemId) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        if(itemId == R.id.nav_home){
            fragmentTransaction.replace(R.id.fragment_container, HomeFragment.class,null);
        }
        if(itemId == R.id.nav_search){
            fragmentTransaction.replace(R.id.fragment_container, SearchFragment.class,null);
        }
        if(itemId == R.id.nav_favorite){
            fragmentTransaction.replace(R.id.fragment_container, FavoriteFragment.class,null);
        }
        if(itemId == R.id.nav_profile){
            fragmentTransaction.replace(R.id.fragment_container, ProfileFragment.class,null);
        }
        fragmentTransaction.commit();

    }

    @Override
    public void updateUI(MusicModel song) {
        Picasso.get().load(song.getImageUrl()).into(music_status_bar_image);
        music_status_bar_song_name.setText(song.getSongName());
    }
}
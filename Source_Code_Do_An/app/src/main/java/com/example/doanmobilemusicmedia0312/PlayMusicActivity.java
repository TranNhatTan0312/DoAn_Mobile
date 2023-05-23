package com.example.doanmobilemusicmedia0312;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.example.doanmobilemusicmedia0312.Adapter.PlayMusicAdapter;
import com.example.doanmobilemusicmedia0312.Interface.IToolbarHandler;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

public class PlayMusicActivity extends AppCompatActivity implements IToolbarHandler{
    String song_id;
    ViewPager2 viewPager;
//    FrameLayout mainPanel;
    FrameLayout bottomSheetLayout;
    BottomSheetBehavior bottomSheetBehavior;

    CardView songOptionCancel;

    PlayMusicAdapter playMusicAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_music);

        addControls();
        addEvents();
    }

    private void addEvents() {
        songOptionCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            }
        });
    }

    private void addControls() {

        getData();
        addCommonControl();
        addBottomSheet();
        addViewPager();
    }

    private void getData() {
        Intent intent = getIntent();
        song_id = intent.getStringExtra("id");
    }

    private void addCommonControl() {
        viewPager = (ViewPager2) findViewById(R.id.play_music_view_paper);
//        mainPanel = (FrameLayout) findViewById(R.id.activity_play_music_main_container);
    }

    private void addBottomSheet() {
        bottomSheetLayout = (FrameLayout) findViewById(R.id.bottom_sheet_layout_song_option);
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheetLayout);

        songOptionCancel = (CardView) findViewById(R.id.song_option_cancel);
    }

    private void addViewPager() {
        playMusicAdapter = new PlayMusicAdapter(this);
        playMusicAdapter.setToolbarListener(this);
        playMusicAdapter.setSong(song_id);
        viewPager.setAdapter(playMusicAdapter);

    }

    @Override
    public void onBackButtonClick() {
        super.onBackPressed();
    }

    @Override
    public void onMoreOptionSongClick() {
        if(bottomSheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED){
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        }else{
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        }
    }


}
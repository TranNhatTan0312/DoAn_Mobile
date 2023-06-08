package com.example.doanmobilemusicmedia0312;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.example.doanmobilemusicmedia0312.Adapter.PlayMusicAdapter;
import com.example.doanmobilemusicmedia0312.Interface.IToolbarHandler;
import com.example.doanmobilemusicmedia0312.Model.MusicModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class PlayMusicActivity extends AppCompatActivity implements IToolbarHandler{
    private static final String SHARED_PREFERENCES_NAME = "song_pref";
    DocumentReference docRef;
    SharedPreferences pref;
    SharedPreferences.Editor pref_editor;
    MusicModel song;
    String song_id;
    boolean isPlaylist, isNewMusic;
    ViewPager2 viewPager;
//    FrameLayout mainPanel;
    FrameLayout bottomSheetLayout;
    BottomSheetBehavior bottomSheetBehavior;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

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
        song = new MusicModel();
        getData();
        addCommonControl();
        addBottomSheet();

    }

    private void getData() {

        Bundle bundle = getIntent().getBundleExtra("data");

        song_id = bundle.getString("SONG","");
        isPlaylist = bundle.getBoolean("PLAYLIST",false);
        isNewMusic = bundle.getBoolean("NEWSONG", false);

        if(isNewMusic){
            docRef = db.collection("songs").document(song_id);
            docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    if (documentSnapshot.exists()) {
                        song.setSinger(documentSnapshot.getString("singer"));
                        song.setGenre(documentSnapshot.getString("genre"));
                        song.setLength(documentSnapshot.getString("length"));
                        song.setSongName(documentSnapshot.getString("name"));
                        song.setImageUrl(documentSnapshot.getString("cover_image"));
                        song.setDateRelease(documentSnapshot.getString("date_release"));
                        song.setSourceUrl(documentSnapshot.getString("url"));
                        song.setViews(documentSnapshot.getLong("views"));

                    } else {

                    }
                    addViewPager();
                }
            });
        }else{
            addViewPager();
        }


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
        if(isNewMusic == true){
            playMusicAdapter.setSong(song);
            playMusicAdapter.setIsNewSong(isPlaylist);
        }
        playMusicAdapter.setIsNewSong(isNewMusic);
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
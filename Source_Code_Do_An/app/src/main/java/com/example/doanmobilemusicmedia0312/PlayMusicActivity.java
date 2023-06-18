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
import android.widget.Toast;

import com.example.doanmobilemusicmedia0312.Adapter.PlayMusicAdapter;
import com.example.doanmobilemusicmedia0312.Interface.IToolbarHandler;
import com.example.doanmobilemusicmedia0312.Model.MusicModel;
import com.example.doanmobilemusicmedia0312.Utils.SqliteHelper;
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
    SqliteHelper sqliteHelper = new SqliteHelper(this);
    ViewPager2 viewPager;
//    FrameLayout mainPanel;
    FrameLayout bottomSheetLayout;
    BottomSheetBehavior bottomSheetBehavior;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    CardView songOptionCancel, add_to_playlist, save_to_favorite, download, share, sleep_time;

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

        add_to_playlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(sqliteHelper.addMusicToPlaylist(song)){
                    Toast.makeText(getApplicationContext(),"The song is added to playlist successfully", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(),"The song already have been in playlist", Toast.LENGTH_SHORT).show();
                };
            }
        });
    }

    private void addControls() {
        song = new MusicModel();
        addCommonControl();
        addBottomSheet();
        getData();

    }

    private void getData() {

        Bundle bundle = getIntent().getBundleExtra("data");

        isPlaylist = bundle.getBoolean("PLAYLIST",false);
        isNewMusic = bundle.getBoolean("NEWSONG", false);

        if(isNewMusic){
            song = (MusicModel) bundle.getSerializable("SONG");
            addViewPager();

//            docRef = db.collection("songs").document(song_id);
//            docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
//                @Override
//                public void onSuccess(DocumentSnapshot documentSnapshot) {
//                    if (documentSnapshot.exists()) {
//                        song.setSinger(documentSnapshot.getString("singer"));
//                        song.setGenre(documentSnapshot.getString("genre"));
//                        song.setLength(documentSnapshot.getString("length"));
//                        song.setSongName(documentSnapshot.getString("name"));
//                        song.setImageUrl(documentSnapshot.getString("cover_image"));
//                        song.setDateRelease(documentSnapshot.getString("date_release"));
//                        song.setSourceUrl(documentSnapshot.getString("url"));
//                        song.setViews(documentSnapshot.getLong("views"));
//
//                    } else {
//
//                    }
//                    addViewPager();
//                }
//            });
        }else{
            song = (MusicModel) bundle.getSerializable("OLDSONG");
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
        add_to_playlist = findViewById(R.id.add_to_playlist);
        share = findViewById(R.id.add_to_playlist);
        download = findViewById(R.id.add_to_playlist);
        save_to_favorite = findViewById(R.id.add_to_playlist);
        sleep_time = findViewById(R.id.sleep_time);

    }

    private void addViewPager() {
        playMusicAdapter = new PlayMusicAdapter(this);
        playMusicAdapter.setToolbarListener(this);
        playMusicAdapter.setSong(song);
        if(isNewMusic){
            playMusicAdapter.setIsPlaylist(isPlaylist);
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
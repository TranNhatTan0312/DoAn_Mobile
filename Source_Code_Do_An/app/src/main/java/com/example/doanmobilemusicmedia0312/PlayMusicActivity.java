package com.example.doanmobilemusicmedia0312;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.doanmobilemusicmedia0312.Adapter.PlayMusicAdapter;
import com.example.doanmobilemusicmedia0312.Interface.IToolbarHandler;
import com.example.doanmobilemusicmedia0312.Model.MusicModel;
import com.example.doanmobilemusicmedia0312.Utils.SqliteHelper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.firebase.dynamiclinks.DynamicLink;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
import com.google.firebase.dynamiclinks.ShortDynamicLink;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

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
    TextView option_title,option_singer;
    ImageView option_image;

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

        save_to_favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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
        option_title.setText(song.getSongName());
        option_singer.setText(song.getSinger());
        Picasso.get().load(song.getImageUrl()).into(option_image);
        //                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//                    ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
//                    objectOutputStream.writeObject(song);
//                    objectOutputStream.close();
//                    byteArrayOutputStream.close();
//                    byte[] bytes = byteArrayOutputStream.toByteArray();
//                    intent.putExtra(Intent.EXTRA_STREAM, bytes);
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FirebaseDynamicLinks.getInstance().createDynamicLink()
                        .setLink(Uri.parse("https://s2play.com"+"?id="+song.getId()))
                        .setDomainUriPrefix("https://doanmobilemusicmedia0312.page.link")
                        .setAndroidParameters(new DynamicLink.AndroidParameters.Builder().build())
                        .buildShortDynamicLink()
                        .addOnCompleteListener(new OnCompleteListener<ShortDynamicLink>() {
                            @Override
                            public void onComplete(@NonNull Task<ShortDynamicLink> task) {
                                if (task.isSuccessful()) {
                                    Uri shortLink = task.getResult().getShortLink();

                                    // Chia sẻ shortLink đến các ứng dụng khác
                                    Intent intent = new Intent(Intent.ACTION_SEND);
                                    intent.setType("text/plain");
                                    intent.putExtra(Intent.EXTRA_SUBJECT, "Chia sẻ bài hát");
                                    intent.putExtra(Intent.EXTRA_TEXT, shortLink.toString());
                                    startActivity(Intent.createChooser(intent, "Chọn ứng dụng để chia sẻ"));
                                } else {
                                    // Xử lý lỗi
                                    Exception e = task.getException();
                                    if (e != null) {
                                        Log.e("Dynamic Links", "Error creating short link", e);
                                    }
                                }
                            }
                        });

//                String uri = "https://s2play/" + song.getId();
//                Intent intent = new Intent(Intent.ACTION_SEND);
//                intent.setType("text/plain");
//                intent.putExtra(Intent.EXTRA_TEXT, uri);
//                startActivity(Intent.createChooser(intent, "Share Music"));



            }
        });

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
        share = findViewById(R.id.share);
        download = findViewById(R.id.download);
        save_to_favorite = findViewById(R.id.save_to_favorite);
        sleep_time = findViewById(R.id.sleep_time);

        option_title = findViewById(R.id.option_title);
        option_singer = findViewById(R.id.option_singer);
        option_image = findViewById(R.id.option_image);
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
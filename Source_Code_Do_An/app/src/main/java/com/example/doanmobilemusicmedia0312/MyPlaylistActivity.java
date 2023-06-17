package com.example.doanmobilemusicmedia0312;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.doanmobilemusicmedia0312.Adapter.PlaylistAdapter;
import com.example.doanmobilemusicmedia0312.Adapter.SearchMusicDetailAdapter;
import com.example.doanmobilemusicmedia0312.Model.SearchSongModel;
import com.example.doanmobilemusicmedia0312.Utils.SqliteHelper;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class MyPlaylistActivity extends AppCompatActivity {
    private ImageView back_setting;
    RecyclerView rcyPlaylist;
    ArrayList<SearchSongModel> arrayList = new ArrayList<>();
    SqliteHelper sqliteHelper = new SqliteHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_playlist);

        back_setting = findViewById(R.id.back_setting);



        rcyPlaylist = findViewById(R.id.rcyPlaylist);
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(MyPlaylistActivity.this);
        rcyPlaylist.setLayoutManager(layoutManager);

        PlaylistAdapter musicAdapter=new PlaylistAdapter(MyPlaylistActivity.this, sqliteHelper.showPlaylist());
        rcyPlaylist.setAdapter(musicAdapter);

        addEvents();
    }

    private void addEvents() {
        back_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}
package com.example.doanmobilemusicmedia0312;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.doanmobilemusicmedia0312.Adapter.SearchMusicDetailAdapter;
import com.example.doanmobilemusicmedia0312.Model.MusicModel;
import com.example.doanmobilemusicmedia0312.Model.SearchSongModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class SearchDetailActivity extends AppCompatActivity {
    SearchView searchView;
    RecyclerView recyclerView;
    ArrayList<MusicModel> arrayList = new ArrayList<>();
    ArrayList<MusicModel> songList = new ArrayList<>();
    ImageView back;
    ArrayList<MusicModel> searchList;
    ArrayList<MusicModel> songSearchList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_detail);

        recyclerView = findViewById(R.id.recyclerView);
        searchView = findViewById(R.id.searchView);
        back = findViewById(R.id.back_search_song_detail);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        // Khởi tạo Firebase Firestore
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference songsRef = db.collection("songs");
        CollectionReference songsSearchRef = db.collection("history");

        songsSearchRef.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if (!queryDocumentSnapshots.isEmpty()) {
                    for (DocumentSnapshot document : queryDocumentSnapshots.getDocuments()) {
                        MusicModel item = new MusicModel();
                        item.setId(document.getId());
                        item.setImageUrl(document.getString("cover_image"));
                        item.setSourceUrl(document.getString("url"));
                        item.setGenre(document.getString("genre"));
                        item.setLength(document.getString("length"));
                        item.setSinger(document.getString("singer"));
                        item.setSongName(document.getString("name"));
                        item.setDateRelease(document.getString("date_release"));
                        arrayList.add(item);
                    }

                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(SearchDetailActivity.this);
                    recyclerView.setLayoutManager(layoutManager);

                    SearchMusicDetailAdapter musicAdapter = new SearchMusicDetailAdapter(SearchDetailActivity.this, arrayList);
                    recyclerView.setAdapter(musicAdapter);
                }
            }
        });

        songsRef.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if (!queryDocumentSnapshots.isEmpty()) {
                    for (DocumentSnapshot document : queryDocumentSnapshots.getDocuments()) {
                        MusicModel item = new MusicModel();
                        item.setId(document.getId());
                        item.setImageUrl(document.getString("cover_image"));
                        item.setSourceUrl(document.getString("url"));
                        item.setGenre(document.getString("genre"));
                        item.setLength(document.getString("length"));
                        item.setSinger(document.getString("singer"));
                        item.setSongName(document.getString("name"));
                        item.setDateRelease(document.getString("date_release"));
                        songList.add(item);
                    }
                }
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                songSearchList = new ArrayList<>();
                if (newText.isEmpty()) {
                    songSearchList = new ArrayList<>(arrayList);
                } else {
                    for (int i = 0; i < songList.size(); i++) {
                        if (songList.get(i).getSongName().toUpperCase().contains(newText.toUpperCase())) {
                            MusicModel item = new MusicModel();
                            item.setId(songList.get(i).getId());
                            item.setImageUrl(songList.get(i).getImageUrl());
                            item.setSourceUrl(songList.get(i).getSourceUrl());
                            item.setGenre(songList.get(i).getGenre());
                            item.setLength(songList.get(i).getLength());
                            item.setSinger(songList.get(i).getSinger());
                            item.setSongName(songList.get(i).getSongName());
                            item.setDateRelease(songList.get(i).getDateRelease());
                            songSearchList.add(item);
                        }
                    }
                }

                // Tạo layout manager cho RecyclerView
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(SearchDetailActivity.this);
                recyclerView.setLayoutManager(layoutManager);

                // Tạo adapter và thiết lập cho RecyclerView
                SearchMusicDetailAdapter musicAdapter = new SearchMusicDetailAdapter(SearchDetailActivity.this, songSearchList);
                recyclerView.setAdapter(musicAdapter);
                return false;
            }
        });
    }

}
package com.example.doanmobilemusicmedia0312;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.doanmobilemusicmedia0312.Adapter.SearchMusicDetailAdapter;
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
    ArrayList<SearchSongModel> arrayList = new ArrayList<>();
    ArrayList<SearchSongModel> songList = new ArrayList<>();
    ImageView back;
    ArrayList<SearchSongModel> searchList;
    ArrayList<SearchSongModel> songSearchList;
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
        CollectionReference songsSearchRef = db.collection("search_history");

        songsSearchRef.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if (!queryDocumentSnapshots.isEmpty()) {
                    for (DocumentSnapshot document : queryDocumentSnapshots.getDocuments()) {
                        SearchSongModel item = new SearchSongModel();
                        item.setSongId(document.getId());
                        item.setImg(document.getString("cover_image"));
                        item.setMusicName(document.getString("name"));
                        item.setMusicNum(document.getString("singer"));
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
                        SearchSongModel item = new SearchSongModel();
                        item.setSongId(document.getId());
                        item.setImg(document.getString("cover_image"));
                        item.setMusicName(document.getString("name"));
                        item.setMusicNum(document.getString("singer"));
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
                        if (songList.get(i).getMusicName().toUpperCase().contains(newText.toUpperCase())) {
                            SearchSongModel modelClass = new SearchSongModel();
                            modelClass.setMusicName(songList.get(i).getMusicName());
                            modelClass.setMusicNum(songList.get(i).getMusicNum());
                            modelClass.setImg(songList.get(i).getImg());
                            songSearchList.add(modelClass);
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
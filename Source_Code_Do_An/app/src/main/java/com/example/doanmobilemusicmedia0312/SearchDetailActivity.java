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

import java.util.ArrayList;

public class SearchDetailActivity extends AppCompatActivity {
    SearchView searchView;
    RecyclerView recyclerView;
    ArrayList<SearchSongModel> arrayList = new ArrayList<>();
    ImageView back;
    ArrayList<SearchSongModel> searchList;
    String [] musicList = new String[]{"Havana", "Let Me Love You", "Someone Like You","Treat you better", "There for you"};
    String[] musicNum = new String[]{"Camila Cabello ", "DJ Snake", "Andele", "Treat You Better", "Martin Garrix"};
    String[] imgList=new String[]{"https://firebasestorage.googleapis.com/v0/b/s2play.appspot.com/o/song_cover_images%2Flisa-21.jpeg?alt=media&token=5756e692-4972-489f-8771-2dc18a62d2a3",
    "https://firebasestorage.googleapis.com/v0/b/s2play.appspot.com/o/song_cover_images%2Flisa-21.jpeg?alt=media&token=5756e692-4972-489f-8771-2dc18a62d2a3",
    "https://firebasestorage.googleapis.com/v0/b/s2play.appspot.com/o/song_cover_images%2Flisa-21.jpeg?alt=media&token=5756e692-4972-489f-8771-2dc18a62d2a3",
    "https://firebasestorage.googleapis.com/v0/b/s2play.appspot.com/o/song_cover_images%2Flisa-21.jpeg?alt=media&token=5756e692-4972-489f-8771-2dc18a62d2a3",
    "https://firebasestorage.googleapis.com/v0/b/s2play.appspot.com/o/song_cover_images%2Flisa-21.jpeg?alt=media&token=5756e692-4972-489f-8771-2dc18a62d2a3"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_detail);


        recyclerView = findViewById(R.id.recyclerView);
        searchView = findViewById(R.id.searchView);
        back = findViewById(R.id.back_search_song_detail);

//        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        for (int i = 0; i < musicList.length; i++) {
            SearchSongModel modelClass=new SearchSongModel();
            modelClass.setMusicName(musicList[i]);
            modelClass.setMusicNum(musicNum[i]);
            modelClass.setImg(imgList[i]);
            arrayList.add(modelClass);
        }

        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(SearchDetailActivity.this);
        layoutManager.setItemPrefetchEnabled(false);
        layoutManager.setAutoMeasureEnabled(false);
        recyclerView.setLayoutManager(layoutManager);

        SearchMusicDetailAdapter musicAdapter=new SearchMusicDetailAdapter(SearchDetailActivity.this, arrayList);
        recyclerView.setAdapter(musicAdapter);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchList=new ArrayList<>();
                if (query.length()>0){
                    for (int i = 0; i < arrayList.size(); i++) {
                        if (arrayList.get(i).getMusicName().toUpperCase().contains(query.toUpperCase())){
                            SearchSongModel modelClass=new SearchSongModel();
                            modelClass.setMusicName(arrayList.get(i).getMusicName());
                            modelClass.setMusicNum(arrayList.get(i).getMusicNum());
                            modelClass.setImg(arrayList.get(i).getImg());
                            searchList.add(modelClass);
                        }
                    }

                    RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(SearchDetailActivity.this);
                    recyclerView.setLayoutManager(layoutManager);

                    SearchMusicDetailAdapter musicAdapter=new SearchMusicDetailAdapter(SearchDetailActivity.this, searchList);
                    recyclerView.setAdapter(musicAdapter);
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
    }
}
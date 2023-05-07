package com.example.project2;

import android.os.Bundle;
import androidx.appcompat.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Locale;


public class SearchDetailActivity extends AppCompatActivity {

    SearchView searchView;
    RecyclerView recyclerView;
    ArrayList<SearchModelClass> arrayList = new ArrayList<>();
    ArrayList<SearchModelClass> searchList;
    String [] musicList = new String[]{"Havana", "Let Me Love You", "Someone Like You","Treat you better", "There for you"};
    String[] musicNum = new String[]{"Camila Cabello ", "DJ Snake", "Andele", "Treat You Better", "Martin Garrix"};
    int[] imgList=new int[]{R.drawable.img_1,R.drawable.img_2,R.drawable.img_3,R.drawable.img_4,R.drawable.img_5};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search);
        getSupportActionBar().hide();
        recyclerView = findViewById(R.id.recyclerView);
        searchView = findViewById(R.id.searchView);

//        LinearLayoutManager layoutManager = new LinearLayoutManager(this);


        for (int i = 0; i < musicList.length; i++) {
            SearchModelClass modelClass=new SearchModelClass();
            modelClass.setMusicName(musicList[i]);
            modelClass.setMusicNum(musicNum[i]);
            modelClass.setImg(imgList[i]);
            arrayList.add(modelClass);
        }

        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(SearchDetailActivity.this);
        layoutManager.setItemPrefetchEnabled(false);
        layoutManager.setAutoMeasureEnabled(false);
        recyclerView.setLayoutManager(layoutManager);

        MusicAdapter musicAdapter=new MusicAdapter(SearchDetailActivity.this, arrayList);
        recyclerView.setAdapter(musicAdapter);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchList=new ArrayList<>();
                if (query.length()>0){
                    for (int i = 0; i < arrayList.size(); i++) {
                        if (arrayList.get(i).getMusicName().toUpperCase().contains(query.toUpperCase())){
                            SearchModelClass modelClass=new SearchModelClass();
                            modelClass.setMusicName(arrayList.get(i).getMusicName());
                            modelClass.setMusicNum(arrayList.get(i).getMusicNum());
                            modelClass.setImg(arrayList.get(i).getImg());
                            searchList.add(modelClass);
                        }
                    }

                    RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(SearchDetailActivity.this);
                    recyclerView.setLayoutManager(layoutManager);

                    MusicAdapter musicAdapter=new MusicAdapter(SearchDetailActivity.this, searchList);
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

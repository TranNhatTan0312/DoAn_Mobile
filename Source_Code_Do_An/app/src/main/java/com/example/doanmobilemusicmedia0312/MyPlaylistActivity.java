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

import java.util.ArrayList;

public class MyPlaylistActivity extends AppCompatActivity {
    private ImageView back_setting;
    RecyclerView rcyPlaylist;
    String [] musicList = new String[]{"Havana", "Let Me Love You", "Someone Like You","Treat you better", "There for you"};
    String[] musicNum = new String[]{"Camila Cabello ", "DJ Snake", "Andele", "Treat You Better", "Martin Garrix"};
    int[] imgList=new int[]{R.drawable.img_1,R.drawable.img_2,R.drawable.img_3,R.drawable.img_4,R.drawable.img_5};
    ArrayList<SearchSongModel> arrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_playlist);

        back_setting = findViewById(R.id.back_setting);


        for (int i = 0; i < musicList.length; i++) {
            SearchSongModel modelClass=new SearchSongModel();
            modelClass.setSongId(i+"");
            modelClass.setMusicName(musicList[i]);
            modelClass.setMusicNum(musicNum[i]);
            modelClass.setImg(imgList[i]);
            arrayList.add(modelClass);
        }

        rcyPlaylist = findViewById(R.id.rcyPlaylist);
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(MyPlaylistActivity.this);
        rcyPlaylist.setLayoutManager(layoutManager);

        PlaylistAdapter musicAdapter=new PlaylistAdapter(MyPlaylistActivity.this, arrayList);
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
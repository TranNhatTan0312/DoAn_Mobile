package com.example.doanmobilemusicmedia0312;

import static java.security.AccessController.getContext;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.doanmobilemusicmedia0312.Adapter.MoodAdapter;
import com.example.doanmobilemusicmedia0312.Model.MoodModel;
import com.example.doanmobilemusicmedia0312.Model.MusicModel;
import com.example.doanmobilemusicmedia0312.Model.SearchSongModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Objects;

public class MoodActivity extends AppCompatActivity {

    RecyclerView rcyMood;
    ImageView back;

    ArrayList<MusicModel> SongOfMoodList;

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mood);

        rcyMood = findViewById(R.id.rcyMood);
        back = findViewById(R.id.back_setting);
        SongOfMoodList = new ArrayList<>();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        Intent intent = getIntent();

        String type = intent.getStringExtra("type");

        if(Objects.equals(type, "concert")) {
            String concert = intent.getStringExtra("concert");
            db.collection("songs").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                @Override

                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                    if(!queryDocumentSnapshots.isEmpty()){
                        for (DocumentSnapshot document : queryDocumentSnapshots.getDocuments()) {
                            if (Objects.equals(document.getString("concert"), concert)) {
                                MusicModel item = new MusicModel();
                                item.setId(document.getId());
                                item.setImageUrl(document.getString("cover_image"));
                                item.setSourceUrl(document.getString("url"));
                                item.setGenre(document.getString("genre"));
                                item.setLength(document.getString("length"));
                                item.setSinger(document.getString("singer"));
                                item.setSongName(document.getString("name"));
                                item.setDateRelease(document.getString("date_release"));
                                SongOfMoodList.add(item);
                            }
                        }

                        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(MoodActivity.this);
                        rcyMood.setLayoutManager(layoutManager);

                        MoodAdapter customAdapter = new MoodAdapter(MoodActivity.this, SongOfMoodList);

                        rcyMood.setAdapter(customAdapter);
                    }
                }

            });
        }
        else if (Objects.equals(type, "mood")) {

            String mood = intent.getStringExtra("mood");

            db.collection("songs").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                @Override

                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                    if(!queryDocumentSnapshots.isEmpty()){
                        for (DocumentSnapshot document : queryDocumentSnapshots.getDocuments()) {
                            if (Objects.equals(document.getString("mood"), mood)) {
                                MusicModel item = new MusicModel();
                                item.setId(document.getId());
                                item.setImageUrl(document.getString("cover_image"));
                                item.setSourceUrl(document.getString("url"));
                                item.setGenre(document.getString("genre"));
                                item.setLength(document.getString("length"));
                                item.setSinger(document.getString("singer"));
                                item.setSongName(document.getString("name"));
                                item.setDateRelease(document.getString("date_release"));
                                SongOfMoodList.add(item);
                            }
                        }

                        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(MoodActivity.this);
                        rcyMood.setLayoutManager(layoutManager);

                        MoodAdapter customAdapter = new MoodAdapter(MoodActivity.this, SongOfMoodList);

                        rcyMood.setAdapter(customAdapter);
                    }

                }
            });
        };
        if (Objects.equals(type, "singer")) {

            String singer = intent.getStringExtra("singer");

            db.collection("songs").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                @Override

                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                    if(!queryDocumentSnapshots.isEmpty()){
                        for (DocumentSnapshot document : queryDocumentSnapshots.getDocuments()) {
                            if (Objects.equals(document.getString("singer"), singer)) {
                                MusicModel item = new MusicModel();
                                item.setId(document.getId());
                                item.setImageUrl(document.getString("cover_image"));
                                item.setSourceUrl(document.getString("url"));
                                item.setGenre(document.getString("genre"));
                                item.setLength(document.getString("length"));
                                item.setSinger(document.getString("singer"));
                                item.setSongName(document.getString("name"));
                                item.setDateRelease(document.getString("date_release"));
                                SongOfMoodList.add(item);
                            }
                        }

                        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(MoodActivity.this);
                        rcyMood.setLayoutManager(layoutManager);

                        MoodAdapter customAdapter = new MoodAdapter(MoodActivity.this, SongOfMoodList);

                        rcyMood.setAdapter(customAdapter);
                    }

                }
            });
        };


    }
}
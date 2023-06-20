package com.example.doanmobilemusicmedia0312;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import com.example.doanmobilemusicmedia0312.Model.MusicModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;

public class ShareActivity extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);

        Intent intent = getIntent();
        Uri data = intent.getData();

        if (data != null) {
            try{
                String link = data.toString();
                String id = link.split("=")[1];


                db.collection("songs").document(id).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {

                            MusicModel song = new MusicModel();
                            song.setSinger(documentSnapshot.getString("singer"));
                            song.setGenre(documentSnapshot.getString("genre"));
                            song.setLength(documentSnapshot.getString("length"));
                            song.setSongName(documentSnapshot.getString("name"));
                            song.setImageUrl(documentSnapshot.getString("cover_image"));
                            song.setDateRelease(documentSnapshot.getString("date_release"));
                            song.setSourceUrl(documentSnapshot.getString("url"));
                            song.setViews(documentSnapshot.getLong("views"));

                            Intent newIntent = new Intent(ShareActivity.this, PlayMusicActivity.class);

                            Bundle bundle = new Bundle();
                            bundle.putSerializable("SONG", song);
                            bundle.putBoolean("PLAYLIST", false);
                            bundle.putBoolean("NEWSONG", true);

                            newIntent.putExtra("data", bundle);

                            startActivity(newIntent);
                        }
                    }
                });

            }catch (Exception e){

            }

        }

        finish();
    }
}
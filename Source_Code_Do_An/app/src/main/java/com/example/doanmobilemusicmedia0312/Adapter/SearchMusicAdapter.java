package com.example.doanmobilemusicmedia0312.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.example.doanmobilemusicmedia0312.MainActivity;
import com.example.doanmobilemusicmedia0312.Model.SearchSongModel;
import com.example.doanmobilemusicmedia0312.PlayMusicActivity;
import com.example.doanmobilemusicmedia0312.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SearchMusicAdapter extends BaseAdapter {
    Context context;
    ArrayList<SearchSongModel> data;
    LayoutInflater inflter;

    public SearchMusicAdapter(Context context, ArrayList<SearchSongModel> data) {
        super();
        this.context = context;
        this.data = data;


        inflter	=	LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return data.size();
    }

    @Override
    public Object getItem(int arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long getItemId(int arg0) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup arg2) {
        // TODO Auto-generated method stub
        view	=	inflter.inflate(R.layout.search_music_item, null);
        ImageView icon	=	(ImageView) view.findViewById(R.id.icon);
        Picasso.get().load(data.get(i).getImg()).into(icon);
        SearchSongModel song = data.get(i);
        icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                CollectionReference searchHistoryRef = db.collection("search_history");
                Query query = searchHistoryRef.whereEqualTo("name", song.getMusicName());
                query.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (queryDocumentSnapshots.isEmpty()) {
                            Map<String, Object> songData = new HashMap<>();
                            songData.put("name", song.getMusicName());
                            songData.put("singer", song.getMusicNum());
                            songData.put("cover_image", song.getImg());

                            searchHistoryRef.add(songData)
                                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                        @Override
                                        public void onSuccess(DocumentReference documentReference) {
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                        }
                                    });
                        }
                    }
                });
                String songId = song.getSongId();
                System.out.println("Song ID: " + songId);
                Intent intent = new Intent(context,PlayMusicActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("SONG", data.get(i).getSongId());
                bundle.putBoolean("PLAYLIST",false);
                bundle.putBoolean("NEWSONG",true);

                intent.putExtra("data",bundle);
                context.startActivity(intent);
            }
        });

        return view;
    }
}

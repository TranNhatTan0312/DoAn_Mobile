package com.example.doanmobilemusicmedia0312.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doanmobilemusicmedia0312.Model.MusicModel;
import com.example.doanmobilemusicmedia0312.PlayMusicActivity;
import com.example.doanmobilemusicmedia0312.R;
import com.example.doanmobilemusicmedia0312.Utils.SqliteHelper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class FavoriteSongAdapter extends RecyclerView.Adapter<FavoriteSongAdapter.ViewHolder> {
    public ArrayList<MusicModel> songList;
    Context context;
    LayoutInflater layoutInflater;

    public FavoriteSongAdapter(Context context, ArrayList<MusicModel> songList) {
        this.context = context;
        this.songList = songList;
        layoutInflater = LayoutInflater.from(context);
    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.favorite_item, parent, false);
        return new ViewHolder(itemView).linkAdapter(this);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        MusicModel song = songList.get(position);
        holder.musicName.setText(song.getSongName());
        holder.musicSinger.setText(String.valueOf(song.getSinger()));
        holder.musicLength.setText(String.valueOf(song.getLength()));
        Picasso.get().load(song.getImageUrl()).into(holder.img);

        holder.setSong(song);
    }

    @Override
    public int getItemCount() {
        return songList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // Khai báo các view trong layout item của RecyclerView
        TextView musicName, musicSinger, musicLength;
        ImageView img;
        MusicModel song;
        ImageView favorite_delete;
        FavoriteSongAdapter adapter;

        public void setSong(MusicModel song){
            this.song = song;
        }
        public ViewHolder(View itemView) {
            super(itemView);
            musicName = itemView.findViewById(R.id.text);
            musicSinger = itemView.findViewById(R.id.text1);
            musicLength = itemView.findViewById(R.id.text2);
            img = itemView.findViewById(R.id.image);
            favorite_delete = itemView.findViewById(R.id.favorite_delete);

            itemView.setOnClickListener(this);

            favorite_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                    SharedPreferences sharedPreferences  = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                    String username = sharedPreferences.getString("username","");
                    db.collection("favorites").document(username).collection("songs").document(song.getId())
                            .delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        adapter.songList.remove(getAdapterPosition());
                                        adapter.notifyItemRemoved(getAdapterPosition());
                                    }
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show();
                                }
                            });



                }
            });
        }
        public FavoriteSongAdapter.ViewHolder linkAdapter(FavoriteSongAdapter adapter){
            this.adapter = adapter;
            return this;
        }
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(context, PlayMusicActivity.class);

            Bundle bundle = new Bundle();
            bundle.putBoolean("PLAYLIST",true);
            bundle.putSerializable("SONG",this.song);
            bundle.putBoolean("NEWSONG",true);


            intent.putExtra("data",bundle);
            context.startActivity(intent);
        }
    }
}
package com.example.doanmobilemusicmedia0312.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.doanmobilemusicmedia0312.Model.MusicModel;
import com.example.doanmobilemusicmedia0312.PlayMusicActivity;
import com.example.doanmobilemusicmedia0312.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class FavoriteSongAdapter extends RecyclerView.Adapter<FavoriteSongAdapter.ViewHolder> {
    private ArrayList<MusicModel> songList;
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
        return new ViewHolder(itemView);
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

        public void setSong(MusicModel song){
            this.song = song;
        }
        public ViewHolder(View itemView) {
            super(itemView);
            musicName = itemView.findViewById(R.id.text);
            musicSinger = itemView.findViewById(R.id.text1);
            musicLength = itemView.findViewById(R.id.text2);
            img = itemView.findViewById(R.id.image);

            itemView.setOnClickListener(this);
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
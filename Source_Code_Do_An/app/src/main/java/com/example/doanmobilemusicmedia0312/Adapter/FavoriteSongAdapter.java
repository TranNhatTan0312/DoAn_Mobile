package com.example.doanmobilemusicmedia0312.Adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doanmobilemusicmedia0312.Fragment.FavoriteFragment;
import com.example.doanmobilemusicmedia0312.Model.FavoriteSongModel;
import com.example.doanmobilemusicmedia0312.Model.MusicModel;
import com.example.doanmobilemusicmedia0312.R;

import java.util.List;

public class FavoriteSongAdapter extends RecyclerView.Adapter<FavoriteSongAdapter.ViewHolder> {
    private List<FavoriteSongModel> songList;
    Context context;
    LayoutInflater layoutInflater;

    public FavoriteSongAdapter(List<FavoriteSongModel> songList) {
        this.songList = songList;
    }

    public FavoriteSongAdapter(Context context, List<FavoriteSongModel> songList) {
        this.context = context;
        this.songList = songList;
        layoutInflater = LayoutInflater.from(context);
    }

    // Implement các phương thức cần thiết như onCreateViewHolder, onBindViewHolder và getItemCount
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // Khai báo các view trong layout item của RecyclerView
        TextView musicName, musicSinger, musicLength;
        ImageView img;
        public ViewHolder(View itemView) {
            super(itemView);
            musicName = itemView.findViewById(R.id.text);
            musicSinger = itemView.findViewById(R.id.text1);
            musicLength = itemView.findViewById(R.id.text2);
            img = itemView.findViewById(R.id.image);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.favorite_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        FavoriteSongModel Musicsongclass = songList.get(position);
        holder.musicName.setText(Musicsongclass.getMusicName());
        holder.musicSinger.setText(String.valueOf(Musicsongclass.getMusicSinger()));
        holder.musicLength.setText(String.valueOf(Musicsongclass.getLength()));
        holder.img.setImageURI(Uri.parse(Musicsongclass.getImg()));
    }

    @Override
    public int getItemCount() {
        return songList.size();
    }
}


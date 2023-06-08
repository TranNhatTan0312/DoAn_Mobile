package com.example.doanmobilemusicmedia0312.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doanmobilemusicmedia0312.Model.SearchSongModel;
import com.example.doanmobilemusicmedia0312.PlayMusicActivity;
import com.example.doanmobilemusicmedia0312.R;

import java.util.ArrayList;

public class PlaylistAdapter  extends RecyclerView.Adapter<PlaylistAdapter.PlaylistHolder> {

    Context context;
    ArrayList<SearchSongModel> arrayList;
    LayoutInflater layoutInflater;

    public PlaylistAdapter(Context context, ArrayList<SearchSongModel> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
        layoutInflater = LayoutInflater.from(context);
    }


    @NonNull
    @Override
    public PlaylistHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.search_music_detail_item, parent, false);
        return new PlaylistHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlaylistHolder holder, int position) {
        SearchSongModel searchModelClass = arrayList.get(position);
        holder.musicName.setText(searchModelClass.getMusicName());
        holder.musicNum.setText(String.valueOf(searchModelClass.getMusicNum()));
        holder.img.setImageResource(searchModelClass.getImg());
        holder.setSong_id(searchModelClass.getSongId());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class PlaylistHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView musicName, musicNum;
        ImageView img;

        String song_id;

        public PlaylistHolder(@NonNull View itemView) {
            super(itemView);
            musicName = itemView.findViewById(R.id.txt);
            musicNum = itemView.findViewById(R.id.txt2);
            img = itemView.findViewById(R.id.img);

            itemView.setOnClickListener(this);
        }

        public void setSong_id(String song_id) {
            this.song_id = song_id;
        }

        @Override
        public void onClick(View view) {

            Intent intent = new Intent(context, PlayMusicActivity.class);

            Bundle bundle = new Bundle();
            bundle.putBoolean("PLAYLIST",true);
            bundle.putString("SONG",song_id);
            bundle.putBoolean("NEWSONG",true);


            intent.putExtra("data",bundle);
            context.startActivity(intent);
        }
    }
}
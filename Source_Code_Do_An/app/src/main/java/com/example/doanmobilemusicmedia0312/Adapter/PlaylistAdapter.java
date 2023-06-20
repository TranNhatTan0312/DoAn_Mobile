package com.example.doanmobilemusicmedia0312.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doanmobilemusicmedia0312.Model.MusicModel;
import com.example.doanmobilemusicmedia0312.Model.SearchSongModel;
import com.example.doanmobilemusicmedia0312.PlayMusicActivity;
import com.example.doanmobilemusicmedia0312.R;
import com.example.doanmobilemusicmedia0312.Utils.SqliteHelper;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PlaylistAdapter  extends RecyclerView.Adapter<PlaylistAdapter.PlaylistHolder> {

    Context context;
    ArrayList<MusicModel> arrayList;
    LayoutInflater layoutInflater;

    public PlaylistAdapter(Context context, ArrayList<MusicModel> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
        layoutInflater = LayoutInflater.from(context);
    }


    @NonNull
    @Override
    public PlaylistHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.search_music_detail_item, parent, false);
        return new PlaylistHolder(view).linkAdapter(this);
    }

    @Override
    public void onBindViewHolder(@NonNull PlaylistHolder holder, int position) {
        MusicModel musicModel = arrayList.get(position);
        holder.musicName.setText(musicModel.getSongName());
        holder.musicNum.setText(String.valueOf(musicModel.getSinger()));
        Picasso.get().load(musicModel.getImageUrl()).into((holder.img));
        holder.setSong(musicModel);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class PlaylistHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView musicName, musicNum;
        ImageView img;

        MusicModel song;
        ImageButton deleteButton;
        PlaylistAdapter adapter;

        public PlaylistHolder(@NonNull View itemView) {
            super(itemView);
            musicName = itemView.findViewById(R.id.txt);
            musicNum = itemView.findViewById(R.id.txt2);
            img = itemView.findViewById(R.id.img);
            deleteButton = itemView.findViewById(R.id.deleteButton);

            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SqliteHelper sqliteHelper = new SqliteHelper(context);
                    boolean result = sqliteHelper.deleteMusicFromPlaylist(song.getId());
                    if(result){
                        adapter.arrayList.remove(getAdapterPosition());
                        adapter.notifyItemRemoved(getAdapterPosition());
                    }
                }
            });

            itemView.setOnClickListener(this);
        }
        public PlaylistHolder linkAdapter(PlaylistAdapter adapter){
            this.adapter = adapter;
            return this;
        }
        public void setSong(MusicModel song) {
            this.song = song;
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
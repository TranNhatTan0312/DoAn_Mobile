//package com.example.doanmobilemusicmedia0312.Adapter;
//
//public class MoodAdapter {
//
//}

package com.example.doanmobilemusicmedia0312.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doanmobilemusicmedia0312.Model.MoodModel;
import com.example.doanmobilemusicmedia0312.Model.MusicModel;
import com.example.doanmobilemusicmedia0312.PlayMusicActivity;
import com.example.doanmobilemusicmedia0312.R;

import java.security.AccessControlContext;
import java.util.ArrayList;

public class MoodAdapter extends RecyclerView.Adapter<MoodAdapter.MyHolder> {

    Context context;
    ArrayList<MusicModel> arrayList;
    LayoutInflater layoutInflater;

    public MoodAdapter(Context context, ArrayList<MusicModel> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
        layoutInflater = LayoutInflater.from(context);
    }


    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.search_music_detail_item, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        MusicModel musicModelClass = arrayList.get(position);
        holder.musicName.setText(musicModelClass.getSongName());
        holder.musicSinger.setText(String.valueOf(musicModelClass.getSinger()));
        holder.img.setImageURI(Uri.parse(musicModelClass.getImageUrl()));
        holder.setSong(musicModelClass);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView musicName, musicSinger;
        ImageView img;
        MusicModel song;

        public void setSong(MusicModel song) { this.song = song;}

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            musicName = itemView.findViewById(R.id.txt);
            musicSinger = itemView.findViewById(R.id.txt2);
            img = itemView.findViewById(R.id.img);

        }

        @Override
        public void onClick(View v) {
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

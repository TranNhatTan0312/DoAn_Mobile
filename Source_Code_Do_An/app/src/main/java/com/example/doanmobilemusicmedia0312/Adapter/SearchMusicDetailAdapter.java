package com.example.doanmobilemusicmedia0312.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doanmobilemusicmedia0312.Model.SearchSongModel;
import com.example.doanmobilemusicmedia0312.R;

import java.util.ArrayList;

public class SearchMusicDetailAdapter extends RecyclerView.Adapter<SearchMusicDetailAdapter.MyHolder> {

    Context context;
    ArrayList<SearchSongModel> arrayList;
    LayoutInflater layoutInflater;

    public SearchMusicDetailAdapter(Context context, ArrayList<SearchSongModel> arrayList) {
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
        SearchSongModel searchModelClass = arrayList.get(position);
        holder.musicName.setText(searchModelClass.getMusicName());
        holder.musicNum.setText(String.valueOf(searchModelClass.getMusicNum()));
        holder.img.setImageResource(searchModelClass.getImg());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        TextView musicName, musicNum;
        ImageView img;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            musicName = itemView.findViewById(R.id.txt);
            musicNum = itemView.findViewById(R.id.txt2);
            img = itemView.findViewById(R.id.img);
        }
    }
}

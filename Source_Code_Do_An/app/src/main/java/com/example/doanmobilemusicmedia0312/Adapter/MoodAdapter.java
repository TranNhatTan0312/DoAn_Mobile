//package com.example.doanmobilemusicmedia0312.Adapter;
//
//public class MoodAdapter {
//
//}

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

import com.example.doanmobilemusicmedia0312.Model.MoodModel;
import com.example.doanmobilemusicmedia0312.R;

import java.security.AccessControlContext;
import java.util.ArrayList;

public class MoodAdapter extends RecyclerView.Adapter<MoodAdapter.MyHolder> {

    Context context;
    ArrayList<MoodModel> arrayList;
    LayoutInflater layoutInflater;

    public MoodAdapter(Context context, ArrayList<MoodModel> arrayList) {
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
        MoodModel moodModelClass = arrayList.get(position);
        holder.musicName.setText(moodModelClass.getMusicName());
        holder.musicSinger.setText(String.valueOf(moodModelClass.getMusicSinger()));
        holder.img.setImageURI(Uri.parse(moodModelClass.getImg()));
        holder.id = moodModelClass.getId();
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        TextView musicName, musicSinger;
        ImageView img;
        String id;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            musicName = itemView.findViewById(R.id.txt);
            musicSinger = itemView.findViewById(R.id.txt2);
            img = itemView.findViewById(R.id.img);

        }
    }
}

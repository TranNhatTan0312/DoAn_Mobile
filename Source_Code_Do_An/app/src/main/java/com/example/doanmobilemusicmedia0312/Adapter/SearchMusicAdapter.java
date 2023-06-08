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

import com.example.doanmobilemusicmedia0312.MainActivity;
import com.example.doanmobilemusicmedia0312.Model.SearchSongModel;
import com.example.doanmobilemusicmedia0312.PlayMusicActivity;
import com.example.doanmobilemusicmedia0312.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

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
        icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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

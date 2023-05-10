package com.example.doanmobilemusicmedia0312.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.doanmobilemusicmedia0312.MainActivity;
import com.example.doanmobilemusicmedia0312.PlayMusicActivity;
import com.example.doanmobilemusicmedia0312.R;

public class SearchMusicAdapter extends BaseAdapter {
    Context context;
    int logos[];
    LayoutInflater inflter;

    public SearchMusicAdapter(Context context, int[] logos) {
        super();
        this.context = context;
        this.logos = logos;
        inflter	=	LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return logos.length;
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
        icon.setImageResource(logos[i]);

        icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,PlayMusicActivity.class);
                context.startActivity(intent);
            }
        });

        return view;
    }
}

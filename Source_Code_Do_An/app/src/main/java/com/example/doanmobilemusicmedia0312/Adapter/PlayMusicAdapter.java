package com.example.doanmobilemusicmedia0312.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.adapter.FragmentViewHolder;
import androidx.viewpager2.widget.ViewPager2;

import com.example.doanmobilemusicmedia0312.Fragment.PlayingMusicFragment;
import com.example.doanmobilemusicmedia0312.Fragment.PlayingMusicLyricsFrament;
import com.example.doanmobilemusicmedia0312.Interface.IToolbarHandler;
import com.example.doanmobilemusicmedia0312.R;

import java.util.List;

public class PlayMusicAdapter extends FragmentStateAdapter {

    IToolbarHandler listener;
    public PlayMusicAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0: return new PlayingMusicFragment();
            case 1: return new PlayingMusicLyricsFrament();
            default: return null;
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }

    public void setToolbarListener(IToolbarHandler listener){
        this.listener = listener;
    }

    @Override
    public void onBindViewHolder(@NonNull FragmentViewHolder holder, int position, @NonNull List<Object> payloads) {
        super.onBindViewHolder(holder, position, payloads);

        Fragment fragment = this.createFragment(position);
        if (fragment instanceof PlayingMusicFragment) {
            ((PlayingMusicFragment) fragment).setToolbarListener(listener);
        }else if (fragment instanceof PlayingMusicLyricsFrament) {
            ((PlayingMusicLyricsFrament) fragment).setToolbarListener(listener);
        }
    }
}


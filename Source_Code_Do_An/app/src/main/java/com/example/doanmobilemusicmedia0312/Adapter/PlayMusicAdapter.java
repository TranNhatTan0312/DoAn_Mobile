package com.example.doanmobilemusicmedia0312.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.adapter.FragmentViewHolder;

import com.example.doanmobilemusicmedia0312.Fragment.PlayingMusicFragment;
import com.example.doanmobilemusicmedia0312.Fragment.PlayingMusicLyricsFrament;
import com.example.doanmobilemusicmedia0312.Interface.IToolbarHandler;
import com.example.doanmobilemusicmedia0312.Model.MusicModel;

import java.util.ArrayList;
import java.util.List;

public class PlayMusicAdapter extends FragmentStateAdapter {
    MusicModel song;
    boolean isNewSong,isPlaylist;
    ArrayList<MusicModel> playlist = null;
    IToolbarHandler listener;
    public PlayMusicAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0: return new PlayingMusicFragment(song, isNewSong, playlist);
            case 1: return new PlayingMusicLyricsFrament();
            default: return null;
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
    public void setSong(MusicModel song){ this.song = song;}
    public void setIsNewSong(boolean isNewSong){ this.isNewSong = isNewSong;}
    public void setIsPlaylist(boolean isPlaylist){ this.isPlaylist = isPlaylist;}
    public void setPlayList(ArrayList<MusicModel> playlist){ this.playlist = playlist;}

    public MusicModel getSong(){ return song; }

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


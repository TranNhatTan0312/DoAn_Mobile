package com.example.doanmobilemusicmedia0312.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.doanmobilemusicmedia0312.Interface.IToolbarHandler;
import com.example.doanmobilemusicmedia0312.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PlayingMusicFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PlayingMusicFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ImageView moreOption, back;

    private static IToolbarHandler toolbarListener;
    public PlayingMusicFragment() {
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PlayingMusicFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PlayingMusicFragment newInstance(String param1, String param2) {
        PlayingMusicFragment fragment = new PlayingMusicFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_playing_music, container, false);

        moreOption = (ImageView)view.findViewById(R.id.btnOption);
        back = (ImageView)view.findViewById(R.id.btnBack);

        moreOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toolbarListener.onMoreOptionSongClick();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toolbarListener.onBackButtonClick();
            }
        });

        // Inflate the layout for this fragment
        return view;
    }

    public void setToolbarListener(IToolbarHandler listener){
        this.toolbarListener = listener;
    }

}
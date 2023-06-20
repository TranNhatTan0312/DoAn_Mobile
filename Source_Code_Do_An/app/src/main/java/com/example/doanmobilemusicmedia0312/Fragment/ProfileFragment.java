package com.example.doanmobilemusicmedia0312.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.doanmobilemusicmedia0312.Adapter.SearchMusicDetailAdapter;
import com.example.doanmobilemusicmedia0312.EditProfileActivity;
import com.example.doanmobilemusicmedia0312.Model.SearchSongModel;
import com.example.doanmobilemusicmedia0312.MyPlaylistActivity;
import com.example.doanmobilemusicmedia0312.R;
import com.example.doanmobilemusicmedia0312.SearchDetailActivity;
import com.example.doanmobilemusicmedia0312.Utils.SqliteHelper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;


public class ProfileFragment extends Fragment {

    private TextView edit_profile,number_playlist;
    private RelativeLayout go_to_playlist;
    SqliteHelper sqliteHelper;
    private TextView txt_name;
    SharedPreferences sharedPreferences;
    String username;

    public ProfileFragment() {
    }
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        edit_profile = view.findViewById(R.id.edit_profile_information);
        go_to_playlist = view.findViewById(R.id.go_to_playlist);
        number_playlist = view.findViewById(R.id.number_playlist);
        txt_name = view.findViewById(R.id.txt_name);

        sharedPreferences  = getContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        username = sharedPreferences.getString("username","");

        addEvents();

        return view;
    }

    private void addEvents() {
        edit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), EditProfileActivity.class);
                startActivity(intent);
            }
        });

        go_to_playlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MyPlaylistActivity.class);
                startActivity(intent);
            }
        });

        txt_name.setText(username);
        sqliteHelper = new SqliteHelper(getActivity());
        number_playlist.setText(sqliteHelper.getNumberOfSongPlaylist()+"");
    }
}
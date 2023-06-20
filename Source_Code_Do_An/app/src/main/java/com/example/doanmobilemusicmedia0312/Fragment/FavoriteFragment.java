package com.example.doanmobilemusicmedia0312.Fragment;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.doanmobilemusicmedia0312.Adapter.FavoriteSongAdapter;
import com.example.doanmobilemusicmedia0312.Adapter.MoodAdapter;
import com.example.doanmobilemusicmedia0312.Model.MusicModel;
import com.example.doanmobilemusicmedia0312.MoodActivity;
import com.example.doanmobilemusicmedia0312.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;


public class FavoriteFragment extends Fragment {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    ArrayList<MusicModel> musicList = new ArrayList<>();
    public FavoriteFragment() {
        // Required empty public constructor
    }
    private SharedPreferences sharedPreferences;
    String username;
    RecyclerView recyclerView;

    public static FavoriteFragment newInstance(String param1, String param2) {
        FavoriteFragment fragment = new FavoriteFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences  = getContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        username = sharedPreferences.getString("username","");


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_favorite, container, false);

        recyclerView = view.findViewById(R.id.rcyPlaylist);

        db.collection("favorites").document(username)
                .collection("songs")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {


                        if (!queryDocumentSnapshots.isEmpty()) {
                            for (DocumentSnapshot document : queryDocumentSnapshots.getDocuments()) {

                                // Tạo một đối tượng ModelSong mới
                                MusicModel item = new MusicModel();
                                item.setId(document.getId());
                                item.setImageUrl(document.getString("cover_image"));
                                item.setSourceUrl(document.getString("url"));
                                item.setGenre(document.getString("genre"));
                                item.setLength(document.getString("length"));
                                item.setSinger(document.getString("singer"));
                                item.setSongName(document.getString("name"));
                                item.setDateRelease(document.getString("date_release"));

                                // Thêm đối tượng song vào danh sách
                                musicList.add(item);

                            }
                        }


                        // Lấy đối tượng Activity chứa Fragment
                        Activity activity = getActivity();

                        if (activity != null) {
                            // Tạo adapter và truyền danh sách songList vào

                            RecyclerView.LayoutManager layoutManager =new LinearLayoutManager(activity);
                            recyclerView.setLayoutManager(layoutManager);

                            FavoriteSongAdapter customAdapter = new FavoriteSongAdapter(activity, musicList);
                            recyclerView.setAdapter(customAdapter);


                        } else {
                            Log.e("RecyclerView", "Activity is null");
                        }
                    }
                });

        return view;

    }
}
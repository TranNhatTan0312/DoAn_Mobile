package com.example.doanmobilemusicmedia0312.Fragment;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.doanmobilemusicmedia0312.Adapter.FavoriteSongAdapter;
import com.example.doanmobilemusicmedia0312.Model.FavoriteSongModel;
import com.example.doanmobilemusicmedia0312.Model.MusicModel;
import com.example.doanmobilemusicmedia0312.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FavoriteFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FavoriteFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FavoriteFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FavoriteFragment newInstance(String param1, String param2) {
        FavoriteFragment fragment = new FavoriteFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        RecyclerView recyclerView;
        db.collection("favorites").document("users")
                .collection("songs")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<FavoriteSongModel> songList = new ArrayList<>(); // Tạo danh sách để lưu các đối tượng ModelSong

                        if (!queryDocumentSnapshots.isEmpty()) {
                            for (DocumentSnapshot document : queryDocumentSnapshots.getDocuments()) {
                                String musicName = document.getString("name");
                                String musicSinger = document.getString("singer");
                                String musicLength = document.getString("length");
                                String img = document.getString("cover_image");

                                // Tạo một đối tượng ModelSong mới
                                FavoriteSongModel song = new FavoriteSongModel();
                                song.setMusicName(musicName);
                                song.setMusicSinger(musicSinger);
                                song.setLength(musicLength);
                                song.setImg(img);

                                // Thêm đối tượng song vào danh sách
                                songList.add(song);
                            }
                        }
                        RecyclerView recyclerView = getView().findViewById(R.id.rcyPlaylist);

                        // Lấy đối tượng Activity chứa Fragment
                        Activity activity = getActivity();

                        if (activity != null) {
                            LinearLayoutManager etLayoutManager(layoutManager);

                            // Tạo adapter và truyền danh sách songList vào
                            FavoriteSongAdapter adapter = new FavoriteSongAdapter(songList);layoutManager = new LinearLayoutManager(activity);
                            recyclerView.s
                            recyclerView.setAdapter(adapter);
                        } else {
                            Log.e("RecyclerView", "Activity is null");
                        }
                    }
                });
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite, container, false);
    }
}
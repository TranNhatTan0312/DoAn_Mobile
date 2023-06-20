package com.example.doanmobilemusicmedia0312.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.doanmobilemusicmedia0312.Model.MusicModel;
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

    public static FavoriteFragment newInstance(String param1, String param2) {
        FavoriteFragment fragment = new FavoriteFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences  = getContext().getSharedPreferences("users", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("username","");

        db.collection("favorites").document(username)
                .collection("songs")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if(queryDocumentSnapshots.isEmpty()){
                           for (DocumentSnapshot document : queryDocumentSnapshots.getDocuments()){
                               MusicModel model = new MusicModel();
                               model.setLength(document.get("length").toString());
                               model.setLength(document.get("length").toString());
                               model.setLength(document.get("length").toString());
                               model.setLength(document.get("length").toString());
                               model.setLength(document.get("length").toString());

                               musicList.add(model);
                           }
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
package com.example.doanmobilemusicmedia0312.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.example.doanmobilemusicmedia0312.Adapter.SearchMusicAdapter;
import com.example.doanmobilemusicmedia0312.Model.SearchSongModel;
import com.example.doanmobilemusicmedia0312.R;
import com.example.doanmobilemusicmedia0312.SearchDetailActivity;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class SearchFragment extends Fragment {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    SearchView searchView;
    GridView simpleGrid;

    ArrayList<SearchSongModel> data = new ArrayList<>();



    public SearchFragment() {
        // Required empty public constructor
    }

    public static SearchFragment newInstance(String param1, String param2) {
        SearchFragment fragment = new SearchFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        simpleGrid = (GridView) view.findViewById(R.id.simpleGridView);
        searchView = view.findViewById(R.id.searchView);

        db.collection("songs").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if(!queryDocumentSnapshots.isEmpty()){
                    for (DocumentSnapshot document : queryDocumentSnapshots.getDocuments()){
                        SearchSongModel item = new SearchSongModel();
                        item.setSongId(document.getId());
                        item.setImg(document.getString("cover_image"));
                        item.setMusicName(document.getString("name"));
                        item.setMusicNum(document.getString("singer"));
                        data.add(item);
                    }
                    SearchMusicAdapter customAdapter = new SearchMusicAdapter(getContext(), data);
                    simpleGrid.setAdapter(customAdapter);
                }

            }
        });



        searchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    Intent intent = new Intent(getContext(), SearchDetailActivity.class);
                    startActivity(intent);
                }
            }
        });

        return view;
    }
}
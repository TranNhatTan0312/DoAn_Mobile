package com.example.doanmobilemusicmedia0312.Fragment;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.TextView;

import com.example.doanmobilemusicmedia0312.MoodActivity;
import com.example.doanmobilemusicmedia0312.R;
import com.example.doanmobilemusicmedia0312.SettingActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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

    GridLayout mood1;
    GridLayout mood2;
    GridLayout mood3;
    GridLayout mood4;
    GridLayout mood5;

    GridLayout concert1;

    GridLayout singer1;
    GridLayout singer2;
    GridLayout singer3;
    GridLayout singer4;

    CardView trending_songs;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        mood1 = view.findViewById(R.id.mood1);
        mood2 = view.findViewById(R.id.mood2);
        mood3 = view.findViewById(R.id.mood3);
        mood4 = view.findViewById(R.id.mood4);
        mood5 = view.findViewById(R.id.mood5);

        concert1 = view.findViewById(R.id.concert1);

        singer1 = view.findViewById(R.id.singer1);
        singer2 = view.findViewById(R.id.singer2);
        singer3 = view.findViewById(R.id.singer3);
        singer4 = view.findViewById(R.id.singer4);

        trending_songs = view.findViewById(R.id.trending_songs);
        mood1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MoodActivity.class);
                intent.putExtra("mood","morning" );
                intent.putExtra("type","mood");


                startActivity(intent);

            }
        });

        mood2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MoodActivity.class);
                intent.putExtra("mood","rb" );
                intent.putExtra("type","mood");

                startActivity(intent);

            }
        });

        mood3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MoodActivity.class);
                intent.putExtra("mood","romance" );
                intent.putExtra("type","mood");

                startActivity(intent);

            }
        });

        mood4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MoodActivity.class);
                intent.putExtra("mood","hiphop" );
                intent.putExtra("type","mood");

                startActivity(intent);

            }
        });

        mood5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MoodActivity.class);
                intent.putExtra("mood","dance" );
                intent.putExtra("type","mood");

                startActivity(intent);

            }
        });

        concert1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MoodActivity.class);
                intent.putExtra("concert","freedivine" );
                intent.putExtra("type","concert");
                startActivity(intent);
            }
        });

        singer1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MoodActivity.class);
                intent.putExtra("singer","Selena Gomez" );
                intent.putExtra("type","singer");
                startActivity(intent);
            }
        });
        singer2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MoodActivity.class);
                intent.putExtra("singer","Zyan Malyk" );
                intent.putExtra("type","singer");
                startActivity(intent);
            }
        });

        singer3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MoodActivity.class);
                intent.putExtra("singer","Divine" );
                intent.putExtra("type","singer");
                startActivity(intent);
            }
        });

        singer4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MoodActivity.class);
                intent.putExtra("singer","Shakira" );
                intent.putExtra("type","singer");
                startActivity(intent);
            }
        });

        trending_songs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MoodActivity.class);
                intent.putExtra("type","trending");
                startActivity(intent);
            }
        });

        return view;
    }
}
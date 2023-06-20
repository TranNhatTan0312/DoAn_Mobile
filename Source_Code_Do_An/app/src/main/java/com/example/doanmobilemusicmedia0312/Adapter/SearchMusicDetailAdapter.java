package com.example.doanmobilemusicmedia0312.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ImageButton;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doanmobilemusicmedia0312.Model.MusicModel;
import com.example.doanmobilemusicmedia0312.Model.SearchSongModel;
import com.example.doanmobilemusicmedia0312.PlayMusicActivity;
import com.example.doanmobilemusicmedia0312.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

public class SearchMusicDetailAdapter extends RecyclerView.Adapter<SearchMusicDetailAdapter.MyHolder> {

    Context context;
    ArrayList<MusicModel> arrayList;
    LayoutInflater layoutInflater;


    public SearchMusicDetailAdapter(Context context, ArrayList<MusicModel> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
        layoutInflater = LayoutInflater.from(context);
    }


    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.search_music_detail_item, parent, false);
        return new MyHolder(view);
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        TextView musicName, musicNum;
        ImageView img;
        ImageButton deleteButton;
        int position;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            musicName = itemView.findViewById(R.id.txt);
            musicNum = itemView.findViewById(R.id.txt2);
            img = itemView.findViewById(R.id.img);
            deleteButton = itemView.findViewById(R.id.deleteButton);
            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int clickedPosition = getAdapterPosition();
                    if (clickedPosition != RecyclerView.NO_POSITION) {
                        // Truy cập Firebase Firestore
                        FirebaseFirestore db = FirebaseFirestore.getInstance();
                        CollectionReference searchHistoryRef = db.collection("search_history");

                        MusicModel clickedItem = arrayList.get(clickedPosition);
                        Query query = searchHistoryRef.whereEqualTo("name", clickedItem.getSongName());
                        query.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                if (!queryDocumentSnapshots.isEmpty()) {
                                    for (DocumentSnapshot document : queryDocumentSnapshots.getDocuments()) {
                                        searchHistoryRef.document(document.getId()).delete()
                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {
                                                        arrayList.remove(clickedPosition);
                                                        notifyItemRemoved(clickedPosition);
                                                    }
                                                })
                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        // Xử lý khi xóa thất bại
                                                    }
                                                });
                                    }
                                }
                            }
                        });
                    }
                }
            });
        }
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        MusicModel searchModelClass = arrayList.get(position);
        holder.musicName.setText(searchModelClass.getSongName());
        holder.musicNum.setText(String.valueOf(searchModelClass.getSinger()));
        Glide.with(context)
                .load(searchModelClass.getImageUrl())
                .into(holder.img);


        // Xử lý khi bấm vào một mục trong danh sách kết quả tìm kiếm
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Truy cập Firebase Firestore
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                CollectionReference searchHistoryRef = db.collection("search_history");

                // Kiểm tra xem bài hát đã tồn tại trong search_history chưa
                Query query = searchHistoryRef.whereEqualTo("name", searchModelClass.getSongName());
                query.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (queryDocumentSnapshots.isEmpty()) {
                            Map<String, Object> songData = new HashMap<>();

                            songData.put("name", searchModelClass.getSongName());
                            songData.put("singer", searchModelClass.getSinger());
                            songData.put("url", searchModelClass.getSourceUrl());
                            songData.put("cover_image", searchModelClass.getImageUrl());
                            songData.put("genre", searchModelClass.getGenre());
                            songData.put("length", searchModelClass.getLength());
                            songData.put("date_release", searchModelClass.getDateRelease());

                            System.out.println("haha");

                            searchHistoryRef.add(songData)
                                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                        @Override
                                        public void onSuccess(DocumentReference documentReference) {
                                            System.out.println("đúng");
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                        }
                                    });
                        }
                    }
                });

                // Tạo intent để chuyển đến trang phát nhạc
                Intent intent = new Intent(context, PlayMusicActivity.class);

                // Tạo bundle để truyền dữ liệu bài hát
                Bundle bundle = new Bundle();
                bundle.putSerializable("SONG", searchModelClass);
                bundle.putBoolean("PLAYLIST", false);
                bundle.putBoolean("NEWSONG", true);

                // Đặt bundle làm dữ liệu cho intent
                intent.putExtra("data", bundle);

                // Chạy intent để chuyển đến trang phát nhạc
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }


}

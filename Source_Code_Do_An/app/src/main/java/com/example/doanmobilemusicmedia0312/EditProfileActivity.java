package com.example.doanmobilemusicmedia0312;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.doanmobilemusicmedia0312.Model.Users;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class EditProfileActivity extends AppCompatActivity {

    ImageView back;
    EditText gender, dob,phone, pass, name;
    Button save;


    TextView idd ;
    ImageView avt;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    void PullData() {
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String getEmail = sharedPreferences.getString("username", "");

        db.collection("users")
                .whereEqualTo("email", getEmail)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            QuerySnapshot querySnapshot = task.getResult();
                            if (querySnapshot != null) {
                                for (QueryDocumentSnapshot document : querySnapshot) {
                                    // Xử lý dữ liệu từ mỗi document
                                    gender.setText(document.getString("gender"));
                                    dob.setText(document.getString("date_of_birth"));
                                    name.setText(document.getString("full_name"));
                                    phone.setText(document.getString("phone_number"));
//                                  pass.setText(document.getString("password"));

                                }
                            } else {
                                Toast.makeText(getApplicationContext(), "Không có thông tin", Toast.LENGTH_LONG).show();
                            }
                        } else {
                            //Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }



    void PushData(){
        String data_phone = phone.getText().toString();
        String data_name = name.getText().toString();
        String data_dob = dob.getText().toString();
        String data_pass = pass.getText().toString();
        String data_gender = gender.getText().toString();

        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String getEmail = sharedPreferences.getString("username", "");

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("users").document(getEmail);

        // Sử dụng phương thức update() để đẩy dữ liệu lên field đã có sẵn
        Map<String, Object> user = new HashMap<>();
        Users users1 = new Users(data_name, getEmail,data_phone, data_pass,data_gender, data_dob);
        user.put("full_name", users1.getFullName());
        user.put("email", users1.getEmail());
        user.put("gender", users1.getGender());
        user.put("avatar_url", users1.getAvatar());
        user.put("date_of_birth", users1.getBirth());
        user.put("phone_number", users1.getPhone());
        // user.put("password", user1.getPassword());
        user.put("password", Encrypt.HashPasswordMd5(data_pass));

        docRef.set(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Đẩy dữ liệu thành công
                        Toast.makeText(getApplicationContext(), "Cap nhat thanh cong", Toast.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Xử lý khi đẩy dữ liệu không thành công
                        Toast.makeText(getApplicationContext(), "Cap nhat that bai", Toast.LENGTH_LONG).show();
                    }
                });
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        save = findViewById(R.id.btn_Save);
        back = findViewById(R.id.back_edit_profile);
        avt = findViewById(R.id.Edit1_avt);
        phone = findViewById(R.id.Edit1_phone);
        dob = findViewById(R.id.Edit1_dob);
        pass = findViewById(R.id.Edit1_pass);
        gender = findViewById(R.id.Edit1_gender);
        idd = findViewById(R.id.id_user);
        name = findViewById(R.id.Edi1_Fullname);

        PullData();


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                PushData();
                // user.put("password", Encrypt.HashPasswordMd5(Password));
            }
        });
    }



}
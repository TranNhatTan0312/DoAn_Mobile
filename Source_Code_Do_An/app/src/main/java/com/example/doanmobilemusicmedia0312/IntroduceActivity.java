package com.example.doanmobilemusicmedia0312;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;

public class IntroduceActivity extends AppCompatActivity {

    Button btn_start;

    private SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        sharedPreferences  = getSharedPreferences("users", Context.MODE_PRIVATE);

        btn_start = findViewById(R.id.btn_started);
        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = sharedPreferences.getString("username","");
                if(username.equals("")){
                    Intent intent = new Intent(getApplicationContext(),Introduce2Activity.class);
                    startActivity(intent);
                    finish();
                }else{
                    Intent main = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(main);
                    finish();
                }

            }
        });
    }
}
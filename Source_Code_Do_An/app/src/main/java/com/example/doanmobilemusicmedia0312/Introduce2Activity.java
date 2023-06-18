package com.example.doanmobilemusicmedia0312;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Introduce2Activity extends AppCompatActivity {

    Button btn_sin,btn_sup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introduce2);

        btn_sin = findViewById(R.id.btn_sIn);
        btn_sup = findViewById(R.id.btn_sUp);
        btn_sin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),SignInActivity.class);
                startActivity(intent);
                finish();
            }
        });
        btn_sup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
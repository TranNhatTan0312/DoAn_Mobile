package com.example.doanmobilemusicmedia0312;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.doanmobilemusicmedia0312.Model.Users;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    private EditText emailname, fullname, password;

    // creating variable for button
    private Button signupBtn;

    // creating a strings for storing
    // our values from edittext fields.
    private String emailName, fullName, Password;
    TextView SignUp;
    // creating a variable
    // for firebasefirestore
    private FirebaseFirestore db;

    private String encryptPassword(String password) {
        try {
            // Tạo một đối tượng MessageDigest với thuật toán MD5
            MessageDigest md = MessageDigest.getInstance("MD5");

            // Chuyển đổi chuỗi mật khẩu thành mảng byte
            byte[] passwordBytes = password.getBytes();

            // Mã hóa mảng byte của mật khẩu
            byte[] encryptedBytes = md.digest(passwordBytes);

            // Chuyển đổi mảng byte thành chuỗi hex
            StringBuilder sb = new StringBuilder();
            for (byte b : encryptedBytes) {
                sb.append(String.format("%02x", b));
            }

            // Trả về chuỗi mã hóa
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        // Nếu xảy ra lỗi, trả về null hoặc giá trị mặc định
        return null;
    }

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // getting our instance
        // from Firebase Firestore.
        db = FirebaseFirestore.getInstance();

        // initializing our edittext and buttons
        fullname = findViewById(R.id.SignUP_Fullname);
        emailname = findViewById(R.id.SignUP_EMail);
        password = findViewById(R.id.SignUp_Password);
        signupBtn = findViewById(R.id.btn_Register);
        SignUp = findViewById(R.id.BackToLogin);
        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SignInActivity.class);
                startActivity(intent);
                finish();
            }
        });
        // adding on click listener for button
        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // getting data from edittext fields.
                emailName = emailname.getText().toString();
                fullName = fullname.getText().toString();
                Password = password.getText().toString();

                // validating the text fields if empty or not.
                if (TextUtils.isEmpty(fullName)) {
                    fullname.setError("Full name phải trên 6 ký tự");
                }
                if(Validate.ValidateGmail(emailName) == false){
                    fullname.setError("Email phải trên 6 ký tự");
                }
                if(Validate.ValidatePassword(Password) == false){
                    fullname.setError("Password phải trên 6 ký tự");;
                }
                else {
                    // calling method to add data to Firebase Firestore.
                    addDataToFirestore(emailName, fullName,Password);

                }
            }
        });
    }

    private void addDataToFirestore(String _email, String full_name, String Pas) {

        // creating a collection reference
        // for our Firebase Firestore database.
        CollectionReference dbUsers = db.collection("users");

        // adding our data to our users object class.
        Users users1 = new Users(fullName, emailName,Password);
        Map<String, Object> user = new HashMap<>();
        user.put("full_name", users1.getFullName());
        user.put("email", users1.getEmail());
        user.put("gender", users1.getGender());
        user.put("avatar_url", users1.getAvatar());
        user.put("date_of_birth", users1.getBirth());
        user.put("phone_number", users1.getPhone());
        // user.put("password", user1.getPassword());
        user.put("password", Encrypt.HashPasswordMd5(Password));

        // below method is use to add data to Firebase Firestore.
        dbUsers.add(user).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                // after the data addition is successful
                // we are displaying a success toast message.
                Toast.makeText(RegisterActivity.this, "Dang ky thanh cong", Toast.LENGTH_LONG).show();
                emailname.setText("");
                fullname.setText("");
                password.setText("");

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // this method is called when the data addition process is failed.
                // displaying a toast message when data addition is failed.
                Log.d("abc",e.getStackTrace().toString());
                Toast.makeText(RegisterActivity.this, "Dang ky that bai \n" + e.getStackTrace().toString(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
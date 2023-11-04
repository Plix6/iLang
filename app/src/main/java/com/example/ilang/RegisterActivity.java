package com.example.ilang;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.Console;
import java.util.HashMap;
import java.util.Map;


public class RegisterActivity extends AppCompatActivity {
    FirebaseAuth mAuth;
    FirebaseFirestore db;
    TextView registerRedirect;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration_layout);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        Button registerBtn = findViewById(R.id.confirmButton);
        EditText username = findViewById(R.id.loginChoice);
        EditText password = findViewById(R.id.passwordChoice);
        EditText passwordConfirm = findViewById(R.id.passwordConfirmation);
        registerRedirect = findViewById(R.id.registerRedirectText);

        registerRedirect.setOnClickListener(v -> finish());

        registerBtn.setOnClickListener(v -> {
            if (username.getText().toString().isEmpty() || password.getText().toString().isEmpty() || passwordConfirm.getText().toString().isEmpty()) {
                Toast.makeText(RegisterActivity.this, "Fill the login form first", Toast.LENGTH_SHORT).show();
                return;
            }
            if (password.getText().toString().compareTo(passwordConfirm.getText().toString()) != 0) {
                Toast.makeText(RegisterActivity.this, "Enter the same password twice", Toast.LENGTH_SHORT).show();
                return;
            }

            mAuth.createUserWithEmailAndPassword(username.getText().toString(), password.getText().toString())
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            addUser(user.getUid());
                        } else {
                            Toast.makeText(RegisterActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        });
    }

    private void addUser(String id) {
        Map<String, Object> userData = new HashMap<>();
        userData.put("en", 0);
        db.collection("Users").document(id)
                .set(userData)
                .addOnSuccessListener(unused -> {
                    Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                })
                .addOnFailureListener(e -> Toast.makeText(RegisterActivity.this, "Communication with online database failed, pleas retry later", Toast.LENGTH_SHORT).show());
    }
}


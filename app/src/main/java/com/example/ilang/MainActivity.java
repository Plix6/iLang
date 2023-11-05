package com.example.ilang;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ilang.fragments.LanguageRecyclerViewAdapter;
import com.example.ilang.pojo.Language;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    FirebaseAuth mAuth;
    FirebaseFirestore db;
    private RecyclerView languageRecyclerView;
    private LanguageRecyclerViewAdapter adapter;
    private TextView currentTextView;
    private TextView languageScore;
    private String languageCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        ArrayList<Language> itemList = generateItems();

        languageRecyclerView = findViewById(R.id.languageRecyclerView);
        currentTextView = findViewById(R.id.selectedItemRecyclerView);
        languageScore = findViewById(R.id.languageScore);
        adapter = new LanguageRecyclerViewAdapter(itemList, language -> {
            currentTextView.setText(language.getTitle());
            languageCode = language.getCode();
            getUserScore();
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        languageRecyclerView.setLayoutManager(layoutManager);

        languageRecyclerView.setAdapter(adapter);


        Button exerciseButton = findViewById(R.id.exerciseButton);
        exerciseButton.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString("targetLanguageCode", languageCode);
            Intent intent = new Intent(MainActivity.this, ExerciseActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        getUserScore();
    }

    private ArrayList<Language> generateItems() {
        ArrayList<Language> itemList = new ArrayList<Language>(){{
            add(new Language("English", "en"));
            add(new Language("French", "fr"));
            add(new Language("Italian", "it"));
        }};

        return itemList;
    }

    private void getUserScore() {
        FirebaseUser user = mAuth.getCurrentUser();

        db.collection("Users").document(user.getUid())
                .get().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            Map<String, Object> data = document.getData();
                            if (data.get(languageCode) != null) {
                                languageScore.setText("Current score for selected language is : " + data.get(languageCode));
                            } else {
                                data.put(languageCode, 0);
                                languageScore.setText("New language");
                                db.collection("Users").document(user.getUid())
                                        .set(data);
                            }
                        } else {
                            Toast.makeText(MainActivity.this, "User data not found, please restart the app", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(MainActivity.this, "Online database could not be accessed, please retry later", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
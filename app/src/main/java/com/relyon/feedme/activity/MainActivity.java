package com.relyon.feedme.activity;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.relyon.feedme.Util;
import com.relyon.feedme.databinding.ActivityMainBinding;
import com.relyon.feedme.model.User;

import java.time.LocalDate;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    private FirebaseAuth mAuth;
    private GoogleSignInClient googleSignInClient;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseFirestore db;
    private GoogleSignInAccount account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mAuth = FirebaseAuth.getInstance();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(
                GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(this, gso);

        account = GoogleSignIn.getLastSignedInAccount(this);

        if (account != null) {
            String name = account.getDisplayName();
            String email = account.getEmail();
            binding.email.setText(email);
            binding.name.setText(name);
        }

        authStateListener = firebaseAuth -> {
            FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
            if (firebaseUser != null) {
                createDbConnection();
                retrieveUserFromDB(firebaseUser.getUid());
            } else {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            }
        };

        binding.logout.setOnClickListener(tv -> {
            logout();
        });

        binding.addRecipeButton.setOnClickListener(view -> {
            startActivity(new Intent(getApplicationContext(), AddRecipeActivity.class));
        });

        binding.profileButton.setOnClickListener(view -> {
            startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
        });
    }

    private void retrieveUserFromDB(String id) {
        db.collection("users").document(id)
                .get().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            Log.d(TAG, "DocumentSnapshot data: " + document.getData());

                        } else {
                            Log.d(TAG, "No such document");
                            User user = document.toObject(User.class);
                            if (user == null && account != null) {
                                user = createUser(id);
                            }
                            Util.setUser(user);
                        }
                    } else {
                        Log.d(TAG, "get failed with ", task.getException());
                    }
                });
    }

    private User createUser(String id) {

        User user = new User(id, account.getDisplayName(), account.getEmail(), LocalDate.now().toString(), 0.0);

        db.collection("users").document(id)
                .set(user);

        return user;
    }

    private void createDbConnection() {
        db = FirebaseFirestore.getInstance();
        Util.setDb(db);
    }

    private void logout() {
        googleSignInClient.signOut().addOnCompleteListener(task -> {
            mAuth.signOut();
            finish();
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(authStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (authStateListener != null) {
            mAuth.removeAuthStateListener(authStateListener);
        }
    }
}
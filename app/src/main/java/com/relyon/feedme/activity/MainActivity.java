package com.relyon.feedme.activity;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.relyon.feedme.R;
import com.relyon.feedme.Util;
import com.relyon.feedme.activity.fragment.bottommenu.AlertFragment;
import com.relyon.feedme.activity.fragment.bottommenu.HomeFragment;
import com.relyon.feedme.activity.fragment.bottommenu.ProfileFragment;
import com.relyon.feedme.activity.fragment.bottommenu.RankingFragment;
import com.relyon.feedme.databinding.ActivityMainBinding;
import com.relyon.feedme.model.User;

import java.time.LocalDate;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private GoogleSignInClient googleSignInClient;

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

        googleSignInClient = GoogleSignIn.getClient(getApplicationContext(), gso);

        authStateListener = firebaseAuth -> {
            FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
            if (firebaseUser != null) {
                retrieveUserFromDB(firebaseUser.getUid());
            } else {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            }
        };

        binding.navView.setSelectedItemId(R.id.navigation_home);

        binding.navView.setOnItemSelectedListener(item -> {

            int itemId = item.getItemId();
            if (itemId == R.id.navigation_home) {
                replaceFragment(new HomeFragment());
            } else if (itemId == R.id.navigation_ranking) {
                replaceFragment(new RankingFragment());
            } else if (itemId == R.id.navigation_profile) {
                replaceFragment(new ProfileFragment());
            } else if (itemId == R.id.navigation_alerts) {
                replaceFragment(new AlertFragment());
            }

            return true;
        });
    }

    public void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.nav_host_fragment_activity_main, fragment);
        fragmentTransaction.commit();
    }

    private void retrieveUserFromDB(String id) {
        Util.db.collection("users").document(id)
                .get().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                            Util.user = document.toObject(User.class);
                        } else {
                            Log.d(TAG, "No such document");
                            User user = document.toObject(User.class);
                            if (user == null && mAuth.getCurrentUser() != null) {
                                user = createUser(id, null, null);
                                Util.user = user;
                            }
                        }
                    } else {
                        Log.d(TAG, "get failed with ", task.getException());
                    }
                });
    }

    private User createUser(String id, String name, String lastName) {
        User user;
        user = new User(id, name, lastName, mAuth.getCurrentUser().getDisplayName(), mAuth.getCurrentUser().getEmail(), LocalDate.now().toString(), 0.0, mAuth.getCurrentUser().getPhotoUrl() != null ? mAuth.getCurrentUser().getPhotoUrl().toString() : null);
        Util.db.collection("users").document(id)
                .set(user);
        return user;
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(authStateListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (authStateListener != null) {
            mAuth.removeAuthStateListener(authStateListener);
        }
    }
}
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
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.relyon.feedme.R;
import com.relyon.feedme.Util;
import com.relyon.feedme.activity.fragment.RecipeFragment;
import com.relyon.feedme.activity.fragment.bottomtabs.AlertFragment;
import com.relyon.feedme.activity.fragment.bottomtabs.HomeFragment;
import com.relyon.feedme.activity.fragment.bottomtabs.ProfileFragment;
import com.relyon.feedme.activity.fragment.bottomtabs.RankingFragment;
import com.relyon.feedme.databinding.ActivityMainBinding;
import com.relyon.feedme.model.Recipe;
import com.relyon.feedme.model.User;
import com.relyon.feedme.recyclerviews.RecipeRecyclerViewAdapter;

import java.time.LocalDate;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseFirestore db;
    private GoogleSignInClient googleSignInClient;

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

        googleSignInClient = GoogleSignIn.getClient(getApplicationContext(), gso);

        account = GoogleSignIn.getLastSignedInAccount(getApplicationContext());

        authStateListener = firebaseAuth -> {
            FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
            if (firebaseUser != null) {
                createDbConnection();
                retrieveUserFromDB(firebaseUser.getUid());
            } else {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            }
        };

        binding.navView.setSelectedItemId(R.id.navigation_home);

        binding.navView.setOnItemSelectedListener(item -> {

            switch (item.getItemId()) {
                case R.id.navigation_home:
                    replaceFragment(new HomeFragment());
                    break;
                case R.id.navigation_ranking:
                    replaceFragment(new RankingFragment());
                    break;
                case R.id.navigation_profile:
                    replaceFragment(new ProfileFragment());
                    break;
                case R.id.navigation_alerts:
                    replaceFragment(new AlertFragment());
                    break;
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
        db.collection("users").document(id)
                .get().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                            User user = document.toObject(User.class);
                            Util.setUser(user);
                        } else {
                            Log.d(TAG, "No such document");
                            User user = document.toObject(User.class);
                            if (user == null && account != null) {
                                user = createUser(id);
                            }
                            Util.setUser(user);
                        }
                        Util.getDb().collection("users").document(Util.getUser().getId()).collection("favoriteRecipes").get();
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
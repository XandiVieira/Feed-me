package com.relyon.feedme.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.FirebaseFirestore;
import com.relyon.feedme.R;
import com.relyon.feedme.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private GoogleSignInClient googleSignInClient;
    private FirebaseAuth mAuth;
    private FirebaseFirestore firebaseFirestore;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityLoginBinding.inflate(getLayoutInflater());

        View view = binding.getRoot();
        setContentView(view);

        mAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(
                GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(this, gso);

        binding.googleButton.setSize(SignInButton.SIZE_WIDE);
        binding.googleButton.setColorScheme(SignInButton.COLOR_AUTO);
        binding.googleButton.setOnClickListener(view1 -> signInGoogle());
        setGooglePlusButtonText(binding.googleButton, "Fazer Login");

        binding.registerLink.setOnClickListener(view1 -> {
            startActivity(new Intent(getApplicationContext(), RegistrationActivity.class));
        });

        binding.backButton.setOnClickListener(view1 -> {
            startActivity(new Intent(getApplicationContext(), OnBoardingActivity.class));
        });

        binding.loginButton.setOnClickListener(view1 -> {
            Editable email = binding.emailInput.getText();
            Editable password = binding.passwordInput.getText();
            if (credentialsAreValid(email, password))
                mAuth.signInWithEmailAndPassword(email.toString(), password.toString())
                        .addOnCompleteListener(this, task -> {
                            if (task.isSuccessful()) {
                                if (task.getResult().getAdditionalUserInfo() != null && task.getResult().getAdditionalUserInfo().isNewUser()) {
                                    onBoardingActivity();
                                } else {
                                    homeActivity();
                                }
                            } else {
                                Log.w("Fail", "createUserWithEmail:failure", task.getException());
                                Toast.makeText(LoginActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
        });
    }

    protected void setGooglePlusButtonText(SignInButton signInButton, String buttonText) {
        // Find the TextView that is inside of the SignInButton and set its text
        for (int i = 0; i < signInButton.getChildCount(); i++) {
            View v = signInButton.getChildAt(i);

            if (v instanceof TextView) {
                TextView tv = (TextView) v;
                tv.setText(buttonText);
                return;
            }
        }
    }

    private boolean credentialsAreValid(Editable email, Editable password) {
        if (TextUtils.isEmpty(email.toString())) {
            binding.emailInput.setError("E-mail n??o pode estar vazio.");
        } else if (TextUtils.isEmpty(password.toString())) {
            binding.passwordInput.setError("Senha n??o pode estar vazia.");
        } else {
            return true;
        }
        return false;
    }

    private void signInGoogle() {
        Intent intent = googleSignInClient.getSignInIntent();
        openActivity.launch(intent);
    }

    ActivityResultLauncher<Intent> openActivity = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent data = result.getData();
                    Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
                    try {
                        GoogleSignInAccount acc = task.getResult(ApiException.class);
                        loginGoogle(acc.getIdToken());
                    } catch (ApiException e) {
                        Toast.makeText(this, "Fail", Toast.LENGTH_SHORT).show();
                    }
                }
            }
    );

    private void loginGoogle(String token) {
        AuthCredential credential = GoogleAuthProvider.getCredential(token, null);
        mAuth.signInWithCredential(credential).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                if (task.getResult().getAdditionalUserInfo() != null && task.getResult().getAdditionalUserInfo().isNewUser()) {
                    onBoardingActivity();
                } else {
                    homeActivity();
                }
                finish();
            } else {
                Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void homeActivity() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }

    private void onBoardingActivity() {
        Intent intent = new Intent(getApplicationContext(), OnBoardingActivity.class);
        startActivity(intent);
    }
}
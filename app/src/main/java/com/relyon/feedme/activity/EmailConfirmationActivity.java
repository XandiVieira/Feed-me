package com.relyon.feedme.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.relyon.feedme.CallActivity;
import com.relyon.feedme.databinding.ActivityEmailConfirmationBinding;

public class EmailConfirmationActivity extends AppCompatActivity {

    private ActivityEmailConfirmationBinding binding;
    private FirebaseAuth mAuth;
    private Bundle extras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityEmailConfirmationBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        extras = getIntent().getExtras();

        mAuth = FirebaseAuth.getInstance();

        binding.backButton.setOnClickListener(view -> CallActivity.openActivity(EmailConfirmationActivity.this, LoginActivity.class));

        binding.resendEmail.setOnClickListener(view -> resendEmail());

        binding.confirmButton.setOnClickListener(view -> login());

        binding.releaseButton.setOnClickListener(view -> setFloatingMessageVisibility(View.INVISIBLE));
    }

    private void resendEmail() {
        if (mAuth != null && mAuth.getCurrentUser() != null) {
            mAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(task1 -> {
                if (task1.isSuccessful()) {
                    setFloatingMessageVisibility(View.VISIBLE);
                } else {
                    Toast.makeText(getApplicationContext(), task1.getException().getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        } else {
            Toast.makeText(getApplicationContext(), "Ocorreu um erro. Tente logar novamente.", Toast.LENGTH_LONG).show();
        }
    }

    private void setFloatingMessageVisibility(int visibility) {
        binding.floatingMessage.setVisibility(visibility);
    }

    private void login() {
        if (extras != null) {
            String email = extras.getString("email");
            String password = extras.getString("password");
            if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {
                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this, task -> {
                            if (task.isSuccessful()) {
                                if (mAuth.getCurrentUser().isEmailVerified()) {
                                    if (task.getResult().getAdditionalUserInfo() != null && task.getResult().getAdditionalUserInfo().isNewUser()) {
                                        CallActivity.openActivity(EmailConfirmationActivity.this, OnBoardingActivity.class);
                                    } else {
                                        CallActivity.openActivity(this, MainActivity.class);
                                    }
                                } else {
                                    startActivity(new Intent(getApplicationContext(), EmailConfirmationActivity.class));
                                    Toast.makeText(getApplicationContext(), "Por favor, verifique seu e-mail.", Toast.LENGTH_LONG).show();
                                }
                            } else {
                                Log.w("Fail", "createUserWithEmail:failure", task.getException());
                                Toast.makeText(getApplicationContext(), "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
            } else {
                CallActivity.openActivity(EmailConfirmationActivity.this, LoginActivity.class);
            }
        } else {
            CallActivity.openActivity(EmailConfirmationActivity.this, LoginActivity.class);
        }
    }
}
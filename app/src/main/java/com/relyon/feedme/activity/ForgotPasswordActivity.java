package com.relyon.feedme.activity;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.relyon.feedme.R;
import com.relyon.feedme.databinding.ActivityForgotPasswordBinding;

public class ForgotPasswordActivity extends AppCompatActivity {

    private ActivityForgotPasswordBinding binding;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityForgotPasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mAuth = FirebaseAuth.getInstance();

        binding.recoverPasswordButton.setOnClickListener(view -> recoverPassword());
    }

    private void recoverPassword() {
        if (credentialsAreValid(binding.emailInput)) {
            sendPasswordRecoveringEmail();
        }
    }

    private void sendPasswordRecoveringEmail() {
        mAuth.sendPasswordResetEmail(binding.emailInput.getText().toString().trim()).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                finish();
                Toast.makeText(getApplicationContext(), "Verifique sua caixa de e-mail.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean credentialsAreValid(TextInputEditText email) {
        if (email == null || email.getText() == null || TextUtils.isEmpty(email.toString())) {
            changeToErrorMode(binding.email, binding.emailInput, "E-mail não deve estar vazio.", binding.emailSupportMessage);
            return false;
        } else if (!isValidEmail(email.getText())) {
            changeToErrorMode(binding.email, binding.emailInput, "E-mail inválido.", binding.emailSupportMessage);
            return false;
        }
        return true;
    }

    private boolean isValidEmail(Editable editable) {
        return !TextUtils.isEmpty(editable) && Patterns.EMAIL_ADDRESS.matcher(editable).matches();
    }

    private void changeToErrorMode(TextView field, EditText input, String errorMessage, TextView
            message) {
        field.setTextColor(getResources().getColor(R.color.red));
        message.setText(errorMessage);
        input.getBackground().setColorFilter(getResources().getColor(R.color.red), PorterDuff.Mode.SRC_ATOP);
        message.setVisibility(View.VISIBLE);
    }
}
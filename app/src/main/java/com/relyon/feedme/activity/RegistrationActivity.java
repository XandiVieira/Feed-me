package com.relyon.feedme.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.GoogleAuthProvider;
import com.relyon.feedme.R;
import com.relyon.feedme.Util;
import com.relyon.feedme.databinding.ActivityRegistrationBinding;
import com.relyon.feedme.model.User;

import java.time.LocalDate;

public class RegistrationActivity extends AppCompatActivity {

    private ActivityRegistrationBinding binding;
    private FirebaseAuth mAuth;
    private GoogleSignInClient googleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegistrationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mAuth = FirebaseAuth.getInstance();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(
                GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(this, gso);

        binding.googleButton.setOnClickListener(view1 -> signInGoogle());

        binding.nameInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() < 1) {
                    changeToErrorMode(binding.name, binding.nameInput, "O nome não deve estar vazio.", binding.nameSupportMessage);
                } else {
                    binding.nameSupportMessage.setVisibility(View.INVISIBLE);
                    changeFocusMode(true, binding.name, binding.nameInput);
                }
            }
        });
        binding.nameInput.setOnFocusChangeListener((view, b) -> changeFocusMode(b && binding.nameSupportMessage.getVisibility() == View.VISIBLE, binding.name, binding.nameInput));


        binding.lastNameInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() < 1) {
                    changeToErrorMode(binding.lastName, binding.lastNameInput, "O sobrenome não deve estar vazio.", binding.lastNameSupportMessage);
                } else {
                    binding.nameSupportMessage.setVisibility(View.INVISIBLE);
                    changeFocusMode(true, binding.lastName, binding.lastNameInput);
                }
            }
        });
        binding.lastNameInput.setOnFocusChangeListener((view, b) -> changeFocusMode(b && binding.lastNameSupportMessage.getVisibility() == View.VISIBLE, binding.lastName, binding.lastNameInput));


        binding.emailInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() < 1) {
                    changeToErrorMode(binding.email, binding.emailInput, "O e-mail não deve estar vazio.", binding.emailSupportMessage);
                } else if (!isValidEmail(editable)) {
                    changeToErrorMode(binding.email, binding.emailInput, "E-mail inválido.", binding.emailSupportMessage);
                } else {
                    binding.emailSupportMessage.setVisibility(View.INVISIBLE);
                    changeFocusMode(true, binding.email, binding.emailInput);
                }
            }
        });
        binding.emailInput.setOnFocusChangeListener((view, b) -> changeFocusMode(b && binding.emailSupportMessage.getVisibility() == View.VISIBLE, binding.email, binding.emailInput));


        binding.passwordInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() < 1) {
                    changeToErrorMode(binding.password, binding.passwordInput, "A senha não deve estar vazia.", binding.passwordSupportMessage);
                } else if (editable.length() < 8) {
                    changeToErrorMode(binding.password, binding.passwordInput, "A senha deve conter pelo menos 8 caracteres.", binding.passwordSupportMessage);
                } else {
                    binding.passwordSupportMessage.setVisibility(View.INVISIBLE);
                    changeFocusMode(true, binding.password, binding.passwordInput);
                }
            }
        });
        binding.passwordInput.setOnFocusChangeListener((view, b) -> changeFocusMode(b && binding.passwordSupportMessage.getVisibility() == View.VISIBLE, binding.password, binding.passwordInput));


        binding.passwordConfirmationInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (TextUtils.isEmpty(binding.passwordInput.getText())) {
                    changeToErrorMode(binding.password, binding.passwordInput, "A senha não deve estar vazia.", binding.passwordConfirmationSupportMessage);
                } else if (!editable.toString().equals(binding.passwordInput.getText().toString())) {
                    changeToErrorMode(binding.passwordConfirmation, binding.passwordConfirmationInput, "As senhas devem ser iguais.", binding.passwordConfirmationSupportMessage);
                } else {
                    binding.passwordConfirmationSupportMessage.setVisibility(View.INVISIBLE);
                    changeFocusMode(true, binding.passwordConfirmation, binding.passwordConfirmationInput);
                }
            }
        });
        binding.passwordConfirmationInput.setOnFocusChangeListener((view, b) -> changeFocusMode(b && binding.passwordConfirmationSupportMessage.getVisibility() == View.VISIBLE, binding.passwordConfirmation, binding.passwordConfirmationInput));


        binding.loginLink.setOnClickListener(view1 -> startActivity(new Intent(getApplicationContext(), LoginActivity.class)));

        binding.registerButton.setOnClickListener(view1 -> {
            registerUserWithEmailAndPassword();
        });

        binding.googleButton.setOnClickListener(view1 -> signInGoogle());
    }

    private void registerUserWithEmailAndPassword() {
        Editable name = binding.nameInput.getText();
        Editable lastName = binding.lastNameInput.getText();
        Editable email = binding.emailInput.getText();
        Editable password = binding.passwordInput.getText();
        Editable passwordConfirmation = binding.passwordInput.getText();
        if (credentialsAreValid(name, lastName, email, password, passwordConfirmation)) {
            mAuth.createUserWithEmailAndPassword(email.toString(), password.toString())
                    .addOnCompleteListener(this, task -> {
                        if (task.isSuccessful()) {
                            if (mAuth.getCurrentUser() != null) {
                                mAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(task1 -> {
                                    if (task1.isSuccessful()) {
                                        createUser(name.toString(), lastName.toString(), mAuth.getCurrentUser().getUid());
                                        Toast.makeText(getApplicationContext(), "Conta criada com sucesso. Por favor, verifique sua caixa de e-mail.", Toast.LENGTH_LONG).show();
                                        binding.emailInput.setText("");
                                        binding.passwordInput.setText("");
                                    } else {
                                        Toast.makeText(getApplicationContext(), task1.getException().getMessage(), Toast.LENGTH_LONG).show();
                                    }
                                });
                                loginActivity();
                            } else {
                                Log.w("Fail", "createUserWithEmail:failure", task.getException());
                                Toast.makeText(RegistrationActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            handleFirebaseExceptions(task);
                        }
                    });
        }
    }

    private void handleFirebaseExceptions(Task<AuthResult> task) {
        if (task != null && task.getException() != null) {
            try {
                throw task.getException();
            } catch (FirebaseAuthWeakPasswordException e) {
                changeToErrorMode(binding.password, binding.passwordInput, getString(R.string.error_weak_password), binding.passwordSupportMessage);
                binding.passwordInput.requestFocus();
            } catch (FirebaseAuthInvalidCredentialsException e) {
                changeToErrorMode(binding.email, binding.emailInput, getString(R.string.invalid_email), binding.emailSupportMessage);
                binding.emailInput.requestFocus();
            } catch (FirebaseAuthUserCollisionException e) {
                changeToErrorMode(binding.email, binding.emailInput, getString(R.string.email_already_exists), binding.emailSupportMessage);
                binding.emailInput.requestFocus();
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "Um erro inesperado ocorreu.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private boolean isValidEmail(Editable editable) {
        return Patterns.EMAIL_ADDRESS.matcher(editable).matches();
    }

    private void changeToErrorMode(TextView field, EditText input, String errorMessage, TextView
            message) {
        field.setTextColor(getResources().getColor(R.color.red));
        message.setText(errorMessage);
        input.getBackground().setColorFilter(getResources().getColor(R.color.red), PorterDuff.Mode.SRC_ATOP);
        message.setVisibility(View.VISIBLE);
    }

    private void changeFocusMode(boolean focused, TextView text, EditText input) {
        int color = focused ? getResources().getColor(R.color.green) : getResources().getColor(R.color.grey_dark);
        text.setTextColor(color);
        input.getBackground().setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
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
                homeActivity();
                finish();
            } else {
                Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean credentialsAreValid(Editable name, Editable lastName, Editable
            email, Editable password, Editable passwordConfirmation) {
        if (TextUtils.isEmpty(name)) {
            changeToErrorMode(binding.name, binding.nameInput, "Nome não deve estar vazio.", binding.nameSupportMessage);
            return false;
        }

        if (TextUtils.isEmpty(lastName)) {
            changeToErrorMode(binding.lastName, binding.lastNameInput, "Sobrenome não deve estar vazio.", binding.lastNameSupportMessage);
            return false;
        }

        if (TextUtils.isEmpty(email.toString())) {
            changeToErrorMode(binding.email, binding.emailInput, "E-mail não deve estar vazio.", binding.emailSupportMessage);
            return false;
        } else if (!isValidEmail(email)) {
            changeToErrorMode(binding.email, binding.emailInput, "E-mail inválido.", binding.emailSupportMessage);
            return false;
        }

        if (TextUtils.isEmpty(password.toString())) {
            changeToErrorMode(binding.password, binding.passwordInput, "Senha não deve estar vazia.", binding.passwordSupportMessage);
            return false;
        } else if (password.length() < 8) {
            changeToErrorMode(binding.password, binding.passwordInput, "Senha deve conter pelo menos 8 caracteres.", binding.passwordSupportMessage);
            return false;
        }

        if (TextUtils.isEmpty(passwordConfirmation.toString())) {
            changeToErrorMode(binding.password, binding.passwordInput, "A confirmação da senha não deve estar vazia.", binding.passwordSupportMessage);
            return false;
        } else if (!passwordConfirmation.toString().equals(password.toString())) {
            changeToErrorMode(binding.password, binding.passwordInput, "As senhas devem ser iguais.", binding.passwordSupportMessage);
            return false;
        }
        return true;
    }

    private void loginActivity() {
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
    }

    private void onBoardingActivity() {
        Intent intent = new Intent(getApplicationContext(), OnBoardingActivity.class);
        startActivity(intent);
    }

    private void homeActivity() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }

    private User createUser(String name, String lastName, String id) {
        User user;
        user = new User(id, name, lastName, mAuth.getCurrentUser().getDisplayName(), mAuth.getCurrentUser().getEmail(), LocalDate.now().toString(), 0.0, mAuth.getCurrentUser().getPhotoUrl() != null ? mAuth.getCurrentUser().getPhotoUrl().toString() : null);
        Util.db.collection("users").document(id)
                .set(user);
        return user;
    }
}
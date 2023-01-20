package com.relyon.feedme.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
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

        binding.forgotPassword.setOnClickListener(view1 -> {
            startActivity(new Intent(getApplicationContext(), ForgotPasswordActivity.class));
        });

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
        binding.emailInput.setOnFocusChangeListener((view1, b) -> {
            changeFocusMode(b && binding.emailInput.getError() == null, binding.email, binding.emailInput);
        });

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
                    changeToErrorMode(binding.password, binding.passwordInput, "Senha inválida.", binding.passwordSupportMessage);
                } else if (editable.length() < 8) {
                    changeToErrorMode(binding.password, binding.passwordInput, "Senha muito curta.", binding.passwordSupportMessage);
                } else {
                    binding.passwordSupportMessage.setVisibility(View.INVISIBLE);
                    changeFocusMode(true, binding.password, binding.passwordInput);
                }
            }
        });

        binding.passwordInput.setOnFocusChangeListener((view1, b) -> {
            changeFocusMode(b && binding.passwordInput.getError() == null, binding.password, binding.passwordInput);
        });

        binding.loginButton.setOnClickListener(view1 -> {
            Editable email = binding.emailInput.getText();
            Editable password = binding.passwordInput.getText();
            if (credentialsAreValid(email, password))
                mAuth.signInWithEmailAndPassword(email.toString(), password.toString())
                        .addOnCompleteListener(this, task -> {
                            if (task.isSuccessful()) {
                                if (mAuth.getCurrentUser().isEmailVerified()) {
                                    if (task.getResult().getAdditionalUserInfo() != null && task.getResult().getAdditionalUserInfo().isNewUser()) {
                                        onBoardingActivity();
                                    } else {
                                        homeActivity();
                                    }
                                } else {
                                    startActivity(new Intent(getApplicationContext(), EmailConfirmationActivity.class).putExtra("email", email).putExtra("password", password));
                                    Toast.makeText(getApplicationContext(), "Por favor, verifique seu e-mail.", Toast.LENGTH_LONG).show();
                                }
                            } else {
                                Log.w("Fail", "createUserWithEmail:failure", task.getException());
                                Toast.makeText(LoginActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
        });
        binding.passwordVisibility.setOnClickListener(view1 -> changePasswordVisibility(binding.passwordVisibility, binding.passwordInput));
    }

    private void changePasswordVisibility(ImageView passwordVisibility, TextInputEditText passwordInput) {
        Drawable visibleDrawable = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_visible_password, getTheme());
        Drawable invisibleDrawable = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_hidden_password, getTheme());

        if (passwordVisibility.getTag().equals("visible")) {
            passwordVisibility.setTag("invisible");
            passwordVisibility.setImageDrawable(invisibleDrawable);
            passwordInput.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        } else {
            passwordVisibility.setTag("visible");
            passwordVisibility.setImageDrawable(visibleDrawable);
            passwordInput.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        }
        passwordInput.setSelection(passwordInput.getText() != null ? passwordInput.getText().length() : 0);
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
            binding.emailInput.setError("E-mail não pode estar vazio.");
        } else if (TextUtils.isEmpty(password.toString())) {
            binding.passwordInput.setError("Senha não pode estar vazia.");
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
        finish();
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }

    private void onBoardingActivity() {
        finish();
        Intent intent = new Intent(getApplicationContext(), OnBoardingActivity.class);
        startActivity(intent);
    }
}
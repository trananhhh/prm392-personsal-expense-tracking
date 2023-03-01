package com.example.prm392_personalexpensetracking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private static final String TAG = "EmailPassword";
    EditText nameInput;
    EditText emailInput;
    EditText passwordInput;
    Button signUpBtn;
    TextView suggestLoginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        nameInput = findViewById(R.id.editTextTextPersonName);
        emailInput = findViewById(R.id.editTextTextEmailAddress);
        passwordInput = findViewById(R.id.editTextTextPassword);
        signUpBtn = findViewById(R.id.signUpBtn);
        suggestLoginBtn = findViewById(R.id.suggestLoginBtn);

        mAuth = FirebaseAuth.getInstance();

        suggestLoginBtn.setOnClickListener(view -> {
            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
        });

        signUpBtn.setOnClickListener(view -> {
            createAccount();
        });
    }

    private void createAccount() {
        String name = nameInput.getText().toString();
        String email = emailInput.getText().toString();
        String password = passwordInput.getText().toString();

        if(TextUtils.isEmpty(name)){
            nameInput.setError("Name cannot be empty!");
            nameInput.requestFocus();
        } else if(TextUtils.isEmpty(email)){
            emailInput.setError("Email cannot be empty!");
            emailInput.requestFocus();
        } else if (TextUtils.isEmpty(password)) {
            passwordInput.setError("Password cannot be empty!");
            passwordInput.requestFocus();
        }
        else{
            mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "createUserWithEmail:success");
                            Toast.makeText(RegisterActivity.this, "Authentication success.",
                                    Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                        } else {
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(RegisterActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });
        }
    }


    private void updateUI(FirebaseUser user) {}
}
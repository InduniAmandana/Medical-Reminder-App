package com.s92075608.medical_app_s92075608;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.s92075608.medical_app_s92075608.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {

    // Declare the binding and database helper
    ActivityLoginBinding binding;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize binding and set the content view
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Initialize the database helper
        databaseHelper = new DatabaseHelper(this);

        // Set onClickListener for the login button
        binding.loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get the input email and password from the user
                String email = binding.loginEmail.getText().toString();
                String password = binding.loginPassword.getText().toString();

                // Check if any field is empty
                if (email.equals("") || password.equals("")) {
                    Toast.makeText(LoginActivity.this, "All fields are mandatory", Toast.LENGTH_SHORT).show();
                } else {
                    // Validate the email and password with the database
                    Boolean checkCredentials = databaseHelper.checkEmailPassword(email, password);

                    // If credentials are valid, proceed to the MainActivity
                    if (checkCredentials == true) {
                        Toast.makeText(LoginActivity.this, "Login Successfully", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                    } else {
                        // If credentials are invalid, show an error message
                        Toast.makeText(LoginActivity.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        // Set onClickListener for the signup redirect text
        binding.singupRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Redirect to the SignupActivity
                Intent intent = new Intent(LoginActivity.this, SingupActivity.class);
                startActivity(intent);
            }
        });

    }
}
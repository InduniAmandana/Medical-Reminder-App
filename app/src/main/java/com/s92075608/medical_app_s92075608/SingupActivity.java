package com.s92075608.medical_app_s92075608;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.s92075608.medical_app_s92075608.databinding.ActivitySingupBinding;

public class SingupActivity extends AppCompatActivity {
    // Declare the binding and database helper
    ActivitySingupBinding binding;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize binding and set the content view
        binding = ActivitySingupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Initialize the database helper
        databaseHelper = new DatabaseHelper(this);

        // Set onClickListener for the signup button
        binding.singupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get the input email, password, and confirm password from the user
                String email = binding.singupEmail.getText().toString();
                String password = binding.singupPassword.getText().toString();
                String confirmPassword = binding.singupComfirm.getText().toString();

                // Check if any field is empty
                if (email.equals("") || password.equals("") || confirmPassword.equals("")) {
                    Toast.makeText(SingupActivity.this, "All fields are mandatory", Toast.LENGTH_SHORT).show();
                } else {
                    // Check if password and confirm password match
                    if (password.equals(confirmPassword)) {
                        // Check if the user email already exists in the database
                        Boolean checkUserEmail = databaseHelper.checkEmail(email);

                        // If user email does not exist, insert the new user data
                        if (!checkUserEmail) {
                            Boolean insert = databaseHelper.insertData(email, password);

                            // If insertion is successful, redirect to LoginActivity
                            if (insert) {
                                Toast.makeText(SingupActivity.this, "SignUp Successful", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                                startActivity(intent);
                            } else {
                                // If insertion fails, show an error message
                                Toast.makeText(SingupActivity.this, "SignUp Failed", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            // If user email already exists, prompt the user to login
                            Toast.makeText(SingupActivity.this, "User already exists, Please login", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        // If password and confirm password do not match, show an error message
                        Toast.makeText(SingupActivity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        // Set onClickListener for the login redirect text
        binding.loginRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Redirect to the LoginActivity
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}

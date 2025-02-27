package com.example.mom;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class firebase extends AppCompatActivity {

    private TextInputEditText u_name, pass_word;
    private TextView registerButton;
    private Button btnSubmit;
    private RadioButton radioCustomer, radioHelper;

    // Firebase Database Reference
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firebase);

        // Initialize Firebase Database
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");



        // Initialize Views
        u_name = findViewById(R.id.u_name);
        pass_word = findViewById(R.id.pass_word);
        registerButton = findViewById(R.id.registerButton);
        btnSubmit = findViewById(R.id.loginButton);
        radioCustomer = findViewById(R.id.radio_customer);
        radioHelper = findViewById(R.id.radio_helper);

        // Set button click listener for login
        btnSubmit.setOnClickListener(v -> saveData());

        // Redirect to the registration page
        registerButton.setOnClickListener(view -> {
            Intent i = new Intent(firebase.this, SelectionActivity.class);
            startActivity(i);
        });
    }

    private void saveData() {
        String uname = u_name.getText().toString().trim();
        String pswd = pass_word.getText().toString().trim();

        if (uname.isEmpty() || pswd.isEmpty()) {
            Toast.makeText(this, "Please fill out all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create a unique ID for each user
        String userId = databaseReference.push().getKey();

        // Create a User object
        User user = new User(uname, pswd);

        // Save data under the unique user ID
        if (userId != null) {
            databaseReference.child(userId).setValue(user).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(this, "Login successful!", Toast.LENGTH_SHORT).show();

                    // Check which radio button is selected and navigate accordingly
                    if (radioCustomer.isChecked()) {
                        Intent intent = new Intent(firebase.this, homec.class);
                        intent.putExtra("username", uname);
                        startActivity(intent);
                    } else if (radioHelper.isChecked()) {
                        Intent intent = new Intent(firebase.this, HelperActivity.class);
                        intent.putExtra("username", uname);
                        startActivity(intent);
                    }

                    finish(); // Close login activity
                } else {
                    Toast.makeText(this, "Failed to save data", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}

// User model class
class User {
    private String uname;
    private String pswd;

    public User() {
        // Default constructor required for Firebase
    }

    public User(String uname, String pswd) {
        this.uname = uname;
        this.pswd = pswd;
    }

    public String getName() {
        return uname;
    }

    public String getPassword() {
        return pswd;
    }
}
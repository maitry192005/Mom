package com.example.mom;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class firebase extends AppCompatActivity {

    private TextInputEditText u_name, pass_word;
    private TextView registerButton;
    private Button btnSubmit;

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
        registerButton=findViewById(R.id.registerButton);
        btnSubmit = findViewById(R.id.loginButton);

        // Set button click listener
        btnSubmit.setOnClickListener(v -> saveData());
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i= new Intent(firebase.this, selection.class);
                startActivity(i);
            }
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
                    Toast.makeText(this, "Data saved successfully", Toast.LENGTH_SHORT).show();
                    u_name.setText("");
                    pass_word.setText("");
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
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String uname, String pswd) {
        this.uname = uname;
        this.pswd = pswd;
    }

    public String getName() {
        return uname;
    }

    public String getMobile() {
        return pswd;
    }
}
package com.example.mom;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class firebase extends AppCompatActivity {

    private EditText u_name, pass_word;
    private TextView registerButton;
    private Button btnSubmit;
    private RadioButton radioCustomer, radioHelper;

    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firebase);

        databaseReference = FirebaseDatabase.getInstance().getReference("Users");

        u_name = findViewById(R.id.u_name);
        pass_word = findViewById(R.id.pass_word);
        registerButton = findViewById(R.id.registerButton);
        btnSubmit = findViewById(R.id.loginButton);
        radioCustomer = findViewById(R.id.radio_customer);
        radioHelper = findViewById(R.id.radio_helper);

        registerButton.setOnClickListener(view -> {
            startActivity(new Intent(firebase.this, signupcustomer.class));
        });

        btnSubmit.setOnClickListener(view -> {
            String usernameText = u_name.getText().toString().trim();
            String passwordText = pass_word.getText().toString().trim();

            if (validateInputs(usernameText, passwordText)) {
                loginUser(usernameText, passwordText);
            }
        });
    }

    private boolean validateInputs(String username, String password) {
        if (TextUtils.isEmpty(username)) {
            u_name.setError("Username is required");
            u_name.requestFocus();
            return false;
        }
        if (TextUtils.isEmpty(password)) {
            pass_word.setError("Password is required");
            pass_word.requestFocus();
            return false;
        }
        return true;
    }

    private void loginUser(String username, String password) {
        btnSubmit.setVisibility(View.INVISIBLE);

        databaseReference.child(username).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                btnSubmit.setVisibility(View.VISIBLE);

                if (snapshot.exists()) {
                    String passwordFromDB = snapshot.child("password").getValue(String.class);

                    if (passwordFromDB != null && passwordFromDB.equals(password)) {
                        Toast.makeText(firebase.this, "Login Successful!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(firebase.this, homec.class);
                        startActivity(intent);
                        finish();
                    } else {
                        pass_word.setError("Incorrect password");
                        pass_word.requestFocus();
                    }
                } else {
                    u_name.setError("User not found");
                    u_name.requestFocus();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(firebase.this, "Database error: " + error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}

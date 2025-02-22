package com.example.mom;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class signuph extends AppCompatActivity {

    private EditText fullName, address, phoneNumber, age, username, password, languages;
    private RadioGroup genderGroup;
    private Button registerButton;

    private FirebaseDatabase database;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signuph);

        // Initialize Firebase Database
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("Users");

        // Initialize views
        fullName = findViewById(R.id.editTextFullName);
        address = findViewById(R.id.editTextAddress);
        phoneNumber = findViewById(R.id.editTextPhoneNumber);
        age = findViewById(R.id.editTextAge);
        username = findViewById(R.id.editTextUsername);
        password = findViewById(R.id.editTextPassword);
        languages = findViewById(R.id.editlang);
        genderGroup = findViewById(R.id.radioGroupGender);
        registerButton = findViewById(R.id.buttonRegister);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });
    }

    private void registerUser() {
        String name = fullName.getText().toString().trim();
        String addr = address.getText().toString().trim();
        String phone = phoneNumber.getText().toString().trim();
        String userAge = age.getText().toString().trim();
        String user = username.getText().toString().trim();
        String pass = password.getText().toString().trim();
        String lang = languages.getText().toString().trim();

        int selectedGenderId = genderGroup.getCheckedRadioButtonId();
        RadioButton selectedGenderButton = findViewById(selectedGenderId);
        String gender = selectedGenderButton != null ? selectedGenderButton.getText().toString() : null;

        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(addr) || TextUtils.isEmpty(phone) ||
                TextUtils.isEmpty(userAge) || TextUtils.isEmpty(user) || TextUtils.isEmpty(pass) ||
                TextUtils.isEmpty(lang) || TextUtils.isEmpty(gender)) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Store data in Firebase
        String userId = databaseReference.push().getKey();
        HashMap<String, String> userMap = new HashMap<>();
        userMap.put("fullName", name);
        userMap.put("gender", gender);
        userMap.put("address", addr);
        userMap.put("phone", phone);
        userMap.put("age", userAge);
        userMap.put("username", user);
        userMap.put("password", pass);
        userMap.put("languages", lang);

        databaseReference.child(userId).setValue(userMap).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(signuph.this, "User Registered Successfully", Toast.LENGTH_SHORT).show();
                clearFields();

                // Redirect to firebase activity
                Intent intent = new Intent(signuph.this, firebase.class);
                startActivity(intent);
                finish(); // Close this activity so the user cannot navigate back
            } else {
                Toast.makeText(signuph.this, "Registration Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void clearFields() {
        fullName.setText("");
        address.setText("");
        phoneNumber.setText("");
        age.setText("");
        username.setText("");
        password.setText("");
        languages.setText("");
        genderGroup.clearCheck();
    }
}

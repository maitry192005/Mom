package com.example.mom;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class signupcustomer extends AppCompatActivity {

    private EditText fullName, address, landmark, houseType, pincode, phoneNumber, email, username, password;
    private Button register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signupcustomer);

        // Initialize UI Components
        fullName = findViewById(R.id.text1);
        address = findViewById(R.id.text2);
        landmark = findViewById(R.id.textland);
        pincode = findViewById(R.id.num);
        phoneNumber = findViewById(R.id.textPhone);
        email = findViewById(R.id.textage);
        username = findViewById(R.id.signupuser);
        password = findViewById(R.id.signuppass);
        register = findViewById(R.id.regis1);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fullNameText = fullName.getText().toString().trim();
                String addressText = address.getText().toString().trim();
                String landmarkText = landmark.getText().toString().trim();
                String houseTypeText = houseType.getText().toString().trim();
                String pincodeText = pincode.getText().toString().trim();
                String phoneText = phoneNumber.getText().toString().trim();
                String emailText = email.getText().toString().trim();
                String usernameText = username.getText().toString().trim();
                String passwordText = password.getText().toString().trim();

                // Validate Inputs
                if (!validateInputs(usernameText, emailText, phoneText, passwordText)) {
                    return;
                }

                // Reference to Firebase Database
                DatabaseReference databaseReference = FirebaseDatabase.getInstance()
                        .getReferenceFromUrl("https://maid-o-meter-d43ae-default-rtdb.firebaseio.com/")
                        .child("Users");

                register.setVisibility(View.INVISIBLE);

                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        try {
                            boolean usernameExists = snapshot.child(usernameText).exists();
                            boolean phoneExists = false;

                            for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                                String existingPhone = userSnapshot.child("phone").getValue(String.class);
                                if (existingPhone != null && phoneText.equals(existingPhone)) {
                                    phoneExists = true;
                                    break;
                                }
                            }

                            if (usernameExists) {
                                username.setError("Username already exists");
                                username.requestFocus();
                                register.setVisibility(View.VISIBLE);
                            } else if (phoneExists) {
                                phoneNumber.setError("Phone number already exists");
                                phoneNumber.requestFocus();
                                register.setVisibility(View.VISIBLE);
                            } else {
                                // Save user details to Firebase
                                Users newUser = new Users(usernameText, emailText, phoneText, passwordText, fullNameText, addressText, landmarkText, houseTypeText, pincodeText);
                                databaseReference.child(usernameText).setValue(newUser)
                                        .addOnCompleteListener(task -> {
                                            register.setVisibility(View.VISIBLE);
                                            if (task.isSuccessful()) {
                                                Log.d("FirebaseDB", "User registered successfully: " + usernameText);
                                                Toast.makeText(signupcustomer.this, "User Registered Successfully", Toast.LENGTH_SHORT).show();
                                                finish();
                                            } else {
                                                Log.e("FirebaseDB", "Database write failed", task.getException());
                                                Toast.makeText(signupcustomer.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(signupcustomer.this, "An error occurred: " + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        register.setVisibility(View.VISIBLE);
                        Toast.makeText(signupcustomer.this, "Database error: " + error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }

    private boolean validateInputs(String usernameText, String emailText, String phoneText, String passwordText) {
        String mobileRegex = "[6-9][0-9]{9}";
        Pattern mobilePattern = Pattern.compile(mobileRegex);
        Matcher mobileMatcher = mobilePattern.matcher(phoneText);

        if (TextUtils.isEmpty(usernameText)) {
            username.setError("Username is required");
            username.requestFocus();
            return false;
        }
        if (usernameText.contains(" ")) {
            username.setError("Username cannot contain spaces");
            username.requestFocus();
            return false;
        }
        if (TextUtils.isEmpty(emailText) || !Patterns.EMAIL_ADDRESS.matcher(emailText).matches()) {
            email.setError("Valid Email is required");
            email.requestFocus();
            return false;
        }
        if (phoneText.length() != 10 || !mobileMatcher.find()) {
            phoneNumber.setError("Valid Mobile number is required");
            phoneNumber.requestFocus();
            return false;
        }
        if (TextUtils.isEmpty(passwordText) || passwordText.length() < 6) {
            password.setError("Password must be at least 6 characters");
            password.requestFocus();
            return false;
        }
        return true;
    }

    // Define User class to represent user data
    public static class Users {
        public String username, email, phone, password, fullName, address, landmark, houseType, pincode;

        public Users() {
            // Default constructor required for Firebase
        }

        public Users(String username, String email, String phone, String password, String fullName, String address, String landmark, String houseType, String pincode) {
            this.username = username;
            this.email = email;
            this.phone = phone;
            this.password = password;
            this.fullName = fullName;
            this.address = address;
            this.landmark = landmark;
            this.houseType = houseType;
            this.pincode = pincode;
        }
    }
}

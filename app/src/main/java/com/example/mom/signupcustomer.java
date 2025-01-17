package com.example.mom;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class signupcustomer extends AppCompatActivity {

    // Declare EditText fields
    private EditText fullName, address, landmark, houseType, pincode, phoneNumber, email, username, password;
    private Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signupcustomer);  // Assuming the layout XML file is named activity_signup.xml

        // Initialize the EditText fields
        fullName = findViewById(R.id.text1);
        address = findViewById(R.id.text2);
        landmark = findViewById(R.id.textland);
        houseType = findViewById(R.id.texthtype);
        pincode = findViewById(R.id.num);
        phoneNumber = findViewById(R.id.textPhone);
        email = findViewById(R.id.textage);
        username = findViewById(R.id.signupuser);
        password = findViewById(R.id.signuppass);
        registerButton = findViewById(R.id.regis1);

        // Set an OnClickListener for the Register button
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the input values
                String fullNameText = fullName.getText().toString();
                String addressText = address.getText().toString();
                String landmarkText = landmark.getText().toString();
                String houseTypeText = houseType.getText().toString();
                String pincodeText = pincode.getText().toString();
                String phoneText = phoneNumber.getText().toString();
                String emailText = email.getText().toString();
                String usernameText = username.getText().toString();
                String passwordText = password.getText().toString();

                // Validate the input fields
                if (fullNameText.isEmpty() || addressText.isEmpty() || landmarkText.isEmpty() ||
                        houseTypeText.isEmpty() || pincodeText.isEmpty() || phoneText.isEmpty() ||
                        emailText.isEmpty() || usernameText.isEmpty() || passwordText.isEmpty()) {
                    Toast.makeText(signupcustomer.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                } else {
                    // If all fields are filled, show a success message (you can replace this with actual registration logic)
                    Toast.makeText(signupcustomer.this, "Registration Successful", Toast.LENGTH_SHORT).show();

                    // Optionally, clear the input fields after successful registration
                    clearFields();
                }
            }
        });
    }

    // Method to clear all the input fields
    private void clearFields() {
        fullName.setText("");
        address.setText("");
        landmark.setText("");
        houseType.setText("");
        pincode.setText("");
        phoneNumber.setText("");
        email.setText("");
        username.setText("");
        password.setText("");
    }
}

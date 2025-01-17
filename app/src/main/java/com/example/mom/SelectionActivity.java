package com.example.mom;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SelectionActivity extends AppCompatActivity {

    private Button btnHelper, btnCustomer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection);  // Set the layout from the provided XML file

        // Initialize buttons from layout
        btnHelper = findViewById(R.id.btnHelper);
        btnCustomer = findViewById(R.id.btnCustomer);

        // Handle Helper Button Click
        btnHelper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toast message to show action
                Toast.makeText(SelectionActivity.this, "Signing up as a Helper", Toast.LENGTH_SHORT).show();

                // Start the sign-up activity for Helper
                Intent intent = new Intent(SelectionActivity.this, signuph.class);
                startActivity(intent);
            }
        });

        // Handle Customer Button Click
        btnCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toast message to show action
                Toast.makeText(SelectionActivity.this, "Signing up as a Customer", Toast.LENGTH_SHORT).show();

                // Start the sign-up activity for Customer
                Intent intent = new Intent(SelectionActivity.this, signupcustomer.class);
                startActivity(intent);
            }
        });
    }
}

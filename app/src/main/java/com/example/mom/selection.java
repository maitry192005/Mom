package com.example.mom;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class selection extends AppCompatActivity {
    private Button btnHelper, btnCustomer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection);

        btnHelper = findViewById(R.id.btnHelper);
        btnCustomer = findViewById(R.id.btnCustomer);

        // Handle Helper Button Click
        btnHelper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to Helper Sign-Up page or show message
                Toast.makeText(selection.this, "Signing up as a Helper", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(selection.this, signuph.class);
                startActivity(intent);
            }
        });

        // Handle Customer Button Click
        btnCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to Customer Sign-Up page or show message
                try {
                    Toast.makeText(selection.this, "Signing up as a Customer", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), signupc.class);
                    startActivity(intent);
                }catch(Exception e){
                    Toast.makeText(selection.this, "error"+e.getMessage(), Toast.LENGTH_SHORT).show();
                    e.printStackTrace();

                }
            }
        });
    }
}
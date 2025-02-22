package com.example.mom;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import androidx.appcompat.app.AppCompatActivity;

public class HelperActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_helper);

        // Initialize buttons
        Button buttonOneTimeWork = findViewById(R.id.buttonOneTimeWork);
        Button buttonPartTimeWork = findViewById(R.id.buttonPartTimeWork);
        Button buttonFullTimeWork = findViewById(R.id.buttonFullTimeWork);

        buttonOneTimeWork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an AlertDialog
                new AlertDialog.Builder(HelperActivity.this)
                        .setMessage("You can hire the helper for maximum 4 hours and for one day only")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Switch to workc activity
                                Intent intent = new Intent(HelperActivity.this, workc.class);
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton("Cancel", null)
                        .show();
            }
        });

        buttonPartTimeWork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDaysSelectionDialog("You can hire the helper for maximum 6 hours\n\nFor how many days do you want to hire?");
            }
        });

        buttonFullTimeWork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDaysSelectionDialog("You can hire the helper for maximum 8 hours\n\nFor how many days do you want to hire?");
            }
        });
    }

    private void showDaysSelectionDialog(String message) {
        // Create a drop-down (spinner)
        final Spinner daySpinner = new Spinner(HelperActivity.this);
        Integer[] days = new Integer[30];
        for (int i = 0; i < 30; i++) {
            days[i] = i + 1;
        }
        ArrayAdapter<Integer> adapter = new ArrayAdapter<>(HelperActivity.this, android.R.layout.simple_spinner_dropdown_item, days);
        daySpinner.setAdapter(adapter);

        // Create an AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(HelperActivity.this);
        builder.setMessage(message)
                .setView(daySpinner)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Switch to workc activity
                        Intent intent = new Intent(HelperActivity.this, workc.class);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }
}

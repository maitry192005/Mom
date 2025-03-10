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

public class homec extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homec);

        // Initialize buttons
        Button btnOneTimeHelp = findViewById(R.id.btnOneTimeHelp);
        Button btnPartTimeHelp = findViewById(R.id.btnPartTimeHelp);
        Button btnFullTimeHelp = findViewById(R.id.btnFullTimeHelp);

        btnOneTimeHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an AlertDialog
                new AlertDialog.Builder(homec.this)
                        .setMessage("You can hire the helper for maximum 4 hours and for one day only")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Switch to workc activity
                                Intent intent = new Intent(homec.this, workc.class);
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton("Cancel", null)
                        .show();
            }
        });

        btnPartTimeHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDaysSelectionDialog("You can hire the helper for maximum 6 hours\n\nFor how many days do you want to hire?");
            }
        });

        btnFullTimeHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDaysSelectionDialog("You can hire the helper for maximum 8 hours\n\nFor how many days do you want to hire?");
            }
        });
    }

    private void showDaysSelectionDialog(String message) {
        // Create a drop-down (spinner)
        final Spinner daySpinner = new Spinner(homec.this);
        Integer[] days = new Integer[30];
        for (int i = 0; i < 30; i++) {
            days[i] = i + 1;
        }
        ArrayAdapter<Integer> adapter = new ArrayAdapter<>(homec.this, android.R.layout.simple_spinner_dropdown_item, days);
        daySpinner.setAdapter(adapter);

        // Create an AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(homec.this);
        builder.setMessage(message)
                .setView(daySpinner)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Switch to workc activity
                        Intent intent = new Intent(homec.this, workc.class);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }
}

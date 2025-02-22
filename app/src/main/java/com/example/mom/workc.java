package com.example.mom;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.content.DialogInterface;
import androidx.appcompat.app.AlertDialog;

import androidx.appcompat.app.AppCompatActivity;

public class workc extends AppCompatActivity {

    private CheckBox cookingCheckBox, cleaningCheckBox, utensilCleaningCheckBox, dustingCheckBox;
    private RadioGroup cookingOptions;
    private RadioButton vegRadioButton, nonVegRadioButton, bothRadioButton;
    private EditText membersEditText, houseSizeEditText;
    private Button submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workc);

        // Initialize Views
        cookingCheckBox = findViewById(R.id.cookingCheckBox);
        cookingOptions = findViewById(R.id.cookingOptions);
        vegRadioButton = findViewById(R.id.vegRadioButton);
        nonVegRadioButton = findViewById(R.id.nonVegRadioButton);
        bothRadioButton = findViewById(R.id.bothRadioButton);
        membersEditText = findViewById(R.id.membersEditText);
        cleaningCheckBox = findViewById(R.id.cleaningCheckBox);
        houseSizeEditText = findViewById(R.id.houseSizeEditText);
        utensilCleaningCheckBox = findViewById(R.id.utensilCleaningCheckBox);
        dustingCheckBox = findViewById(R.id.dustingCheckBox);
        submitButton = findViewById(R.id.submitButton);

        // Show/Hide cooking options and members input based on checkbox
        cookingCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            cookingOptions.setVisibility(isChecked ? View.VISIBLE : View.GONE);
            membersEditText.setVisibility(isChecked ? View.VISIBLE : View.GONE);
        });

        // Show/Hide house size input based on cleaning checkbox
        cleaningCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            houseSizeEditText.setVisibility(isChecked ? View.VISIBLE : View.GONE);
        });

        // Handle Submit Button Click
        submitButton.setOnClickListener(v -> {
            StringBuilder result = new StringBuilder("Selected Work Preferences:\n");

            if (cookingCheckBox.isChecked()) {
                result.append("- Cooking (");
                if (vegRadioButton.isChecked()) result.append("Veg");
                else if (nonVegRadioButton.isChecked()) result.append("N. Veg");
                else if (bothRadioButton.isChecked()) result.append("Both");
                result.append(")\n");

                String members = membersEditText.getText().toString();
                result.append("- Members: ").append(members.isEmpty() ? "N/A" : members).append("\n");
            }

            if (cleaningCheckBox.isChecked()) {
                String houseSize = houseSizeEditText.getText().toString();
                result.append("- Cleaning (Sweep/Mop), House Size: ")
                        .append(houseSize.isEmpty() ? "N/A" : houseSize + " BHK").append("\n");
            }

            if (utensilCleaningCheckBox.isChecked()) {
                result.append("- Cleaning Utensils\n");
            }

            if (dustingCheckBox.isChecked()) {
                result.append("- Dusting (Intense Cleaning)\n");
            }
        });
    }
}
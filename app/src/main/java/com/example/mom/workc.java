package com.example.mom;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import android.content.DialogInterface;
import androidx.appcompat.app.AlertDialog;

import androidx.appcompat.app.AppCompatActivity;

public class workc extends AppCompatActivity {

    private CheckBox cookingCheckBox, cleaningCheckBox, utensilCleaningCheckBox, dustingCheckBox;
    private RadioGroup cookingOptions;
    private RadioButton vegRadioButton, nonVegRadioButton, bothRadioButton;
    private EditText membersEditText, houseSizeEditText;
    private Button submitButton;
    private static final String CHANNEL_ID = "ActivityNotificationChannel";
    private EditText editTextUsername;

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

        editTextUsername = findViewById(R.id.editUsername);
        Button notifyButton = findViewById(R.id.notifyButton);
        requestNotificationPermission();

        notifyButton.setOnClickListener(v -> sendNotification());

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
    private void requestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) { // Android 13+
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.POST_NOTIFICATIONS}, 101);
            }
        }
    }
    private void sendNotification() {
        String username = editTextUsername.getText().toString().trim();

        if (username.isEmpty()) {
            Toast.makeText(this, "Please enter your username", Toast.LENGTH_SHORT).show();
            return;
        }

        // Intent to open NotificationActivity when the notification is clicked
        Intent intent = new Intent(this, admin.class);
        intent.putExtra("username", username); // Passing the entered username

        PendingIntent pendingIntent = PendingIntent.getActivity(
                this, 0, intent, PendingIntent.FLAG_IMMUTABLE
        );

        // Create Notification Channel (for Android 8.0+)
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID, "Activity Notifications", NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }

        // Build and Show Notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(android.R.drawable.ic_dialog_info) // Use system icon if custom icon missing
                .setContentTitle("New Notification")
                .setContentText("Click to see your username")
                .setAutoCancel(true)
                .setContentIntent(pendingIntent);

        notificationManager.notify(1, builder.build());

        // Show confirmation message
        Toast.makeText(this, "Notification Sent!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 101) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted
            }
        }
    }

}
package com.example.mom;

import android.os.Bundle;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class admin extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        // Find TextViews
        TextView adminTextView = findViewById(R.id.adminTextView);
        TextView messageTextView = findViewById(R.id.messageTextView);
        TextView usernameTextView = findViewById(R.id.usernameTextView);
        TextView textView = findViewById(R.id.usernameTextView);
        String username = getIntent().getStringExtra("username");
        if (username != null && !username.isEmpty()) {
            textView.setText( username + "!");
        } else {
            textView.setText("No username received.");
        }
    }
}

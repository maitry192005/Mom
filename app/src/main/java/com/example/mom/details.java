package com.example.mom;

import android.content.Intent;
import android.os.Bundle;
 import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;

public class details extends AppCompatActivity {
    
    Button login,signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_detail);
        login=findViewById(R.id.login);
        signup=findViewById(R.id.signup);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(details.this,firebase.class);
                startActivity(intent);
            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(details.this, selection.class);
                startActivity(intent);
            }
        });



    }
}
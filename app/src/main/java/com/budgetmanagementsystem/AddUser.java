package com.budgetmanagementsystem;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class AddUser extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_user);

        Button submitButton = findViewById(R.id.submit);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // CHECK IF USERNAME IS UNIQUE
                // ADD USERNAME AND PASSWORD TO DB
                Intent intent = new Intent(AddUser.this, MainActivity.class);
                startActivity(intent);
            }
        });


    }

}

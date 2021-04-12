package com.budgetmanagementsystem;

import android.content.Intent;
import android.database.DatabaseUtils;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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

                //USERNAME ALREADY EXISTS MESSAGE
                //Toast.makeText(getApplicationContext(), "Username Already Exists", Toast.LENGTH_SHORT).show();

                // ADD USERNAME AND PASSWORD TO DB
                User user = new User();
                EditText username = (EditText) findViewById(R.id.editTextTextPersonName2);
                String usernameString = username.getText().toString();
                user.Username = usernameString;

                EditText password = (EditText) findViewById(R.id.editTextTextPassword2);
                String passwordString = password.getText().toString();
                user.Password = passwordString;

                DataBaseUtils.SaveUser(this, user);


                Intent intent = new Intent(AddUser.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }

}

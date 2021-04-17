package com.budgetmanagementsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.DatabaseUtils;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    TextView txt1;
    User user;
    Goal goal;
    Transaction trans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //txt1 = findViewById(R.id.text1);
       // user = DataBaseUtils.GetUserByID(this, 1);
        //goal = DataBaseUtils.GetGoalByID(this, 1);
        //trans = DataBaseUtils.GetTransactionByID(this, 1);

        //System.out.println("User exists so should be true: " + DataBaseUtils.UserExists(this, "Jacob", "abc123"));
        //System.out.println("User does not exist so should be false: " + DataBaseUtils.UserExists(this, "Nobody", "NotAUser"));

        Button loginButton = findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText username = (EditText) findViewById(R.id.editTextTextPersonName);
                String usernameString = username.getText().toString();

                EditText password = (EditText) findViewById(R.id.editTextTextPassword);
                String passwordString = password.getText().toString();


                if (DataBaseUtils.UserExists(v.getContext(), usernameString, passwordString)){

                    //GO To MENU ACTIVITY IF USERNAME AND PASSWORD EXISTS
                    Intent intent = new Intent(MainActivity.this, Menu.class);
                    startActivity(intent);
                };
                if (DataBaseUtils.UserExists(v.getContext(), usernameString, passwordString) == false){
                    //IF WRONG PASSWORD/USERNAME - WRONG PASSWORD/USERNAME MESSAGE
                    Toast.makeText(getApplicationContext(), "Incorrect Password or Username", Toast.LENGTH_SHORT).show();
                }

            }

        });

        Button addUserButton = findViewById(R.id.addUserButton);
        addUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //GO TO ADD USER ACTIVITY
                Intent intent = new Intent(MainActivity.this, AddUser.class);
                startActivity(intent);
            }
        });


    }
}
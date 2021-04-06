package com.budgetmanagementsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;

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
        user = DataBaseUtils.GetUserByID(this, 1);
        goal = DataBaseUtils.GetGoalByID(this, 1);
        trans = DataBaseUtils.GetTransactionByID(this, 1);

       // txt1.setText(user.Username);

        Button loginButton = findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //MENU ACTIVITY
                Intent intent = new Intent(MainActivity.this, Menu.class);
                startActivity(intent);

                //IF WRONG PASSWORD - WRONG PASSWORD MESSAGE
                //Toast.makeText(getApplicationContext(), "Incorrect Password", Toast.LENGTH_SHORT).show();
            }

        });

        Button addUserButton = findViewById(R.id.addUserButton);
        addUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ADD USER ACTIVITY
                Intent intent = new Intent(MainActivity.this, AddUser.class);
                startActivity(intent);
            }
        });




    }
}
package com.budgetmanagementsystem;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Menu extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);

        Button addTransaction = findViewById(R.id.enterTransactionButton);
        addTransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ADD TRANSACTION ACTIVITY
                Intent intent = new Intent(Menu.this, AddTransaction.class);
                startActivity(intent);
            }
        });

        Button addGoal = findViewById(R.id.enterGoalButton);
        addGoal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ADD GOAL ACTIVITY
                Intent intent = new Intent(Menu.this, AddGoal.class);
                startActivity(intent);
            }
        });

        Button viewHistory = findViewById(R.id.viewHistoryButton);
        viewHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ADD TRANSACTION ACTIVITY
                Intent intent = new Intent(Menu.this, ViewHistory.class);
                startActivity(intent);
            }
        });


    }
}

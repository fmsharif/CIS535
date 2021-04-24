package com.budgetmanagementsystem;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import androidx.appcompat.app.AppCompatActivity;

import static com.budgetmanagementsystem.DataBaseUtils.*;

public class Menu extends AppCompatActivity {

    private long userID;

    private TextView goalDate, goalAmnt, aheadOrBehind;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);

        userID = getIntent().getLongExtra("userID", 0);

        goalDate = findViewById(R.id.viewGoalDate);
        goalAmnt = findViewById(R.id.viewGoalAmount);
        aheadOrBehind = findViewById(R.id.viewAheadOrBehind);

        System.out.println("User ID: " + userID);
        setupGoalDisplay();

        Button addTransaction = findViewById(R.id.enterTransactionButton);
        addTransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //GO TO ADD TRANSACTION ACTIVITY
                Intent intent = new Intent(Menu.this, AddTransaction.class);
                intent.putExtra("userID", userID);
                startActivity(intent);
            }
        });

        Button addGoal = findViewById(R.id.enterGoalButton);
        addGoal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //GO TO ADD GOAL ACTIVITY
                Intent intent = new Intent(Menu.this, AddGoal.class);
                intent.putExtra("userID", userID);
                startActivity(intent);
            }
        });

        Button viewHistory = findViewById(R.id.viewHistoryButton);
        viewHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //GO TO ADD TRANSACTION ACTIVITY
                Intent intent = new Intent(Menu.this, ViewHistory.class);
                intent.putExtra("userID", userID);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        setupGoalDisplay();
    }

    private double GetCurrentBalance(Goal goal)
    {
        Transaction[] transactions = GetTransactionsByUser(this, goal.UserID);
        double balance = goal.StartBalance;

        for(Transaction transaction : transactions)
        {
            if(transaction.TransactionDate.after(goal.StartDate))
            {
                balance += transaction.TransactionAmount;
            }
        }

        return balance;
    }

    public void setupGoalDisplay()
    {
        Goal goal = DataBaseUtils.GetGoalByID(this, userID);

        if(goal == null)
        {
            goalDate.setText("You do not have a goal set up!");
            goalAmnt.setText("");
            aheadOrBehind.setText("");
            return;
        }

        Date currentDate = new Date();
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            currentDate = sdf.parse(sdf.format(new Date()));
        }
        catch(Exception ex)
        {
            // Do nothing
        }

        double currentBalance = GetCurrentBalance(goal);

        long timeElapsed = currentDate.getTime() - goal.StartDate.getTime();
        long totalTime = goal.EndDate.getTime() - goal.StartDate.getTime();
        double timeProgress = (double)timeElapsed / (double)totalTime;

        double savingsGoalToday = goal.StartBalance + (goal.EndBalance - goal.StartBalance) * timeProgress;

        double aheadOrBehindAmnt = currentBalance - savingsGoalToday;

        goalDate.setText("Goal Date: " + goal.EndDate.toString());
        goalAmnt.setText("Goal Amount: " + goal.EndBalance);

        if(aheadOrBehindAmnt < 0)
        {
            aheadOrBehind.setText("You are currently behind by " + NumberFormat.getCurrencyInstance().format(aheadOrBehindAmnt) + "!");
        }
        else
        {
            aheadOrBehind.setText("You are currently ahead by " + NumberFormat.getCurrencyInstance().format(aheadOrBehindAmnt) + "!");
        }

    }
}

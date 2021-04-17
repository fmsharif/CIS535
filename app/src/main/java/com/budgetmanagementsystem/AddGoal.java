package com.budgetmanagementsystem;

import android.content.Context;
import android.content.Intent;
import android.database.DatabaseUtils;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddGoal extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_goal);

        Button goalButton = findViewById(R.id.goalButton);
        goalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //GET GOAL AMOUNT FROM USER AND CREATE TEXT VIEW FOR DISPLAY
                EditText goal = (EditText) findViewById(R.id.goalAmount);
                int i = Integer.parseInt(goal.getText().toString());
                TextView textViewGoalAmount = findViewById(R.id.viewGoalAmount);
                textViewGoalAmount.setText(i);

                //GET USER
                User user;
                EditText username = (EditText) findViewById(R.id.editTextTextPersonName);
                String usernameString = username.getText().toString();

                EditText password = (EditText) findViewById(R.id.editTextTextPassword);
                String passwordString = password.getText().toString();

                user = DataBaseUtils.GetUserByLogin(v.getContext(), usernameString, passwordString);


                // textView is the TextView view that should display it
                //textView.setText(currentDateTimeString);

                //GET GOAL DATE FROM USER AND CREATE TEXT VIEW FOR DISPLAY
                Date endDate = new Date();
                EditText goalEndDate = (EditText) findViewById(R.id.editTextDate);
                String goalDate = goalEndDate.toString();
                try {
                    endDate = new SimpleDateFormat("MM/dd/yyyy").parse(goalDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                TextView textViewGoalDate = findViewById(R.id.viewGoalDate);
                textViewGoalDate.setText(goalDate);

                Date d = new Date("MM/dd/yyyy");
                //SAVE GOAL
                Goal g = new Goal();
                g.UserID = user.UserID;
                g.EndBalance = i;
                g.StartBalance=0;
                g.StartDate = d;
                g.EndDate = endDate;

                DataBaseUtils.SaveGoal(v.getContext(), g);
                Toast.makeText(getApplicationContext(), "Goal Saved", Toast.LENGTH_SHORT).show();

                //Intent intent = new Intent(AddGoal.this, ViewHistory.class);
                //startActivity(intent);

            }
        });


    }
}

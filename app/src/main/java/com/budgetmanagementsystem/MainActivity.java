package com.budgetmanagementsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

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

        txt1 = findViewById(R.id.text1);
        user = DataBaseUtils.GetUserByID(this, 1);
        goal = DataBaseUtils.GetGoalByID(this, 1);
        trans = DataBaseUtils.GetTransactionByID(this, 1);

        txt1.setText(user.Username);
    }
}
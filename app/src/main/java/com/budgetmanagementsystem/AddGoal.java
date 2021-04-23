package com.budgetmanagementsystem;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.database.DatabaseUtils;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.cottacush.android.currencyedittext.CurrencyEditText;

import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AddGoal extends AppCompatActivity {

    EditText etGoalStartDate, etGoalEndDate;
    CurrencyEditText etGoalStartAmnt, etGoalEndAmnt;
    Button btnSubmit;

    long userID;
    final Calendar myCalendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_goal);

        userID = getIntent().getLongExtra("userID", 0);

        etGoalStartDate = findViewById(R.id.editTextDate2);
        etGoalEndDate = findViewById(R.id.editTextDate);
        etGoalStartAmnt = findViewById(R.id.goalAmount);
        etGoalEndAmnt = findViewById(R.id.editTextNumber);
        btnSubmit = findViewById(R.id.goalButton);

        Goal goal = DataBaseUtils.GetGoalByID(this, userID);
        if(goal != null)
        {
            etGoalStartDate.setText(goal.StartDate.toString());
            etGoalEndDate.setText(goal.EndDate.toString());
            etGoalStartAmnt.setText(goal.StartBalance+"");
            etGoalEndAmnt.setText(goal.EndBalance+"");
        }

        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel(etGoalStartDate);
            }
        };

        DatePickerDialog.OnDateSetListener date2 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel(etGoalEndDate);
            }
        };

        etGoalStartDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(AddGoal.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        etGoalEndDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(AddGoal.this, date2, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Goal goal = new Goal();

                goal.StartDate = java.sql.Date.valueOf(etGoalStartDate.getText().toString());
                goal.EndDate = java.sql.Date.valueOf(etGoalEndDate.getText().toString());
                goal.StartBalance = etGoalStartAmnt.getNumericValue();
                goal.EndBalance = etGoalEndAmnt.getNumericValue();
                goal.UserID = userID;

                DataBaseUtils.SaveGoal(v.getContext(), goal);
                Toast.makeText(getApplicationContext(), "Goal Saved", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(AddGoal.this, Menu.class);
                intent.putExtra("userID", userID);
                startActivity(intent);
            }
        });
    }

    private void updateLabel(EditText et) {
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        et.setText(sdf.format(myCalendar.getTime()));
    }
}

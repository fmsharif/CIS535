package com.budgetmanagementsystem;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import androidx.appcompat.app.AppCompatActivity;

import com.cottacush.android.currencyedittext.CurrencyEditText;

public class AddTransaction extends AppCompatActivity {

    EditText etDesc, etDate;
    CurrencyEditText etAmnt;
    long userID;
    final Calendar myCalendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_transaction);

        etDesc = findViewById(R.id.description);
        etDate = findViewById(R.id.date);
        etAmnt = findViewById(R.id.amount);

        userID = getIntent().getLongExtra("userID", 0);

        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }
        };

        etDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(AddTransaction.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    public void submitTransaction(View view)
    {
        Transaction trans = new Transaction();

        trans.UserID = userID;
        trans.TransactionName = etDesc.getText().toString();
        trans.TransactionDate = Date.valueOf(etDate.getText().toString());
        trans.TransactionAmount = etAmnt.getNumericValue();

        DataBaseUtils.SaveTransaction(view.getContext(), trans);
        finish();
    }

    public void cancel(View view)
    {
        finish();
    }

    private void updateLabel() {
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        etDate.setText(sdf.format(myCalendar.getTime()));
    }
}

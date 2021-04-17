package com.budgetmanagementsystem;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.cottacush.android.currencyedittext.CurrencyEditText;

import java.sql.Date;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class EditTransaction extends AppCompatActivity {

    EditText etDesc, etDate;
    CurrencyEditText etAmnt;
    Transaction trans;
    final Calendar myCalendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_transaction);

        etDesc = findViewById(R.id.description);
        etDate = findViewById(R.id.date);
        etAmnt = findViewById(R.id.amount);

        long transID = getIntent().getLongExtra("transactionID", 0);
        trans = DataBaseUtils.GetTransactionByID(this, transID);

        etDesc.setText(trans.TransactionName);
        etDate.setText(trans.TransactionDate.toString());
        etAmnt.setText(trans.TransactionAmount + "");

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
                new DatePickerDialog(EditTransaction.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    public void updateTransaction(View view)
    {
        trans.TransactionName = etDesc.getText().toString();
        trans.TransactionDate = Date.valueOf(etDate.getText().toString());
        trans.TransactionAmount = etAmnt.getNumericValue();

        DataBaseUtils.SaveTransaction(view.getContext(), trans);
        finish();
    }

    public void deleteTransaction(View view)
    {
        DataBaseUtils.DeleteTransaction(view.getContext(), trans);
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

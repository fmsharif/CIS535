package com.budgetmanagementsystem;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.sql.Date;

import androidx.appcompat.app.AppCompatActivity;

public class AddTransaction extends AppCompatActivity {

    EditText etDesc, etDate, etAmnt;
    long userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_transaction);

        etDesc = findViewById(R.id.description);
        etDate = findViewById(R.id.date);
        etAmnt = findViewById(R.id.amount);

        userID = getIntent().getLongExtra("userID", 0);
    }

    public void submitTransaction(View view)
    {
        Transaction trans = new Transaction();

        trans.UserID = userID;
        trans.TransactionName = etDesc.getText().toString();
        trans.TransactionDate = Date.valueOf(etDate.getText().toString());
        trans.TransactionAmount = Double.parseDouble(etAmnt.getText().toString());

        DataBaseUtils.SaveTransaction(view.getContext(), trans);
        finish();
    }

    public void cancel(View view)
    {
        finish();
    }
}

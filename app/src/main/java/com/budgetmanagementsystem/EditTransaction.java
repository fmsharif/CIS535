package com.budgetmanagementsystem;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.sql.Date;

public class EditTransaction extends AppCompatActivity {

    EditText etDesc, etDate, etAmnt;
    Transaction trans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_transaction);

        etDesc = findViewById(R.id.description);
        etDate = findViewById(R.id.date);
        etAmnt = findViewById(R.id.amount);

        long transID = getIntent().getLongExtra("transactionID", 0);
        System.out.println(transID);
        trans = DataBaseUtils.GetTransactionByID(this, transID);

        etDesc.setText(trans.TransactionName);
        etDate.setText(trans.TransactionDate.toString());
        etAmnt.setText(trans.TransactionAmount + "");
    }

    public void updateTransaction(View view)
    {
        trans.TransactionName = etDesc.getText().toString();
        trans.TransactionDate = Date.valueOf(etDate.getText().toString());
        trans.TransactionAmount = Double.parseDouble(etAmnt.getText().toString());

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
}

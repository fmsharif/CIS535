package com.budgetmanagementsystem;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import androidx.appcompat.app.AppCompatActivity;

public class ViewHistory extends AppCompatActivity {

    ListView listView;
    long userID;

    private void refreshActivity()
    {
        listView = findViewById(R.id.historyListView);
        userID = getIntent().getLongExtra("userID", 0);

        ArrayList<Transaction> transactions = new ArrayList<>();
        TransactionAdapter adapter = new TransactionAdapter(this, transactions);
        listView.setAdapter(adapter);

        Transaction[] transArray = DataBaseUtils.GetTransactionsByUser(this, userID);
        for (Transaction transaction : transArray) {
            adapter.add(transaction);
        }

        listView.setOnItemClickListener((parent, view, position, id) -> {
            Transaction trans = (Transaction) listView.getItemAtPosition(position);

            Intent intent = new Intent(ViewHistory.this, EditTransaction.class);
            intent.putExtra("transactionID", trans.TransactionID);
            startActivity(intent);
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_history);

        refreshActivity();
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        refreshActivity();
    }

    public void back(View view)
    {
        finish();
    }
}

package com.budgetmanagementsystem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class TransactionAdapter extends ArrayAdapter<Transaction> {

    public TransactionAdapter(Context context, ArrayList<Transaction> transactions) {
        super(context, 0, transactions);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_transaction, parent, false);
        }
        // Get the data item for this position
        Transaction transaction = getItem(position);

        if(transaction != null)
        {
            // Lookup view for data population
            TextView tvName = (TextView) convertView.findViewById(R.id.tvName);
            TextView tvDate = (TextView) convertView.findViewById(R.id.tvDate);
            TextView tvAmnt = (TextView) convertView.findViewById(R.id.tvAmnt);
            // Populate the data into the template view using the data object
            System.out.println(position);
            System.out.println(transaction != null);
            tvName.setText(transaction.TransactionName);
            tvDate.setText(transaction.TransactionDate.toString());
            tvAmnt.setText(transaction.TransactionAmount + "");
            
        }

        // Return the completed view to render on screen
        return convertView;
    }

}

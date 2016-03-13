package me.saket.phonepesaket.ui.transaction.list;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import me.saket.phonepesaket.R;

/**
 * Shows a list of transactions — Pending and Completed (history).
 */
public class TransactionListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_list);
    }

}
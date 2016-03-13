package me.saket.phonepesaket.ui.transaction.list;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import me.saket.phonepesaket.R;
import me.saket.phonepesaket.ui.BaseActivity;

/**
 * Shows a list of transactions — Pending and Completed (history).
 */
public class TransactionListActivity extends BaseActivity {

    /** Launches this Activity */
    public static void start(Context context) {
        context.startActivity(new Intent(context, TransactionListActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_list);
    }

}
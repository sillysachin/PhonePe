package me.saket.phonepesaket.ui.transaction.list;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;

import me.saket.phonepesaket.R;
import me.saket.phonepesaket.ui.BaseActivity;
import me.saket.phonepesaket.ui.BasePresenter;

/**
 * Shows a list of transactions — Pending and Completed (history).
 */
public class TransactionListActivity extends BaseActivity implements TxnListContract.View {

    /** Launches this Activity */
    public static void start(Context context) {
        context.startActivity(new Intent(context, TransactionListActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_list);
    }

    @NonNull
    @Override
    protected BasePresenter<? extends BaseActivity> getPresenter() {
        return null;
    }

}
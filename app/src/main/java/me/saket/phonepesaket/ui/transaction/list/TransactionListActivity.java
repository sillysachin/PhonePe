package me.saket.phonepesaket.ui.transaction.list;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.view.ViewGroup;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.saket.phonepesaket.R;
import me.saket.phonepesaket.data.TransactionManager;
import me.saket.phonepesaket.data.models.Transaction;
import me.saket.phonepesaket.ui.BaseActivity;
import me.saket.phonepesaket.ui.widgets.recyclerview.SimpleRecyclerView;

import static android.support.design.widget.Snackbar.LENGTH_LONG;

/**
 * Shows a list of transactions — Pending and Completed (history).
 */
public class TransactionListActivity extends BaseActivity implements TxnListContract.View {

    @Bind(R.id.root_layout) ViewGroup mRootLayout;
    @Bind(R.id.list_transaction) SimpleRecyclerView mTransactionList;

    private TransactionListAdapter mTransactionListAdapter;
    private TxnListPresenter mPresenter;

    /** Launches this Activity */
    public static void start(Context context) {
        context.startActivity(new Intent(context, TransactionListActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_list);
        ButterKnife.bind(this);

        mPresenter = new TxnListPresenter(this, TransactionManager.getInstance());
        mPresenter.onCreate();
    }

    @NonNull
    @Override
    protected TxnListPresenter getPresenter() {
        return mPresenter;
    }

    @Override
    public void setupTransactionList() {
        mTransactionListAdapter = new TransactionListAdapter(this, getEventBus());
        mTransactionList.setAdapter(mTransactionListAdapter);
    }

    @Override
    public void updateTransactionList(List<Transaction> pendingTransactions, List<Transaction>
            pastTransactions) {
        mTransactionListAdapter.updateData(pendingTransactions, pastTransactions);
    }

    @Override
    public void setListLoadingProgressIndicatorVisible(boolean visible) {
        mTransactionListAdapter.setLoadingMorePastTransactions(visible);
    }

    @Override
    public void showGenericNetworkError() {
        Snackbar.make(mRootLayout, R.string.could_not_reach_phone_pe_servers, LENGTH_LONG).show();
    }

    @OnClick(R.id.btn_load_some_transactions)
    void onLoadMoreTransactionsClick() {
        mPresenter.onLoadMoreTransactionsClick();

        // Also scroll to the bottom so that the new items become visible
        mTransactionList.scrollToPosition(mTransactionListAdapter.getItemCount() - 1);
    }

}
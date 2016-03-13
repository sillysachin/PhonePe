package me.saket.phonepesaket.ui.transaction.list;

import android.support.annotation.StringRes;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.saket.phonepesaket.R;

/**
 * ViewHolder for a transaction group header (eg., "Pending", "History").
 * Used in {@link TransactionListAdapter}.
 */
public class ViewHolderTransactionHeaderItem extends RecyclerView.ViewHolder {

    @Bind(R.id.txt_header) TextView mHeaderText;

    public ViewHolderTransactionHeaderItem(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void bind(@StringRes int headerStringRes) {
        mHeaderText.setText(headerStringRes);
    }

}
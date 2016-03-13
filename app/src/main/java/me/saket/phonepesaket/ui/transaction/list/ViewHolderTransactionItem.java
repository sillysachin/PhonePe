package me.saket.phonepesaket.ui.transaction.list;

import android.content.Context;
import android.support.annotation.StringRes;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.saket.phonepesaket.R;
import me.saket.phonepesaket.data.models.Transaction;
import me.saket.phonepesaket.data.models.TransactionType;
import me.saket.phonepesaket.utils.Numbers;

/**
 * ViewHolder for one item in the transaction list. Used in {@link TransactionListAdapter}.
 * The data-binding part happens inside {@link #bind(Transaction)}.
 */
public class ViewHolderTransactionItem extends RecyclerView.ViewHolder {

    @Bind(R.id.transaction_title) TextView titleText;
    @Bind(R.id.transaction_amount) TextView amountText;
    @Bind(R.id.timestamp) TextView timestampText;

    @Bind(R.id.btn_decline) Button declineButton;
    @Bind(R.id.btn_pay) Button payButton;

    public ViewHolderTransactionItem(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    /**
     * Binds the data of one {@link Transaction} with this VH's Views.
     * OnClick listeners for the action buttons (Pay, Decline) must be
     * handled by the adapter.
     */
    void bind(Transaction transaction) {
        // Title
        // Eg., "{User} has requested", "Electricity bill due {date}"
        titleText.setText(constructTransactionDisplayTitle(transaction));

        // Show relative timestamp (eg., "5 hours ago", "Now", etc.)
        //timestampText.setText(getRelativeTimestamp(transaction.timeUpdatedMs));

        // Transaction amount
        amountText.setText(getAmountTextWithRupeeSymbol(transaction));
    }

    private static String getAmountTextWithRupeeSymbol(Transaction transaction) {
        return Numbers.IndianFigureFormatter.format(transaction.getAmount().doubleValue());
    }

    /**
     * Depending upon the type of <var>transaction</var>, returns a text that can be
     * displayed for this list item.
     *
     * Eg., "{User} has requested", "Electricity bill due {date}", etc.
     */
    @StringRes
    private static int constructTransactionDisplayTitle(Transaction transaction) {
        final TransactionType transactionType = transaction.getTransactionType();
        switch (transactionType) {
            case PAY:
                return R.string.you_paid;

            case COLLECT:
                return R.string.user_has_requested;

            case RECHARGE:
                return R.string.phone_recharge;

            default:
                throw new IllegalStateException("Unknown TransactionType: " + transactionType);
        }
    }

    /**
     * Returns a human-readable timestamp, relative to the current time.
     * Eg., "42 mins ago", etc.
     */
    private static CharSequence getRelativeTimestamp(Context context, long timeMs) {
        return DateUtils.getRelativeTimeSpanString(context, timeMs);
    }

}
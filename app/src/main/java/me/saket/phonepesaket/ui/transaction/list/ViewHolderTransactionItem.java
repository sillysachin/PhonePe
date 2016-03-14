package me.saket.phonepesaket.ui.transaction.list;

import android.content.Context;
import android.content.res.Resources;
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
import me.saket.phonepesaket.utils.TimeUtils;

/**
 * ViewHolder for one item in the transaction list. Used in {@link TransactionListAdapter}.
 * The data-binding part happens inside {@link #bindPending(Transaction)}.
 */
public class ViewHolderTransactionItem extends RecyclerView.ViewHolder {

    @Bind(R.id.txt_transaction_title) TextView titleText;
    @Bind(R.id.txt_transaction_amount) TextView amountText;
    @Bind(R.id.txt_timestamp) TextView timestampText;
    @Bind(R.id.txt_transaction_id) TextView transactionId;

    @Bind(R.id.btn_decline) Button declineButton;
    @Bind(R.id.btn_pay) Button payButton;

    public ViewHolderTransactionItem(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    /**
     * Binds the data of a pending {@link Transaction} with this VH's Views.
     * Shows PAY and DECLINE buttons. Their OnClick listeners must be handled
     * by the adapter.
     */
    void bindPending(Transaction transaction) {
        bindTitleTimestampAndAmount(transaction);
        setActionButtonsVisible(true);      // Show Action buttons
        setTransactionIdVisible(false);     // Hide txn Id
    }

    /**
     * Binds the data of a completed {@link Transaction} with this VH's Views.
     * PAY and DECLINE buttons are hidden. Transaction Ids are visible.
     */
    void bindPast(Transaction transaction) {
        bindTitleTimestampAndAmount(transaction);
        setActionButtonsVisible(false);     // Hide Action buttons
        setTransactionIdVisible(true);      // Show txn Id

        payButton.setOnClickListener(null);
        declineButton.setOnClickListener(null);

        // Transaction Id
        final Resources resources = itemView.getResources();
        transactionId.setText(resources.getString(R.string.transaction_id_x, transaction.id));
    }

    /**
     * Binds title, amount and timestamp.
     * These are common to pending and past transactions.
     */
    void bindTitleTimestampAndAmount(Transaction transaction) {
        // Title
        // Eg., "{User} has requested", "Electricity bill due {date}"
        titleText.setText(constructTransactionDisplayTitle(transaction));

        // Show relative timestamp (eg., "5 hours ago", "Now", etc.)
        timestampText.setText(getRelativeTimestamp(
                timestampText.getContext(),
                System.currentTimeMillis() - (TimeUtils.HOURS_IN_MILLIS * 5)
        ));

        // Transaction amount
        amountText.setText(getAmountTextWithRupeeSymbol(transaction));
    }

    /**
     * Pretty formats the amount with commas and Indian rupee symbol.
     */
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
                switch (transaction.getTransactionStatus()) {
                    case COMPLETED:
                        return R.string.successfully_paid;

                    case CANCELLED:
                        return R.string.payment_canceled;

                    case DECLINED:
                        return R.string.payment_declined;

                    case CREATED:
                        return R.string.user_has_requested;
                }

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

    /**
     * Sets the visibility of action buttons. They should only be visible
     * for pending transactions.
     */
    private void setActionButtonsVisible(boolean visible) {
        payButton.setVisibility(visible ? View.VISIBLE : View.GONE);
        declineButton.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    /**
     * Transaction Id is shown for completed transactions.
     */
    private void setTransactionIdVisible(boolean visible) {
        transactionId.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

}
package me.saket.phonepesaket.ui.transaction.list;

import android.content.Context;
import android.support.annotation.StringRes;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import me.saket.phonepesaket.R;
import me.saket.phonepesaket.data.EventBus;
import me.saket.phonepesaket.data.events.OnTransactionDeclineEvent;
import me.saket.phonepesaket.data.events.OnTransactionPayClickEvent;
import me.saket.phonepesaket.data.models.Transaction;
import me.saket.phonepesaket.ui.widgets.recyclerview.RecyclerViewListAdapter;

import static me.saket.phonepesaket.utils.Collections.isEmpty;
import static me.saket.phonepesaket.utils.Collections.size;

/**
 * Adapter for the transaction list which shows pending and past transactions.
 * Used in {@link TransactionListActivity}.
 *
 * Along with the two transaction types, this adapter also shows list headers
 * before each txn type and a loading indicator at the bottom if more items are
 * being down-synced with the server.
 *
 * In order to maintain simplicity (and save time), this adapter uses a single
 * Collection for both transaction type items (pending and past) along with
 * list headers and loading-progress item.
 *
 * See {@link #getItemViewType(int)} for the jugaad.
 *
 * In the future, we can use a more complex adapter that ensures type-safety.
 * Update: Chuck type safety, simplicity always wins.
 *
 * Use {@link #updateData(List, List)} for updating this adapter's data.
 */
public class TransactionListAdapter extends RecyclerViewListAdapter<Object,
        RecyclerView.ViewHolder> {

    private static final String TAG = "TransactionListAdapter";
    public static final int VIEW_TYPE_TRANSACTION = 0;
    public static final int VIEW_TYPE_HEADER = 1;
    public static final int VIEW_TYPE_LOADING_PROGRESS = 2;

    public static final int LAYOUT_RES_TRANSACTION = R.layout.list_item_transaction;
    public static final int LAYOUT_RES_HEADER = R.layout.list_item_transaction_header;
    public static final int LAYOUT_RES_LOADING_PROGRESS = R.layout.list_item_loading_progress;

    public static final long ITEM_ID_HEADER_PENDING = 0L;
    public static final long ITEM_ID_HEADER_HISTORY = 1L;

    private final EventBus mEventBus;

    /**
     * Represents one header in the list.
     * Eg., "Pending", "History", etc.
     */
    class HeaderItem {
        @StringRes int headerStringRes;
        long headerId;

        public HeaderItem(long headerId, @StringRes int headerStringRes) {
            this.headerId = headerId;
            this.headerStringRes = headerStringRes;
        }
    }

    /**
     * Represents the loading-more-items item in the list.
     */
    class LoadingProgressHeaderItem {
    }

    public TransactionListAdapter(Context context, EventBus eventBus) {
        super(context, null /* initialData */);
        mEventBus = eventBus;
    }

    @Override
    public int getItemViewType(int position) {
        final Object item = getItem(position);
        if (item instanceof Transaction) {
            return VIEW_TYPE_TRANSACTION;
        }

        if (item instanceof HeaderItem) {
            return VIEW_TYPE_HEADER;
        }

        if (item instanceof LoadingProgressHeaderItem) {
            return VIEW_TYPE_LOADING_PROGRESS;
        }

        return super.getItemViewType(position);
    }

    @Override
    public long getItemId(int position) {
        final Object item = getItem(position);
        final int viewType = getItemViewType(position);

        switch (viewType) {
            case VIEW_TYPE_HEADER:
                return ((HeaderItem) item).headerId;

            case VIEW_TYPE_TRANSACTION:
                return ((Transaction) item).id;

            case VIEW_TYPE_LOADING_PROGRESS:
                return RecyclerView.NO_ID;

            default:
                throw new IllegalStateException("Unknown view-type: " + viewType);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case VIEW_TYPE_HEADER:
                return new ViewHolderTransactionHeaderItem(inflate(parent, LAYOUT_RES_HEADER));

            case VIEW_TYPE_TRANSACTION:
                return new ViewHolderTransactionItem(inflate(parent, LAYOUT_RES_TRANSACTION));

            case VIEW_TYPE_LOADING_PROGRESS:
                return new ViewHolderLoadingProgress(inflate(parent, LAYOUT_RES_LOADING_PROGRESS));

            default:
                throw new IllegalStateException("Unknown view-type: " + viewType);
        }
    }

    @Override
    protected void onBindViewHolderWithData(RecyclerView.ViewHolder holder, int position) {
        final int viewType = getItemViewType(position);
        switch (viewType) {
            case VIEW_TYPE_HEADER:
                final HeaderItem headerItem = (HeaderItem) getItem(position);
                ((ViewHolderTransactionHeaderItem) holder).bind(headerItem.headerStringRes);
                break;

            case VIEW_TYPE_TRANSACTION:
                final Transaction transaction = (Transaction) getItem(position);
                bindTransactionItem((ViewHolderTransactionItem) holder, transaction);
                break;

            case VIEW_TYPE_LOADING_PROGRESS:
                // No binding required for the progress circle.
                break;

            default:
                throw new IllegalStateException("Unknown view-type: " + viewType);
        }
    }

    /**
     * Binds a transaction item with its VH + registers click listeners for
     * the "Pay" and "Decline" buttons.
     */
    private void bindTransactionItem(ViewHolderTransactionItem transactionItemVh,
                                     Transaction transaction) {
        // The ViewHolder handles binding the title,
        // avatar, amount and timestamp.
        transactionItemVh.bind(transaction);

        // Pay button
        transactionItemVh.payButton.setOnClickListener(view -> {
            mEventBus.emit(new OnTransactionPayClickEvent(transaction));
        });

        // Decline button
        transactionItemVh.declineButton.setOnClickListener(view -> {
            mEventBus.emit(new OnTransactionDeclineEvent(transaction));
        });
    }

    /**
     * @deprecated Use {@link #updateData(List, List)} instead.
     */
    @Override
    public void updateData(List<Object> newObjects) {
        super.updateData(newObjects);
    }

    /**
     * Updates the data-set of this adapter. Pending transactions are kept at
     * the top. The ordering of the transactions is not affected.
     *
     * @param pendingTransactions Transactions pending to be done by the user.
     * @param pastTransactions    Transactions already completed by the user.
     */
    public void updateData(List<Transaction> pendingTransactions,
                           List<Transaction> pastTransactions) {

        final List<Object> newItems = new ArrayList<>(size(pendingTransactions)
                + size(pastTransactions) + 3);    // +2 for headers +1 for loading indicator

        int i=0;

        // Start with the pending transactions
        if (!isEmpty(pendingTransactions)) {
            // Header
            newItems.add(i, new HeaderItem(ITEM_ID_HEADER_PENDING, R.string.pending));
            i++;

            // Transaction items
            for (final Transaction pendingTransaction : pendingTransactions) {
                newItems.add(i, pendingTransaction);
                i++;
            }
        }

        // Next, past transactions
        if (!isEmpty(pastTransactions)) {
            // Header
            newItems.add(i, new HeaderItem(ITEM_ID_HEADER_HISTORY, R.string.history));
            i++;

            // Transaction items
            for (final Transaction pastTransaction : pastTransactions) {
                newItems.add(i, pastTransaction);
                i++;
            }
        }

        // TODO: Finally the loading indicator if down-sync is on-going

        // Refresh!
        Log.i(TAG, "List updated with " + newItems.size() + " items");
        super.updateData(newItems);
    }

}
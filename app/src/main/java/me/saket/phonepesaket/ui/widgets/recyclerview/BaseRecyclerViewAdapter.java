package me.saket.phonepesaket.ui.widgets.recyclerview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Saketme on 9/13/2014.
 * Plug-n-Play Adapter for RecyclerView that:
 * <p>
 * 1. Implements setActivated() on views whose positions are checked in the
 * associated RecyclerView.
 * <p>
 * 2. Sets OnClick and OnLongClick listeners.
 */
public abstract class BaseRecyclerViewAdapter<VH extends RecyclerView.ViewHolder>
        extends RecyclerView.Adapter<VH> {

    private SimpleRecyclerView mSimpleRecyclerView;
    private final Context mContext;
    private final LayoutInflater mInflater;
    private OnItemClickListener mItemClickListener;

    /**
     * Interface definition for a callback to be invoked when an item in this
     * AdapterView is clicked.
     */
    public interface OnItemClickListener {
        /**
         * Callback method to be invoked when an item in this RecyclerView has
         * been clicked.
         * <p>
         *
         * @param view            The view within the RecyclerView that was clicked (this
         *                        will be a view provided by the adapter)
         * @param adapterPosition The position of the view in the adapter.
         * @param viewPosition    The (index) position of the view inside the RecyclerView
         * @param itemId          The row id of the item that was clicked.
         */
        void onItemClick(View view, int viewPosition, int adapterPosition, long itemId);
    }

    /**
     * Interface definition for a callback to be invoked when an item in this
     * view has been clicked and held.
     */
    public interface OnItemLongClickListener {
        /**
         * Callback method to be invoked when an item in this view has been
         * clicked and held.
         * <p>
         * Implementers can call getItemAtPosition(position) if they need to access
         * the data associated with the selected item.
         *
         * @param view     The view within the AbsListView that was clicked
         * @param position The position of the view in the list
         * @param id       The row id of the item that was clicked
         * @return true if the callback consumed the long click, false otherwise
         */
        boolean onItemLongClick(View view, int position, long id);
    }

    private OnItemLongClickListener mItemLongClickListener;

    public BaseRecyclerViewAdapter(@NonNull Context context) {
        if (context == null) {
            throw new IllegalArgumentException("Context cannot be null");
        }
        mContext = context;
        mInflater = LayoutInflater.from(context);
        setHasStableIds(true);
    }

    protected boolean outOfBounds(int position) {
        return (getItemCount() < 0) || (position < 0) || (position >= getItemCount());
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mItemClickListener = listener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener longClickListener) {
        mItemLongClickListener = longClickListener;
    }

    public Context getContext() {
        return mContext;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        mSimpleRecyclerView = (SimpleRecyclerView) recyclerView;
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        mSimpleRecyclerView = null;
    }

    public SimpleRecyclerView getRecyclerView() {
        return mSimpleRecyclerView;
    }

    public LayoutInflater getInflater() {
        return mInflater;
    }

    /**
     * Returns the position of an item using its itemId
     */
    public int getPositionFromItemId(long itemId) {
        for (int i = 0; i < getItemCount(); i++) {
            if (getItemId(i) == itemId) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public abstract long getItemId(int position);

    @Override
    public final void onBindViewHolder(final VH holder, int position) {
        //Log.i(TAG, "onBindVH at pos: " + holder.getPosition());
        onBindViewHolderWithData(holder, position);

        // Tap and long press listeners
        if (mItemClickListener != null) {
            holder.itemView.setOnClickListener(v -> mItemClickListener.onItemClick(
                    holder.itemView,
                    ((ViewGroup) holder.itemView.getParent()).indexOfChild(holder.itemView),
                    position,
                    getItemId(position)
            ));
        }

        if (mItemLongClickListener != null) {
            holder.itemView.setOnLongClickListener(v -> mItemLongClickListener.onItemLongClick(
                    holder.itemView,
                    position,
                    getItemId(position)
            ));
        }
    }

    protected abstract void onBindViewHolderWithData(final VH holder, final int position);

    /**
     * Convenience method for inflating Views inside onCreateViewHolder()
     */
    protected View inflate(ViewGroup parent, int layoutResId) {
        return mInflater.inflate(layoutResId, parent, false);
    }

}
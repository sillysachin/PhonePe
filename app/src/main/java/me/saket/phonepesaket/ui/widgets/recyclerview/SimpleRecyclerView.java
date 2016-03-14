package me.saket.phonepesaket.ui.widgets.recyclerview;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Saketme on 9/13/2014.
 * Plug-n-Play RecyclerView that:
 *
 * 1. Handles a few repetitive tasks for setting up RecyclerView. See {@link #initInternal()}
 * 2. Offers onItemClick listeners.
 *    See {@link #setOnItemClickListener(BaseRecyclerViewAdapter.OnItemClickListener)}
 *    and {@link #setOnItemLongClickListener(BaseRecyclerViewAdapter.OnItemLongClickListener)}.
 */
public class SimpleRecyclerView extends RecyclerView implements
        BaseRecyclerViewAdapter.OnItemLongClickListener,
        BaseRecyclerViewAdapter.OnItemClickListener {

    private LinearLayoutManager mLayoutManager;
    private BaseRecyclerViewAdapter.OnItemClickListener mOnItemClickListener;
    private BaseRecyclerViewAdapter.OnItemLongClickListener mOnItemLongClickListener;

    public SimpleRecyclerView(Context context) {
        super(context);
        initInternal();
    }

    public SimpleRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initInternal();
    }

    public SimpleRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initInternal();
    }

    public LinearLayoutManager getLinearLayoutManager() {
        return mLayoutManager;
    }

    public void setOnItemClickListener(BaseRecyclerViewAdapter.OnItemClickListener listener) {
        mOnItemClickListener = listener;
    }

    public void setOnItemLongClickListener(BaseRecyclerViewAdapter.OnItemLongClickListener listener) {
        mOnItemLongClickListener = listener;
    }

// ======== SETUP ======== //

    protected LinearLayoutManager createLayoutManager() {
        return new LinearLayoutManager(getContext());
    }

    @Override
    public void setLayoutManager(LayoutManager layout) {
        if (layout instanceof LinearLayoutManager) {
            mLayoutManager = (LinearLayoutManager) layout;
        }
        super.setLayoutManager(layout);
    }

    private void initInternal() {
        // Enable scrollbars by default
        setVerticalScrollBarEnabled(true);

        // Add a LayoutManager that manages laying out the Views.
        setLayoutManager(createLayoutManager());

        // Allows for optimizations if the list's height never changes.
        // Should be true for most of the cases.
        setHasFixedSize(true);
    }

    @Override
    public void setAdapter(Adapter adapter) {
        if (isInEditMode()) {
            super.setAdapter(adapter);
            return;
        }

        if (!(adapter instanceof BaseRecyclerViewAdapter)) {
            throw new IllegalStateException("RecyclerViewX only supports Adapters of " +
                    "type RecyclerAdapterX");
        }

        super.setAdapter(adapter);

        // Register click and long-press listeners
        final BaseRecyclerViewAdapter viewAdapterX = (BaseRecyclerViewAdapter) adapter;
        viewAdapterX.setOnItemClickListener(this);
        viewAdapterX.setOnItemLongClickListener(this);
    }

// ======== ON CLICK ======== //

    @Override
    public boolean onItemLongClick(View view, int adapterPosition, long itemId) {
        return mOnItemLongClickListener != null && mOnItemLongClickListener.onItemLongClick(view,
                adapterPosition, itemId);
    }

    @Override
    public void onItemClick(View view, int viewPosition, int adapterPosition, long itemId) {
        if (mOnItemClickListener != null) {
            mOnItemClickListener.onItemClick(view, viewPosition, adapterPosition, itemId);
        }
    }

}
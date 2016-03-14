package me.saket.phonepesaket.ui.widgets.recyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import java.util.List;

/**
 * Created by Saketme on 25-04-2015.
 * Base class for an Array Adapter to be used with a RecyclerView.
 * Use {@link #updateData(List)} for updating the data-set.
 *
 * @param <T>  Type of the list items
 * @param <VH> Type of the ViewHolder
 */
public abstract class RecyclerViewListAdapter<T, VH extends RecyclerView.ViewHolder>
        extends BaseRecyclerViewAdapter<VH> {

    private List<T> mObjects;

    public RecyclerViewListAdapter(Context context, final List<T> objects) {
        super(context);
        mObjects = objects;
    }

    /**
     * Adds the specified object at the end of the array.
     *
     * @param object The object to add at the end of the array.
     */
    public void add(final T object) {
        mObjects.add(object);
        notifyItemInserted(getItemCount() - 1);
    }

    /**
     * Remove all elements from the list.
     */
    public void clear() {
        final int size = getItemCount();
        mObjects.clear();
        notifyItemRangeRemoved(0, size);
    }

    @Override
    public int getItemCount() {
        return mObjects != null ? mObjects.size() : 0;
    }

    public T getItem(final int position) {
        return mObjects != null ? mObjects.get(position) : null;
    }

    public long getItemId(final int position) {
        return position;
    }

    /**
     * Returns the position of the specified item in the array.
     *
     * @param item The item to retrieve the position of.
     * @return The position of the specified item.
     */
    public int getPosition(final T item) {
        return mObjects.indexOf(item);
    }

    /**
     * Inserts the specified object at the specified index in the array.
     *
     * @param object The object to insert into the array.
     * @param index  The index at which the object must be inserted.
     */
    public void insert(final T object, int index) {
        mObjects.add(index, object);
        notifyItemInserted(index);
    }

    /**
     * Removes the specified object from the array.
     *
     * @param object The object to remove.
     */
    public void remove(T object) {
        final int position = getPosition(object);
        mObjects.remove(object);
        notifyItemRemoved(position);
    }

    /**
     * Returns the data-set.
     */
    public List<T> getData() {
        return mObjects;
    }

    /**
     * Refreshes the existing data-set with a new one.
     */
    public void updateData(List<T> newObjects) {
        onDataAboutToBeUpdated(newObjects);
        mObjects = newObjects;
        notifyDataSetChanged();
    }

    protected void onDataAboutToBeUpdated(List<T> newResults) {
        // For rent. Broker free.
    }

}
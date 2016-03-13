package me.saket.phonepesaket.ui.widgets.recyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Saketme on 25-04-2015.
 * Base class for an Array Adapter to be used with a RecyclerView.
 * Use {@link #updateDataset(List)} for updating the data-set.
 *
 * @param <T>  Type of the list items
 * @param <VH> Type of the ViewHolder
 */
public abstract class ArrayAdapterRecyclerView<T, VH extends RecyclerView.ViewHolder>
        extends BaseRecyclerViewAdapter<VH> {

    private List<T> mObjects;

    public ArrayAdapterRecyclerView(Context context, final List<T> objects) {
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
     * Replaces the existing data-set with a new one.
     */
    public void updateDataset(List<T> newObjects) {
        mObjects = newObjects;
        notifyDataSetChanged();
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

    public abstract long getItemId(final int position);

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
     * Sorts the content of this adapter using the specified comparator.
     *
     * @param comparator The comparator used to sort the objects contained in this adapter.
     */
    public void sort(Comparator<? super T> comparator) {
        Collections.sort(mObjects, comparator);
        notifyItemRangeChanged(0, getItemCount());
    }

    /**
     * Returns the data-set.
     */
    public List<T> getItems() {
        return mObjects;
    }

}
/*
 * Copyright (C) 2015 Tomás Ruiz-López.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package me.saket.phonepesaket.ui.widgets.recyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

/**
 * Copied from a library.
 * An extension to RecyclerView.Adapter to provide sections with headers and footers to a
 * RecyclerView. Each section can have an arbitrary number of items.
 *
 * For showing different ViewTypes for headers and footers, override the following:
 * <ul>
 *     <li>{@link #getSectionHeaderViewType(int)}</li>
 *     <li>{@link #getSectionFooterViewType(int)} </li>
 *     <li>{@link #isSectionHeaderViewType(int)}</li>
 *     <li>{@link #isSectionFooterViewType(int)}</li>
 * </ul>
 *
 * Additionally, sections are by default disabled if the section count is < 2.
 * Override {@link #showHeaderForSingleSection()} to disable this behavior.
 *
 * @param <T>  Type of the data-set items.
 * @param <H>  Class extending RecyclerView.ViewHolder to hold and bind the header view
 * @param <VH> Class extending RecyclerView.ViewHolder to hold and bind the items view
 * @param <F>  Class extending RecyclerView.ViewHolder to hold and bind the footer view
 *             <p>
 *             v1.1.0
 */
public abstract class SectionedRecyclerViewListAdapter<T,
                H extends RecyclerView.ViewHolder,
                VH extends RecyclerView.ViewHolder,
                F extends RecyclerView.ViewHolder>
        extends RecyclerViewListAdapter<T, RecyclerView.ViewHolder> {

    protected static final int TYPE_SECTION_HEADER = -1;
    protected static final int TYPE_SECTION_FOOTER = -2;
    protected static final int TYPE_ITEM = -3;

    private int[] sectionForPosition = null;
    private int[] positionWithinSection = null;
    private boolean[] isHeader = null;
    private boolean[] isFooter = null;
    private int count = 0;

    public SectionedRecyclerViewListAdapter(Context context) {
        super(context, null);
        registerAdapterDataObserver(new SectionDataObserver());
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        setupIndices();
    }

    /**
     * Returns the sum of number of items for each section
     * plus headers and footers if they are provided.
     */
    @Override
    public int getItemCount() {
        return count;
    }

    /**
     * Total item count minus section headers and footers.
     */
    public int getItemCountWithoutSections() {
        return super.getItemCount();
    }

    private void setupIndices() {
        count = countItems();
        allocateAuxiliaryArrays(count);
        preComputeIndices();
    }

    private int countItems() {
        int count;
        final int sections = getSectionCount();

        if (sections == 1 && !showHeaderForSingleSection()) {
            count = getItemCountForSection(0) + (hasFooterInSection(0) ? 1 : 0);

        } else {
            count = 0;
            for (int i = 0; i < sections; i++) {
                count += 1 + getItemCountForSection(i) + (hasFooterInSection(i) ? 1 : 0);
            }
        }

        return count;
    }

    private void preComputeIndices() {
        int sections = getSectionCount();
        int index = 0;
        final boolean showHeaders = sections > 1 || showHeaderForSingleSection();

        for (int i = 0; i < sections; i++) {
            if (showHeaders) {
                setPrecomputedItem(index, true, false, i, 0);
                index++;
            }

            for (int j = 0; j < getItemCountForSection(i); j++) {
                setPrecomputedItem(index, false, false, i, j);
                index++;
            }

            if (hasFooterInSection(i)) {
                setPrecomputedItem(index, false, true, i, 0);
                index++;
            }
        }
    }

    private void allocateAuxiliaryArrays(int count) {
        sectionForPosition = new int[count];
        positionWithinSection = new int[count];
        isHeader = new boolean[count];
        isFooter = new boolean[count];
    }

    private void setPrecomputedItem(int index, boolean isHeader, boolean isFooter, int section,
                                    int position) {
        this.isHeader[index] = isHeader;
        this.isFooter[index] = isFooter;
        sectionForPosition[index] = section;
        positionWithinSection[index] = position;
    }

    @Override
    public final RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final RecyclerView.ViewHolder viewHolder;
        if (isSectionHeaderViewType(viewType)) {
            viewHolder = onCreateSectionHeaderViewHolder(parent, viewType);

        } else if (isSectionFooterViewType(viewType)) {
            viewHolder = onCreateSectionFooterViewHolder(parent, viewType);

        } else {
            viewHolder = onCreateItemViewHolder(parent, viewType);
        }

        return viewHolder;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected final void onBindViewHolderWithData(RecyclerView.ViewHolder holder, int position) {
        final int sectionPos = sectionForPosition[position];

        if (isSectionHeaderPosition(position)) {
            onBindSectionHeaderViewHolder((H) holder, sectionPos);

        } else if (isSectionFooterPosition(position)) {
            onBindSectionFooterViewHolder((F) holder, sectionPos);

        } else {
            onBindViewHolderWithData((VH) holder, sectionPos, position);
        }
    }

    @Override
    public final int getItemViewType(int position) {
        if (sectionForPosition == null) setupIndices();
        final int section = sectionForPosition[position];

        if (isSectionHeaderPosition(position)) {
            return getSectionHeaderViewType(section);

        } else if (isSectionFooterPosition(position)) {
            return getSectionFooterViewType(section);

        } else {
            //final int index = positionWithinSection[position];
            final int wrappedAdapterPosition = getCorrectedPosition(section, position);
            return getSectionItemViewType(section, position, super.getItem(wrappedAdapterPosition));
        }
    }

    protected int getSectionHeaderViewType(int section) {
        return TYPE_SECTION_HEADER;
    }

    protected int getSectionFooterViewType(int section) {
        return TYPE_SECTION_FOOTER;
    }

    protected int getSectionItemViewType(int section, int position, T item) {
        return TYPE_ITEM;
    }

    /**
     * Returns true if <var>position</var> corresponds to a header
     */
    public boolean isSectionHeaderPosition(int position) {
        if (isHeader == null) {
            setupIndices();
        }
        return isHeader[position];
    }

    /**
     * Returns true if the argument position corresponds to a footer
     */
    public boolean isSectionFooterPosition(int position) {
        if (isFooter == null) {
            setupIndices();
        }
        return isFooter[position];
    }

    protected boolean isSectionHeaderViewType(int viewType) {
        return viewType == TYPE_SECTION_HEADER;
    }

    protected boolean isSectionFooterViewType(int viewType) {
        return viewType == TYPE_SECTION_FOOTER;
    }

    /**
     * Returns an item's position relative to its section.
     */
    protected int getItemPositionUnderIndex(int itemPosition) {
        if (positionWithinSection == null) {
            setupIndices();
        }
        return positionWithinSection[itemPosition];
    }

    /**
     * Whether or not to show the section header when we only have 1 section in the data-set.
     */
    protected boolean showHeaderForSingleSection() {
        return false;
    }

// ======== POSITION OFF-SETTING ======== //

    // DO NOT CALL getItem() from inside getItemViewType()!
    @Override
    public T getItem(int position) {
        final int viewType = getItemViewType(position);
        if (isSectionHeaderViewType(viewType) || isSectionFooterViewType(viewType)) {
            //Log.w(TAG, "Is section header / footer");
            return null;

        } else {
            final int correctedPosition = getCorrectedPosition(position);
            return super.getItem(correctedPosition);
        }
    }

    @Override
    public final long getItemId(int position) {
        //Log.i(TAG, "getItemId() -> position: " + position);
        final int viewType = getItemViewType(position);
        return isSectionHeaderViewType(viewType) || isSectionFooterViewType(viewType)
                ? RecyclerView.NO_ID
                : super.getItemId(position);
    }

    /**
     * Computes an item's actual position in the wrapped adapter.
     */
    private int getCorrectedPosition(int sectionIndex, int sectionedAdapterPosition) {
        final int correctedPosition;

        if (getSectionCount() == 1 && !showHeaderForSingleSection()) {
            correctedPosition = sectionedAdapterPosition;
        } else {
            correctedPosition = sectionedAdapterPosition - (sectionIndex + 1);
            //Log.d(TAG, "getItem() -> position: " + position + ", sectionPos: " +
            // sectionPos
            //        + ", correctedPosition: " + correctedPosition);
        }
        return correctedPosition;
    }

    /**
     * Computes an item's actual position in the wrapped adapter.
     */
    private int getCorrectedPosition(int sectionedAdapterPosition) {
        if (sectionForPosition == null) setupIndices();
        final int sectionPos = sectionForPosition[sectionedAdapterPosition];
        return getCorrectedPosition(sectionPos, sectionedAdapterPosition);
    }

// ======== CONTRACT ======== //

    /**
     * Returns the number of sections in the RecyclerView
     */
    protected abstract int getSectionCount();

    /**
     * Returns the number of items for a given section
     */
    protected abstract int getItemCountForSection(int section);

    /**
     * Returns true if a given section should have a footer
     */
    protected abstract boolean hasFooterInSection(int section);

    /**
     * Creates a ViewHolder of class H for a Header
     */
    protected abstract H onCreateSectionHeaderViewHolder(ViewGroup parent, int viewType);

    /**
     * Creates a ViewHolder of class F for a Footer
     */
    protected abstract F onCreateSectionFooterViewHolder(ViewGroup parent, int viewType);

    /**
     * Creates a ViewHolder of class VH for an Item
     */
    protected abstract VH onCreateItemViewHolder(ViewGroup parent, int viewType);

    /**
     * Binds data to the header view of a given section
     */
    protected abstract void onBindSectionHeaderViewHolder(H holder, int section);

    /**
     * Binds data to the footer view of a given section
     */
    protected abstract void onBindSectionFooterViewHolder(F holder, int section);

    /**
     * Binds data to the item view for a given position within a section
     */
    protected abstract void onBindViewHolderWithData(VH holder, int section, int position);

    class SectionDataObserver extends RecyclerView.AdapterDataObserver {
        @Override
        public void onChanged() {
            setupIndices();
        }
    }

}
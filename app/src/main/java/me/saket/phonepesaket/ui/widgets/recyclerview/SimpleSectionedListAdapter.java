package me.saket.phonepesaket.ui.widgets.recyclerview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.ViewGroup;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * An extension of SectionedRecyclerViewAdapter for simple sectioned RecyclerViews
 * that supports list headers. The information for the headers has to be passed
 * inside {@link #constructSectionStructure(List)}.
 * <p>
 * One section is a group of list items under one header.
 *
 * @param <T>   Type of the list items
 * @param <VH>  Type of the ViewHolder for list items
 * @param <HVH> Type of the ViewHolder for section headers.
 */
public abstract class SimpleSectionedListAdapter<T, VH extends RecyclerView.ViewHolder,
        HVH extends ViewHolder> extends SectionedRecyclerViewListAdapter<T, HVH, VH,
        ViewHolder> {

    private SectionStructure mSectionStructure;
    private Set<Integer> mSectionHeaderViewTypeCache;

    public SimpleSectionedListAdapter(Context context) {
        super(context);
    }

    @Override
    protected int getSectionCount() {
        return mSectionStructure == null ? 1 : mSectionStructure.size();
    }

    @Override
    protected int getItemCountForSection(int section) {
        return mSectionStructure == null
                ? getItemCountWithoutSections()
                : mSectionStructure.get(section).getSectionItemCount();
    }

    @Override
    protected boolean hasFooterInSection(int section) {
        return false;
    }

    @Override
    protected abstract HVH onCreateSectionHeaderViewHolder(ViewGroup parent, int viewType);

    @Override
    protected abstract void onBindSectionHeaderViewHolder(HVH holder, int section);

    @Override
    protected ViewHolder onCreateSectionFooterViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    protected void onBindSectionFooterViewHolder(ViewHolder holder, int section) {

    }

// ======== DATA SET ======== //

    /**
     * Constructs the section headers data upon every data update.
     */
    @Override
    protected void onDataAboutToBeUpdated(List<T> newResults) {
        super.onDataAboutToBeUpdated(newResults);
        mSectionStructure = newResults == null ? null : constructSectionStructure(newResults);

        if (mSectionStructure == null) {
            // Reset sections when mSectionStructure is null.
            mSectionHeaderViewTypeCache = new HashSet<>(1);
            mSectionHeaderViewTypeCache.add(SectionedRecyclerViewListAdapter.TYPE_SECTION_HEADER);

        } else {
            // Cache the header ViewTypes. Useful if multiple types of headers
            // are present. This is later used in isSectionHeaderViewType().
            mSectionHeaderViewTypeCache = new HashSet<>(mSectionStructure.size());
            for (final SectionStructure.SectionItem sectionItem : mSectionStructure) {
                mSectionHeaderViewTypeCache.add(sectionItem.getSectionHeaderViewType());
            }

        }
    }

    /**
     * {@link SectionStructure} controls how your items will be divided into
     * "sections" (or simple, groups), where each section has one header.
     * <p>
     * This is called once everytime your data-set is updated. That is,
     * {@link #updateData(List)} is called.
     * <p>
     * Returning null will reset the sections.
     */
    @Nullable
    protected abstract SectionStructure constructSectionStructure(@NonNull List<T> newDataSet);

// ======== HEADERS ======== //

    @Override
    protected int getSectionHeaderViewType(int section) {
        if (mSectionStructure == null) return super.getSectionHeaderViewType(section);
        return mSectionStructure.get(section).getSectionHeaderViewType();
    }

    @Override
    protected boolean isSectionHeaderViewType(int viewType) {
        return mSectionHeaderViewTypeCache.contains(viewType);
    }

}
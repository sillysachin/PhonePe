package me.saket.phonepesaket.ui.widgets.recyclerview;

import java.util.ArrayList;

/**
 * Holds information about the sections in an adapter, like the number of sections,
 * item count in sections, etc. Each section can have one header.
 *
 * Start with {@link #create()}.
 */
public class SectionStructure extends ArrayList<SectionStructure.SectionItem> {

    public static SectionStructure create() {
        return new SectionStructure();
    }

    /** Use this when there are no sections present in the adapter. */
    public static SectionStructure createNoSections() {
        return null;
    }

    public SectionStructure addSection(SectionItem section) {
        add(section);
        return this;
    }

    public static class SectionItem {

        private int mFromPosition;
        private int mToPosition;
        private int mItemCount;
        private int mSectionHeaderViewType;

        /**
         * Use this if you have multiple header types.
         * @param headerViewType ViewType of this section's header.
         * @param fromPosition   Position of the first item in this section.
         * @param toPosition     Position of the last item in this section.
         * Both <var>fromPosition</var> and <var>toPosition</var> are inclusive.
         */
        public SectionItem(int fromPosition, int toPosition, int headerViewType) {
            if (mToPosition < mFromPosition) {
                throw new IllegalAccessError("toPosition must be greater than fromPosition");
            }

            mSectionHeaderViewType = headerViewType;
            mFromPosition = fromPosition;
            mToPosition = toPosition;
            mItemCount = mToPosition < mFromPosition ? 0 : (mToPosition - mFromPosition + 1);
        }

        /**
         * @param fromPosition  Position of the first item in this section.
         * @param toPosition    Position of the last item in this section.
         * Both <var>fromPosition</var> and <var>toPosition</var> are inclusive.
         */
        public SectionItem(int fromPosition, int toPosition) {
            this(fromPosition, toPosition, SectionedRecyclerViewListAdapter.TYPE_SECTION_HEADER);
        }

        public int getSectionItemCount() {
            return mItemCount;
        }

        public int getSectionHeaderViewType() {
            return mSectionHeaderViewType;
        }

        @Override
        public String toString() {
            return "SectionItem{" +
                    "mFromPosition=" + mFromPosition +
                    ", mToPosition=" + mToPosition +
                    ", mItemCount=" + mItemCount +
                    ", mSectionHeaderViewType=" + mSectionHeaderViewType +
                    '}';
        }

    }

}
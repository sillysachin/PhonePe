package me.saket.phonepesaket.utils;

import java.text.DecimalFormat;

/**
 * Utility methods related to numbers (eg, BigDecimal)
 */
public class Numbers {

    public static final String SYMBOL_RUPEE = "\u20B9";
    public static final String SYMBOL_NEGATIVE = "—";   // Em-dash. Slightly bigger than normal dash

    public static class IndianFigureFormatter {

        private static final DecimalFormat SMALL_FORMATTER = new DecimalFormat("###");
        private static final DecimalFormat LARGE_HUNDREDS_FORMATTER = new DecimalFormat("000");
        private static final DecimalFormat LARGE_OTHER_FORMATTER = new DecimalFormat(",##");
        public static final int MAX_SUPPORTED_LENGTH = 15;

        /**
         * Java's formatting API doesn't support formatting numbers in the Indian style where
         * the first comma (from the right) comes after the 3rd digit and the successive commas
         * after every 2 digits.
         *
         * As a simple workaround, this breaks the number into two parts — the hundreds part
         * and the remaining part and formats them separately.
         *
         * Example, 1000   -> 1,000
         *          50000  -> 50,000
         *          650000 -> 6,50,000 (and not 650,000 as used by other countries)
         *
         * Uses long and double values (4X lesser memory than BigDecimal).
         *
         * Precise up-to 15 digits (99,99,99,99,99,99,999 ~= 999 trillion). Considering that
         * Earth's GDP was 75.59 trillion in 2013, it's pretty safe to use this method for the
         * next couple of years.
         *
         * Negative values are supported, but decimals are not.
         */
        public static String format(double value) {
            final boolean isNegativeValue = value < 0;
            value = Math.abs(value);
            String formattedFigure;

            if (value < 1000) {
                formattedFigure = SMALL_FORMATTER.format((Double) value);

            } else {
                final double hundredsPart = value % 1000;
                final long otherPart = (long) (value / 1000);
                formattedFigure = LARGE_OTHER_FORMATTER.format((Long) otherPart) + ','
                        + LARGE_HUNDREDS_FORMATTER.format((Double) hundredsPart);
            }

            // Prefix rupee symbol
            formattedFigure = SYMBOL_RUPEE + formattedFigure;

            // Along with a negative symbol if required
            if (isNegativeValue) {
                formattedFigure = SYMBOL_NEGATIVE + formattedFigure;
            }
            return formattedFigure;
        }

    }

}
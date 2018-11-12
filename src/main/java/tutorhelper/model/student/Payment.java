package tutorhelper.model.student;

import java.util.Objects;

import tutorhelper.commons.core.index.Index;

/**
 * Represents a payment in the TutorHelper.
 * Guarantees: immutable;
 * amount is valid as declared in {@link #isValidAmount(int)}
 * month is valid as declared in {@link #isValidMonth(int)}
 * year is valid as declared in {@link #isValidYear(int)}
 */
public class Payment {

    public static final String MESSAGE_PAYMENT_AMOUNT_CONSTRAINTS =
            "Amount should only contain positive integers between 0 to 10 000.";
    public static final String MESSAGE_PAYMENT_MONTH_CONSTRAINTS =
            "Month should only contain integers between 1 to 12, inclusive.";
    public static final String MESSAGE_PAYMENT_YEAR_CONSTRAINTS =
            "Year should only contain 4 digits integers.";

    public static final String TAG_VALIDATION_REGEX = "(.)*(\\d)(.)*";
    private static final int MAX_PAYMENT_AMOUNT = 10000;
    private static final int JANUARY = 1;
    private static final int DECEMBER = 12;

    private final Index studentIndex;
    private int amount;
    private final int month;
    private final int year;

    /**
     * Constructs a {@code Payment}.
     *
     * @param index A valid index name.
     * @param amount A valid amount.
     * @param month A valid month.
     * @param year A valid year.
     */
    public Payment(Index index, int amount, int month, int year) {
        this.studentIndex = index;
        this.amount = amount;
        this.month = month;
        this.year = year;
    }

    /**
     * Returns true if a given integer is a valid number.
     */
    public static boolean isValidAmount(int test) {
        if (test > MAX_PAYMENT_AMOUNT) {
            return false;
        }
        return String.valueOf(test).matches(TAG_VALIDATION_REGEX);
    }

    /**
     * Returns true if a given integer is a valid month.
     */
    public static boolean isValidMonth(int test) {
        //Check if month is within the correct range of jan - dec
        if (String.valueOf(test).matches(TAG_VALIDATION_REGEX)) {
            if (test >= JANUARY && test <= DECEMBER) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns true if a given integer is a valid year.
     */
    public static boolean isValidYear(int test) {
        int digits = 0;
        int copyTest = test;
        while (copyTest > 0) {
            copyTest = copyTest / 10;
            digits += 1;
        }

        if (digits != 4) {
            return false;
        }
        return String.valueOf(test).matches(TAG_VALIDATION_REGEX);
    }

    /**
     * Returns student index
     */
    public Index getIndex() {
        return this.studentIndex;
    }

    /*
     * Return month of payment
     */
    public int getMonth() {
        return this.month;
    }

    /*
     * Returns year of payment
     */
    public int getYear() {
        return this.year;
    }

    /*
     * Return amount of payment
     */
    public int getAmount() {
        return this.amount;
    }


    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return " Month: " + month + " Year: " + year + " Amount: " + amount;
    }

    @Override
    public boolean equals(Object other) {

        return other == this
                || (other instanceof Payment
                && this.amount == (((Payment) other).amount)
                && this.month == (((Payment) other).month)
                && this.year == (((Payment) other).year));
    }

    @Override
    public int hashCode() {
        return Objects.hash(studentIndex, amount, month, year);
    }
}



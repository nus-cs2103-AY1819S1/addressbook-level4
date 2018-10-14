package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import java.text.DecimalFormat;

/**
 * Represents a Person's tuition fees per hour.
 */

public class Fees {

    public static final double PRIMARY_BASE_AMOUNT = 20.00;
    public static final double SECONDARY_BASE_AMOUNT = 30.00;
    public static final double JC_BASE_AMOUNT = 35.00;

    private static double feesPerHr;

    private static DecimalFormat df = new DecimalFormat("#.00");


    public Fees(Education education) {
        requireNonNull(education);
        setFeesPerHr(education);
    }

    /**
     * Returns the tuition fee of a student.
     */
    public double getFeesPerHr() {
        return feesPerHr;
    }

    /**
     * Sets the tuition fee of a student based on his educational level and grade.
     */
    public static void setFeesPerHr(Education education) {
        double baseAmount = 0.00;

        switch (education.getEducationalLevel()) {
        case PRIMARY:
            baseAmount = PRIMARY_BASE_AMOUNT;
            break;
        case SECONDARY:
            baseAmount = SECONDARY_BASE_AMOUNT;
            break;
        default:
            baseAmount = JC_BASE_AMOUNT;
            break;
        }

        feesPerHr = baseAmount + education.getEducationalGrade();
    }

    @Override
    public String toString() {
        return "$" + df.format(feesPerHr) + "/hour"; }
}

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

    public final String value;

    private static DecimalFormat df = new DecimalFormat("#.00");


    public Fees(Education education) {
        requireNonNull(education);
        value = df.format(setFeesPerHr(education));
    }

    /**
     * Sets the tuition fee of a student based on his educational level and grade.
     */
    public static double setFeesPerHr(Education education) {
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

        return baseAmount + education.getEducationalGrade();
    }

    @Override
    public String toString() {
        return "$" + value + "/hour"; }
}

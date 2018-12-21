package seedu.address.logic.calculation;

/**
 * Common reference for calculation of quartiles for marks data.
 */
public class Quartiles {

    public static final double FIRST_QUARTILE = 0.25;
    public static final double SECOND_QUARTILE = 0.50;
    public static final double THIRD_QUARTILE = 0.75;

    /**
     * Calculates and replaces each fractional value in quartiles
     * with the value obtained from the corresponding index in marks.
     */
    public static void calculateQuartiles(double[] quartiles, double[] marks) {
        int i;
        if (marks.length == 1) {
            for (i = 0; i < quartiles.length; i++) {
                quartiles[i] = marks[0];
            }
        } else if (marks.length > 1) {
            double percentile;
            double position;
            int index;

            for (i = 0; i < quartiles.length; i++) {
                position = quartiles[i] * marks.length;
                index = (int) position;
                if (index >= marks.length) {
                    continue;
                }
                percentile = marks[index];

                if (position - index == 0) {
                    percentile = (percentile + marks[index - 1]) / 2;
                }

                quartiles[i] = percentile;
            }
        }
    }
}

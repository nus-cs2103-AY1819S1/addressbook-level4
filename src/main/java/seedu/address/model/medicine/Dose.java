package seedu.address.model.medicine;

//@@author snajef
/**
 * POJO class for medicine dosage.
 * @author Darien Chong
 *
 */
public class Dose {
    private int dose;
    private String doseUnit;
    private int dosesPerDay;

    public Dose(int d, String ds, int dpd) {
        dose = d;
        doseUnit = ds;
        dosesPerDay = dpd;
    }

    @Override
    public String toString() {
        return Integer.toString(dose) + " " + doseUnit + " " + fuzzyStringRepresentation(dosesPerDay);
    }

    /**
     * Generates a string representation of the dose per day.
     * Representation differs based on the dose quantity.
     *
     * @param dpd The dose per day.
     * @return A String representation of the dose per day.
     */
    private String fuzzyStringRepresentation(int dpd) {
        switch (dpd) {
        case 1:
            return "once a day.";

        case 2:
            return "twice a day.";

        default:
            return dpd + " times a day.";
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o instanceof Dose) {
            Dose d = (Dose) o;
            return dose == d.dose && doseUnit.equals(d.doseUnit) && dosesPerDay == d.dosesPerDay;
        }

        return false;
    }
}

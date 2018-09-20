package seedu.address.model.medicine;

//@@author snajef
/**
 * POJO class to hold information about medicine.
 * @author Darien Chong
 *
 */
public class Medicine {
    private static final String DIVIDER = " | ";

    /** The name of the medication. */
    private String medName;

    /** Dose to administer. */
    private Dose dose;

    /** Duration of the prescription in milliseconds. */
    private Duration duration;

    public Medicine(String name, Dose d, Duration dur) {
        medName = name;
        dose = d;
        duration = dur;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append(medName).append(DIVIDER).append(dose.toString()).append(DIVIDER).append(duration.toString())
        .append(DIVIDER);

        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o instanceof Medicine) {
            Medicine m = (Medicine) o;
            return medName.equals(m.medName) && dose.equals(m.dose) && duration.equals(m.duration);
        }

        return false;
    }
}

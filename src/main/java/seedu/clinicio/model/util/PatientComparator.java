package seedu.clinicio.model.util;

import java.util.Comparator;

import seedu.clinicio.model.patient.Patient;

/**
 * Creates a comparator that compares between two patients.
 */
public class PatientComparator<E> implements Comparator<E> {
    /**
     * Compares two patients.
     * @param p1 first person.
     * @param p2 second person.
     * @return 1 if 1st person arrives later.
     * @throws ClassCastException
     */
    public int compare(E p1, E p2) throws ClassCastException {
        try {
            return ((Patient) p1).getArrivalTime().getMinute() - ((Patient) p2).getArrivalTime().getMinute();
        } catch (ClassCastException cce) {
            System.err.println(cce);
        }
        return -1;
    }
}

package seedu.address.model.patientqueue;

import java.util.ArrayList;
import java.util.Comparator;

import seedu.address.model.patient.Patient;
import seedu.address.model.person.Person;
/**
 * The patient queue to be inherited by a main queue and a preference queue.
 */
public abstract class PatientQueue<E> implements Comparator<E> {

    protected ArrayList<E> list;

    public PatientQueue() {
        list = new ArrayList<>();
    }

    public abstract void add(E e);

    public boolean hasPatient() {
        return !list.isEmpty();
    }

    public ArrayList<E> getList() {
        return list;
    }

    /**
     * Compares two patients.
     * @param p1 first person.
     * @param p2 second person.
     * @return 1 if 1st person arrives later.
     * @throws ClassCastException
     */
    public int compare(E p1, E p2) throws ClassCastException {
        try {
            return ((Patient) p1).getArrivalTime() - ((Patient) p2).getArrivalTime();
        } catch (ClassCastException cce) {
            System.err.println(cce);
        }
    }
}

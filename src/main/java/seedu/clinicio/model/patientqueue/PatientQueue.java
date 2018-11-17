package seedu.clinicio.model.patientqueue;

import java.util.ArrayList;
/**
 * The patient queue to be inherited by a main queue and a preference queue.
 */
public abstract class PatientQueue<E> {

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
}

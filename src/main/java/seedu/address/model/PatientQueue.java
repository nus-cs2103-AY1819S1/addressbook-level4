package seedu.address.model;

import java.util.ArrayList;

import seedu.address.model.person.Person;

/**
 * The main and only queue of the app.
 */
public class PatientQueue<E> {

    private ArrayList<E> list;

    public PatientQueue() {
        list = new ArrayList<>();
    }

    public void add(E person) {
        list.add(person);
    }

    public boolean hasPatient() {
        return !list.isEmpty();
    }

    public boolean hasParticularPatient(E patient) {
        return list.contains(patient);
    }
}

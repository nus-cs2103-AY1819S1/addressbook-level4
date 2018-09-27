package seedu.address.model;

import java.util.ArrayList;

/**
 * The main and only queue of the app.
 */
public class PatientQueue<E> {

    private ArrayList<E> list;

    public void add(E person) {
        list.add(person);
    }
}

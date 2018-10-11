package seedu.address.model;

import seedu.address.model.person.ServedPatient;

/**
 * The API of the ServedPatient component.
 */
public interface ServedPatientList {

    /**
     * Displays the served patient list.
     * @return String representation of the current list.
     */
    String displayServedPatientList();

    /**
     * Add a served patient to the served patient list.
     */
    void addServedPatient(ServedPatient patient);

    /**
     * Delete a served patient in the served patient list.
     * @return The servedPatient object that was removed.
     */
    ServedPatient removeServedPatient(ServedPatient patient);

    /**
     * Checks whether the served patient list is empty.
     * @return true if the list is empty.
     */
    boolean isEmpty();

    /**
     * Checks whether served patient list contains a specified served patient.
     * @return true if the list contains patient.
     */
    boolean contains(ServedPatient patient);
}
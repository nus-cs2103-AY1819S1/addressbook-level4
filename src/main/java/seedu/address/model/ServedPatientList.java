package seedu.address.model;

import seedu.address.commons.core.index.Index;
import seedu.address.model.person.ServedPatient;

/**
 * The API of the ServedPatientList component.
 */
public interface ServedPatientList {

    /**
     * Displays the served patient list.
     * Mainly for debugging.
     * @return String representation of the current list.
     */
    String displayServedPatientList();

    /**
     * @return Length of the current ServedPatientList.
     */
    int getServedPatientListLength();

    /**
     * Add a served patient to the served patient list.
     */
    void addServedPatient(ServedPatient patient);

    /**
     * Selects a served patient in the served patient list.
     * @return The servedPatient object that was specified bu {@code index}.
     */
    ServedPatient selectServedPatient(Index index);

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

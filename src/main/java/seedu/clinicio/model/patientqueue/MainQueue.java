//@@author iamjackslayer
package seedu.clinicio.model.patientqueue;

import seedu.clinicio.model.person.Person;
/**
 * Represents the "normal" patient queue in the clinic.
 * This class does not enforce uniqueness of the patients in the queue because
 * the uniqueness is enforced by PatientList.
 * No two patients are identical after registration.
 */
public class MainQueue extends PatientQueue<Person> {
    @Override
    public void add(Person person) {
        list.add(person);
    }
}

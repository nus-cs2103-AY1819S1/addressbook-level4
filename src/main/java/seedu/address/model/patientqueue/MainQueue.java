//@@author iamjackslayer
package seedu.address.model.patientqueue;

import seedu.address.model.person.Person;

/**
 * Represents the "normal" patient queue in the clinic.
 * This class does not enforce uniqueness of the patients in the queue because
 * the uniqueness is enforced by PatientList.
 * No two patients are identical after registration.
 * TODO Change Person object into Patient object (await Patient model to be created).
 */
public class MainQueue extends PatientQueue<Person> {
    @Override
    public void add(Person person) {
        list.add(person);
    }
}

//@@author iamjackslayer
package seedu.clinicio.model.patientqueue;

import seedu.clinicio.model.person.Person;

/**
 * Represents the "special" patient queue in the clinic. Each patient in the queue has
 * a preferred/pre-assigned staff with whom he is consulting.
 * TODO Change Person object into Patient object (await Patient model to be created).
 */
public class PreferenceQueue extends PatientQueue<Person> {
    @Override
    public void add(Person person) {
        list.add(person);
    }
}

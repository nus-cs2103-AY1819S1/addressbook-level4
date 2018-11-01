package seedu.address.testutil;

import static seedu.address.testutil.TypicalGroups.getTypicalGroups;
import static seedu.address.testutil.TypicalPersons.getTypicalPersons;

import seedu.address.model.MeetingBook;
import seedu.address.model.group.Group;
import seedu.address.model.person.Person;

/**
 * A utility class that returns a typical {@code MeetingBook} object to be used in tests.
 * {@author jeffreyooi}
 */
public class TypicalMeetingBook {
    /**
     * Returns an {@code MeetingBook} with all the typical persons and groups.
     */
    public static MeetingBook getTypicalMeetingBook() {
        MeetingBook ab = new MeetingBook();
        for (Person person : getTypicalPersons()) {
            ab.addPerson(person);
        }
        for (Group group : getTypicalGroups()) {
            ab.addGroup(group);
        }
        return ab;
    }
}

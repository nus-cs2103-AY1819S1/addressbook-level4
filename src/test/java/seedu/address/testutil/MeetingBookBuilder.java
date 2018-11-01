package seedu.address.testutil;

import seedu.address.model.MeetingBook;
import seedu.address.model.person.Person;

/**
 * A utility class to help with building MeetingBook objects.
 * Example usage: <br>
 *     {@code MeetingBook ab = new MeetingBookBuilder().withPerson("John", "Doe").build();}
 */
public class MeetingBookBuilder {

    private MeetingBook meetingBook;

    public MeetingBookBuilder() {
        meetingBook = new MeetingBook();
    }

    public MeetingBookBuilder(MeetingBook meetingBook) {
        this.meetingBook = meetingBook;
    }

    /**
     * Adds a new {@code Person} to the {@code MeetingBook} that we are building.
     */
    public MeetingBookBuilder withPerson(Person person) {
        meetingBook.addPerson(person);
        return this;
    }

    public MeetingBook build() {
        return meetingBook;
    }
}

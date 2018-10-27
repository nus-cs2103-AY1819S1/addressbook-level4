package seedu.address.model.meeting;

import java.util.function.Predicate;

import seedu.address.model.person.Person;

//@@author AyushChatto
/**
 * Tests that a {@code Person}'s {@code Meeting} is on the same day as the meeting given.
 */
public class SameMeetingDayPredicate implements Predicate<Person> {

    private final Meeting meeting;

    public SameMeetingDayPredicate(Meeting meeting) {
        this.meeting = meeting;
    }

    @Override
    public boolean test(Person person) {
        return meeting.isSameDay(person.getMeeting());
    }
}

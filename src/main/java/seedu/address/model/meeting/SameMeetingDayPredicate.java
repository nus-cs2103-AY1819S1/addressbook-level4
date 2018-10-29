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
        if (meeting.value.equals(Meeting.NO_MEETING)) {
            return !person.getMeeting().value.equals(Meeting.NO_MEETING);
        } else {
            return meeting.isSameDay(person.getMeeting());
        }
    }
}

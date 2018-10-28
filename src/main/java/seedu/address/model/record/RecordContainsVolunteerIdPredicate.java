package seedu.address.model.record;

import java.util.function.Predicate;

import seedu.address.model.volunteer.VolunteerId;

/**
 * Tests that a {@code Record}'s {@code personId} matches the given PersonId.
 */
public class RecordContainsVolunteerIdPredicate implements Predicate<Record> {
    private final VolunteerId volunteerId;

    public RecordContainsVolunteerIdPredicate(VolunteerId volunteerId) {
        this.volunteerId = volunteerId;
    }

    @Override
    public boolean test(Record record) {
        return record.getVolunteerId().id == volunteerId.id;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RecordContainsVolunteerIdPredicate // instanceof handles nulls
                && volunteerId == ((RecordContainsVolunteerIdPredicate) other).volunteerId); // state check
    }

}

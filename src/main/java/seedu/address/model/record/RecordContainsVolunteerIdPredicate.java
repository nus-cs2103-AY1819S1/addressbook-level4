package seedu.address.model.record;

import java.util.function.Predicate;

import seedu.address.model.person.PersonId;

/**
 * Tests that a {@code Record}'s {@code personId} matches the given PersonId.
 */
public class RecordContainsVolunteerIdPredicate implements Predicate<Record> {
    private final PersonId personId;

    public RecordContainsVolunteerIdPredicate(PersonId personId) {
        this.personId = personId;
    }

    @Override
    public boolean test(Record record) {
        return record.getVolunteerId().id == personId.id;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RecordContainsVolunteerIdPredicate // instanceof handles nulls
                && personId == ((RecordContainsVolunteerIdPredicate) other).personId); // state check
    }

}

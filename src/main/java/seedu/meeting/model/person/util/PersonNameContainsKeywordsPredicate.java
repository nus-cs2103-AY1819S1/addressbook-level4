package seedu.meeting.model.person.util;

import java.util.List;
import java.util.function.Function;

import seedu.meeting.model.person.Person;
import seedu.meeting.model.util.EntityContainsKeywordsPredicate;

/**
 * Tests that a {@code Person}'s {@code Name} matches the keywords given.
 */
public class PersonNameContainsKeywordsPredicate extends EntityContainsKeywordsPredicate<Person> {
    private static final Function<Person, String> personNameGetter = person -> person.getName().fullName;

    public PersonNameContainsKeywordsPredicate(List<String> allKeywords, List<String> someKeywords,
                                               List<String> noneKeywords) {
        super(allKeywords, someKeywords, noneKeywords, personNameGetter);
    }
}

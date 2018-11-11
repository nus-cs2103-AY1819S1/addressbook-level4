package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code Person}'s {@code ModuleCode} matches any of the keywords given.
 */
public class PersonOccasionNameContainsKeywordsPredicate implements Predicate<Person> {
    private final List<String> keywords;

    public PersonOccasionNameContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Person person) {
        return keywords.stream()
                .anyMatch(keyword -> person.getOccasionList().asUnmodifiableObservableList().stream()
                        .anyMatch(module -> StringUtil.containsWordIgnoreCase(module.getOccasionName().fullOccasionName,
                                keyword)));
    }



    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PersonOccasionNameContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((PersonOccasionNameContainsKeywordsPredicate) other).keywords)); // state check
    }

}

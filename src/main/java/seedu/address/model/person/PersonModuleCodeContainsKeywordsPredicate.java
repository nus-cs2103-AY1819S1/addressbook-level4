package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code Person}'s {@code ModuleCode} matches any of the keywords given.
 */
public class PersonModuleCodeContainsKeywordsPredicate implements Predicate<Person> {
    private final List<String> keywords;

    public PersonModuleCodeContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Person person) {
        return keywords.stream()
                .anyMatch(keyword -> person.getModuleList().asUnmodifiableObservableList().stream()
                        .anyMatch(module -> StringUtil.containsWordIgnoreCase(module.getModuleCode().fullModuleCode,
                                keyword)));
    }


    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PersonModuleCodeContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((PersonModuleCodeContainsKeywordsPredicate) other).keywords)); // state check
    }

}

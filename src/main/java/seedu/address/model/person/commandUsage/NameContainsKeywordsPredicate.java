package seedu.address.model.person.commandUsage;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.model.person.Person;

/**
 * Tests that a {@code Person}'s {@code Name} matches the keywords given.
 */
public class NameContainsKeywordsPredicate implements Predicate<Person> {
    private final List<String> allKeywords;
    private final List<String> someKeywords;
    private final List<String> noneKeywords;

    public NameContainsKeywordsPredicate(List<String> allKeywords, List<String> someKeywords,
                                         List<String> noneKeywords) {
        this.allKeywords = allKeywords;
        this.someKeywords = someKeywords;
        this.noneKeywords = noneKeywords;
    }

    @Override
    public boolean test(Person person) {
        return !(someKeywords.isEmpty() && allKeywords.isEmpty() && noneKeywords.isEmpty())
            && (someKeywords.isEmpty() || someKeywords.stream()
            .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getName().fullName, keyword)))
            && allKeywords.stream()
            .allMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getName().fullName, keyword))
            && noneKeywords.stream()
            .noneMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getName().fullName, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof NameContainsKeywordsPredicate // instanceof handles nulls
            && someKeywords.equals(((NameContainsKeywordsPredicate) other).someKeywords) // state check
            && allKeywords.equals(((NameContainsKeywordsPredicate) other).allKeywords) // state check
            && noneKeywords.equals(((NameContainsKeywordsPredicate) other).noneKeywords)); // state check
    }

}

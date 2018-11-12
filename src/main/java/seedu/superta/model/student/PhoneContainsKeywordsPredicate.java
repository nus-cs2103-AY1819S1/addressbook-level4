package seedu.superta.model.student;

import java.util.List;
import java.util.function.Predicate;

import seedu.superta.commons.util.StringUtil;

/**
 * Tests that a {@code Student}'s {@code Phone} matches any of the keywords given.
 */
public class PhoneContainsKeywordsPredicate implements Predicate<Student> {
    private final List<String> keywords;

    public PhoneContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Student student) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(student.getPhone().value, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PhoneContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((PhoneContainsKeywordsPredicate) other).keywords)); // state check
    }

}

package seedu.superta.model.student;

import java.util.List;
import java.util.function.Predicate;

import seedu.superta.commons.util.StringUtil;

/**
 * Tests that a {@code Student}'s {@code studentid} matches any of the keywords given.
 */
public class StudentidContainsKeywordsPredicate implements Predicate<Student> {
    private final List<String> keywords;

    public StudentidContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Student student) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(student.getStudentId().studentId, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof StudentidContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((StudentidContainsKeywordsPredicate) other).keywords)); // state check
    }

}

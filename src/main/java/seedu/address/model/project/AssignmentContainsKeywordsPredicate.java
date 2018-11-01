package seedu.address.model.project;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code Assignment}'s {@code ProjectName} matches any of the keywords given.
 */
public class AssignmentContainsKeywordsPredicate implements Predicate<Assignment> {
    private final List<String> keywords;

    public AssignmentContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Assignment assignment) {
        return keywords.stream()
                .anyMatch(keyword ->
                        StringUtil.containsWordIgnoreCase(assignment.getProjectName().fullProjectName, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AssignmentContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((AssignmentContainsKeywordsPredicate) other).keywords)); // state check
    }
}

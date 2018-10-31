package seedu.address.model.leaveapplication;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code LeaveApplication}'s {@code Description} matches any of the keywords given.
 */
public class LeaveContainsKeywordsPredicate implements Predicate<LeaveApplicationWithEmployee> {
    private final List<String> keywords;

    public LeaveContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(LeaveApplicationWithEmployee leaveApplication) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(
                        leaveApplication.getDescription().value, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof LeaveContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((LeaveContainsKeywordsPredicate) other).keywords)); // state check
    }

}

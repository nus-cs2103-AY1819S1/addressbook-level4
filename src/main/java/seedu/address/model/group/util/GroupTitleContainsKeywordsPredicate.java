package seedu.address.model.group.util;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.model.group.Group;

/**
 * Tests that a {@code Group}'s {@code Title} matches any of the keywords given.
 * {@author jeffreyooi}
 */
public class GroupTitleContainsKeywordsPredicate implements Predicate<Group> {
    private final List<String> keywords;

    public GroupTitleContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Group group) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(group.getTitle().fullTitle, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof GroupTitleContainsKeywordsPredicate // instanceof handles null
                && keywords.equals(((GroupTitleContainsKeywordsPredicate) other).keywords)); // state check
    }
}

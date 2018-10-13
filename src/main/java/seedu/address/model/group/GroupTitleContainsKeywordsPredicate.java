package seedu.address.model.group;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.model.tag.Tag;

/**
 * Tests that a {@code Group}'s {@code Title} matches any of the keywords given.
 * TODO: change to Group class instead of Tag when group is properly implemented
 * {@author jeffreyooi}
 */
public class GroupTitleContainsKeywordsPredicate implements Predicate<Tag> {
    private final List<String> keywords;

    public GroupTitleContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Tag group) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(group.tagName, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof GroupTitleContainsKeywordsPredicate // instanceof handles null
                && keywords.equals(((GroupTitleContainsKeywordsPredicate) other).keywords)); // state check
    }
}

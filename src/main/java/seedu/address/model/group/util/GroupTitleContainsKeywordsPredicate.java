package seedu.address.model.group.util;

import java.util.List;
import java.util.function.Function;

import seedu.address.model.group.Group;
import seedu.address.model.util.EntityContainsKeywordsPredicate;

/**
 * Tests that a {@code Group}'s {@code Title} matches any of the keywords given.
 * {@author jeffreyooi}
 */
public class GroupTitleContainsKeywordsPredicate extends EntityContainsKeywordsPredicate<Group> {
    private static final Function<Group, String> groupTitleGetter = group -> group.getTitle().fullTitle;

    public GroupTitleContainsKeywordsPredicate(List<String> allKeywords, List<String> someKeywords,
                                               List<String> noneKeywords) {
        super(allKeywords, someKeywords, noneKeywords, groupTitleGetter);
    }
}

package seedu.address.model.group.util;

import java.util.List;
import java.util.function.Function;

import seedu.address.commons.util.StringUtil;
import seedu.address.model.group.Group;
import seedu.address.model.group.Group;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.EntityContainsKeywordsPredicate;

/**
 * Tests that a {@code Group}'s {@code Title} matches any of the keywords given.
 * {@author jeffreyooi}
 */
public class GroupTitleContainsKeywordsPredicate extends EntityContainsKeywordsPredicate<Tag> {
    private static final Function<Group, String> groupTitleGetter = group -> group.getTitle().fullTitle;

    public GroupTitleContainsKeywordsPredicate(List<String> allKeywords, List<String> someKeywords,
                                               List<String> noneKeywords) {
        super(allKeywords, someKeywords, noneKeywords, Tag::getTagName);
        //TODO replace tag with group
    }
}

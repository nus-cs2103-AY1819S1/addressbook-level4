package ssp.scheduleplanner.model.task;

import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import ssp.scheduleplanner.commons.util.StringUtil;
import ssp.scheduleplanner.model.tag.Tag;

/**
 * Tests that a {@code Task}'s {@code Tag(s)} matches all of the keywords given.
 */
public class TagsContainsAllKeywordsPredicate implements Predicate<Task> {
    private final List<String> keywords;

    public TagsContainsAllKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Task task) {
        Set<Tag> tags = task.getTags();
        // Due to a property of allMatch, called vacuous truth, an empty stream returns true for all tests. However
        // in this case, we do not want to return all tasks in the case of no keywords
        if (keywords.isEmpty()) {
            return false;
        }
        return keywords.stream()
                .allMatch(keyword -> tags.stream().anyMatch(tag -> StringUtil.containsWordIgnoreCase(tag.getTagName(),
                keyword)));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TagsContainsAllKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((TagsContainsAllKeywordsPredicate) other).keywords)); // state check
    }

}

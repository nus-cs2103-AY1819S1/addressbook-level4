package ssp.scheduleplanner.model.task;

import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import ssp.scheduleplanner.commons.util.StringUtil;
import ssp.scheduleplanner.model.tag.Tag;

/**
 * Tests that a {@code Task}'s {@code Tag(s)} matches any of the keywords given.
 */
public class TagsContainsKeywordsPredicate implements Predicate<Task> {
    private final List<String> keywords;

    public TagsContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Task task) {
        Set<Tag> tags = task.getTags();
        return keywords.stream()
                .anyMatch(keyword -> tags.stream().anyMatch(tag -> StringUtil.containsWordIgnoreCase(tag.getTagName(),
                keyword)));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TagsContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((TagsContainsKeywordsPredicate) other).keywords)); // state check
    }

}

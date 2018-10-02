package seedu.address.model.tag;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a tag matches any of the keywords given.
 */
public class TagContainsKeywordPredicate implements Predicate<Tag> {
    private final List<String> keywords;

    public TagContainsKeywordPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Tag tag) {
        return keywords.stream()
            .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(tag.getTag(), keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof TagContainsKeywordPredicate // instanceof handles nulls
            && keywords.equals(((TagContainsKeywordPredicate) other).keywords)); // state check
    }
}

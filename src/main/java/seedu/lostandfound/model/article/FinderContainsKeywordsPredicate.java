package seedu.lostandfound.model.article;

import java.util.List;
import java.util.function.Predicate;

import seedu.lostandfound.commons.util.StringUtil;

/**
 * Tests that a {@code Article}'s {@code Name} matches any of the keywords given.
 */
public class FinderContainsKeywordsPredicate implements Predicate<Article> {
    private final List<String> keywords;

    public FinderContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Article article) { // AND operation
        return !article.getIsResolved()
                && keywords.stream().allMatch(keyword ->
                StringUtil.containsWordIgnoreCase(article.getFinder().fullName, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FinderContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((FinderContainsKeywordsPredicate) other).keywords)); // state check
    }

}

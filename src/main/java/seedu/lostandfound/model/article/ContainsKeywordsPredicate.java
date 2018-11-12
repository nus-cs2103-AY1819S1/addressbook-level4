package seedu.lostandfound.model.article;

import java.util.List;
import java.util.function.Predicate;

import seedu.lostandfound.commons.util.StringUtil;

/**
 * Tests that a {@code Article}'s {@code Name} matches any of the keywords given.
 */
public class ContainsKeywordsPredicate implements Predicate<Article> {
    private final List<String> keywords;

    public ContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Article article) { // AND operation
        return !article.getIsResolved()
                && (keywords.stream().anyMatch(keyword ->
                        StringUtil.containsWordIgnoreCase(article.getName().fullName, keyword))
                || keywords.stream().anyMatch(keyword ->
                        StringUtil.containsWordIgnoreCase(article.getPhone().value, keyword))
                || keywords.stream().anyMatch(keyword ->
                        StringUtil.containsWordIgnoreCase(article.getEmail().value, keyword))
                || keywords.stream().anyMatch(keyword ->
                        StringUtil.containsWordIgnoreCase(article.getDescription().value, keyword))
                || keywords.stream().anyMatch(keyword ->
                        StringUtil.containsWordIgnoreCase(article.getFinder().fullName, keyword))
                || keywords.stream().anyMatch(keyword ->
                        StringUtil.containsWordIgnoreCase(article.getStringTags(), keyword)));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((ContainsKeywordsPredicate) other).keywords)); // state check
    }

}

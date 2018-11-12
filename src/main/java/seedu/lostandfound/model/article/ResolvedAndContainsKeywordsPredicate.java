package seedu.lostandfound.model.article;

import java.util.List;
import java.util.function.Predicate;

import seedu.lostandfound.commons.util.StringUtil;

/**
 * Tests that a {@code Article}'s {@code Name} matches any of the keywords given.
 */
public class ResolvedAndContainsKeywordsPredicate implements Predicate<Article> {
    private final List<String> keywords;

    public ResolvedAndContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Article article) {
        if (keywords.isEmpty()) {
            return article.getIsResolved();
        }

        return article.getIsResolved()
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
                StringUtil.containsWordIgnoreCase(article.getOwner().fullName, keyword))
                || keywords.stream().anyMatch(keyword ->
                StringUtil.containsWordIgnoreCase(article.getStringTags(), keyword)));

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ResolvedAndContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((ResolvedAndContainsKeywordsPredicate) other).keywords)); // state check
    }

}

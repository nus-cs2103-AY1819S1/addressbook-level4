package seedu.lostandfound.model.article;

import java.util.function.Predicate;

/**
 * Tests that a {@code Article}'s {@code Name} matches any of the keywords given.
 */
public class NotResolvedPredicate implements Predicate<Article> {
    @Override
    public boolean test(Article article) {
        return !article.getIsResolved();
    }
}

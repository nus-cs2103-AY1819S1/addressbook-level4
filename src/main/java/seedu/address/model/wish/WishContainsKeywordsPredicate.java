package seedu.address.model.wish;

import java.util.List;
import java.util.function.Predicate;

/**
 * Tests that a {@code Wish}'s {@code Name}, {@code Tags} and {@code Remark} match any or all of the keywords given.
 */
public class WishContainsKeywordsPredicate implements Predicate<Wish> {
    private final boolean isExactMatch;
    private final List<String> nameKeywords;
    private final List<String> tagKeywords;
    private final List<String> remarkKeywords;

    /**
     * Constructs a {@link WishContainsKeywordsPredicate} using the specified keywords.
     * @param nameKeywords Keywords to test against {@code Wish} name.
     * @param tagKeywords Keywords to test against {@code Wish}  tags.
     * @param remarkKeywords Keywords to test against {@code Wish} remark.
     * @param isExactMatch Indicate whether to test for a match with a single keyword or a match with all the keywords
     *                     for each {@code Wish} field.
     */
    public WishContainsKeywordsPredicate(List<String> nameKeywords, List<String> tagKeywords,
                                         List<String> remarkKeywords, boolean isExactMatch) {
        this.isExactMatch = isExactMatch;
        this.nameKeywords = nameKeywords;
        this.tagKeywords = tagKeywords;
        this.remarkKeywords = remarkKeywords;
    }

    @Override
    public boolean test(Wish wish) {
        if (isExactMatch) {
            return testAllMatch(wish.getName().toString(), nameKeywords)
                    && testAllMatch(wish.getTags().toString(), tagKeywords)
                    && testAllMatch(wish.getRemark().toString(), remarkKeywords);
        }
        return testAnyMatch(wish.getName().toString(), nameKeywords)
                && testAnyMatch(wish.getTags().toString(), tagKeywords)
                && testAnyMatch(wish.getRemark().toString(), remarkKeywords);
    }

    @Override
    public boolean equals(Object other) {
        return other == this //short circuit if same object
                || ((other instanceof WishContainsKeywordsPredicate)
                && isExactMatch == ((WishContainsKeywordsPredicate) other).isExactMatch
                && nameKeywords.equals(((WishContainsKeywordsPredicate) other).nameKeywords)
                && tagKeywords.equals(((WishContainsKeywordsPredicate) other).tagKeywords)
                && remarkKeywords.equals(((WishContainsKeywordsPredicate) other).remarkKeywords));
    }

    /** Returns true if all keyword is found in the sentence, case insensitive. */
    private boolean testAllMatch(String sentence, List<String> keywords) {
        String processedSentence = sentence.toLowerCase();
        return keywords.stream().allMatch(keyword -> processedSentence.contains(keyword.toLowerCase()));
    }

    /** Returns true if any keyword is found in the sentence, case insensitive. */
    private boolean testAnyMatch(String sentence, List<String> keywords) {
        if (keywords.isEmpty()) {
            return true;
        }
        String processedSentence = sentence.toLowerCase();
        return keywords.stream().anyMatch(keyword -> processedSentence.contains(keyword.toLowerCase()));
    }
}

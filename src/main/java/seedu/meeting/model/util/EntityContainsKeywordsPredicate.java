package seedu.meeting.model.util;

import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

import seedu.meeting.commons.util.StringUtil;

/**
 * A predicate that matches the keyword an entity to some given lists of keywords.
 * @param <E> the entity type
 */
public abstract class EntityContainsKeywordsPredicate<E> implements Predicate<E> {
    private final List<String> allKeywords; // the list of keywords that the tested element has to match all of
    private final List<String> someKeywords; // the list of keywords that the tested element has to match some of
    private final List<String> noneKeywords; // the list of keywords that the tested element cannot match any of

    private final Function<E, Predicate<String>> testKeywordPredicateGetter;

    public EntityContainsKeywordsPredicate(List<String> allKeywords, List<String> someKeywords,
                                           List<String> noneKeywords, Function<E, String> entityKeywordGetter) {
        this.allKeywords = allKeywords;
        this.someKeywords = someKeywords;
        this.noneKeywords = noneKeywords;
        this.testKeywordPredicateGetter = element -> keyword ->
                StringUtil.containsWordIgnoreCase(entityKeywordGetter.apply(element), keyword);
    }

    @Override
    public boolean test(E element) {
        Predicate<String> keywordPredicate = testKeywordPredicateGetter.apply(element);
        return !(someKeywords.isEmpty() && allKeywords.isEmpty() && noneKeywords.isEmpty())
                && (someKeywords.isEmpty() || someKeywords.stream().anyMatch(keywordPredicate))
                && allKeywords.stream().allMatch(keywordPredicate)
                && noneKeywords.stream().noneMatch(keywordPredicate);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EntityContainsKeywordsPredicate // instanceof handles nulls
                && someKeywords.equals(((EntityContainsKeywordsPredicate) other).someKeywords) // state check
                && allKeywords.equals(((EntityContainsKeywordsPredicate) other).allKeywords) // state check
                && noneKeywords.equals(((EntityContainsKeywordsPredicate) other).noneKeywords)); // state check
    }
}

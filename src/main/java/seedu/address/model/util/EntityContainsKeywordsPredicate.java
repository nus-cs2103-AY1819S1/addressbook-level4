package seedu.address.model.util;

import seedu.address.commons.util.StringUtil;

import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

public abstract class EntityContainsKeywordsPredicate<E> implements Predicate<E> {
    private final List<String> allKeywords;
    private final List<String> someKeywords;
    private final List<String> noneKeywords;

    private final Function<E, Predicate<String>> testKeywordPredicateGetter;

    public EntityContainsKeywordsPredicate(List<String> allKeywords, List<String> someKeywords,
                                           List<String> noneKeywords, Function<E, String> getEntityKeywordFunction) {
        this.allKeywords = allKeywords;
        this.someKeywords = someKeywords;
        this.noneKeywords = noneKeywords;
        this.testKeywordPredicateGetter = element -> keyword ->
                StringUtil.containsWordIgnoreCase(getEntityKeywordFunction.apply(element),keyword);
    }

    @Override
    public boolean test(E element) {
        Predicate<String> testKeywordPredicate = testKeywordPredicateGetter.apply(element);
        return !(someKeywords.isEmpty() && allKeywords.isEmpty() && noneKeywords.isEmpty())
                && (someKeywords.isEmpty() || someKeywords.stream().anyMatch(testKeywordPredicate)
                && allKeywords.stream().allMatch(testKeywordPredicate)
                && noneKeywords.stream().noneMatch(testKeywordPredicate));
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

package seedu.thanepark.logic.matchers;

/**
 * Interface requiring classes to match a list of items as Strings against a pattern.
 */
public interface PatternMatcher {

    /**
     * Matches a list of items as Strings against a given pattern.
     */
    public String[] matchPattern(String[] items, String pattern);
}

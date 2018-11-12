package seedu.thanepark.logic.matchers;

import java.util.Arrays;

/**
 * Singleton class that matches a list of items as Strings against a pattern.
 */
public class PrefixMatcher implements PatternMatcher {
    private static final String PATTERN_PREFIX = "^%1$s.*";

    /**
     * Default constructor
     */
    public PrefixMatcher() {
        ;
    }

    @Override
    public String[] matchPattern(String[] items, String pattern) {
        return Arrays.stream(items).filter(s
            -> s.matches(String.format(PATTERN_PREFIX, pattern))).toArray(String[]::new);
    }

}

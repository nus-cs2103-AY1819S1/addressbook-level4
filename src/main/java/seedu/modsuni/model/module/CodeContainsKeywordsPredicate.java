package seedu.modsuni.model.module;

import java.util.List;
import java.util.function.Predicate;

import seedu.modsuni.commons.util.StringUtil;

/**
 * Tests that a {@code Module}'s {@code Code} matches any of the keywords given.
 */
public class CodeContainsKeywordsPredicate implements Predicate<Module> {
    private final List<String> keywords;

    public CodeContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Module module) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(module.getCode().code, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CodeContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((CodeContainsKeywordsPredicate) other).keywords)); // state check
    }
}

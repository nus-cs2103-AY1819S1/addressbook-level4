package seedu.address.model.module;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code Module}'s {@code AcademicYear} matches any of the keywords given.
 */
public class AcademicYearContainsKeywordsPredicate implements Predicate<Module> {
    private final List<String> keywords;

    public AcademicYearContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Module module) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(module.getAcademicYear().toString(), keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AcademicYearContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((AcademicYearContainsKeywordsPredicate) other).keywords)); // state check
    }

}

package seedu.address.model.category;

import java.util.function.Predicate;

import seedu.address.model.entry.ResumeEntry;

/**
 * Tests that a {@code ResumeEntry}'s matches the keyword given.
 */
public class ContainsCategoryPredicate implements Predicate<ResumeEntry> {
    private final Category category;

    public ContainsCategoryPredicate(String keyword) {
        category = new Category(keyword);
    }

    @Override
    public boolean test(ResumeEntry entry) {
        return category.equals(entry.getCategory());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ContainsCategoryPredicate // instanceof handles nulls
                && category.equals(((ContainsCategoryPredicate) other).category)); // state check
    }

}

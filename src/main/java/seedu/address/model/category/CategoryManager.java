package seedu.address.model.category;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.model.entry.ResumeEntry;
import seedu.address.model.tag.TagManager;

/**
 * Manages category of the entries.
 */
public class CategoryManager extends TagManager {
    /** Returns a {@code Predicate<ResumeEntry>} that does the filtering.
      * Only pick the first category in the {@code categories}. */
    @Override
    public Predicate<ResumeEntry> mkPredicate (List<String> categories) {
        requireNonNull(categories);
        return new ContainsCategoryPredicate(categories.get(0));
    }

    /** Returns a {@code Predicate<ResumeEntry>} that adds on to the predicate with tag filtering */
    @Override
    public Predicate<ResumeEntry> mkPredicate (Predicate<ResumeEntry> predicate, List<String> categories) {
        Predicate<ResumeEntry> categoryPredicate = mkPredicate(categories);

        return new Predicate<ResumeEntry>() {
            @Override
            public boolean test(ResumeEntry entry) {
                return predicate.test(entry) && categoryPredicate.test(entry);
            }
        };
    }
}

package seedu.address.model.tag;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import seedu.address.model.entry.ResumeEntry;

/**
 * Tests that a {@code ResumeEntry}'s {@code Tag} matches any of the keywords given.
 */
public class ContainsTagsPredicate implements Predicate<ResumeEntry> {
    private final List<Tag> tags;

    public ContainsTagsPredicate(String tag) {
        this(Arrays.asList(tag));
    }

    public ContainsTagsPredicate(List<String> tagNames) {
        this.tags = tagNames.stream()
            .map(Tag::new)
            .collect(Collectors.toList());
    }

    @Override
    public boolean test(ResumeEntry entry) {
        return entry.getTags().stream()
                .anyMatch(entryTag -> tags.stream().anyMatch(tag -> tag.equals(entryTag)));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ContainsTagsPredicate // instanceof handles nulls
                && tags.equals(((ContainsTagsPredicate) other).tags)); // state check
    }

}

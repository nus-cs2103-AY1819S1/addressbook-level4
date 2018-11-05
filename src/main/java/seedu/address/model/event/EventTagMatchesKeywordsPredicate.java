package seedu.address.model.event;

import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Tests that an {@code Event}'s {@code EventTag} matches any of the keywords given.
 */
public class EventTagMatchesKeywordsPredicate implements Predicate<Event> {
    private final List<String> keywords;

    public EventTagMatchesKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Event event) {
        Set<String> eventTags = event.getEventTags().stream()
                .map(eventTag -> eventTag.tagName)
                .collect(Collectors.toSet());
        return keywords.stream()
                .anyMatch(keyword -> eventTags.stream().anyMatch(keyword::equalsIgnoreCase));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EventTagMatchesKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((EventTagMatchesKeywordsPredicate) other).keywords)); // state check
    }
}

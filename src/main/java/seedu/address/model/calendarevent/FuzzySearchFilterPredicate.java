package seedu.address.model.calendarevent;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code CalendarEvent}'s {@code Title}, {@code Venue} and {@code Description} match any of the
 * keywords given.
 */
public class FuzzySearchFilterPredicate implements Predicate<CalendarEvent> {
    private final int tolerance = 70;
    private final List<String> keywords;

    public FuzzySearchFilterPredicate(List<String> keywords) {
        assert keywords != null;
        this.keywords = keywords;
    }

    @Override
    public boolean test(CalendarEvent calendarEvent) {
        return !hasKeywords() // If no keywords are present, then always accepts
                || keywords.stream() // Accept if event title, venue or description match any of the keywords
                .anyMatch(keyword -> StringUtil.containsWordFuzzy(calendarEvent.getTitle().value, keyword, tolerance)
                                || StringUtil.containsWordFuzzy(calendarEvent.getDescription(), keyword, tolerance)
                                || StringUtil.containsWordFuzzy(calendarEvent.getVenue().value, keyword, tolerance));
    }

    /**
     * Returns whether  {@code FuzzySearchFilterPredicate} has any {@code keywords}
     */
    public boolean hasKeywords() {
        return !keywords.isEmpty();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof FuzzySearchFilterPredicate // instanceof handles nulls
            && keywords.equals(((FuzzySearchFilterPredicate) other).keywords)); // state check
    }

}

package seedu.address.model.calendarevent;

import java.util.Comparator;
import java.util.List;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code CalendarEvent}'s {@code Title} matches any of the keywords given.
 */
public class FuzzySearchComparator implements Comparator<CalendarEvent> {
    private final List<String> keywords;

    public FuzzySearchComparator(List<String> keywords) {
        assert keywords != null;
        this.keywords = keywords;
    }

    @Override
    public int compare(CalendarEvent calendarEvent1, CalendarEvent calendarEvent2) {
        if (!hasKeywords()) { // If no keywords are present, then it does not sort
            return 0;
        }
        int compareScore = Integer.compare(maxFuzzyMatchScore(calendarEvent2), maxFuzzyMatchScore(calendarEvent1));
        if (compareScore == 0) { // If scores are equal, sort in chronological order
            return calendarEvent1.getStart().compareTo(calendarEvent2.getStart());
        } else {
            return compareScore;
        }
    }

    /**
     * Returns the max fuzzy match score out of all the {@code keywords} with the title of the {@code CalendarEvent}
     */
    public int maxFuzzyMatchScore(CalendarEvent calendarEvent) {
        return keywords.stream()
            .mapToInt(keyword -> Math.max(Math.max(
                    StringUtil.fuzzyMatchScore(calendarEvent.getTitle().value, keyword),
                    StringUtil.fuzzyMatchScore(calendarEvent.getDescription(), keyword)),
                    StringUtil.fuzzyMatchScore(calendarEvent.getVenue().value, keyword)))
            .max()
            .orElse(0);
    }

    /**
     * Returns whether  {@code FuzzySearchComparator} has any {@code keywords}
     */
    public boolean hasKeywords() {
        return !keywords.isEmpty();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof FuzzySearchComparator // instanceof handles nulls
            && keywords.equals(((FuzzySearchComparator) other).keywords)); // state check
    }

}

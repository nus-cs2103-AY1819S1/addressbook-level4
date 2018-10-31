package seedu.jxmusic.model;

import java.util.List;
import java.util.function.Predicate;

import seedu.jxmusic.commons.util.StringUtil;

/**
 * Tests that a {@code Track}'s {@code Name} matches any of the keywords given.
 */
public class TrackNameContainsKeywordsPredicate implements Predicate<Track> {
    private final List<String> keywords;

    public TrackNameContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Track track) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(track.getFileName(), keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TrackNameContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((TrackNameContainsKeywordsPredicate) other).keywords)); // state check
    }

}

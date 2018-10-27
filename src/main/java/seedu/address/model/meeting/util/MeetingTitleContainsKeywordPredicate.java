package seedu.address.model.meeting.util;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.model.meeting.Meeting;

/**
 * Tests that a {@code Meeting}'s {@code Title} matches the keywords given.
 */
public class MeetingTitleContainsKeywordPredicate implements Predicate<Meeting> {
    private final List<String> keywords;

    public MeetingTitleContainsKeywordPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Meeting meeting) {
        return keywords.stream()
            .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(meeting.getTitle().fullTitle, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this
            || (other instanceof MeetingTitleContainsKeywordPredicate
            && keywords.equals(((MeetingTitleContainsKeywordPredicate) other).keywords));
    }
}

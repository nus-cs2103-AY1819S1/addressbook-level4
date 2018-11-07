package seedu.meeting.model.meeting.util;

import java.util.List;
import java.util.function.Function;

import seedu.meeting.model.meeting.Meeting;
import seedu.meeting.model.util.EntityContainsKeywordsPredicate;

/**
 * Tests that a {@code Meeting}'s {@code Title} matches the keywords given.
 */
public class MeetingTitleContainsKeywordsPredicate extends EntityContainsKeywordsPredicate<Meeting> {
    private static final Function<Meeting, String> meetingTitleGetter = meeting -> meeting.getTitle().fullTitle;

    public MeetingTitleContainsKeywordsPredicate(List<String> allKeywords, List<String> someKeywords,
                                                 List<String> noneKeywords) {
        super(allKeywords, someKeywords, noneKeywords, meetingTitleGetter);
    }
}

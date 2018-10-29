package seedu.address.model.meeting.util;

import java.util.List;
import java.util.function.Function;

import seedu.address.model.meeting.Meeting;
import seedu.address.model.util.EntityContainsKeywordsPredicate;

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

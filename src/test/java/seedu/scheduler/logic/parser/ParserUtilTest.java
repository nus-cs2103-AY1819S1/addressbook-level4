package seedu.scheduler.logic.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import static seedu.scheduler.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.scheduler.testutil.TypicalIndexes.INDEX_FIRST_EVENT;

import java.time.Duration;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.scheduler.logic.parser.exceptions.ParseException;
import seedu.scheduler.model.event.Description;
import seedu.scheduler.model.event.EventName;
import seedu.scheduler.model.event.RepeatType;
import seedu.scheduler.model.event.Venue;
import seedu.scheduler.model.tag.Tag;
import seedu.scheduler.testutil.Assert;

public class ParserUtilTest {
    private static final String INVALID_EVENT_NAME = " study study";
    private static final String INVALID_TAG = "#friend";

    private static final String VALID_EVENT_NAME = "Study with me";
    private static final String VALID_DESCRIPTION = "";
    private static final String VALID_VENUE = "";
    private static final String VALID_REPEAT_TYPE_DAILY = "daily";
    private static final String VALID_REPEAT_TYPE_WEEKLY = "weekly";
    private static final String VALID_REPEAT_TYPE_MONTHLY = "monthly";
    private static final String VALID_REMINDER_DURATION = "1H30M";
    private static final String VALID_TAG_1 = "friend";
    private static final String VALID_TAG_2 = "neighbour";

    private static final String WHITESPACE = " \t";

    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @Test
    public void parseIndex_invalidInput_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        ParserUtil.parseIndex("10 a");
    }

    @Test
    public void parseIndex_outOfRangeInput_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        thrown.expectMessage(MESSAGE_INVALID_INDEX);
        ParserUtil.parseIndex(Long.toString(Integer.MAX_VALUE + 1));
    }

    @Test
    public void parseIndex_validInput_success() throws Exception {
        // No whitespaces
        assertEquals(INDEX_FIRST_EVENT, ParserUtil.parseIndex("1"));

        // Leading and trailing whitespaces
        assertEquals(INDEX_FIRST_EVENT, ParserUtil.parseIndex("  1  "));
    }

    @Test
    public void parseEventName_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseEventName(null));
    }

    @Test
    public void parseName_invalidValue_throwsParseException() {
        Assert.assertThrows(ParseException.class, () -> ParserUtil.parseEventName(INVALID_EVENT_NAME));
    }

    @Test
    public void parseEventName_validValueWithoutWhitespace_returnsName() throws Exception {
        EventName expectedEventName = new EventName(VALID_EVENT_NAME);
        assertEquals(expectedEventName, ParserUtil.parseEventName(VALID_EVENT_NAME));
    }

    @Test
    public void parseEventName_validValueWithWhitespace_returnsTrimmedName() throws Exception {
        String eventNameWithWhitespace = VALID_EVENT_NAME + WHITESPACE;
        EventName expectedEventName = new EventName(VALID_EVENT_NAME);
        assertEquals(expectedEventName, ParserUtil.parseEventName(eventNameWithWhitespace));
    }

    @Test
    public void parseDateTime_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseDateTime((String) null));
    }

    @Test
    public void parseDateTime_invalidValue_throwsParseException() {
        Assert.assertThrows(ParseException.class, () -> ParserUtil.parseDateTime("z"));
    }

    @Test
    public void parseDateTime_multipleParsedValue_throwsParseException() {
        Assert.assertThrows(ParseException.class, () -> ParserUtil.parseDateTime("April 1st and May 2nd"));
    }

    @Test
    public void parseDescription_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseDescription((String) null));
    }

    @Test
    public void parseDescription_validValueWithoutWhitespace_returnsDescription() {
        Description expectedDescription = new Description(VALID_DESCRIPTION);
        assertEquals(expectedDescription, ParserUtil.parseDescription(VALID_DESCRIPTION));
    }

    @Test
    public void parseDescription_validValueWithWhitespace_returnsTrimmedDescription() throws Exception {
        String descriptionWithWhitespace = WHITESPACE + VALID_DESCRIPTION + WHITESPACE;
        Description expectedDescription = new Description(VALID_DESCRIPTION);
        assertEquals(expectedDescription, ParserUtil.parseDescription(descriptionWithWhitespace));
    }

    @Test
    public void parseVenue_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseVenue((String) null));
    }

    @Test
    public void parseVenue_validValueWithoutWhitespace_returnsVenue() {
        Venue expectedVenue = new Venue(VALID_VENUE);
        assertEquals(expectedVenue, ParserUtil.parseVenue(VALID_VENUE));
    }

    @Test
    public void parseVenue_validValueWithWhitespace_returnsTrimmedVenue() throws Exception {
        String venueWithWhitespace = WHITESPACE + VALID_VENUE + WHITESPACE;
        Venue expectedVenue = new Venue(VALID_VENUE);
        assertEquals(expectedVenue, ParserUtil.parseVenue(venueWithWhitespace));
    }

    @Test
    public void parseRepeatType_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseRepeatType((String) null));
    }

    @Test
    public void parseRepeatType_invalidValue_throwsParseException() {
        Assert.assertThrows(ParseException.class, () -> ParserUtil.parseRepeatType("z"));
    }

    @Test
    public void parseRepeatType_validValueWithoutWhitespace_returnsRepeatType() throws Exception {
        RepeatType expectedRepeatType = RepeatType.DAILY;
        assertEquals(expectedRepeatType, ParserUtil.parseRepeatType(VALID_REPEAT_TYPE_DAILY));
    }

    @Test
    public void parseRepeatType_validValueWithWhitespace_returnsTrimmedRepeatType() throws Exception {
        String dailyRepeatTypeWithWhitespace = WHITESPACE + VALID_REPEAT_TYPE_DAILY + WHITESPACE;
        RepeatType expectedDailyRepeatType = RepeatType.DAILY;
        String weeklyRepeatTypeWithWhitespace = WHITESPACE + VALID_REPEAT_TYPE_WEEKLY + WHITESPACE;
        RepeatType expectedWeeklyRepeatType = RepeatType.WEEKLY;
        String monthlyRepeatTypeWithWhitespace = WHITESPACE + VALID_REPEAT_TYPE_MONTHLY + WHITESPACE;
        RepeatType expectedMonthlyRepeatType = RepeatType.MONTHLY;
        assertEquals(expectedDailyRepeatType, ParserUtil.parseRepeatType(dailyRepeatTypeWithWhitespace));
        assertEquals(expectedWeeklyRepeatType, ParserUtil.parseRepeatType(weeklyRepeatTypeWithWhitespace));
        assertEquals(expectedMonthlyRepeatType, ParserUtil.parseRepeatType(monthlyRepeatTypeWithWhitespace));
    }

    @Test
    public void parseReminderDuration_null_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        ParserUtil.parseReminderDuration(null);
    }

    @Test
    public void parseReminderDuration_validReminderDurationWithoutWhiteSpace_returnsReminderDuration()
            throws Exception {
        Duration expectedReminderDuration = Duration.ofHours(1).plusMinutes(30);
        assertEquals(expectedReminderDuration, ParserUtil.parseReminderDuration(VALID_REMINDER_DURATION));
    }

    @Test
    public void parseReminderDuration_validReminderDurationWithWhiteSpace_returnsReminderDuration()
            throws Exception {
        String reminderDurationWithWhiteSpace = WHITESPACE + VALID_REMINDER_DURATION + WHITESPACE;
        Duration expectedReminderDuration = Duration.ofHours(1).plusMinutes(30);
        assertEquals(expectedReminderDuration, ParserUtil.parseReminderDuration(reminderDurationWithWhiteSpace));
    }

    @Test
    public void parseReminderDuration_invalidReminderDuration_returnsReminderDuration()
            throws Exception {
        Assert.assertThrows(ParseException.class, () -> ParserUtil.parseReminderDuration("z"));
    }

    @Test
    public void parseTag_null_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        ParserUtil.parseTag(null);
    }

    @Test
    public void parseTag_invalidValue_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        ParserUtil.parseTag(INVALID_TAG);
    }

    @Test
    public void parseTag_validValueWithoutWhitespace_returnsTag() throws Exception {
        Tag expectedTag = new Tag(VALID_TAG_1);
        assertEquals(expectedTag, ParserUtil.parseTag(VALID_TAG_1));
    }

    @Test
    public void parseTag_validValueWithWhitespace_returnsTrimmedTag() throws Exception {
        String tagWithWhitespace = WHITESPACE + VALID_TAG_1 + WHITESPACE;
        Tag expectedTag = new Tag(VALID_TAG_1);
        assertEquals(expectedTag, ParserUtil.parseTag(tagWithWhitespace));
    }

    @Test
    public void parseTags_null_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        ParserUtil.parseTags(null);
    }

    @Test
    public void parseTags_collectionWithInvalidTags_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        ParserUtil.parseTags(Arrays.asList(VALID_TAG_1, INVALID_TAG));
    }

    @Test
    public void parseTags_emptyCollection_returnsEmptySet() throws Exception {
        assertTrue(ParserUtil.parseTags(Collections.emptyList()).isEmpty());
    }

    @Test
    public void parseTags_collectionWithValidTags_returnsTagSet() throws Exception {
        Set<Tag> actualTagSet = ParserUtil.parseTags(Arrays.asList(VALID_TAG_1, VALID_TAG_2));
        Set<Tag> expectedTagSet = new HashSet<Tag>(Arrays.asList(new Tag(VALID_TAG_1), new Tag(VALID_TAG_2)));

        assertEquals(expectedTagSet, actualTagSet);
    }
}

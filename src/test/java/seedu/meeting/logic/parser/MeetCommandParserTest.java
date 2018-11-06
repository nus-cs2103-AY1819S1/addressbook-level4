package seedu.meeting.logic.parser;

import static seedu.meeting.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.meeting.logic.commands.CommandTestUtil.INVALID_MEETING_DESCRIPTION_DESC;
import static seedu.meeting.logic.commands.CommandTestUtil.INVALID_MEETING_LOCATION_DESC;
import static seedu.meeting.logic.commands.CommandTestUtil.INVALID_MEETING_TIME_DESC;
import static seedu.meeting.logic.commands.CommandTestUtil.INVALID_MEETING_TITLE_DESC;
import static seedu.meeting.logic.commands.CommandTestUtil.VALID_MEETING_DESCRIPTION_DESC_URGENT;
import static seedu.meeting.logic.commands.CommandTestUtil.VALID_MEETING_DESCRIPTION_DESC_WEEKLY;
import static seedu.meeting.logic.commands.CommandTestUtil.VALID_MEETING_DESC_WEEKLY;
import static seedu.meeting.logic.commands.CommandTestUtil.VALID_MEETING_LOCATION_DESC;
import static seedu.meeting.logic.commands.CommandTestUtil.VALID_MEETING_TIME_DESC;
import static seedu.meeting.logic.commands.CommandTestUtil.VALID_MEETING_TITLE_DESC_URGENT;
import static seedu.meeting.logic.commands.CommandTestUtil.VALID_MEETING_TITLE_DESC_WEEKLY;
import static seedu.meeting.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.meeting.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.meeting.testutil.TypicalMeetings.WEEKLY;

import org.junit.Test;

import seedu.meeting.logic.commands.MeetCommand;
import seedu.meeting.model.group.Group;
import seedu.meeting.model.meeting.TimeStamp;
import seedu.meeting.model.shared.Address;
import seedu.meeting.model.shared.Description;
import seedu.meeting.model.shared.Title;

// @@author NyxF4ll
public class MeetCommandParserTest {
    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, MeetCommand.MESSAGE_USAGE);

    private MeetCommandParser parser = new MeetCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no group name specified
        assertParseFailure(parser, VALID_MEETING_DESC_WEEKLY, MESSAGE_INVALID_FORMAT);

        // missing title field
        assertParseFailure(parser, "MA1521" + VALID_MEETING_LOCATION_DESC
                + VALID_MEETING_TIME_DESC + VALID_MEETING_DESCRIPTION_DESC_WEEKLY, MESSAGE_INVALID_FORMAT);

        // missing location field
        assertParseFailure(parser, "MA1521" + VALID_MEETING_TITLE_DESC_WEEKLY
                + VALID_MEETING_TIME_DESC + VALID_MEETING_DESCRIPTION_DESC_WEEKLY, MESSAGE_INVALID_FORMAT);

        // missing time field
        assertParseFailure(parser, "MA1521" + VALID_MEETING_TITLE_DESC_WEEKLY
                + VALID_MEETING_LOCATION_DESC + VALID_MEETING_DESCRIPTION_DESC_WEEKLY, MESSAGE_INVALID_FORMAT);

        // missing description field
        assertParseFailure(parser, "MA1521" + VALID_MEETING_TITLE_DESC_WEEKLY
                + VALID_MEETING_LOCATION_DESC + VALID_MEETING_TIME_DESC, MESSAGE_INVALID_FORMAT);

        // missing multiple description fields
        assertParseFailure(parser, "MA1521" + VALID_MEETING_TITLE_DESC_WEEKLY
                + VALID_MEETING_LOCATION_DESC, MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // invalid group name as preamble
        assertParseFailure(parser, "CS123*" + VALID_MEETING_DESC_WEEKLY, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "@aa# some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "abcd i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, "MA1521" + INVALID_MEETING_TITLE_DESC + VALID_MEETING_TIME_DESC
                + VALID_MEETING_LOCATION_DESC + VALID_MEETING_DESCRIPTION_DESC_URGENT, Title.MESSAGE_TITLE_CONSTRAINTS);

        // invalid location
        assertParseFailure(parser, "MA1521" + VALID_MEETING_TITLE_DESC_URGENT + VALID_MEETING_TIME_DESC
                + INVALID_MEETING_LOCATION_DESC + VALID_MEETING_DESCRIPTION_DESC_URGENT,
                Address.MESSAGE_ADDRESS_CONSTRAINTS);

        // invalid timestamp
        assertParseFailure(parser, "MA1521" + VALID_MEETING_TITLE_DESC_URGENT + INVALID_MEETING_TIME_DESC
                + VALID_MEETING_LOCATION_DESC + VALID_MEETING_DESCRIPTION_DESC_URGENT,
                TimeStamp.MESSAGE_TIMESTAMP_CONSTRAINT);

        // invalid description
        assertParseFailure(parser, "MA1521" + VALID_MEETING_TITLE_DESC_URGENT + VALID_MEETING_TIME_DESC
                + VALID_MEETING_LOCATION_DESC + INVALID_MEETING_DESCRIPTION_DESC,
                Description.MESSAGE_DESCRIPTION_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, "MA1521" + VALID_MEETING_TITLE_DESC_URGENT + INVALID_MEETING_TIME_DESC
                + VALID_MEETING_LOCATION_DESC + INVALID_MEETING_DESCRIPTION_DESC,
                TimeStamp.MESSAGE_TIMESTAMP_CONSTRAINT);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Title targetGroupName = new Title("MA1521");
        Group targetGroup = new Group(targetGroupName);
        String userInput = targetGroupName.toString() + VALID_MEETING_DESC_WEEKLY;
        MeetCommand expectedCommand = new MeetCommand(targetGroup, WEEKLY);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_acceptsLast() {
        Title targetGroupName = new Title("MA1521");
        Group targetGroup = new Group(targetGroupName);
        String userInput = targetGroupName.toString() + VALID_MEETING_TITLE_DESC_URGENT
                + VALID_MEETING_DESCRIPTION_DESC_URGENT + VALID_MEETING_DESC_WEEKLY;

        MeetCommand expectedCommand = new MeetCommand(targetGroup, WEEKLY);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidValueFollowedByValidValue_success() {
        Title targetGroupName = new Title("MA1521");
        Group targetGroup = new Group(targetGroupName);
        String userInput = targetGroupName.toString() + INVALID_MEETING_TITLE_DESC
                + INVALID_MEETING_DESCRIPTION_DESC + VALID_MEETING_DESC_WEEKLY;

        MeetCommand expectedCommand = new MeetCommand(targetGroup, WEEKLY);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_resetMeeting_success() {
        Title targetGroupName = new Title("MA1521");
        Group targetGroup = new Group(targetGroupName);
        String userInput = targetGroupName.toString() + "";

        MeetCommand expectedCommand = new MeetCommand(targetGroup, null);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}

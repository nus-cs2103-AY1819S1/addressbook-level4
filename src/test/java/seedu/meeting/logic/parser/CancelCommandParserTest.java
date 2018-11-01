package seedu.meeting.logic.parser;

import static seedu.meeting.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.meeting.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.meeting.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.meeting.logic.commands.CancelCommand;
import seedu.meeting.model.group.Group;
import seedu.meeting.model.shared.Title;

// @@author NyxF4ll
public class CancelCommandParserTest {
    private static final String INVALID_GROUPNAME = "CS123*";
    private static final String VALID_GROUPNAME = "CS1231";

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, CancelCommand.MESSAGE_USAGE);

    private CancelCommandParser parser = new CancelCommandParser();

    @Test
    public void parse_missingGroupName_failure() {
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidGroupName_failure() {
        assertParseFailure(parser, INVALID_GROUPNAME, MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_validGroupNameSpecified_success() {
        Title targetGroupName = new Title(VALID_GROUPNAME);
        Group targetGroup = new Group(targetGroupName);
        CancelCommand expectedCommand = new CancelCommand(targetGroup);

        assertParseSuccess(parser, VALID_GROUPNAME, expectedCommand);
    }
}

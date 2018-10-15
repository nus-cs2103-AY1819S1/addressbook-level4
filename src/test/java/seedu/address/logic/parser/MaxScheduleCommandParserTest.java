package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;

import seedu.address.logic.commands.MaxScheduleCommand;

class MaxScheduleCommandParserTest {

    private MaxScheduleCommandParser parser = new MaxScheduleCommandParser();

    @org.junit.Test
    public void parseEmptyArgthrowsParseException() {
        assertParseFailure(parser, "     ",
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, MaxScheduleCommand.MESSAGE_USAGE));
    }
}

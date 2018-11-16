package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.ListCommand.GET_ALL_WORD;
import static seedu.address.logic.parser.CmdTypeCliSyntax.CMDTYPE_DIAGNOSIS;
import static seedu.address.logic.parser.CmdTypeCliSyntax.CMDTYPE_PATIENT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.ListCommand;

public class ListCommandParserTest {

    private static final String addAll = " " + GET_ALL_WORD;

    private ListCommandParser parser = new ListCommandParser();

    @Test
    public void parse_validArgs_returnsListCommand() {
        assertParseSuccess(parser, CMDTYPE_PATIENT, new ListCommand(CMDTYPE_PATIENT, ""));
        assertParseSuccess(parser, CMDTYPE_PATIENT + addAll, new ListCommand(CMDTYPE_PATIENT, addAll.trim()));
    }

    @Test
    public void parse_missingArgs_throwsParseException() {
        assertParseFailure(parser, "",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
    }

    @Test
    public void parseInvalidArgsThrowsParseException() {
        assertParseFailure(parser, CMDTYPE_DIAGNOSIS,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
    }
}

package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.WHITESPACE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.logic.parser.CommandParserTestUtil.createIndex;

import org.junit.Test;

import seedu.address.logic.commands.DeleteNoteCommand;

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the DeleteNoteCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the DeleteNoteCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class DeleteNoteCommandParserTest {

    private DeleteNoteCommandParser parser = new DeleteNoteCommandParser();

    @Test
    public void parseValidInput() {
        // correct format
        // single digit index
        assertParseSuccess(parser, WHITESPACE + "1", new DeleteNoteCommand(createIndex(1)));
        // multiple digit index
        assertParseSuccess(parser, WHITESPACE + "111", new DeleteNoteCommand(createIndex(111)));

        // In normal use, this input would never happen, but testing with respect to the parser itself, this should
        // be a valid input.
        // no prefix whitespace
        assertParseSuccess(parser, "1", new DeleteNoteCommand(createIndex(1)));
    }

    @Test
    public void parseInvalidInput() {
        // letter index
        assertParseFailure(parser, WHITESPACE + "a",
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteNoteCommand.MESSAGE_USAGE));

        // special char index
        assertParseFailure(parser, WHITESPACE + "/",
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteNoteCommand.MESSAGE_USAGE));

        // int + char index
        assertParseFailure(parser, WHITESPACE + "1a",
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteNoteCommand.MESSAGE_USAGE));

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + "1",
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteNoteCommand.MESSAGE_USAGE));
    }
}

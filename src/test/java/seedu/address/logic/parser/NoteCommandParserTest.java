package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NOTE_NUMBERS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NOTE_SPECIALCHAR;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NOTE_TEXT;
import static seedu.address.logic.parser.AddTextToNoteParserTestUtil.assertParseFailure_charIndex;
import static seedu.address.logic.parser.AddTextToNoteParserTestUtil.assertParseFailure_intAndCharIndex;
import static seedu.address.logic.parser.AddTextToNoteParserTestUtil.assertParseFailure_noPrefixWhitespace;
import static seedu.address.logic.parser.AddTextToNoteParserTestUtil.assertParseFailure_noWhitespaceBetween;
import static seedu.address.logic.parser.AddTextToNoteParserTestUtil.assertParseFailure_nonEmptyPreamble;
import static seedu.address.logic.parser.AddTextToNoteParserTestUtil.assertParseFailure_specialCharIndex;
import static seedu.address.logic.parser.AddTextToNoteParserTestUtil.assertParseSuccess_multipleDigitIndex_numText;
import static seedu.address.logic.parser.AddTextToNoteParserTestUtil.assertParseSuccess_singleDigitIndex_charText;
import static seedu.address.logic.parser.AddTextToNoteParserTestUtil.assertParseSuccess_specialCharText;
import static seedu.address.logic.parser.CommandParserTestUtil.createIndex;

import org.junit.Test;

import seedu.address.logic.commands.NoteCommand;

public class NoteCommandParserTest {

    private NoteCommandParser parser = new NoteCommandParser();

    @Test
    public void parseValidInput() {
        // making use of Each Valid Input At Least Once In A Positive Test Case heuristic
        // correct format
        // single digit index, characters text
        assertParseSuccess_singleDigitIndex_charText(parser, new NoteCommand(createIndex(1), VALID_NOTE_TEXT));

        // multiple digit index, numbers text
        assertParseSuccess_multipleDigitIndex_numText(parser, new NoteCommand(createIndex(111),
            VALID_NOTE_NUMBERS));

        // special characters text
        assertParseSuccess_specialCharText(parser, new NoteCommand(createIndex(1), VALID_NOTE_SPECIALCHAR));
    }

    @Test
    public void parseInvalidInput() {
        // no prefix whitespace
        assertParseFailure_noPrefixWhitespace(parser,
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, NoteCommand.MESSAGE_USAGE));

        // no whitespace between
        assertParseFailure_noWhitespaceBetween(parser,
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, NoteCommand.MESSAGE_USAGE));

        // letter index
        assertParseFailure_charIndex(parser,
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, NoteCommand.MESSAGE_USAGE));

        // special char index
        assertParseFailure_specialCharIndex(parser,
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, NoteCommand.MESSAGE_USAGE));

        // int + char index
        assertParseFailure_intAndCharIndex(parser,
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, NoteCommand.MESSAGE_USAGE));

        // non-empty preamble
        assertParseFailure_nonEmptyPreamble(parser,
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, NoteCommand.MESSAGE_USAGE));
    }
}

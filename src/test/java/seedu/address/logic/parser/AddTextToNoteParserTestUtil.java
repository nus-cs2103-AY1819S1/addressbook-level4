package seedu.address.logic.parser;

import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NOTE_NUMBERS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NOTE_SPECIALCHAR;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NOTE_TEXT;
import static seedu.address.logic.commands.CommandTestUtil.WHITESPACE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import seedu.address.logic.commands.Command;

/**
 * Utility class for NoteCommandParserTest and EditNoteCommandParserTest to use as they have similar tests.
 */
public class AddTextToNoteParserTestUtil {

    /**
     * Tests if parser success if index is single-digit and text made up of alphabets.
     */
    public static void assertParseSuccess_singleDigitIndex_charText(Parser parser, Command expectedCommand) {
        assertParseSuccess(parser, WHITESPACE + "1" + WHITESPACE + VALID_NOTE_TEXT, expectedCommand);
    }

    /**
     * Tests if parser success if index is multiple-digit and text made up of numbers.
     */
    public static void assertParseSuccess_multipleDigitIndex_numText(Parser parser, Command expectedCommand) {
        assertParseSuccess(parser, WHITESPACE + "111" + WHITESPACE + VALID_NOTE_NUMBERS, expectedCommand);
    }

    /**
     * Tests if parser success if text made up of special characters.
     */
    public static void assertParseSuccess_specialCharText(Parser parser, Command expectedCommand) {
        assertParseSuccess(parser, WHITESPACE + "1" + WHITESPACE + VALID_NOTE_SPECIALCHAR, expectedCommand);
    }

    /**
     * Tests if parser failure if there is no prefix whitespace in argument.
     */
    public static void assertParseFailure_noPrefixWhitespace(Parser parser, String expectedMessage) {
        // no prefix whitespace
        assertParseFailure(parser, "1" + WHITESPACE + VALID_NOTE_TEXT, expectedMessage);
    }

    /**
     * Tests if parser failure if there is no whitespace between index and text.
     */
    public static void assertParseFailure_noWhitespaceBetween(Parser parser, String expectedMessage) {
        // no whitespace between
        assertParseFailure(parser, WHITESPACE + "1" + VALID_NOTE_TEXT, expectedMessage);
    }

    /**
     * Tests if parser failure if index is an alphabet.
     */
    public static void assertParseFailure_charIndex(Parser parser, String expectedMessage) {
        // letter index
        assertParseFailure(parser, WHITESPACE + "a" + WHITESPACE + VALID_NOTE_TEXT, expectedMessage);
    }

    /**
     * Tests if parser failure if index is a special character.
     */
    public static void assertParseFailure_specialCharIndex(Parser parser, String expectedMessage) {
        // special char index
        assertParseFailure(parser, WHITESPACE + "/" + WHITESPACE + VALID_NOTE_TEXT, expectedMessage);
    }

    /**
     * Tests if parser failure if index is made up of an int and an alphabet.
     */
    public static void assertParseFailure_intAndCharIndex(Parser parser, String expectedMessage) {
        // int + char index
        assertParseFailure(parser, WHITESPACE + "1a" + WHITESPACE + VALID_NOTE_TEXT, expectedMessage);
    }

    /**
     * Tests if parser failure if there is additional text before index.
     */
    public static void assertParseFailure_nonEmptyPreamble(Parser parser, String expectedMessage) {
        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + "1" + WHITESPACE + VALID_NOTE_TEXT, expectedMessage);
    }
}

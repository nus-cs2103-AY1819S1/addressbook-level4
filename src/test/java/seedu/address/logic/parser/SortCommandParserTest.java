package seedu.address.logic.parser;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;

import org.junit.Test;

import seedu.address.logic.commands.SortCommand;
import seedu.address.logic.parser.exceptions.SimpleParseException;

public class SortCommandParserTest {

    private SortCommandParser parser = new SortCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_doesNotThrow() {
        assertParseSuccess(parser, "d<");
        assertParseSuccess(parser, "name>");
        assertParseSuccess(parser, "d<  name>");
        assertParseSuccess(parser, "d> deadline<");
        assertParseSuccess(parser, "p< due>");
        assertParseSuccess(parser, "p< tag<{cs2103t  cs2030 easy}");
        assertParseSuccess(parser, "p< tag<{ cs2103t  cs2030 easy }");
        assertParseSuccess(parser, "f< n>");
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseThrowsException(parser, "d=");
        assertParseThrowsException(parser, "dues<");
        assertParseThrowsException(parser, "name>>");
        assertParseThrowsException(parser, "name>>  due<");
        assertParseThrowsException(parser, "name~");
        assertParseThrowsException(parser, "p< tag<{ cs2103t  cs2030 easy}}");
        assertParseThrowsException(parser, "p< tag<");
        assertParseThrowsException(parser, "p< tag<{");
        assertParseThrowsException(parser, "frequencies<");
    }

    /**
     * Asserts that the parse was successful.
     */
    private void assertParseSuccess(SortCommandParser parser, String str) {
        try {
            SortCommand command = parser.parse(str);
            assertNotNull("Expected not null", command);
        } catch (SimpleParseException e) {
            fail("Expected no parse error");
        }
    }

    /**
     * Asserts that the parse throws a SimpleParseException.
     */
    private void assertParseThrowsException(SortCommandParser parser, String str) {
        try {
            parser.parse(str);
            fail("Expected a parse error");
        } catch (SimpleParseException ignore) {
            // Do not do anything, because we just want to make sure that an exception is thrown
        }
    }

}

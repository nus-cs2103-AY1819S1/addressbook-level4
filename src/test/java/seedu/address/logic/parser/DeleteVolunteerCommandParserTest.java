package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.Test;

import seedu.address.logic.commands.DeleteVolunteerCommand;

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the DeleteVolunteerCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the DeleteVolunteerCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class DeleteVolunteerCommandParserTest {

    private DeleteVolunteerCommandParser parser = new DeleteVolunteerCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteVolunteerCommand() {
        assertParseSuccess(parser, "1", new DeleteVolunteerCommand(INDEX_FIRST_PERSON));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteVolunteerCommand.MESSAGE_USAGE));
    }
}

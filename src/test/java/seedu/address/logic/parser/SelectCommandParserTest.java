package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.Test;

import seedu.address.logic.commands.SelectCommand;
import seedu.address.model.ContactType;

/**
 * Test scope: similar to {@code DeleteCommandParserTest}.
 * @see DeleteCommandParserTest
 */
public class SelectCommandParserTest {

    private SelectCommandParser parser = new SelectCommandParser(ContactType.CLIENT);

    @Test
    public void parse_validArgs_returnsSelectCommand() {
        assertParseSuccess(parser, "#1", new SelectCommand(INDEX_FIRST_PERSON, ContactType.CLIENT));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "#a", String.format(MESSAGE_INVALID_PERSON_DISPLAYED_INDEX, "a"));
    }
}

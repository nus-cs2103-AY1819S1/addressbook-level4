package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandModuleTestUtil.INVALID_INDEX_INSERT_MODULE;
import static seedu.address.logic.commands.CommandModuleTestUtil.VALID_INDEX_INSERT_MODULE;
import static seedu.address.logic.commands.CommandOccasionTestUtil.INVALID_INDEX_INSERT_OCCASION;
import static seedu.address.logic.commands.CommandOccasionTestUtil.VALID_INDEX_INSERT_OCCASION;
import static seedu.address.logic.commands.CommandPersonTestUtil.VALID_INDEX_INSERT_PERSON;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.TypeUtil;
import seedu.address.logic.commands.RemovePersonCommand;

public class RemovePersonCommandParserTest {
    private RemovePersonCommandParser parser = new RemovePersonCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        assertParseSuccess(parser, VALID_INDEX_INSERT_PERSON + VALID_INDEX_INSERT_MODULE,
                new RemovePersonCommand(Index.fromZeroBased(0), Index.fromZeroBased(0),
                        TypeUtil.MODULE));
        assertParseSuccess(parser, VALID_INDEX_INSERT_PERSON + VALID_INDEX_INSERT_OCCASION,
                new RemovePersonCommand(Index.fromZeroBased(0), Index.fromZeroBased(0),
                        TypeUtil.OCCASION));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                RemovePersonCommand.MESSAGE_USAGE);

        // What happens when a user doesn't enter a person index at all.
        assertParseFailure(parser, VALID_INDEX_INSERT_MODULE, expectedMessage);

        assertParseFailure(parser, VALID_INDEX_INSERT_OCCASION, expectedMessage);

        //When you input module and occasion together without person
        assertParseFailure(parser, VALID_INDEX_INSERT_OCCASION + VALID_INDEX_INSERT_OCCASION,
                expectedMessage);

        // What happens when a user doesn't enter either a module or an occasion.
        assertParseFailure(parser, VALID_INDEX_INSERT_PERSON, expectedMessage);
    }

    @Test
    public void parse_tooManyFields_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                RemovePersonCommand.MESSAGE_USAGE);

        // When a person inputs two persons and a module.
        assertParseFailure(parser, VALID_INDEX_INSERT_PERSON + VALID_INDEX_INSERT_PERSON
                + VALID_INDEX_INSERT_MODULE, expectedMessage);

        assertParseFailure(parser, VALID_INDEX_INSERT_PERSON + VALID_INDEX_INSERT_OCCASION
                + VALID_INDEX_INSERT_MODULE, expectedMessage);
    }

    @Test
    public void parse_negativeIndex_failure() {
        String expectedMessage = ParserUtil.MESSAGE_INVALID_INDEX;

        assertParseFailure(parser, VALID_INDEX_INSERT_PERSON + INVALID_INDEX_INSERT_MODULE,
                expectedMessage);

        assertParseFailure(parser, VALID_INDEX_INSERT_PERSON + INVALID_INDEX_INSERT_OCCASION,
                expectedMessage);
    }
}

package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.AnakinCommandTestUtil.INVALID_NAME_DECK;
import static seedu.address.logic.commands.AnakinCommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.AnakinCommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.AnakinCommandTestUtil.VALID_NAME_DECK_A;
import static seedu.address.logic.parser.AnakinCommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.AnakinCommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.anakincommands.AnakinNewDeckCommand;
import seedu.address.logic.anakinparser.AnakinNewDeckCommandParser;
import seedu.address.model.anakindeck.AnakinDeck;
import seedu.address.model.person.Name;
import seedu.address.testutil.AnakinDeckBuilder;

public class AnakinNewDeckCommandParserTest {
    private AnakinNewDeckCommandParser parser = new AnakinNewDeckCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        AnakinDeck expectedAnakinDeck = new AnakinDeckBuilder().withName("Hello").build();

        // whitespace only preamble
        assertParseSuccess(parser,  PREAMBLE_WHITESPACE + "Hello", new AnakinNewDeckCommand(expectedAnakinDeck));

    }


    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AnakinNewDeckCommand.MESSAGE_USAGE);

        // No argument
        assertParseFailure(parser, "", expectedMessage);

        // Blank name
        assertParseFailure(parser, PREAMBLE_WHITESPACE, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, "n/" + INVALID_NAME_DECK, Name.MESSAGE_NAME_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + VALID_NAME_DECK_A,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AnakinNewDeckCommand.MESSAGE_USAGE));
    }
}

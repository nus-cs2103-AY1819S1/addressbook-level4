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
import seedu.address.logic.commands.InsertPersonCommand;
import seedu.address.model.module.Module;
import seedu.address.model.module.ModuleDescriptor;
import seedu.address.model.occasion.Occasion;
import seedu.address.model.occasion.OccasionDescriptor;

public class InsertPersonCommandParserTest {
    private InsertPersonCommandParser parser = new InsertPersonCommandParser();

    @Test
    public void parse_allFields_success() {

        assertParseSuccess(parser, VALID_INDEX_INSERT_PERSON + VALID_INDEX_INSERT_MODULE,
                            new InsertPersonCommand(Index.fromZeroBased(0), Index.fromZeroBased(0),
                                                    new Module(new ModuleDescriptor())));
        assertParseSuccess(parser, VALID_INDEX_INSERT_PERSON + VALID_INDEX_INSERT_OCCASION,
                            new InsertPersonCommand(Index.fromZeroBased(0), Index.fromZeroBased(0),
                                                    new Occasion(new OccasionDescriptor())));
    }

    @Test
    public void parse_negativeIndex_failure() {
        String expectedMessage = ParserUtil.MESSAGE_INVALID_INDEX;

        assertParseFailure(parser, VALID_INDEX_INSERT_PERSON + INVALID_INDEX_INSERT_MODULE,
                                    expectedMessage);

        assertParseFailure(parser, VALID_INDEX_INSERT_PERSON + INVALID_INDEX_INSERT_OCCASION,
                                    expectedMessage);
    }

    @Test
    public void parse_invalidPersonInformation_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                                                        InsertPersonCommand.MESSAGE_USAGE);

        // What happens when a user doesn't enter a person index at all.
        assertParseFailure(parser, VALID_INDEX_INSERT_MODULE, expectedMessage);

        assertParseFailure(parser, VALID_INDEX_INSERT_OCCASION, expectedMessage);

        // What happens when a user doesn't enter either a module or an occasion.
        assertParseFailure(parser, VALID_INDEX_INSERT_PERSON, expectedMessage);

    }
}

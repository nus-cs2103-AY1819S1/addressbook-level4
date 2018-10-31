package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ALLERGY_TO_DELETE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CONDITION_TO_DELETE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ALLERGY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CONDITION;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteMedicalHistoryCommand;

public class DeleteMedicalHistoryCommandParserTest {
    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteMedicalHistoryCommand.MESSAGE_USAGE);
    private DeleteMedicalHistoryCommandParser parser = new DeleteMedicalHistoryCommandParser();

    @Test
    public void parse_fieldSpecified_success() {
        Index targetIndex = INDEX_FIRST_PERSON;

        //input is not blank for both allergy and condition
        String userInput = targetIndex.getOneBased() + " "
                + PREFIX_ALLERGY + VALID_ALLERGY_TO_DELETE + " " + PREFIX_CONDITION + VALID_CONDITION_TO_DELETE;
        DeleteMedicalHistoryCommand expectedCommand = new DeleteMedicalHistoryCommand(
                INDEX_FIRST_PERSON, VALID_ALLERGY_TO_DELETE, VALID_CONDITION_TO_DELETE);
        assertParseSuccess(parser, userInput, expectedCommand);

        //input for allergy is left blank
        userInput = targetIndex.getOneBased() + " " + PREFIX_ALLERGY + " "
                + PREFIX_CONDITION + VALID_CONDITION_TO_DELETE;
        expectedCommand = new DeleteMedicalHistoryCommand(INDEX_FIRST_PERSON, "", VALID_CONDITION_TO_DELETE);
        assertParseSuccess(parser, userInput, expectedCommand);

        //input for condition is left blank
        userInput = targetIndex.getOneBased() + " " + PREFIX_ALLERGY + VALID_ALLERGY_TO_DELETE + " " + PREFIX_CONDITION;
        expectedCommand = new DeleteMedicalHistoryCommand(INDEX_FIRST_PERSON, VALID_ALLERGY_TO_DELETE, "");
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_missingParts_failure() {
        //no index specified
        assertParseFailure(parser,
                PREFIX_ALLERGY + VALID_ALLERGY_TO_DELETE + " " + PREFIX_CONDITION + VALID_CONDITION_TO_DELETE,
                MESSAGE_INVALID_FORMAT);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5 "
                + PREFIX_ALLERGY + VALID_ALLERGY_TO_DELETE + " " + PREFIX_CONDITION + VALID_CONDITION_TO_DELETE,
                MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0 "
                + PREFIX_ALLERGY + VALID_ALLERGY_TO_DELETE + " " + PREFIX_CONDITION + VALID_CONDITION_TO_DELETE,
                MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/string u/string", MESSAGE_INVALID_FORMAT);
    }
}

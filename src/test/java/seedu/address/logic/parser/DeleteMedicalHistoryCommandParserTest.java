package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ALLERGY_TO_DELETE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CONDITION_TO_DELETE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_ALICE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ALLERGY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CONDITION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.DeleteMedicalHistoryCommand;
import seedu.address.model.person.Name;

public class DeleteMedicalHistoryCommandParserTest {
    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteMedicalHistoryCommand.MESSAGE_USAGE);
    private DeleteMedicalHistoryCommandParser parser = new DeleteMedicalHistoryCommandParser();

    @Test
    public void parse_fieldSpecified_success() {
        //input is not blank for both allergy and condition
        String userInput = " " + PREFIX_NAME + VALID_NAME_ALICE + " "
                + PREFIX_ALLERGY + VALID_ALLERGY_TO_DELETE + " " + PREFIX_CONDITION + VALID_CONDITION_TO_DELETE;
        DeleteMedicalHistoryCommand expectedCommand = new DeleteMedicalHistoryCommand(
                new Name(VALID_NAME_ALICE), null, VALID_ALLERGY_TO_DELETE, VALID_CONDITION_TO_DELETE);
        assertParseSuccess(parser, userInput, expectedCommand);

        //input for allergy is left blank
        userInput = " " + PREFIX_NAME + VALID_NAME_ALICE + " "
                + PREFIX_CONDITION + VALID_CONDITION_TO_DELETE;
        expectedCommand = new DeleteMedicalHistoryCommand(
                new Name(VALID_NAME_ALICE), null, "", VALID_CONDITION_TO_DELETE);
        assertParseSuccess(parser, userInput, expectedCommand);

        //input for condition is left blank
        userInput = " " + PREFIX_NAME + VALID_NAME_ALICE + " "
                + PREFIX_ALLERGY + VALID_ALLERGY_TO_DELETE;
        expectedCommand = new DeleteMedicalHistoryCommand(
                new Name(VALID_NAME_ALICE), null, VALID_ALLERGY_TO_DELETE, "");
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_missingParts_failure() {
        //no name specified
        assertParseFailure(parser,
                PREFIX_ALLERGY + VALID_ALLERGY_TO_DELETE + " " + PREFIX_CONDITION + VALID_CONDITION_TO_DELETE,
                MESSAGE_INVALID_FORMAT);

        // no name and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "alice some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "a/Alice Pauline i/string u/string", MESSAGE_INVALID_FORMAT);
    }
}

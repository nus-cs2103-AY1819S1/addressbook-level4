package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalPatientsAndDoctors.ALICE_PATIENT;

import org.junit.Test;

import seedu.address.logic.commands.ViewPatientCommand;

public class ViewPatientCommandParserTest {

    private ViewPatientCommandParser parser = new ViewPatientCommandParser();

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "123", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ViewPatientCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsViewPatientCommand() {
        // no leading and trailing whitespaces
        String userInput = " " + PREFIX_NAME + ALICE_PATIENT.getName();
        ViewPatientCommand expectedViewPatientCommand =
                new ViewPatientCommand(ALICE_PATIENT.getName());
        assertParseSuccess(parser, userInput, expectedViewPatientCommand);
    }

}

package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalPatientsAndDoctors.GEORGE_DOCTOR;

import org.junit.Test;

import seedu.address.logic.commands.ViewDoctorCommand;

public class ViewDoctorCommandParserTest {

    private ViewDoctorCommandParser parser = new ViewDoctorCommandParser();

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "123", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ViewDoctorCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsViewDoctorCommand() {
        // no leading and trailing whitespaces
        String userInput = " " + PREFIX_NAME + GEORGE_DOCTOR.getName();
        ViewDoctorCommand expectedViewDoctorCommand =
                new ViewDoctorCommand(GEORGE_DOCTOR.getName());
        assertParseSuccess(parser, userInput, expectedViewDoctorCommand);
    }

}

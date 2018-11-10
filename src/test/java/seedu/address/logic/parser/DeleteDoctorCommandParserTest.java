package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalPatientsAndDoctors.GEORGE_DOCTOR;

import org.junit.Test;

import seedu.address.logic.commands.DeleteDoctorCommand;

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the DeleteDoctorCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the DeleteCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class DeleteDoctorCommandParserTest {

    private DeleteDoctorCommandParser parser = new DeleteDoctorCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteCommand() {
        String userInput = " " + PREFIX_NAME + GEORGE_DOCTOR.getName();
        assertParseSuccess(parser, userInput, new DeleteDoctorCommand(GEORGE_DOCTOR.getName(), null));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "123", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteDoctorCommand.MESSAGE_USAGE));
    }
}

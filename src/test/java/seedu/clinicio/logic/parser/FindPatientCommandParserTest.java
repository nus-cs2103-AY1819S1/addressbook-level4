package seedu.clinicio.logic.parser;

import static seedu.clinicio.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.clinicio.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.clinicio.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.Test;

import seedu.clinicio.logic.commands.FindPatientCommand;
import seedu.clinicio.model.patient.PatientNameContainsKeywordsPredicate;

public class FindPatientCommandParserTest {

    private FindPatientCommandParser parser = new FindPatientCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                FindPatientCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindPatientCommand() {
        // no leading and trailing whitespaces
        FindPatientCommand expectedFindPatientCommand =
                new FindPatientCommand(
                        new PatientNameContainsKeywordsPredicate(Arrays.asList("Alex", "Benny")));
        assertParseSuccess(parser, "Alex Benny", expectedFindPatientCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n Alex \n \t Benny  \t", expectedFindPatientCommand);
    }
}

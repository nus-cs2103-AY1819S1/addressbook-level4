package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalPersons.ADAM;

import org.junit.Test;

import seedu.address.logic.commands.LoginCommand;
import seedu.address.model.doctor.Doctor;
import seedu.address.model.doctor.Password;
import seedu.address.model.person.Person;
import seedu.address.testutil.DoctorBuilder;

public class LoginCommandParserTest {
    
    private LoginCommandParser parser = new LoginCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, LoginCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidArg_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT, LoginCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsLoginCommand() {
        // no leading and trailing whitespaces
        Person expectedDoctor = new DoctorBuilder(ADAM).withPassword("doctor1", false).build();
        assertParseSuccess(parser, " r/doctor n/" + ADAM.getName() + " pass/doctor1", new LoginCommand(expectedDoctor));
        
        // multiple whitespaces between keywords
        //assertParseSuccess(parser, " \n Alice \n \t Bob  \t", expectedFindCommand);
    }
}

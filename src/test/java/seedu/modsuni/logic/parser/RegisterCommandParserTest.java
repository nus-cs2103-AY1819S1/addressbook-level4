package seedu.modsuni.logic.parser;

import static seedu.modsuni.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import static seedu.modsuni.logic.commands.CommandTestUtil.ENROLLMENT_DESC;
import static seedu.modsuni.logic.commands.CommandTestUtil.INVALID_ENROLLMENT_DESC;
import static seedu.modsuni.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.modsuni.logic.commands.CommandTestUtil.INVALID_PASSWORD_DESC;
import static seedu.modsuni.logic.commands.CommandTestUtil.INVALID_USERNAME_DESC;
import static seedu.modsuni.logic.commands.CommandTestUtil.LOGIN_PASSWORD_DESC;
import static seedu.modsuni.logic.commands.CommandTestUtil.LOGIN_USERNAME_DESC;
import static seedu.modsuni.logic.commands.CommandTestUtil.MAJOR_DESC;
import static seedu.modsuni.logic.commands.CommandTestUtil.MINOR_DESC;
import static seedu.modsuni.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.modsuni.logic.commands.CommandTestUtil.VALID_ENROLLMENT;
import static seedu.modsuni.logic.commands.CommandTestUtil.VALID_MAJOR;
import static seedu.modsuni.logic.commands.CommandTestUtil.VALID_MINOR;
import static seedu.modsuni.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.modsuni.logic.commands.CommandTestUtil.VALID_PASSWORD;
import static seedu.modsuni.logic.commands.CommandTestUtil.VALID_PATH_TO_PIC;
import static seedu.modsuni.logic.commands.CommandTestUtil.VALID_USERDATA;
import static seedu.modsuni.logic.commands.CommandTestUtil.VALID_USERNAME;
import static seedu.modsuni.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.modsuni.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import org.junit.Test;

import seedu.modsuni.logic.commands.RegisterCommand;
import seedu.modsuni.logic.parser.exceptions.ParseException;
import seedu.modsuni.model.credential.Credential;
import seedu.modsuni.model.credential.Password;
import seedu.modsuni.model.credential.Username;
import seedu.modsuni.model.user.Name;
import seedu.modsuni.model.user.Role;
import seedu.modsuni.model.user.User;
import seedu.modsuni.model.user.student.EnrollmentDate;
import seedu.modsuni.model.user.student.Student;

public class RegisterCommandParserTest {
    private RegisterCommandParser parser = new RegisterCommandParser();

    @Test
    public void parse_allFieldsPresent_success() throws ParseException {
        String userInput = LOGIN_USERNAME_DESC
            + LOGIN_PASSWORD_DESC
            + NAME_DESC_AMY
            + ENROLLMENT_DESC
            + MAJOR_DESC
            + MINOR_DESC;

        Credential toVerify = new Credential(
            ParserUtil.parseUsername(VALID_USERNAME),
            ParserUtil.parsePassword(VALID_PASSWORD));

        User newUser = new Student(
            ParserUtil.parseUsername(VALID_USERNAME),
            ParserUtil.parseName(VALID_NAME_AMY),
            Role.STUDENT,
            ParserUtil.parseEnrollmentDate(VALID_ENROLLMENT),
            Arrays.asList(VALID_MAJOR),
            Arrays.asList(VALID_MINOR));

        Path dummyPath = Paths.get(VALID_USERDATA);

        RegisterCommand expectedCommand = new RegisterCommand(toVerify,
            newUser, dummyPath);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
            RegisterCommand.MESSAGE_USAGE);

        // missing username prefix
        assertParseFailure(parser,
            VALID_USERNAME
                + LOGIN_PASSWORD_DESC
                + NAME_DESC_AMY
                + ENROLLMENT_DESC
                + MAJOR_DESC
                + MINOR_DESC,
            expectedMessage);

        // missing password prefix
        assertParseFailure(parser,
            LOGIN_USERNAME_DESC
                + VALID_PASSWORD
                + NAME_DESC_AMY
                + ENROLLMENT_DESC
                + MAJOR_DESC
                + MINOR_DESC,
            expectedMessage);

        // missing name prefix
        assertParseFailure(parser,
            LOGIN_USERNAME_DESC
                + LOGIN_PASSWORD_DESC
                + VALID_NAME_AMY
                + ENROLLMENT_DESC
                + MAJOR_DESC
                + MINOR_DESC,
            expectedMessage);

        // missing enrollment date prefix
        assertParseFailure(parser,
            LOGIN_USERNAME_DESC
                + LOGIN_PASSWORD_DESC
                + NAME_DESC_AMY
                + VALID_ENROLLMENT
                + MAJOR_DESC
                + MINOR_DESC,
            expectedMessage);

        // missing major prefix
        assertParseFailure(parser,
            LOGIN_USERNAME_DESC
                + LOGIN_PASSWORD_DESC
                + NAME_DESC_AMY
                + ENROLLMENT_DESC
                + VALID_MAJOR
                + MINOR_DESC,
            expectedMessage);

        // all prefixes missing
        assertParseFailure(parser,
            VALID_USERNAME
                + VALID_PASSWORD
                + VALID_NAME_AMY
                + VALID_PATH_TO_PIC
                + VALID_ENROLLMENT
                + VALID_MAJOR
                + VALID_MINOR,
            expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid username
        assertParseFailure(parser,
            INVALID_USERNAME_DESC
                + LOGIN_PASSWORD_DESC
                + NAME_DESC_AMY
                + ENROLLMENT_DESC
                + MAJOR_DESC
                + MINOR_DESC,
            Username.MESSAGE_USERNAME_CONSTRAINTS);

        // invalid password
        assertParseFailure(parser,
            LOGIN_USERNAME_DESC
                + INVALID_PASSWORD_DESC
                + NAME_DESC_AMY
                + ENROLLMENT_DESC
                + MAJOR_DESC
                + MINOR_DESC,
            Password.MESSAGE_PASSWORD_CONSTRAINTS);

        // invalid name
        assertParseFailure(parser,
            LOGIN_USERNAME_DESC
                + LOGIN_PASSWORD_DESC
                + INVALID_NAME_DESC
                + ENROLLMENT_DESC
                + MAJOR_DESC
                + MINOR_DESC,
            Name.MESSAGE_NAME_CONSTRAINTS);

        // invalid enrollment date
        assertParseFailure(parser,
            LOGIN_USERNAME_DESC
                + LOGIN_PASSWORD_DESC
                + NAME_DESC_AMY
                + INVALID_ENROLLMENT_DESC
                + MAJOR_DESC
                + MINOR_DESC,
            EnrollmentDate.MESSAGE_DATE_CONSTRAINTS);

        //TODO invalid major & minor
    }
}

package seedu.modsuni.logic.parser;

import static seedu.modsuni.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.modsuni.logic.commands.CommandTestUtil.EMPLOY_DATE_DESC_AMY;
import static seedu.modsuni.logic.commands.CommandTestUtil.INVALID_EMPLOY_DATE_DESC;
import static seedu.modsuni.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.modsuni.logic.commands.CommandTestUtil.INVALID_SALARY_DESC;
import static seedu.modsuni.logic.commands.CommandTestUtil.INVALID_USERNAME_DESC;
import static seedu.modsuni.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.modsuni.logic.commands.CommandTestUtil.PASSWORD_DESC_AMY;
import static seedu.modsuni.logic.commands.CommandTestUtil.PATH_DESC_AMY;
import static seedu.modsuni.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.modsuni.logic.commands.CommandTestUtil.SALARY_DESC_AMY;
import static seedu.modsuni.logic.commands.CommandTestUtil.USERNAME_DESC_AMY;
import static seedu.modsuni.logic.commands.CommandTestUtil.VALID_EMPLOY_DATE_AMY;
import static seedu.modsuni.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.modsuni.logic.commands.CommandTestUtil.VALID_PASSWORD_AMY;
import static seedu.modsuni.logic.commands.CommandTestUtil.VALID_PATH_AMY;
import static seedu.modsuni.logic.commands.CommandTestUtil.VALID_SALARY_AMY;
import static seedu.modsuni.logic.commands.CommandTestUtil.VALID_USERNAME_AMY;
import static seedu.modsuni.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.modsuni.testutil.TypicalAdmins.AMY;

import org.junit.Test;

import seedu.modsuni.logic.commands.AddAdminCommand;
import seedu.modsuni.model.credential.Username;
import seedu.modsuni.model.user.Admin;
import seedu.modsuni.model.user.EmployDate;
import seedu.modsuni.model.user.Name;
import seedu.modsuni.model.user.Salary;
import seedu.modsuni.testutil.AdminBuilder;

public class AddAdminCommandParserTest {
    private AddAdminCommandParser parser = new AddAdminCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Admin expectedAdmin = new AdminBuilder(AMY).build();
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddAdminCommand.MESSAGE_USAGE);

        // missing username prefix
        assertParseFailure(parser, VALID_USERNAME_AMY + PASSWORD_DESC_AMY + NAME_DESC_AMY + PATH_DESC_AMY
                + SALARY_DESC_AMY + EMPLOY_DATE_DESC_AMY, expectedMessage);

        // missing password prefix
        assertParseFailure(parser, USERNAME_DESC_AMY + VALID_PASSWORD_AMY + NAME_DESC_AMY + PATH_DESC_AMY
                + SALARY_DESC_AMY + EMPLOY_DATE_DESC_AMY, expectedMessage);

        // missing name prefix
        assertParseFailure(parser, USERNAME_DESC_AMY + PASSWORD_DESC_AMY + VALID_NAME_AMY + PATH_DESC_AMY
                + SALARY_DESC_AMY + EMPLOY_DATE_DESC_AMY, expectedMessage);

        // missing path prefix
        assertParseFailure(parser, USERNAME_DESC_AMY + PASSWORD_DESC_AMY + NAME_DESC_AMY + VALID_PATH_AMY
                + SALARY_DESC_AMY + EMPLOY_DATE_DESC_AMY, expectedMessage);

        // missing salary prefix
        assertParseFailure(parser, USERNAME_DESC_AMY + PASSWORD_DESC_AMY + NAME_DESC_AMY + PATH_DESC_AMY
                + VALID_SALARY_AMY + EMPLOY_DATE_DESC_AMY, expectedMessage);

        // missing employ date prefix
        assertParseFailure(parser, USERNAME_DESC_AMY + PASSWORD_DESC_AMY + NAME_DESC_AMY + PATH_DESC_AMY
                + SALARY_DESC_AMY + VALID_EMPLOY_DATE_AMY, expectedMessage);

    }

    @Test
    public void parse_invalidValue_failure() {

        // invalid username
        assertParseFailure(parser, INVALID_USERNAME_DESC + PASSWORD_DESC_AMY
                + NAME_DESC_AMY + PATH_DESC_AMY + SALARY_DESC_AMY + EMPLOY_DATE_DESC_AMY,
                Username.MESSAGE_USERNAME_CONSTRAINTS);

        // invalid name
        assertParseFailure(parser, USERNAME_DESC_AMY + PASSWORD_DESC_AMY
                        + INVALID_NAME_DESC + PATH_DESC_AMY + SALARY_DESC_AMY + EMPLOY_DATE_DESC_AMY,
                Name.MESSAGE_NAME_CONSTRAINTS);

        // invalid salary
        assertParseFailure(parser, USERNAME_DESC_AMY + PASSWORD_DESC_AMY
                        + NAME_DESC_AMY + PATH_DESC_AMY + INVALID_SALARY_DESC + EMPLOY_DATE_DESC_AMY,
                Salary.MESSAGE_SALARY_CONSTRAINTS);

        // invalid employ date
        assertParseFailure(parser, USERNAME_DESC_AMY + PASSWORD_DESC_AMY
                        + NAME_DESC_AMY + PATH_DESC_AMY + SALARY_DESC_AMY + INVALID_EMPLOY_DATE_DESC,
                EmployDate.MESSAGE_DATE_CONSTRAINTS);


        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_USERNAME_DESC + PASSWORD_DESC_AMY
                        + INVALID_NAME_DESC + PATH_DESC_AMY + SALARY_DESC_AMY + EMPLOY_DATE_DESC_AMY,
                Username.MESSAGE_USERNAME_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + USERNAME_DESC_AMY + PASSWORD_DESC_AMY
                        + NAME_DESC_AMY + PATH_DESC_AMY + SALARY_DESC_AMY + EMPLOY_DATE_DESC_AMY,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddAdminCommand.MESSAGE_USAGE));
    }
}

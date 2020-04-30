package seedu.restaurant.logic.parser.account;

import static seedu.restaurant.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.restaurant.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.restaurant.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.restaurant.logic.commands.CommandTestUtil.PREFIX_WITH_INVALID_PASSWORD;
import static seedu.restaurant.logic.commands.CommandTestUtil.PREFIX_WITH_INVALID_USERNAME;
import static seedu.restaurant.logic.commands.CommandTestUtil.PREFIX_WITH_VALID_NAME;
import static seedu.restaurant.logic.commands.CommandTestUtil.PREFIX_WITH_VALID_PASSWORD;
import static seedu.restaurant.logic.commands.CommandTestUtil.PREFIX_WITH_VALID_USERNAME;
import static seedu.restaurant.logic.commands.CommandTestUtil.VALID_NAME_DEMO_ONE;
import static seedu.restaurant.logic.commands.CommandTestUtil.VALID_PASSWORD_DEMO_ONE;
import static seedu.restaurant.logic.commands.CommandTestUtil.VALID_USERNAME_DEMO_ONE;
import static seedu.restaurant.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.restaurant.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.restaurant.testutil.account.AccountBuilder.DEFAULT_PASSWORD;
import static seedu.restaurant.testutil.account.AccountBuilder.DEFAULT_USERNAME;

import org.junit.Before;
import org.junit.Test;

import seedu.restaurant.logic.commands.account.RegisterCommand;
import seedu.restaurant.model.account.Account;
import seedu.restaurant.model.account.Password;
import seedu.restaurant.model.account.Username;
import seedu.restaurant.testutil.account.AccountBuilder;

//@@author AZhiKai
public class RegisterCommandParserTest {

    private RegisterCommandParser parser = new RegisterCommandParser();
    private Account expectedAccount;

    @Before
    public void setUp() {
        expectedAccount = new AccountBuilder()
                .withUsername(VALID_USERNAME_DEMO_ONE)
                .withPassword(VALID_PASSWORD_DEMO_ONE)
                .withName(VALID_NAME_DEMO_ONE)
                .build();
    }

    @Test
    public void parse_allFieldsPresent_success() {
        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + PREFIX_WITH_VALID_USERNAME
                + PREFIX_WITH_VALID_PASSWORD + PREFIX_WITH_VALID_NAME, new RegisterCommand(expectedAccount));

        // whitespace only
        assertParseSuccess(parser, PREFIX_WITH_VALID_USERNAME + PREFIX_WITH_VALID_PASSWORD
                + PREFIX_WITH_VALID_NAME, new RegisterCommand(expectedAccount));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, RegisterCommand.MESSAGE_USAGE);

        // missing username
        assertParseFailure(parser, PREFIX_WITH_VALID_PASSWORD, expectedMessage);

        // missing password
        assertParseFailure(parser, PREFIX_WITH_VALID_USERNAME, expectedMessage);

        // empty input
        assertParseFailure(parser, "", expectedMessage);

        // missing 1 prefix
        assertParseFailure(parser, PREFIX_WITH_VALID_USERNAME + DEFAULT_PASSWORD, expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, DEFAULT_USERNAME + DEFAULT_PASSWORD, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid username
        assertParseFailure(parser, PREFIX_WITH_INVALID_USERNAME + PREFIX_WITH_VALID_PASSWORD
                + PREFIX_WITH_VALID_NAME, Username.MESSAGE_USERNAME_CONSTRAINT);

        // invalid password
        assertParseFailure(parser, PREFIX_WITH_VALID_USERNAME + PREFIX_WITH_INVALID_PASSWORD
                + PREFIX_WITH_VALID_NAME, Password.MESSAGE_PASSWORD_CONSTRAINT);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, PREFIX_WITH_INVALID_USERNAME + PREFIX_WITH_INVALID_PASSWORD
                + PREFIX_WITH_VALID_NAME, Username.MESSAGE_USERNAME_CONSTRAINT);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + PREFIX_WITH_VALID_USERNAME
                + PREFIX_WITH_VALID_PASSWORD + PREFIX_WITH_VALID_NAME, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                RegisterCommand.MESSAGE_USAGE));
    }
}

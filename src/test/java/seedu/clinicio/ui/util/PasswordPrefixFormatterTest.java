package seedu.clinicio.ui.util;

import static org.junit.Assert.assertEquals;

import static seedu.clinicio.logic.commands.CommandTestUtil.VALID_PASSWORD_ADAM;
import static seedu.clinicio.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.clinicio.logic.parser.CliSyntax.PREFIX_PASSWORD;
import static seedu.clinicio.logic.parser.CliSyntax.PREFIX_ROLE;
import static seedu.clinicio.testutil.TypicalPersons.ADAM;

import org.junit.Before;
import org.junit.Test;

import seedu.clinicio.logic.commands.LoginCommand;

public class PasswordPrefixFormatterTest {

    private static final String WHITESPACE = " ";

    private PasswordPrefixFormatter formatter;

    @Before
    public void setUp() {
        formatter = new PasswordPrefixFormatter();
    }

    @Test
    public void maskPassword() {
        String text = LoginCommand.COMMAND_WORD + WHITESPACE + PREFIX_ROLE + "doctor"
                + WHITESPACE + PREFIX_NAME + ADAM.getName().fullName
                + WHITESPACE + PREFIX_PASSWORD + VALID_PASSWORD_ADAM;
        String expectedText = LoginCommand.COMMAND_WORD + WHITESPACE + PREFIX_ROLE + "doctor"
                + WHITESPACE + PREFIX_NAME + ADAM.getName().fullName
                + WHITESPACE + PREFIX_PASSWORD + "-------2";
        assertEquals(expectedText, formatter.maskPassword(text, false, false) + "2");
    }

    @Test
    public void unmaskPassword() {
        String text = LoginCommand.COMMAND_WORD + WHITESPACE + PREFIX_ROLE + "doctor"
                + WHITESPACE + PREFIX_NAME + ADAM.getName().fullName
                + WHITESPACE + PREFIX_PASSWORD + "------1";
        String expectedText = LoginCommand.COMMAND_WORD + WHITESPACE + PREFIX_ROLE + "doctor"
                + WHITESPACE + PREFIX_NAME + ADAM.getName().fullName
                + WHITESPACE + PREFIX_PASSWORD + VALID_PASSWORD_ADAM;
        formatter.maskPassword(expectedText, false, false);
        assertEquals(expectedText, formatter.unmaskPassword(text));
    }
}

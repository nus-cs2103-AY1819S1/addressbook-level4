package seedu.address.logic.parser;

import static seedu.address.logic.commands.CommandTestUtil.CONTENT_DESC_EXCURSION;
import static seedu.address.logic.commands.CommandTestUtil.FROM_DESC_EXCURSION;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.SUBJECT_DESC_EXCURSION;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalEmails.EXCURSION_EMAIL;

import org.junit.Test;
import org.simplejavamail.email.Email;

import seedu.address.logic.commands.ComposeEmailListCommand;
import seedu.address.testutil.DefaultEmailBuilder;

//@@author EatOrBeEaten
public class ComposeEmailListCommandParserTest {
    private ComposeEmailListCommandParser parser = new ComposeEmailListCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Email expectedEmail = new DefaultEmailBuilder(EXCURSION_EMAIL).buildWithoutTo();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + FROM_DESC_EXCURSION + SUBJECT_DESC_EXCURSION
                + CONTENT_DESC_EXCURSION, new ComposeEmailListCommand(expectedEmail));

    }
}

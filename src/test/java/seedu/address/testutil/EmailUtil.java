package seedu.address.testutil;

//@@author EatOrBeEaten

import static seedu.address.logic.parser.CliSyntax.PREFIX_CONTENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FROM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SUBJECT;

import org.simplejavamail.email.Email;

import seedu.address.logic.commands.ComposeEmailListCommand;

/**
 * A utility class for Email
 */
public class EmailUtil {

    /**
     * Returns a ComposeEmailListCommand string for adding the {@code email}.
     */
    public static String getComposeEmailListCommand(Email email) {
        return ComposeEmailListCommand.COMMAND_WORD + " " + getComposeEmailListDetails(email);
    }

    /**
     * Returns the part of command stirng for the given {@code email}'s details for ComposeEmailListCommand.
     */
    public static String getComposeEmailListDetails(Email email) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_FROM + email.getFromRecipient().getAddress() + " ");
        sb.append(PREFIX_SUBJECT + email.getSubject() + " ");
        sb.append(PREFIX_CONTENT + email.getHTMLText() + " ");
        return sb.toString();
    }
}

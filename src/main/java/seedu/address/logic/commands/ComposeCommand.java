package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CONTENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FROM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SUBJECT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TO;

import org.simplejavamail.email.Email;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;

//@@author EatOrBeEaten
/**
 * Composes email and writes it to hard disk
 */
public class ComposeCommand extends Command {

    public static final String COMMAND_WORD = "compose";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Composes an email. "
            + "Parameters: "
            + PREFIX_FROM + "EMAIL "
            + PREFIX_TO + "EMAIL "
            + PREFIX_SUBJECT + "SUBJECT "
            + PREFIX_CONTENT + "CONTENT\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_FROM + "johndoe@example.com "
            + PREFIX_TO + "samsee@example.com "
            + PREFIX_SUBJECT + "Meeting this Friday "
            + PREFIX_CONTENT + "Hey there's a meeting this friday. ";

    public static final String MESSAGE_SUCCESS = "Email composed: %s";

    private final Email toCompose;

    public ComposeCommand(Email email) {
        requireNonNull(email);
        toCompose = email;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        model.saveEmail(toCompose);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toCompose.getSubject()));
    }
}

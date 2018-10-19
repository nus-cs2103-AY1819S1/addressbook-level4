package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CONTENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FROM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SUBJECT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TO;

import java.util.List;

import org.simplejavamail.email.Email;
import org.simplejavamail.email.EmailBuilder;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

//@@author EatOrBeEaten
/**
 * Composes email to specified index and writes it to hard disk
 */
public class EmailIndexCommand extends Command {

    public static final String COMMAND_WORD = "email_index";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Composes an email to specified index. "
            + "Parameters: "
            + PREFIX_FROM + "EMAIL "
            + PREFIX_TO + "INDEX "
            + PREFIX_SUBJECT + "SUBJECT "
            + PREFIX_CONTENT + "CONTENT(Input <br /> for newline)\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_FROM + "johndoe@example.com "
            + PREFIX_TO + "1 "
            + PREFIX_SUBJECT + "Meeting this Friday "
            + PREFIX_CONTENT + "Dear Sam<br /><br />Remember our meeting this friday.<br /><br />John";

    public static final String MESSAGE_SUCCESS = "Email(Index) composed: %s";

    private final Email toCompose;
    private final Index index;

    public EmailIndexCommand(Email email, Index index) {
        requireNonNull(email);
        requireNonNull(index);

        toCompose = email;
        this.index = index;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person toPerson = lastShownList.get(index.getZeroBased());
        String toEmail = toPerson.getEmail().value;
        Email emailWithRecipient = EmailBuilder.copying(toCompose).to(toEmail).buildEmail();

        model.saveEmail(emailWithRecipient);
        return new CommandResult(String.format(MESSAGE_SUCCESS, emailWithRecipient.getSubject()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EmailIndexCommand // instanceof handles nulls
                && toCompose.equals(((EmailIndexCommand) other).toCompose));
    }
}

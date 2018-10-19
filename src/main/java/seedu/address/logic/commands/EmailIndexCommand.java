package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CONTENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FROM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SUBJECT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TO;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
 * Composes email to specified indexes and writes it to hard disk
 */
public class EmailIndexCommand extends Command {

    public static final String COMMAND_WORD = "email_index";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Composes an email to specified index(es). "
            + "Parameters: "
            + PREFIX_FROM + "EMAIL "
            + PREFIX_TO + "INDEXES "
            + PREFIX_SUBJECT + "SUBJECT "
            + PREFIX_CONTENT + "CONTENT(Input <br /> for newline)\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_FROM + "johndoe@example.com "
            + PREFIX_TO + "1 6 10 "
            + PREFIX_SUBJECT + "Meeting this Friday "
            + PREFIX_CONTENT + "Dear All<br /><br />Remember our meeting this friday.<br /><br />John";

    public static final String MESSAGE_SUCCESS = "Email(Index) composed: %s";

    private final Email toCompose;
    private final Set<Index> indexList;

    public EmailIndexCommand(Email email, Set<Index> indexList) {
        requireNonNull(email);
        requireNonNull(indexList);

        toCompose = email;
        this.indexList = indexList;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        Email emailWithRecipient = addIndexesToEmail(lastShownList);

        model.saveEmail(emailWithRecipient);
        return new CommandResult(String.format(MESSAGE_SUCCESS, emailWithRecipient.getSubject()));
    }

    /**
     * Creates an {@code Email} with recipients in the list.
     * @param lastShownList Current filtered list.
     * @return Email with recipients from list.
     * @throws CommandException if index is beyond list size
     */
    private Email addIndexesToEmail(List<Person> lastShownList) throws CommandException {
        final Set<String> emailList = new HashSet<>();
        for (Index index : indexList) {
            if (index.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
            }

            Person toPerson = lastShownList.get(index.getZeroBased());
            emailList.add(toPerson.getEmail().value);
        }
        return EmailBuilder.copying(toCompose).toMultiple(emailList).buildEmail();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EmailIndexCommand // instanceof handles nulls
                && toCompose.equals(((EmailIndexCommand) other).toCompose));
    }
}

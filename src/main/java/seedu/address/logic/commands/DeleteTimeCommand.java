package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIME;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.Time;

/**
 * Deletes a time slot of a Person in the address book.
 */
public class DeleteTimeCommand extends Command {
    public static final String COMMAND_WORD = "deleteTime";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Deletes a tutorial time slot of a person. "
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_TIME + "TIME "
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_TIME + "mon 1300 1500 ";

    public static final String MESSAGE_SUCCESS_DELETED = "Time slot successfully deleted";
    public static final String MESSAGE_PERSON_NOT_FOUND = "This person does not exists in the address book";
    public static final String MESSAGE_TIME_NOT_FOUND = "This tuition timing is not found in the address book";


    private final Time toDelete;

    private final Index index;

    /**
     * Creates a DeleteTimeCommand to delete the specified {@code Time}
     */
    public DeleteTimeCommand(Index index, Time time) {
        requireNonNull(index);
        requireNonNull(time);
        toDelete = time;
        this.index = index;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();
        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person targetPerson = lastShownList.get(index.getZeroBased());

        if (!targetPerson.getTime().contains(toDelete)) {
            throw new CommandException(MESSAGE_TIME_NOT_FOUND);
        }
        if (!model.hasPerson(targetPerson)) {
            throw new CommandException(MESSAGE_PERSON_NOT_FOUND);
        }
        model.deleteTime(targetPerson, toDelete);

        model.commitAddressBook();
        return new CommandResult(String.format(MESSAGE_SUCCESS_DELETED));
    }

}

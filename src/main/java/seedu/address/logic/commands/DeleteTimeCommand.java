package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIME;

import java.util.Arrays;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.person.Time;

/**
 * Deletes a time slot of a Person in the address book.
 */
public class DeleteTimeCommand extends Command {
    public static final String COMMAND_WORD = "deleteTime";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Deletes a tutorial time slot of a person. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_TIME + "TIME "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "John Doe "
            + PREFIX_TIME + "mon 1300 1500 ";

    public static final String MESSAGE_SUCCESS_DELETED = "Time slot successfully deleted";
    public static final String MESSAGE_PERSON_NOT_FOUND = "This person does not exists in the student list";
    public static final String MESSAGE_TIME_NOT_FOUND = "This tuition timing is not found in the student list";

    private String[] personToFind;

    private final Time toDelete;

    private final String toFind;

    /**
     * Creates a DeleteTimeCommand to delete the specified {@code Time}
     */
    public DeleteTimeCommand(String personName, Time time) {
        requireNonNull(time);
        toDelete = time;
        toFind = personName;
        personToFind = personName.split("\\s+");
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        model.updateFilteredPersonList(new NameContainsKeywordsPredicate(Arrays.asList(personToFind)));

        Person targetPerson = model.getFilteredPersonList().get(0);

        if (!targetPerson.getTime().contains(toDelete)) {
            throw new CommandException(MESSAGE_TIME_NOT_FOUND);
        }
        if (!model.hasPerson(targetPerson)) {
            throw new CommandException(MESSAGE_PERSON_NOT_FOUND);
        }
        model.deleteTime(toFind, toDelete);
        model.commitAddressBook();
        return new CommandResult(String.format(MESSAGE_SUCCESS_DELETED));
    }

}

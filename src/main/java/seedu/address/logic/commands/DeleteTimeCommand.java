package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIME;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Address;
import seedu.address.model.person.Education;
import seedu.address.model.person.Email;
import seedu.address.model.person.Grades;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Time;
import seedu.address.model.tag.Tag;

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

    public static final String MESSAGE_SUCCESS = "Time slot successfully deleted";
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

        Person editedPerson = createEditedPerson(targetPerson);
        editedPerson.deleteTime(toDelete);

        model.updatePerson(targetPerson, editedPerson);
        model.commitAddressBook();
        return new CommandResult(String.format(MESSAGE_SUCCESS));
    }
    /**
     * copy a person with a new timing ArrayList
     *
     * @param personToEdit origin person
     * @return
     */
    private static Person createEditedPerson(Person personToEdit) {
        assert personToEdit != null;

        Name updatedName = personToEdit.getName();
        Phone updatedPhone = personToEdit.getPhone();
        Email updatedEmail = personToEdit.getEmail();

        Address updatedAddress = personToEdit.getAddress();
        Education updatedEducation = personToEdit.getEducation();

        HashMap<String, Grades> updatedGrades = personToEdit.getGrades();
        ArrayList<Time> updatedTime = new ArrayList<>(personToEdit.getTime());
        Set<Tag> updatedTags = personToEdit.getTags();

        return new Person(updatedName, updatedPhone, updatedEmail,
                updatedAddress, updatedEducation, updatedGrades, updatedTime, updatedTags);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteTimeCommand // instanceof handles nulls
                && toDelete.equals(((DeleteTimeCommand) other).toDelete))
                && index.equals(((DeleteTimeCommand) other).index);
    }
}

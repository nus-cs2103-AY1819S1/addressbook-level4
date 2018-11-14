
package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;

/**
 * Deletes the profile picture of a person identified using it's last displayed index from the address book and
 * reset it to the original image.
 */
public class DeleteProfilePhotoCommand extends Command {
    public static final String COMMAND_WORD = "deleteProfilePhoto";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the profile picture of the person identified by the index number used in "
            + "the last person listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";

    public static final String MESSAGE_DELETE_PROFILE_PIC_SUCCESS = "Deleted profile picture of Person: %1$s";

    private final Index index;

    private Person personToEdit;

    private Person editedPerson;

    public DeleteProfilePhotoCommand(Index index) {

        requireNonNull(index);

        this.index = index;

    }


    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {

        List<Person> lastShownList = model.getFilteredPersonList();

        int personIndex = index.getZeroBased();

        if (personIndex >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        personToEdit = lastShownList.get(personIndex);
        editedPerson = new Person(personToEdit);

        editedPerson.deleteProfilePhoto();

        try {
            model.updatePerson(personToEdit, editedPerson);
        } catch (DuplicatePersonException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("The target person cannot be missing");
        }

        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        model.commitAddressBook();
        return new CommandResult(String.format(MESSAGE_DELETE_PROFILE_PIC_SUCCESS, index.getOneBased()));
    }


    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteProfilePhotoCommand // instanceof handles nulls
                && this.index.equals(((DeleteProfilePhotoCommand) other).index)); // state check
    }

}

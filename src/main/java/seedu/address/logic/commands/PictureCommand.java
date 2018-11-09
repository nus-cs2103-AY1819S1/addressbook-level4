package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FILE_LOCATION;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.Picture;

//@@author denzelchung
/**
 * Adds a picture to a contact in the address book.
 */
public class PictureCommand extends Command {

    public static final String COMMAND_WORD = "pic";
    public static final String COMMAND_ALIAS = "p";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a picture to a contact in the address book "
        + "by the index number used in the displayed person list.\n"
        + "Parameters: INDEX (must be a positive integer) "
        + PREFIX_FILE_LOCATION + "FILE_LOCATION\n"
        + "Example: " + COMMAND_WORD + " 1 "
        + PREFIX_FILE_LOCATION + "johndoe.jpg";

    public static final String MESSAGE_SUCCESS = "Added picture for Person: %1$s";

    private final Index index;
    private final Picture picture;

    /**
     * @param index of the person in the filtered person list to edit
     * @param picture of the profile picture
     */
    public PictureCommand(Index index, Picture picture) {
        requireNonNull(index);
        requireNonNull(picture);

        this.index = index;
        this.picture = picture;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToEdit = lastShownList.get(index.getZeroBased());
        Person editedPerson = new Person(personToEdit.getName(), personToEdit.getPhone(), personToEdit.getEmail(),
                personToEdit.getAddress(), personToEdit.getTags(), personToEdit.getMeeting());
        editedPerson.setPicture(picture);

        model.updatePerson(personToEdit, editedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        model.commitAddressBook();

        return new CommandResult(String.format(MESSAGE_SUCCESS, editedPerson));
    }
}

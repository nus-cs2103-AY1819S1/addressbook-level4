package seedu.address.logic.commands;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

import java.util.List;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

//@@author zioul123
/**
 * Edits the details of an existing person in the address book.
 */
public class EditByNameCommand extends ByNameCommand {

    private final EditCommand.EditPersonDescriptor editPersonDescriptor;

    public EditByNameCommand(Person person, EditCommand.EditPersonDescriptor editPersonDescriptor) {
        super(person);
        requireNonNull(editPersonDescriptor);

        this.editPersonDescriptor = editPersonDescriptor;
    }

    //@@author zioul123-reused
    //Adapted from the execute method of EditCommand.
    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Person> allPersonsList = model.getAddressBook().getPersonList();

        if (!allPersonsList.contains(person)) {
            throw new CommandException(Messages.MESSAGE_PERSON_NOT_FOUND);
        }

        Person editedPerson = EditCommand.createEditedPerson(person, editPersonDescriptor);

        if (!person.isSamePerson(editedPerson) && model.hasPerson(editedPerson)) {
            throw new CommandException(EditCommand.MESSAGE_DUPLICATE_PERSON);
        }

        model.updatePerson(person, editedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        model.commitAddressBook();
        return new CommandResult(String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, editedPerson));
    }
    //@@author zioul123
}

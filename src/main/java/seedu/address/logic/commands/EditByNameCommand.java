package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.PersonFinderUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

//@@author zioul123
/**
 * Edits the details of an existing person in the address book using their name.
 */
public class EditByNameCommand extends EditCommand {

    protected final String personIdentifier;

    public EditByNameCommand(String personIdentifier, EditPersonDescriptor editPersonDescriptor) {
        super(Index.fromZeroBased(NO_INDEX), editPersonDescriptor);
        requireNonNull(personIdentifier);

        this.personIdentifier = personIdentifier;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        Person person;
        try {
            person = PersonFinderUtil.findPerson(model, personIdentifier);
        } catch (ParseException pe) {
            throw new CommandException(MESSAGE_NO_EDIT_IDENTIFIER);
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

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditByNameCommand)) {
            return false;
        }

        // state check
        EditByNameCommand e = (EditByNameCommand) other;
        return personIdentifier.equals(e.personIdentifier)
                && editPersonDescriptor.equals(e.editPersonDescriptor);
    }
}

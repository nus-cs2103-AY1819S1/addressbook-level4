package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.Arrays;
import java.util.function.Supplier;
import java.util.stream.Stream;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.person.NameContainsAllKeywordsPredicate;
import seedu.address.model.person.Person;

//@@author zioul123
/**
 * Edits the details of an existing person in the address book.
 */
public class EditByNameCommand extends EditCommand {

    protected final String personIdentifier;

    public EditByNameCommand(String personIdentifier, EditPersonDescriptor editPersonDescriptor) {
        super(Index.fromZeroBased(NO_INDEX), editPersonDescriptor);
        requireNonNull(personIdentifier);

        this.personIdentifier = personIdentifier;
    }

    //@@author zioul123-reused
    //Adapted from the execute method of EditCommand.
    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        Person person;
        try {
            person = findPerson(model);
        } catch (ParseException pe) {
            throw new CommandException(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
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

    /**
     * Find a single person from the specified {@Code Model} using the {@Code String personIdentifier}.
     */
    private Person findPerson(Model model) throws ParseException, CommandException {
        //@@author zioul123-reused
        //Based on code from FindCommandParser.
        String trimmedArgs = personIdentifier.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
        }
        String[] nameKeywords = trimmedArgs.split("\\s+");
        NameContainsAllKeywordsPredicate predicate = new NameContainsAllKeywordsPredicate(Arrays.asList(nameKeywords));
        //@@author zioul123

        // Supplier is used because the stream is acted on more than once.
        Supplier<Stream<Person>> filteredPersons = () ->
                model.getAddressBook().getPersonList().stream().filter(predicate);

        long numOfPeopleMatching = filteredPersons.get().count();
        if (numOfPeopleMatching == 0) {
            throw new CommandException(Messages.MESSAGE_PERSON_NOT_FOUND);
        } else if (numOfPeopleMatching != 1) {
            throw new CommandException(Messages.MESSAGE_MULTIPLE_PERSONS_FOUND);
        }

        return filteredPersons.get().iterator().next();
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

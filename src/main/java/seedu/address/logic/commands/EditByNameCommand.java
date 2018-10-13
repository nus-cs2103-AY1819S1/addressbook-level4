package seedu.address.logic.commands;

import javafx.collections.ObservableList;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;

import java.util.Arrays;
import java.util.List;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

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

    private Person findPerson(Model model) throws ParseException, CommandException {
        //@@author zioul123-reused
        //Based on code from FindCommandParser.
        String trimmedArgs = personIdentifier.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
        }
        String[] nameKeywords = trimmedArgs.split("\\s+");
        NameContainsKeywordsPredicate predicate = new NameContainsKeywordsPredicate(Arrays.asList(nameKeywords));
        //@@author zioul123

        List<Person> allPersonsList = model.getAddressBook().getPersonList();

        long numOfPeopleMatching = allPersonsList.stream().filter(predicate).count();
        if (numOfPeopleMatching == 0) {
            throw new CommandException(Messages.MESSAGE_PERSON_NOT_FOUND);
        } else if (numOfPeopleMatching != 1) {
            throw new CommandException(Messages.MESSAGE_MULTIPLE_PERSONS_FOUND);
        }

        return allPersonsList.get(0);
    }
}

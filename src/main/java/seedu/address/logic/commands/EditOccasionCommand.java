package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_OCCASIONDATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_OCCASIONLOCATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_OCCASIONNAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_OCCASIONS;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;
import static seedu.address.model.occasion.Occasion.createEditedOccasion;

import java.util.List;
import java.util.ListIterator;
import java.util.function.Consumer;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.ShowOccasionRequestEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.occasion.Occasion;
import seedu.address.model.occasion.OccasionDescriptor;
import seedu.address.model.occasion.UniqueOccasionList;
import seedu.address.model.person.Person;
import seedu.address.model.person.PersonDescriptor;

/**
 * Edits the details of an existing occasion in the address book.
 *
 * @author alistair
 * @author kongzijin
 */
public class EditOccasionCommand extends Command {

    public static final String COMMAND_WORD = "editoccasion";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the occasion identified "
            + "by the index number used in the displayed occasion list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_OCCASIONNAME + "OCCASION NAME] "
            + "[" + PREFIX_OCCASIONDATE + "OCCASION DATE] "
            + "[" + PREFIX_OCCASIONLOCATION + "OCCASION LOCATION] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_OCCASIONNAME + "Barbecue "
            + PREFIX_OCCASIONDATE + "2019-06-17 "
            + PREFIX_OCCASIONLOCATION + "NUS";

    public static final String MESSAGE_EDIT_OCCASION_SUCCESS = "Edited Occasion: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_OCCASION = "This occasion already exists in the address book.";

    private final Index index;
    private final OccasionDescriptor editOccasionDescriptor;

    /**
     * @param index of the occasion in the filtered occasion list to edit
     * @param editOccasionDescriptor details to edit the occasion with
     */
    public EditOccasionCommand(Index index, OccasionDescriptor editOccasionDescriptor) {
        requireNonNull(index);
        requireNonNull(editOccasionDescriptor);

        this.index = index;
        this.editOccasionDescriptor = new OccasionDescriptor(editOccasionDescriptor);
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Occasion> lastShownList = model.getFilteredOccasionList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_OCCASION_DISPLAYED_INDEX);
        }

        Occasion occasionToEdit = lastShownList.get(index.getZeroBased());
        Occasion editedOccasion = createEditedOccasion(occasionToEdit, editOccasionDescriptor);
        removeOccasionFromAssociatedPersons(model, occasionToEdit, editedOccasion);

        if (!occasionToEdit.isSameOccasion(editedOccasion) && model.hasOccasion(editedOccasion)) {
            throw new CommandException(MESSAGE_DUPLICATE_OCCASION);
        }

        model.updateOccasion(occasionToEdit, editedOccasion);
        model.updateFilteredOccasionList(PREDICATE_SHOW_ALL_OCCASIONS);
        model.commitAddressBook();
        EventsCenter.getInstance().post(new ShowOccasionRequestEvent());
        return new CommandResult(String.format(MESSAGE_EDIT_OCCASION_SUCCESS, editedOccasion));
    }


    //Reused from teammate @waytan with minor modifications
    /**
     * Removes a occasion from the occasionList of all associated Persons.
     */
    private void removeOccasionFromAssociatedPersons(Model model, Occasion occasionToEdit, Occasion editedOccasion) {
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        List<Person> completePersonList = model.getFilteredPersonList();
        ListIterator<Person> personListIterator = completePersonList.listIterator();
        while (personListIterator.hasNext()) {
            Person person = personListIterator.next();
            person.getOccasionList()
                    .asNormalList()
                    .stream()
                    .filter(occasion -> occasion.isSameOccasion(occasionToEdit))
                    .findFirst()
                    .ifPresent(editOccasionFromPerson(model, person, editedOccasion));
        }
    }

    //Reused from teammate @waytan with minor modifications
    /**
     * Returns a consumer that takes in a Occasion and removes it from the specified Person in the Model.
     */
    private Consumer<Occasion> editOccasionFromPerson(Model model, Person person, Occasion editOccasion) {
        return occasion -> {
            PersonDescriptor updatedPersonDescriptor = new PersonDescriptor();
            List<Occasion> updatedOccasions = person.getOccasionList().makeShallowDuplicate().asNormalList();
            int indexOfOccasionToEdit = updatedOccasions.indexOf(editOccasion);
            if (indexOfOccasionToEdit != -1) {
                updatedOccasions.remove(occasion);
                updatedOccasions.add(indexOfOccasionToEdit, editOccasion);
                UniqueOccasionList updatedOccasionList = new UniqueOccasionList(updatedOccasions);
                updatedPersonDescriptor.setUniqueOccasionList(updatedOccasionList);
                Person updatedPerson = Person.createEditedPerson(person, updatedPersonDescriptor);
                model.updatePerson(person, updatedPerson);
            }
        };
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditOccasionCommand)) {
            return false;
        }

        // state check
        EditOccasionCommand e = (EditOccasionCommand) other;
        return index.equals(e.index)
                && editOccasionDescriptor.equals(e.editOccasionDescriptor);
    }


}

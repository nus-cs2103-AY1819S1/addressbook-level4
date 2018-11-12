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

/**
 * Unfavourites an exisiting contact
 */

public class UnfavouriteCommand extends Command {

    public static final String COMMAND_WORD = "unfavourite";
    public static final String COMMAND_ALIAS = "unfav";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Removes a person from your favourite contacts "
            + "by the index number used in the last person listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1 ";
    public static final String MESSAGE_UNFAVOURITE_PERSON_FAIL = "Person not in favourites: %1$s";
    public static final String MESSAGE_UNFAVOURITE_PERSON_SUCCESS = "Person removed from favourites: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in SSENISUB.";
    private final Index index;
    /**
     * @param index of the person in the filtered person list to edit
     */
    public UnfavouriteCommand(Index index) {
        requireNonNull(index);
        this.index = index;
    }
    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        List<Person> lastShownList = model.getFilteredPersonList();
        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }
        Person personToUnfavourite = lastShownList.get(index.getZeroBased());
        Person unfavouritedPerson = createFavouritedPerson(personToUnfavourite);

        model.unfavouritePerson(personToUnfavourite, unfavouritedPerson);

        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        model.commitSsenisub();

        if (personToUnfavourite.getFavourite()) {
            return new CommandResult(String.format(MESSAGE_UNFAVOURITE_PERSON_SUCCESS,
                    unfavouritedPerson.getName().fullName));
        } else {
            return new CommandResult(String.format(MESSAGE_UNFAVOURITE_PERSON_FAIL,
                    unfavouritedPerson.getName().fullName));
        }
    }
    /**
     * Creates and returns a {@code Person} with the details of {@code personToFavourite}
     */
    private static Person createFavouritedPerson(Person personToUnfavourite) {
        assert personToUnfavourite != null;

        boolean newFavourite;

        if (personToUnfavourite.getFavourite()) {
            newFavourite = !personToUnfavourite.getFavourite();
        } else {
            newFavourite = personToUnfavourite.getFavourite();
        }

        return new Person(personToUnfavourite.getName(), personToUnfavourite.getPhone(),
                personToUnfavourite.getEmail(), personToUnfavourite.getAddress(), personToUnfavourite.getRating(),
                personToUnfavourite.getDepartment(), personToUnfavourite.getManager(), personToUnfavourite.getSalary(),
                personToUnfavourite.getOtHours(), personToUnfavourite.getOtRate(), personToUnfavourite.getDeductibles(),
                personToUnfavourite.getFeedback(), personToUnfavourite.getTags(), newFavourite);
    }
    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }
        // instanceof handles nulls
        if (!(other instanceof UnfavouriteCommand)) {
            return false;
        }
        // state check
        UnfavouriteCommand e = (UnfavouriteCommand) other;
        return index.equals(e.index);
    }
}

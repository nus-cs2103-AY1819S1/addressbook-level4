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
 * Favourites an exisiting contact
 */

public class FavouriteCommand extends Command {

    public static final String COMMAND_WORD = "favourite";
    public static final String COMMAND_ALIAS = "fav";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a person to your favourite contacts "
            + "by the index number used in the last person listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1 ";
    public static final String MESSAGE_FAVOURITE_PERSON_FAIL = "Person already favourited: %1$s";
    public static final String MESSAGE_FAVOURITE_PERSON_SUCCESS = "Added person to favourites: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in SSENISUB.";
    private final Index index;
    /**
     * @param index of the person in the filtered person list to edit
     */
    public FavouriteCommand(Index index) {
        requireNonNull(index);
        this.index = index;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        List<Person> lastShownList = model.getFilteredPersonList();
        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }
        Person personToFavourite = lastShownList.get(index.getZeroBased());
        Person favouritedPerson = createFavouritedPerson(personToFavourite);

        model.favouritePerson(personToFavourite, favouritedPerson);

        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        model.commitSsenisub();

        if (!personToFavourite.getFavourite()) {
            return new CommandResult(String.format(MESSAGE_FAVOURITE_PERSON_SUCCESS,
                    favouritedPerson.getName().fullName));
        } else {
            return new CommandResult(String.format(MESSAGE_FAVOURITE_PERSON_FAIL,
                    favouritedPerson.getName().fullName));
        }

    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToFavourite}
     */
    private static Person createFavouritedPerson(Person personToFavourite) {
        assert personToFavourite != null;

        boolean newFavourite;

        if (personToFavourite.getFavourite()) {
            newFavourite = personToFavourite.getFavourite();
        } else {
            newFavourite = !personToFavourite.getFavourite();
        }

        return new Person(personToFavourite.getName(), personToFavourite.getPhone(), personToFavourite.getEmail(),
                personToFavourite.getAddress(), personToFavourite.getRating(), personToFavourite.getDepartment(),
                personToFavourite.getManager(), personToFavourite.getSalary(), personToFavourite.getOtHours(),
                personToFavourite.getOtRate(), personToFavourite.getDeductibles(), personToFavourite.getFeedback(),
                personToFavourite.getTags(), newFavourite);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }
        // instanceof handles nulls
        if (!(other instanceof FavouriteCommand)) {
            return false;
        }
        // state check
        FavouriteCommand e = (FavouriteCommand) other;
        return index.equals(e.index);
    }
}

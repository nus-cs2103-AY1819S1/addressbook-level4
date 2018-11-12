package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalSsenisub;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.Ssenisub;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.person.Rating;
import seedu.address.testutil.PersonBuilder;

public class RateCommandTest {
    private Model model = new ModelManager(getTypicalSsenisub(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void equals() {
        RateCommand rateFirstCommand = new RateCommand(INDEX_FIRST_PERSON, new Rating("1"));
        RateCommand rateSecondCommand = new RateCommand(INDEX_SECOND_PERSON, new Rating("10"));

        // same object -> returns true
        assertTrue(rateFirstCommand.equals(rateFirstCommand));

        // same values -> returns true
        RateCommand rateFirstCommandCopy = new RateCommand(INDEX_FIRST_PERSON, new Rating("1"));
        assertTrue(rateFirstCommand.equals(rateFirstCommandCopy));

        // different types -> returns false
        assertFalse(rateFirstCommand.equals(1));

        // null -> returns false
        assertFalse(rateFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(rateFirstCommand.equals(rateSecondCommand));
    }

    @Test
    public void execute_success() {
        Index indexLastPerson = Index.fromOneBased(model.getFilteredPersonList().size());
        Person lastPerson = model.getFilteredPersonList().get(indexLastPerson.getZeroBased());

        PersonBuilder personInList = new PersonBuilder(lastPerson);
        Person editedPerson = personInList.withRating("6").build();

        RateCommand rateCommand = new RateCommand(indexLastPerson, new Rating("6"));

        String expectedMessage = String.format(RateCommand.MESSAGE_RATING_PERSON_SUCCESS, editedPerson);
        Model expectedModel = new ModelManager(new Ssenisub(model.getSsenisub()), new UserPrefs());
        expectedModel.updatePerson(lastPerson, editedPerson);
        expectedModel.commitSsenisub();

        assertCommandSuccess(rateCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    public void execute_invalidPersonIndexFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getSsenisub().getPersonList().size());

        RateCommand rateCommand = new RateCommand(outOfBoundIndex, new Rating("0"));

        assertCommandFailure(rateCommand, model, commandHistory, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

}

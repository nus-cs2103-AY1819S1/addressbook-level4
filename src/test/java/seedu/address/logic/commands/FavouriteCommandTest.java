package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_DATE_MEETING;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.FavouriteCommand.MESSAGE_FAVOURITE_EVENT_SUCCESS;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_EVENT;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventDate;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code FavouriteCommand}.
 */
public class FavouriteCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_validEvent_success() {
        Event favouriteEvent = model.getFilteredEventListByDate().get(0).get(INDEX_FIRST_EVENT.getZeroBased());
        FavouriteCommand favouriteCommand = new FavouriteCommand(new EventDate(VALID_EVENT_DATE_MEETING),
                INDEX_FIRST_EVENT);

        String expectedMessage = String.format(MESSAGE_FAVOURITE_EVENT_SUCCESS
                + favouriteEvent.getEventName() + " on " + favouriteEvent.getEventDate());


        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.updateFavourite("Event Name: " + favouriteEvent.getEventName()
                + "\nEvent Date: " + favouriteEvent.getEventDate() + ", " + favouriteEvent.getEventDay()
                + "\nEvent Time: " + favouriteEvent.getEventStartTime() + " - " + favouriteEvent.getEventEndTime()
                + "\nEvent Details: " + favouriteEvent.getEventDescription());
        expectedModel.commitAddressBook();

        assertCommandSuccess(favouriteCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndex_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredEventListByDate().get(0).size() + 1);
        FavouriteCommand favouriteCommand = new FavouriteCommand(new EventDate(VALID_EVENT_DATE_MEETING),
                outOfBoundIndex);

        assertCommandFailure(favouriteCommand, model, commandHistory, Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);

    }

    @Test
    public void execute_invalidDate_throwsCommandException() {
        EventDate outOfBoundDate = new EventDate("2019-12-31");
        FavouriteCommand favouriteCommand = new FavouriteCommand(outOfBoundDate, INDEX_FIRST_EVENT);

        assertCommandFailure(favouriteCommand, model, commandHistory, Messages.MESSAGE_INVALID_EVENT_DISPLAYED_DATE);
    }
}

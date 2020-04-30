package seedu.restaurant.logic.commands.reservation;

import static seedu.restaurant.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.restaurant.logic.commands.reservation.ReservationCommandTestUtil.showReservationAtIndex;
import static seedu.restaurant.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.restaurant.testutil.TypicalRestaurantBook.getTypicalRestaurantBook;

import org.junit.Before;
import org.junit.Test;

import seedu.restaurant.logic.CommandHistory;
import seedu.restaurant.model.Model;
import seedu.restaurant.model.ModelManager;
import seedu.restaurant.model.UserPrefs;

//@@author m4dkip
/**
 * Contains integration tests (interaction with the Model) and unit tests for ListReservationsCommand.
 */
public class ListReservationsCommandTest {

    private Model model;
    private Model expectedModel;
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalRestaurantBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getRestaurantBook(), new UserPrefs());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(new ListReservationsCommand(), model, commandHistory,
                ListReservationsCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        showReservationAtIndex(model, INDEX_FIRST);
        assertCommandSuccess(new ListReservationsCommand(), model, commandHistory,
                ListReservationsCommand.MESSAGE_SUCCESS, expectedModel);
    }
}

package seedu.restaurant.logic.commands.reservation;

import static seedu.restaurant.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.restaurant.logic.commands.reservation.SortReservationsCommand.MESSAGE_SORTED;
import static seedu.restaurant.testutil.reservation.TypicalReservations.ANDREW;
import static seedu.restaurant.testutil.reservation.TypicalReservations.BILLY;

import org.junit.Test;

import seedu.restaurant.logic.CommandHistory;
import seedu.restaurant.model.Model;
import seedu.restaurant.model.ModelManager;
import seedu.restaurant.model.RestaurantBook;
import seedu.restaurant.model.UserPrefs;
import seedu.restaurant.testutil.RestaurantBookBuilder;

//@@author m4dkip
/**
 * Contains integration tests (interaction with the Model) for {@code SortReservationsCommand}.
 */
public class SortReservationsCommandTest {
    private RestaurantBook ab = new RestaurantBookBuilder().withReservation(BILLY).withReservation(ANDREW).build();
    private Model model = new ModelManager(ab, new UserPrefs());
    private Model expectedModel = new ModelManager(ab, new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_sortReservations() {
        String expectedMessage = String.format(MESSAGE_SORTED);
        SortReservationsCommand command = new SortReservationsCommand();
        expectedModel.sortReservations();
        expectedModel.commitRestaurantBook();
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
    }
}

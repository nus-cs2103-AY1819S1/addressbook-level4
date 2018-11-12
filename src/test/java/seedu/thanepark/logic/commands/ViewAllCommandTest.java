package seedu.thanepark.logic.commands;

import static seedu.thanepark.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.thanepark.logic.commands.CommandTestUtil.showRideAtIndex;
import static seedu.thanepark.logic.commands.ViewAllCommand.MESSAGE_SUCCESS;
import static seedu.thanepark.testutil.TypicalIndexes.INDEX_FIRST_RIDE;
import static seedu.thanepark.testutil.TypicalRides.getTypicalThanePark;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import seedu.thanepark.logic.CommandHistory;
import seedu.thanepark.model.Model;
import seedu.thanepark.model.ModelManager;
import seedu.thanepark.model.UserPrefs;
import seedu.thanepark.model.ride.Ride;
import seedu.thanepark.model.ride.Status;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ViewAllCommand.
 */
public class ViewAllCommandTest {

    private Model model;
    private Model expectedModel;
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalThanePark(), new UserPrefs());
        expectedModel = new ModelManager(model.getThanePark(), new UserPrefs());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        List<Ride> expectedRides = expectedModel.getFilteredRideList();
        int[] statusArray = new int[3];
        statusArray[0] = (int) expectedRides.stream()
                .filter(x -> x.getStatus() == Status.OPEN)
                .count();
        statusArray[1] = (int) expectedRides.stream()
                .filter(x -> x.getStatus() == Status.SHUTDOWN)
                .count();
        statusArray[2] = (int) expectedRides.stream()
                .filter(x -> x.getStatus() == Status.MAINTENANCE)
                .count();
        String expectedMessage = String.format(MESSAGE_SUCCESS,
                expectedModel.getFilteredRideList().size(), statusArray[0], statusArray[1], statusArray[2]);
        assertCommandSuccess(new ViewAllCommand(), model, commandHistory,
                expectedMessage, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        List<Ride> expectedRides = expectedModel.getFilteredRideList();
        int[] statusArray = new int[3];
        statusArray[0] = (int) expectedRides.stream()
                .filter(x -> x.getStatus() == Status.OPEN)
                .count();
        statusArray[1] = (int) expectedRides.stream()
                .filter(x -> x.getStatus() == Status.SHUTDOWN)
                .count();
        statusArray[2] = (int) expectedRides.stream()
                .filter(x -> x.getStatus() == Status.MAINTENANCE)
                .count();
        String expectedMessage = String.format(MESSAGE_SUCCESS,
                expectedModel.getFilteredRideList().size(), statusArray[0], statusArray[1], statusArray[2]);
        showRideAtIndex(model, INDEX_FIRST_RIDE);
        assertCommandSuccess(new ViewAllCommand(), model, commandHistory,
                expectedMessage, expectedModel);
    }
}

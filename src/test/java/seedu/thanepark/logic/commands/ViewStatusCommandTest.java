package seedu.thanepark.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.thanepark.commons.core.Messages.MESSAGE_RIDES_LISTED_OVERVIEW;
import static seedu.thanepark.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.thanepark.testutil.TypicalRides.getTypicalThanePark;

import java.util.Collections;

import org.junit.Test;
import seedu.thanepark.logic.CommandHistory;
import seedu.thanepark.model.Model;
import seedu.thanepark.model.ModelManager;
import seedu.thanepark.model.UserPrefs;
import seedu.thanepark.model.ride.RideStatusPredicate;
import seedu.thanepark.model.ride.Status;

/**
 * Contains integration tests (interaction with the Model) for {@code ViewStatusCommand}.
 */
public class ViewStatusCommandTest {

    private Model model = new ModelManager(getTypicalThanePark(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalThanePark(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void equals() {
        RideStatusPredicate firstPredicate = new RideStatusPredicate(Status.OPEN);
        RideStatusPredicate secondPredicate = new RideStatusPredicate(Status.MAINTENANCE);
        ViewStatusCommand firstCommand = new ViewStatusCommand(firstPredicate);
        ViewStatusCommand secondCommand = new ViewStatusCommand(secondPredicate);
        ViewStatusCommand thirdCommand = new ViewStatusCommand(firstPredicate);

        // same object -> returns true
        assertTrue(firstCommand.equals(firstCommand));

        // same value -> returns true
        assertTrue(firstCommand.equals(thirdCommand));

        // null -> returns false
        assertFalse(firstCommand.equals(null));

        // different type -> returns false
        assertFalse(firstCommand.equals("open"));

        // different status -> returns false
        assertFalse(firstCommand.equals(secondCommand));
    }

    @Test
    public void execute_noRideFound() {
        String expectedMessage = String.format(MESSAGE_RIDES_LISTED_OVERVIEW, 0);
        RideStatusPredicate predicate = new RideStatusPredicate(Status.MAINTENANCE);
        ViewStatusCommand command = new ViewStatusCommand(predicate);
        expectedModel.updateFilteredRideList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredRideList());
    }

}

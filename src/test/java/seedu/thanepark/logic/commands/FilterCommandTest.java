package seedu.thanepark.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.thanepark.commons.core.Messages.MESSAGE_RIDES_LISTED_OVERVIEW;
import static seedu.thanepark.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.thanepark.testutil.TypicalRides.ACCELERATOR;
import static seedu.thanepark.testutil.TypicalRides.BIG;
import static seedu.thanepark.testutil.TypicalRides.CASTLE;
import static seedu.thanepark.testutil.TypicalRides.DUMBO;
import static seedu.thanepark.testutil.TypicalRides.FANTASY;
import static seedu.thanepark.testutil.TypicalRides.GALAXY;
import static seedu.thanepark.testutil.TypicalRides.getTypicalThanePark;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.thanepark.logic.CommandHistory;
import seedu.thanepark.model.Model;
import seedu.thanepark.model.ModelManager;
import seedu.thanepark.model.UserPrefs;
import seedu.thanepark.model.ride.AttributePredicate;
import seedu.thanepark.model.ride.Maintenance;
import seedu.thanepark.model.ride.RideContainsConditionPredicate;
import seedu.thanepark.model.ride.WaitTime;

/**
 * Contains integration tests (interaction with the Model) for {@code FilterCommand}.
 */
public class FilterCommandTest {
    private Model model = new ModelManager(getTypicalThanePark(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalThanePark(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void equals() {
        RideContainsConditionPredicate firstPredicate =
                new RideContainsConditionPredicate(List.of(new AttributePredicate("<",
                        new Maintenance("100"))));
        RideContainsConditionPredicate secondPredicate =
                new RideContainsConditionPredicate(List.of(new AttributePredicate("<=",
                        new WaitTime("100"))));

        FilterCommand filterFirstCommand = new FilterCommand(firstPredicate);
        FilterCommand filterSecondCommand = new FilterCommand(secondPredicate);

        // same object -> returns true
        assertTrue(filterFirstCommand.equals(filterFirstCommand));

        // same values -> return true
        FilterCommand filterFirstCommandCopy = new FilterCommand(firstPredicate);
        assertTrue(filterFirstCommand.equals(filterFirstCommandCopy));

        // different types -> returns false
        assertFalse(filterFirstCommand.equals(1));

        // null -> returns false
        assertFalse(filterFirstCommand.equals(null));

        // different predicate -> returns false
        assertFalse(filterFirstCommand.equals(filterSecondCommand));
    }

    @Test
    public void execute_zeroPredicates_noRideFound() {
        String expectedMessage = String.format(MESSAGE_RIDES_LISTED_OVERVIEW, 0);
        RideContainsConditionPredicate predicate =
                new RideContainsConditionPredicate(List.of(new AttributePredicate(" ", new Maintenance("0"))));
        FilterCommand command = new FilterCommand(predicate);
        expectedModel.updateFilteredRideList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredRideList());

        predicate = new RideContainsConditionPredicate(List.of(new AttributePredicate(" ", new WaitTime("0"))));
        command = new FilterCommand(predicate);
        expectedModel.updateFilteredRideList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredRideList());
    }

    @Test
    public void execute_singlePredicate_multipleRidesFound() {
        String expectedMessage = String.format(MESSAGE_RIDES_LISTED_OVERVIEW, 3);
        RideContainsConditionPredicate predicate =
                new RideContainsConditionPredicate(Arrays.asList(prepareMaintenancePredicate(">=", "15")));
        FilterCommand command = new FilterCommand(predicate);
        expectedModel.updateFilteredRideList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(BIG, DUMBO, GALAXY), model.getFilteredRideList());

        expectedMessage = String.format(MESSAGE_RIDES_LISTED_OVERVIEW, 5);
        predicate = new RideContainsConditionPredicate(Arrays.asList(prepareWaitTimePredicate("<", "15")));
        command = new FilterCommand(predicate);
        expectedModel.updateFilteredRideList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ACCELERATOR, CASTLE, DUMBO, FANTASY, GALAXY), model.getFilteredRideList());
    }

    @Test
    public void execute_multiplePredicates_multipleRidesFound() {
        String expectedMessage = String.format(MESSAGE_RIDES_LISTED_OVERVIEW, 2);
        RideContainsConditionPredicate predicate =
                new RideContainsConditionPredicate(Arrays.asList(prepareMaintenancePredicate(">", "15"),
                        prepareMaintenancePredicate("<=", "50")));
        FilterCommand command = new FilterCommand(predicate);
        expectedModel.updateFilteredRideList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(BIG, GALAXY), model.getFilteredRideList());

        expectedMessage = String.format(MESSAGE_RIDES_LISTED_OVERVIEW, 2);
        predicate = new RideContainsConditionPredicate(Arrays.asList(prepareWaitTimePredicate("<=", "15"),
                prepareWaitTimePredicate(">", "5")));
        command = new FilterCommand(predicate);
        expectedModel.updateFilteredRideList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(CASTLE, FANTASY), model.getFilteredRideList());

        expectedMessage = String.format(MESSAGE_RIDES_LISTED_OVERVIEW, 2);
        predicate = new RideContainsConditionPredicate(Arrays.asList(prepareWaitTimePredicate("<=", "15"),
                prepareMaintenancePredicate(">=", "15")));
        command = new FilterCommand(predicate);
        expectedModel.updateFilteredRideList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(DUMBO, GALAXY), model.getFilteredRideList());
    }

    private AttributePredicate prepareMaintenancePredicate(String s, String value) {
        return new AttributePredicate(s, new Maintenance(value));
    }

    private AttributePredicate prepareWaitTimePredicate(String s, String value) {
        return new AttributePredicate(s, new WaitTime(value));
    }
}

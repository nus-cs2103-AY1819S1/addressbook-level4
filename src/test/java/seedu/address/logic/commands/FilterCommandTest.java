package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_RIDES_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalRides.ACCELERATOR;
import static seedu.address.testutil.TypicalRides.BIG;
import static seedu.address.testutil.TypicalRides.CASTLE;
import static seedu.address.testutil.TypicalRides.DUMBO;
import static seedu.address.testutil.TypicalRides.FANTASY;
import static seedu.address.testutil.TypicalRides.GALAXY;
import static seedu.address.testutil.TypicalRides.getTypicalThanePark;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.ride.AttributePredicate;
import seedu.address.model.ride.Maintenance;
import seedu.address.model.ride.RideContainsConditionPredicate;
import seedu.address.model.ride.WaitTime;

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
                new RideContainsConditionPredicate(List.of(new AttributePredicate('<',
                        new Maintenance("100"))));
        RideContainsConditionPredicate secondPredicate =
                new RideContainsConditionPredicate(List.of(new AttributePredicate('<',
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
                new RideContainsConditionPredicate(List.of(new AttributePredicate(' ', new Maintenance("0"))));
        FilterCommand command = new FilterCommand(predicate);
        expectedModel.updateFilteredRideList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredRideList());

        predicate = new RideContainsConditionPredicate(List.of(new AttributePredicate(' ', new WaitTime("0"))));
        command = new FilterCommand(predicate);
        expectedModel.updateFilteredRideList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredRideList());
    }

    @Test
    public void execute_singlePredicate_multipleRidesFound() {
        String expectedMessage = String.format(MESSAGE_RIDES_LISTED_OVERVIEW, 3);
        RideContainsConditionPredicate predicate =
                new RideContainsConditionPredicate(Arrays.asList(prepareMaintenancePredicate('>', "15")));
        FilterCommand command = new FilterCommand(predicate);
        expectedModel.updateFilteredRideList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(BIG, DUMBO, GALAXY), model.getFilteredRideList());

        expectedMessage = String.format(MESSAGE_RIDES_LISTED_OVERVIEW, 5);
        predicate = new RideContainsConditionPredicate(Arrays.asList(prepareWaitTimePredicate('<', "15")));
        command = new FilterCommand(predicate);
        expectedModel.updateFilteredRideList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ACCELERATOR, CASTLE, DUMBO, FANTASY, GALAXY), model.getFilteredRideList());
    }

    @Test
    public void execute_multiplePredicates_multipleRidesFound() {
        String expectedMessage = String.format(MESSAGE_RIDES_LISTED_OVERVIEW, 2);
        RideContainsConditionPredicate predicate =
                new RideContainsConditionPredicate(Arrays.asList(prepareMaintenancePredicate('>', "15"),
                        prepareMaintenancePredicate('<', "50")));
        FilterCommand command = new FilterCommand(predicate);
        expectedModel.updateFilteredRideList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(BIG, GALAXY), model.getFilteredRideList());

        expectedMessage = String.format(MESSAGE_RIDES_LISTED_OVERVIEW, 2);
        predicate = new RideContainsConditionPredicate(Arrays.asList(prepareWaitTimePredicate('<', "15"),
                prepareWaitTimePredicate('>', "5")));
        command = new FilterCommand(predicate);
        expectedModel.updateFilteredRideList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(CASTLE, FANTASY), model.getFilteredRideList());

        expectedMessage = String.format(MESSAGE_RIDES_LISTED_OVERVIEW, 2);
        predicate = new RideContainsConditionPredicate(Arrays.asList(prepareWaitTimePredicate('<', "15"),
                prepareMaintenancePredicate('>', "15")));
        command = new FilterCommand(predicate);
        expectedModel.updateFilteredRideList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(DUMBO, GALAXY), model.getFilteredRideList());
    }

    private AttributePredicate prepareMaintenancePredicate(char c, String value) {
        return new AttributePredicate(c, new Maintenance(value));
    }

    private AttributePredicate prepareWaitTimePredicate(char c, String value) {
        return new AttributePredicate(c, new WaitTime(value));
    }
}

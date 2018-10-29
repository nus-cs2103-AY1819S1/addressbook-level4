package seedu.thanepark.logic.commands;

import static org.junit.Assert.assertEquals;
import static seedu.thanepark.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.thanepark.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.thanepark.logic.commands.CommandTestUtil.deleteFirstPerson;
import static seedu.thanepark.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.thanepark.testutil.TypicalRides.getTypicalThanePark;

import org.junit.Test;

import seedu.thanepark.logic.CommandHistory;
import seedu.thanepark.logic.commands.UpdateCommand.UpdateRideDescriptor;
import seedu.thanepark.model.Model;
import seedu.thanepark.model.ModelManager;
import seedu.thanepark.model.ThanePark;
import seedu.thanepark.model.UserPrefs;
import seedu.thanepark.model.ride.Ride;
import seedu.thanepark.testutil.RideBuilder;
import seedu.thanepark.testutil.UpdateRideDescriptorBuilder;

public class UndoCommandTest {

    private final Model model = new ModelManager(getTypicalThanePark(), new UserPrefs());
    private final Model expectedModel = new ModelManager(getTypicalThanePark(), new UserPrefs());
    private final CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute() {
        //setup
        deleteFirstPerson(model);
        deleteFirstPerson(model);

        deleteFirstPerson(expectedModel);
        deleteFirstPerson(expectedModel);

        // multiple undoable states in model
        expectedModel.undoThanePark();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // single undoable state in model
        expectedModel.undoThanePark();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // no undoable states in model
        assertCommandFailure(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_FAILURE);
    }

    @Test
    public void undoRedoCommandsAreIncluded() throws Exception {
        //includes add, clear, delete and edit
        CommandHistory commandHistory = new CommandHistory();
        int total = 0;

        Ride validRide = new RideBuilder().buildDifferent();
        new AddCommand(validRide).execute(model, commandHistory);
        expectedModel.addRide(validRide);
        expectedModel.commitThanePark();
        assertEquals(expectedModel, model);
        total++;

        Ride rideToDelete = model.getFilteredRideList().get(INDEX_FIRST_PERSON.getZeroBased());
        new DeleteCommand(INDEX_FIRST_PERSON).execute(model, commandHistory);
        expectedModel.deleteRide(rideToDelete);
        expectedModel.commitThanePark();
        assertEquals(expectedModel, model);
        total++;

        //Change some ride back to the deleted one
        Ride editedRide = rideToDelete;
        UpdateRideDescriptor descriptor = new UpdateRideDescriptorBuilder(editedRide).build();
        expectedModel.updateRide(model.getFilteredRideList().get(0), editedRide);
        new UpdateCommand(INDEX_FIRST_PERSON, descriptor).execute(model, commandHistory);
        expectedModel.commitThanePark();
        assertEquals(expectedModel, model);
        total++;

        expectedModel.resetData(new ThanePark());
        expectedModel.commitThanePark();
        new ClearCommand().execute(model, commandHistory);
        assertEquals(expectedModel, model);
        total++;

        for (int i = 0; i < total; i++) {
            expectedModel.undoThanePark();
            assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);
        }

        for (int i = 0; i < total; i++) {
            expectedModel.redoThanePark();
            assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
        }
    }

}

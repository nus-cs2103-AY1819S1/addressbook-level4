package seedu.thanepark.logic.commands;

import static seedu.thanepark.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.thanepark.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.thanepark.testutil.TypicalRides.getTypicalThanePark;

import org.junit.Before;
import org.junit.Test;
import seedu.thanepark.logic.CommandHistory;
import seedu.thanepark.model.Model;
import seedu.thanepark.model.ModelManager;
import seedu.thanepark.model.UserPrefs;
import seedu.thanepark.model.ride.Ride;
import seedu.thanepark.testutil.RideBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddCommand}.
 */
public class AddCommandIntegrationTest {

    private Model model;
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalThanePark(), new UserPrefs());
    }

    @Test
    public void execute_newPerson_success() {
        Ride validRide = new RideBuilder().buildDifferent();

        Model expectedModel = new ModelManager(model.getThanePark(), new UserPrefs());
        expectedModel.addRide(validRide);
        expectedModel.commitThanePark();

        assertCommandSuccess(new AddCommand(validRide), model, commandHistory,
                String.format(AddCommand.MESSAGE_SUCCESS, validRide), expectedModel);
    }

    @Test
    public void execute_duplicateRideSameName_throwsCommandException() {
        Ride sameNameRide = new RideBuilder().build();
        assertCommandFailure(new AddCommand(sameNameRide), model, commandHistory,
                AddCommand.MESSAGE_DUPLICATE_RIDE);
    }

    @Test
    public void execute_duplicatePerson_throwsCommandException() {
        Ride rideInList = model.getThanePark().getRideList().get(0);
        assertCommandFailure(new AddCommand(rideInList), model, commandHistory,
                AddCommand.MESSAGE_DUPLICATE_RIDE);
    }

}

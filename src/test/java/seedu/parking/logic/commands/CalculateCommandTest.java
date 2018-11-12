package seedu.parking.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.parking.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.parking.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.parking.testutil.TypicalCarparks.getTypicalCarparkFinder;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;

import seedu.parking.commons.core.Messages;
import seedu.parking.logic.CommandHistory;
import seedu.parking.model.Model;
import seedu.parking.model.ModelManager;
import seedu.parking.model.UserPrefs;
import seedu.parking.model.carpark.CarparkIsOfNumberPredicate;

public class CalculateCommandTest {
    private Model model = new ModelManager(getTypicalCarparkFinder(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalCarparkFinder(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void equals() {

        Date inputStartOne = null;
        Date inputEndOne = null;
        Date inputStartTwo = null;
        Date inputEndTwo = null;
        try {
            SimpleDateFormat dateFormat1 = new SimpleDateFormat("hh.mmaa");
            inputStartOne = dateFormat1.parse("9.30am");
            inputEndOne = dateFormat1.parse("10.30am");
            inputStartTwo = dateFormat1.parse("9.30am");
            inputEndTwo = dateFormat1.parse("10.30am");

        } catch (java.text.ParseException pe) {
            System.out.println("Parse exception");
        }

        CalculateCommand firstCommand = new CalculateCommand("TJ39", "SUN", inputStartOne, inputEndOne);
        CalculateCommand secondCommand = new CalculateCommand("W20", "MON", inputStartTwo, inputEndTwo);

        // same object -> returns true
        assertTrue(firstCommand.equals(firstCommand));

        // same values -> returns true
        CalculateCommand firstCommandCopy = new CalculateCommand("TJ39", "SUN", inputStartOne,
                inputEndOne);
        assertTrue(firstCommand.equals(firstCommandCopy));

        // different carpark -> returns false
        assertFalse(firstCommand.equals(secondCommand));
    }

    @Test
    public void execute() { // calculate SK88 mon 9.30am 5.00pm

        Date inputStart = null;
        Date inputEnd = null;
        try {
            SimpleDateFormat dateFormat1 = new SimpleDateFormat("hh.mmaa");
            inputStart = dateFormat1.parse("9.30am");
            inputEnd = dateFormat1.parse("5.00pm");

        } catch (java.text.ParseException pe) {
            System.out.println("Parse exception");
        }

        CarparkIsOfNumberPredicate predicate = new CarparkIsOfNumberPredicate("SK88");
        expectedModel.updateFilteredCarparkList(predicate);

        String expectedMessage = String.format(Messages.MESSAGE_COST_OF_PARKING, 9.00);
        CalculateCommand command = new CalculateCommand("SK88", "MON", inputStart,
                inputEnd);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidCarparkName_throwsCommandException() {

        Date inputStart = null;
        Date inputEnd = null;
        try {
            SimpleDateFormat dateFormat1 = new SimpleDateFormat("hh.mmaa");
            inputStart = dateFormat1.parse("9.30am");
            inputEnd = dateFormat1.parse("5.00pm");

        } catch (java.text.ParseException pe) {
            System.out.println("Parse exception");
        }

        model.updateFilteredCarparkList(new CarparkIsOfNumberPredicate("S88"));
        String expectedMessage = Messages.MESSAGE_INVALID_CARPARK_NAME;
        CalculateCommand command = new CalculateCommand("S88", "MON", inputStart,
                inputEnd);
        assertCommandFailure(command, model, commandHistory, expectedMessage);
    }

    @Test
    public void execute_noShortTermParking_throwsCommandException() {

        Date inputStart = null;
        Date inputEnd = null;
        try {
            SimpleDateFormat dateFormat1 = new SimpleDateFormat("hh.mmaa");
            inputStart = dateFormat1.parse("9.30am");
            inputEnd = dateFormat1.parse("5.00pm");

        } catch (java.text.ParseException pe) {
            System.out.println("Parse exception");
        }

        model.updateFilteredCarparkList(new CarparkIsOfNumberPredicate("PP5"));
        String expectedMessage = Messages.MESSAGE_NO_SHORT_TERM_PARKING;
        CalculateCommand command = new CalculateCommand("PP5", "MON", inputStart,
                inputEnd);
        assertCommandFailure(command, model, commandHistory, expectedMessage);
    }

    @Test
    public void execute_invalidStartOrEndTime_throwsCommandException() {

        Date inputStart = null;
        Date inputEnd = null;
        try {
            SimpleDateFormat dateFormat1 = new SimpleDateFormat("hh.mmaa");
            inputStart = dateFormat1.parse("6.30am");
            inputEnd = dateFormat1.parse("5.00pm");

        } catch (java.text.ParseException pe) {
            System.out.println("Parse exception");
        }

        model.updateFilteredCarparkList(new CarparkIsOfNumberPredicate("SE39"));
        String expectedMessage = Messages.MESSAGE_INVALID_START_OR_END_TIME;
        CalculateCommand command = new CalculateCommand("SE39", "MON", inputStart,
                inputEnd);
        assertCommandFailure(command, model, commandHistory, expectedMessage);
    }
}

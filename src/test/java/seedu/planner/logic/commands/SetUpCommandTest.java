package seedu.planner.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.planner.logic.commands.CommandTestUtil.INVALID_FOCUS_AREA_LD;
import static seedu.planner.logic.commands.CommandTestUtil.INVALID_MAJOR_SS;
import static seedu.planner.logic.commands.CommandTestUtil.VALID_FOCUS_AREA_AI;
import static seedu.planner.logic.commands.CommandTestUtil.VALID_FOCUS_AREA_SE;
import static seedu.planner.logic.commands.CommandTestUtil.VALID_MAJOR_CS;
import static seedu.planner.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.planner.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.planner.logic.commands.SetUpCommand.MESSAGE_SET_UP_SUCCESS;

import java.util.Set;

import org.junit.Test;

import seedu.planner.logic.CommandHistory;
import seedu.planner.model.Model;
import seedu.planner.model.ModelManager;
import seedu.planner.model.course.FocusArea;
import seedu.planner.model.course.Major;

public class SetUpCommandTest {

    private Model model = new ModelManager();
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_validArgs_success() {
        SetUpCommand setUpCommand = new SetUpCommand(VALID_MAJOR_CS, Set.of(VALID_FOCUS_AREA_SE));

        String expectedMessage = String.format(MESSAGE_SET_UP_SUCCESS, VALID_MAJOR_CS, VALID_FOCUS_AREA_SE);
        Model expectedModel = new ModelManager();
        expectedModel.setUpUserProfile(Major.COMPUTER_SCIENCE, Set.of(FocusArea.SOFTWARE_ENGINEERING));

        assertCommandSuccess(setUpCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validArgsMultipleFocusAreas_success() {
        SetUpCommand setUpCommand = new SetUpCommand(VALID_MAJOR_CS, Set.of(VALID_FOCUS_AREA_SE, VALID_FOCUS_AREA_AI));

        String expectedMessage = String.format(MESSAGE_SET_UP_SUCCESS, VALID_MAJOR_CS,
                VALID_FOCUS_AREA_AI + " " + VALID_FOCUS_AREA_SE);
        Model expectedModel = new ModelManager();
        expectedModel.setUpUserProfile(Major.COMPUTER_SCIENCE,
                Set.of(FocusArea.SOFTWARE_ENGINEERING, FocusArea.ARTIFICIAL_INTELLIGENCE));

        assertCommandSuccess(setUpCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validArgsNoFocusAreas_success() {
        SetUpCommand setUpCommand = new SetUpCommand(VALID_MAJOR_CS, Set.of());

        String expectedMessage = String.format(MESSAGE_SET_UP_SUCCESS, VALID_MAJOR_CS, "");
        Model expectedModel = new ModelManager();
        expectedModel.setUpUserProfile(Major.COMPUTER_SCIENCE, Set.of());

        assertCommandSuccess(setUpCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidMajor_throwsCommandException() {
        SetUpCommand setUpCommand = new SetUpCommand(INVALID_MAJOR_SS, Set.of(VALID_FOCUS_AREA_SE));

        String expectedMessage = String.format(SetUpCommand.MESSAGE_INVALID_MAJOR, INVALID_MAJOR_SS);

        assertCommandFailure(setUpCommand, model, commandHistory, expectedMessage);
    }

    @Test
    public void execute_invalidFocusAreas_throwsCommandException() {
        SetUpCommand setUpCommand = new SetUpCommand(VALID_MAJOR_CS, Set.of(INVALID_FOCUS_AREA_LD));

        String expectedMessage = String.format(SetUpCommand.MESSAGE_INVALID_FOCUS_AREAS);

        assertCommandFailure(setUpCommand, model, commandHistory, expectedMessage);
    }

    @Test
    public void execute_invalidAndValidFocusAreas_throwsCommandException() {
        // If there are some invalid and valid focus areas, invalid focus areas will be ignored.
        SetUpCommand setUpCommand = new SetUpCommand(VALID_MAJOR_CS,
                Set.of(INVALID_FOCUS_AREA_LD, VALID_FOCUS_AREA_SE));

        String expectedMessage = String.format(SetUpCommand.MESSAGE_SET_UP_SUCCESS,
                Major.COMPUTER_SCIENCE, FocusArea.SOFTWARE_ENGINEERING);
        Model expectedModel = new ModelManager();
        expectedModel.setUpUserProfile(Major.COMPUTER_SCIENCE, Set.of(FocusArea.SOFTWARE_ENGINEERING));

        assertCommandSuccess(setUpCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateFocusAreas_throwsCommandException() {
        // Make sure we can handle multiple focus area strings in different casing but refer to the same focus area.
        SetUpCommand setUpCommand = new SetUpCommand(VALID_MAJOR_CS,
                Set.of(VALID_FOCUS_AREA_SE, VALID_FOCUS_AREA_SE.toLowerCase(), VALID_FOCUS_AREA_SE.toUpperCase()));

        String expectedMessage = String.format(SetUpCommand.MESSAGE_SET_UP_SUCCESS,
                Major.COMPUTER_SCIENCE, FocusArea.SOFTWARE_ENGINEERING);
        Model expectedModel = new ModelManager();
        expectedModel.setUpUserProfile(Major.COMPUTER_SCIENCE, Set.of(FocusArea.SOFTWARE_ENGINEERING));

        assertCommandSuccess(setUpCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidCombined_throwsCommandException() {
        SetUpCommand setUpCommand = new SetUpCommand(INVALID_MAJOR_SS, Set.of(INVALID_FOCUS_AREA_LD));

        String expectedMessage = String.format(String.format(SetUpCommand.MESSAGE_INVALID_MAJOR, INVALID_MAJOR_SS)
            + String.format(SetUpCommand.MESSAGE_INVALID_FOCUS_AREAS));

        assertCommandFailure(setUpCommand, model, commandHistory, expectedMessage);
    }

    @Test
    public void equals() {
        SetUpCommand setUpCommand = new SetUpCommand(VALID_MAJOR_CS, Set.of(VALID_FOCUS_AREA_SE));

        // same object -> returns true
        assertTrue(setUpCommand.equals(setUpCommand));

        // same major and focus area -> returns true
        assertTrue(setUpCommand.equals(new SetUpCommand(VALID_MAJOR_CS, Set.of(VALID_FOCUS_AREA_SE))));

        // different focus area -> returns false
        assertFalse(setUpCommand.equals(new SetUpCommand(VALID_MAJOR_CS, Set.of(VALID_FOCUS_AREA_AI))));

        // null -> returns false
        assertFalse(setUpCommand.equals(null));

        // different type -> returns false
        assertFalse(setUpCommand.equals(5));
    }
}

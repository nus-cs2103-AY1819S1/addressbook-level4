package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalTasks.getTypicalTaskManager;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.achievement.AchievementRecord;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code AchievementsCommand}.
 */
public class AchievementsCommandTest {
    private Model model = new ModelManager(getTypicalTaskManager(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void executePrimitive_validThisWeekOption_success() {

        // achievements command set the display option of achievement record to be 3 (this week)
        AchievementsCommand achievementsCommand = new AchievementsCommand(AchievementsCommand.THIS_WEEK_OPTION);
        String expectedMessage = String.format(AchievementsCommand.MESSAGE_DISPLAY_ACHIEVEMENT_SUCCESS,
                AchievementsCommand.THIS_WEEK_OPTION);

        ModelManager expectedModel = new ModelManager(model.getTaskManager(), new UserPrefs());
        expectedModel.updateAchievementDisplayOption(AchievementRecord.DISPLAY_THIS_WEEK);
        expectedModel.commitTaskManager();
        assertCommandSuccess(achievementsCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void executePrimitive_validTodayOption_success() {

        // achievements command set the display option of achievement record to be 2 (today)
        AchievementsCommand achievementsCommand = new AchievementsCommand(AchievementsCommand.TODAY_OPTION);
        String expectedMessage = String.format(AchievementsCommand.MESSAGE_DISPLAY_ACHIEVEMENT_SUCCESS,
                AchievementsCommand.TODAY_OPTION);

        ModelManager expectedModel = new ModelManager(model.getTaskManager(), new UserPrefs());
        expectedModel.updateAchievementDisplayOption(AchievementRecord.DISPLAY_TODAY);
        expectedModel.commitTaskManager();
        assertCommandSuccess(achievementsCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void executePrimitive_validAllTimeOption_success() {

        // achievements command set the display option of achievement record to be 1 (all-time)
        AchievementsCommand achievementsCommand = new AchievementsCommand(AchievementsCommand.ALL_TIME_OPTION);
        String expectedMessage = String.format(AchievementsCommand.MESSAGE_DISPLAY_ACHIEVEMENT_SUCCESS,
                AchievementsCommand.ALL_TIME_OPTION);

        ModelManager expectedModel = new ModelManager(model.getTaskManager(), new UserPrefs());
        expectedModel.updateAchievementDisplayOption(AchievementRecord.DISPLAY_ALL_TIME);
        expectedModel.commitTaskManager();
        assertCommandSuccess(achievementsCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void executeUndoRedo() {
        AchievementsCommand achievementsCommand = new AchievementsCommand(AchievementsCommand.TODAY_OPTION);
        String expectedMessage = String.format(AchievementsCommand.MESSAGE_DISPLAY_ACHIEVEMENT_SUCCESS,
                AchievementsCommand.TODAY_OPTION);

        ModelManager expectedModel = new ModelManager(model.getTaskManager(), new UserPrefs());
        expectedModel.updateAchievementDisplayOption(AchievementRecord.DISPLAY_TODAY);
        expectedModel.commitTaskManager();

        // achievements command set display option to 2 (today)
        achievementsCommand.executePrimitive(model, commandHistory);

        // undo -> reverts task manager to previous state and display option of the achievement record to 1 (all-time)
        expectedModel.undoTaskManager();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> achievement record display option set to 2 (today) again
        expectedModel.redoTaskManager();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void equals() {
        AchievementsCommand displayAllTimeCommand = new AchievementsCommand(AchievementsCommand.ALL_TIME_OPTION);
        AchievementsCommand displayTodayCommand = new AchievementsCommand(AchievementsCommand.TODAY_OPTION);

        // same object -> returns true
        assertTrue(displayAllTimeCommand.equals(displayAllTimeCommand));

        // same value -> returns true
        AchievementsCommand displayAllTimeCommandCopy = new AchievementsCommand(AchievementsCommand.ALL_TIME_OPTION);
        assertTrue(displayAllTimeCommand.equals(displayAllTimeCommandCopy));

        // different types -> returns false
        assertFalse(displayAllTimeCommand.equals(1));

        // null -> returns false
        assertFalse(displayAllTimeCommand.equals(null));

        // different value -> returns false
        assertFalse(displayAllTimeCommand.equals(displayTodayCommand));
    }
}

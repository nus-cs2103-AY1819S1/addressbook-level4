package seedu.superta.logic.commands;

import static seedu.superta.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.superta.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.superta.testutil.TypicalSuperTaClient.getTypicalSuperTaClient;

import org.junit.Test;

import seedu.superta.logic.CommandHistory;
import seedu.superta.model.Model;
import seedu.superta.model.ModelManager;
import seedu.superta.model.UserPrefs;
import seedu.superta.model.attendance.Session;
import seedu.superta.model.tutorialgroup.TutorialGroup;
import seedu.superta.testutil.Assert;

// @@author Caephler
public class ViewSessionTest {
    private Model model = new ModelManager(getTypicalSuperTaClient(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalSuperTaClient(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    private final Session lab1 = new Session("Lab 1");

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertNullPointerExceptionThrown(() -> new ViewSessionCommand(null, null));
        assertNullPointerExceptionThrown(() -> new ViewSessionCommand(null, "Lab 1"));
        assertNullPointerExceptionThrown(() -> new ViewSessionCommand("04a", null));
    }

    @Test
    public void viewSession_success() {
        addSession("04a", "Lab 1");
        ViewSessionCommand command = new ViewSessionCommand("04a", "Lab 1");
        String expectedMessage = String.format(ViewSessionCommand.MESSAGE_SUCCESS, lab1);
        assertCommandSuccess(command, model, commandHistory, expectedMessage);
    }

    @Test
    public void viewSession_failure() {
        ViewSessionCommand command = new ViewSessionCommand("04a", "Lab 1");
        // Tutorial Group not present
        String expectedMessage = String.format(ViewSessionCommand.MESSAGE_NO_SUCH_TUTORIAL_GROUP);
        assertCommandFailure(command, model, commandHistory, expectedMessage);

        // Session not present
        addTutorialGroup("04a");
        expectedMessage = ViewSessionCommand.MESSAGE_NO_SUCH_SESSION;
        command = new ViewSessionCommand("04a", "Lab 1");
        assertCommandFailure(command, model, commandHistory, expectedMessage);

    }

    private void addSession(String tutorialGroupId, String name) {
        TutorialGroup tutorialGroup = new TutorialGroup(tutorialGroupId, "test");
        tutorialGroup.createAttendanceSession(new Session(name));
        model.addTutorialGroup(tutorialGroup);
    }

    private void addTutorialGroup(String tutorialGroupId) {
        model.addTutorialGroup(new TutorialGroup(tutorialGroupId, "test"));
    }

    private void assertNullPointerExceptionThrown(Assert.VoidCallable callable) {
        Assert.assertThrows(NullPointerException.class, callable);
    }
}

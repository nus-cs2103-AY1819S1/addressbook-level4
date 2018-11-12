package seedu.superta.logic.commands;

import static org.junit.Assert.assertEquals;
import static seedu.superta.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.superta.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.superta.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.superta.testutil.TypicalSuperTaClient.getTypicalSuperTaClient;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.superta.logic.CommandHistory;
import seedu.superta.model.Model;
import seedu.superta.model.ModelManager;
import seedu.superta.model.UserPrefs;
import seedu.superta.model.student.Student;
import seedu.superta.model.tutorialgroup.TutorialGroup;
import seedu.superta.testutil.TutorialGroupBuilder;

public class RemoveStudentFromTutorialGroupCommandTest {

    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();

    private Model model = new ModelManager(getTypicalSuperTaClient(), new UserPrefs());

    private final String typicalTutorialGroupId = "testing_purposes_only";

    @Test
    public void constructor_nullTutorialGroupId_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new RemoveStudentFromTutorialGroupCommand(null,
            model.getFilteredStudentList().get(INDEX_FIRST_PERSON.getZeroBased()).getStudentId());
    }

    @Test
    public void constructor_nullStudentId_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        TutorialGroup tutorialGroup = new TutorialGroupBuilder().build();
        new RemoveStudentFromTutorialGroupCommand(tutorialGroup.getId(), null);
    }

    @Test
    public void execute_studentRemoveFromTutorialGroupSuccessful() throws Exception {
        TutorialGroup validTutorialGroup = new TutorialGroupBuilder().build();
        model.addTutorialGroup(validTutorialGroup);
        Student studentToRemove = model.getFilteredStudentList().get(INDEX_SECOND_PERSON.getZeroBased());
        validTutorialGroup.addStudent(studentToRemove);

        CommandResult commandResult = new RemoveStudentFromTutorialGroupCommand(
            validTutorialGroup.getId(), studentToRemove.getStudentId()).execute(model, commandHistory);

        assertEquals(RemoveStudentFromTutorialGroupCommand.MESSAGE_SUCCESS, commandResult.feedbackToUser);
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);
    }

    @Test
    public void execute_studentRemoveFromInvalidTutorialGroup_unsuccessful() throws Exception {
        TutorialGroup validTutorialGroup = new TutorialGroupBuilder().build();
        model.addTutorialGroup(validTutorialGroup);
        Student studentToRemove = model.getFilteredStudentList().get(INDEX_SECOND_PERSON.getZeroBased());

        RemoveStudentFromTutorialGroupCommand removeStudentFromTutorialGroupCommand =
            new RemoveStudentFromTutorialGroupCommand(typicalTutorialGroupId, studentToRemove.getStudentId());

        assertCommandFailure(removeStudentFromTutorialGroupCommand, model, commandHistory,
            "No such tutorial group.");
    }

    @Test
    public void execute_invalidStudentRemoveFromTutorialGroup_unsuccessful() throws Exception {
        TutorialGroup validTutorialGroup = new TutorialGroupBuilder().build();
        model.addTutorialGroup(validTutorialGroup);
        Student studentToRemove = model.getFilteredStudentList().get(INDEX_SECOND_PERSON.getZeroBased());

        RemoveStudentFromTutorialGroupCommand removeStudentFromTutorialGroupCommand =
            new RemoveStudentFromTutorialGroupCommand(validTutorialGroup.getId(), studentToRemove.getStudentId());

        assertCommandFailure(removeStudentFromTutorialGroupCommand, model, commandHistory,
            "No such student.");
    }
}


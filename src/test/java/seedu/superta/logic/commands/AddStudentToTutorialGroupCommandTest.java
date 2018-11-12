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
import seedu.superta.model.student.StudentId;
import seedu.superta.model.tutorialgroup.TutorialGroup;
import seedu.superta.testutil.TutorialGroupBuilder;

public class AddStudentToTutorialGroupCommandTest {

    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();

    private Model model = new ModelManager(getTypicalSuperTaClient(), new UserPrefs());

    private final String typicalTutorialGroupId = "testing_purposes_only";

    @Test
    public void constructor_nullTutorialGroupId_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AddStudentToTutorialGroupCommand(null,
                model.getFilteredStudentList().get(INDEX_FIRST_PERSON.getZeroBased()).getStudentId());
    }

    @Test
    public void constructor_nullStudentId_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        TutorialGroup tutorialGroup = new TutorialGroupBuilder().build();
        new AddStudentToTutorialGroupCommand(tutorialGroup.getId(), null);
    }

    @Test
    public void execute_studentAddToTutorialGroupSuccessful() throws Exception {
        TutorialGroup validTutorialGroup = new TutorialGroupBuilder().build();
        model.addTutorialGroup(validTutorialGroup);
        Student studentToAdd = model.getFilteredStudentList().get(INDEX_SECOND_PERSON.getZeroBased());

        CommandResult commandResult = new AddStudentToTutorialGroupCommand(
            validTutorialGroup.getId(), studentToAdd.getStudentId()).execute(model, commandHistory);

        assertEquals(AddStudentToTutorialGroupCommand.MESSAGE_SUCCESS, commandResult.feedbackToUser);
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);
    }

    @Test
    public void execute_studentAddToInvalidTutorialGroup_unsuccessful() throws Exception {
        TutorialGroup validTutorialGroup = new TutorialGroupBuilder().build();
        model.addTutorialGroup(validTutorialGroup);
        Student studentToAdd = model.getFilteredStudentList().get(INDEX_SECOND_PERSON.getZeroBased());

        AddStudentToTutorialGroupCommand addStudentToTutorialGroupCommand = new AddStudentToTutorialGroupCommand(
            typicalTutorialGroupId, studentToAdd.getStudentId());

        assertCommandFailure(addStudentToTutorialGroupCommand, model, commandHistory,
            "No such tutorial group.");
    }

    @Test
    public void execute_invalidStudentAddToTutorialGroup_unsuccessful() throws Exception {
        TutorialGroup validTutorialGroup = new TutorialGroupBuilder().build();
        model.addTutorialGroup(validTutorialGroup);

        AddStudentToTutorialGroupCommand addStudentToTutorialGroupCommand = new AddStudentToTutorialGroupCommand(
            validTutorialGroup.getId(), new StudentId("A0123456T"));

        assertCommandFailure(addStudentToTutorialGroupCommand, model, commandHistory,
            "No such student.");
    }

    @Test
    public void execute_duplicateStudentAddToTutorialGroup_unsuccessful() throws Exception {
        TutorialGroup validTutorialGroup = new TutorialGroupBuilder().build();
        model.addTutorialGroup(validTutorialGroup);
        Student studentToAdd = model.getFilteredStudentList().get(INDEX_SECOND_PERSON.getZeroBased());
        validTutorialGroup.addStudent(studentToAdd);

        AddStudentToTutorialGroupCommand addStudentToTutorialGroupCommand = new AddStudentToTutorialGroupCommand(
            validTutorialGroup.getId(), studentToAdd.getStudentId());

        assertCommandFailure(addStudentToTutorialGroupCommand, model, commandHistory,
            "Student is already in this tutorial group!");
    }
}

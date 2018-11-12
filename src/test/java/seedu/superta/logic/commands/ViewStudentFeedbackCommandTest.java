package seedu.superta.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.superta.logic.commands.CommandTestUtil.VALID_STUDENT_ID_AMY;
import static seedu.superta.logic.commands.CommandTestUtil.VALID_STUDENT_ID_BOB;
import static seedu.superta.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.superta.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.superta.testutil.StudentBuilder.DEFAULT_STUDENT_ID;
import static seedu.superta.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.superta.testutil.TypicalSuperTaClient.getTypicalSuperTaClient;

import org.junit.Before;
import org.junit.Test;

import seedu.superta.logic.CommandHistory;
import seedu.superta.model.Model;
import seedu.superta.model.ModelManager;
import seedu.superta.model.UserPrefs;
import seedu.superta.model.student.Student;
import seedu.superta.model.student.StudentId;
import seedu.superta.testutil.StudentBuilder;

public class ViewStudentFeedbackCommandTest {
    private Model model;
    private Model expectedModel;
    private CommandHistory commandHistory = new CommandHistory();

    private final StudentId amy = new StudentId(VALID_STUDENT_ID_AMY);
    private final StudentId bob = new StudentId(VALID_STUDENT_ID_BOB);
    private final String expectedMsg = String.format("Student ID: %s\nFeedback:\n", DEFAULT_STUDENT_ID);

    private final String additionalFeedback1 = "Cool beans.";
    private final String additionalFeedback2 = "Needs to pay more attention in class.";
    private final String additionalFeedback3 = "Always helps others with their work.";

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalSuperTaClient(), new UserPrefs());
        expectedModel = new ModelManager(model.getSuperTaClient(), new UserPrefs());
    }

    @Test
    public void equals() {

        ViewStudentFeedbackCommand firstViewStudentFeedbackCommand = new ViewStudentFeedbackCommand(amy);
        ViewStudentFeedbackCommand secondViewStudentFeedbackCommand = new ViewStudentFeedbackCommand(bob);

        // same object -> returns true
        assertTrue(firstViewStudentFeedbackCommand.equals(firstViewStudentFeedbackCommand));

        // same values -> returns true
        ViewStudentFeedbackCommand firstViewStudentFeedbackCommandCopy = new ViewStudentFeedbackCommand(amy);
        assertTrue(firstViewStudentFeedbackCommand.equals(firstViewStudentFeedbackCommandCopy));

        // different types -> returns false
        assertFalse(firstViewStudentFeedbackCommand.equals(1));

        // null -> returns false
        assertFalse(firstViewStudentFeedbackCommand.equals(null));

        // different student -> returns false
        assertFalse(firstViewStudentFeedbackCommand.equals(secondViewStudentFeedbackCommand));
    }

    @Test
    public void execute_viewStudentFeedbackCommand_success() {
        assertCommandSuccess(new ViewStudentFeedbackCommand(new StudentId(DEFAULT_STUDENT_ID)),
                model,
                commandHistory,
                expectedMsg,
                expectedModel);
    }

    @Test
    public void execute_viewStudentMultipleFeedbackCommand_success() {
        Student firstStudent = model.getFilteredStudentList().get(INDEX_FIRST_PERSON.getZeroBased());
        Student editedStudent = new StudentBuilder(firstStudent)
                .withFeedback(additionalFeedback1, additionalFeedback2, additionalFeedback3).build();
        model.updateStudent(firstStudent, editedStudent);
        expectedModel.updateStudent(firstStudent, editedStudent);

        StringBuilder expectedOutput = new StringBuilder(expectedMsg);
        expectedOutput.append("0 " + additionalFeedback1 + "\n");
        expectedOutput.append("1 " + additionalFeedback2 + "\n");
        expectedOutput.append("2 " + additionalFeedback3 + "\n");

        assertCommandSuccess(new ViewStudentFeedbackCommand(new StudentId(DEFAULT_STUDENT_ID)),
                model,
                commandHistory,
                expectedOutput.toString(),
                expectedModel);
    }

    @Test
    public void execute_viewStudentFeedbackCommand_failure() {
        assertCommandFailure(new ViewStudentFeedbackCommand(new StudentId("A0116733Y")),
                model,
                commandHistory,
                ViewStudentFeedbackCommand.MESSAGE_FAILURE);
    }
}

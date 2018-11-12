package seedu.superta.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.superta.logic.commands.CommandTestUtil.VALID_FEEDBACK_AMY;
import static seedu.superta.logic.commands.CommandTestUtil.VALID_FEEDBACK_BOB;
import static seedu.superta.logic.commands.CommandTestUtil.VALID_STUDENT_ID_AMY;
import static seedu.superta.logic.commands.CommandTestUtil.VALID_STUDENT_ID_BOB;
import static seedu.superta.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.superta.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.superta.logic.commands.FeedbackCommand.MESSAGE_INVALID_STUDENT;
import static seedu.superta.logic.commands.FeedbackCommand.MESSAGE_SUCCESS;
import static seedu.superta.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.superta.testutil.TypicalSuperTaClient.getTypicalSuperTaClient;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import seedu.superta.logic.CommandHistory;
import seedu.superta.model.Model;
import seedu.superta.model.ModelManager;
import seedu.superta.model.UserPrefs;
import seedu.superta.model.student.Feedback;
import seedu.superta.model.student.Student;
import seedu.superta.model.student.StudentId;
import seedu.superta.testutil.StudentBuilder;
import seedu.superta.ui.testutil.EventsCollectorRule;

// @@author triger15
public class FeedbackCommandTest {

    private static final String FEEDBACK_STUB = "Some feedback";

    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    private Model model;
    private CommandHistory commandHistory = new CommandHistory();
    private StudentId amy = new StudentId(VALID_STUDENT_ID_AMY);
    private Feedback amyFeedback = new Feedback(VALID_FEEDBACK_AMY);
    private StudentId bob = new StudentId(VALID_STUDENT_ID_BOB);
    private Feedback bobFeedback = new Feedback(VALID_FEEDBACK_BOB);

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalSuperTaClient(), new UserPrefs());
    }

    @Test
    public void execute_feedback_success() {
        Student firstStudent = model.getFilteredStudentList().get(INDEX_FIRST_PERSON.getZeroBased());
        Student editedStudent = new StudentBuilder(firstStudent).withFeedback(FEEDBACK_STUB).build();

        FeedbackCommand feedbackCommand = new FeedbackCommand(firstStudent.getStudentId(), new Feedback(FEEDBACK_STUB));

        String expectedMessage = String.format(MESSAGE_SUCCESS, FEEDBACK_STUB);

        Model expectedModel = new ModelManager(model.getSuperTaClient(), new UserPrefs());
        expectedModel.updateStudent(firstStudent, editedStudent);
        expectedModel.commitSuperTaClient();

        assertCommandSuccess(feedbackCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidStudentAddFeedback_failure() {
        FeedbackCommand feedbackCommand = new FeedbackCommand(new StudentId("A0000000Z"),
                new Feedback(FEEDBACK_STUB));
        assertCommandFailure(feedbackCommand, model, commandHistory, MESSAGE_INVALID_STUDENT);
    }

    @Test
    public void equals() {
        final FeedbackCommand standardCommand = new FeedbackCommand(amy, amyFeedback);
        // same values -> returns true
        FeedbackCommand commandWithSameValues = new FeedbackCommand(amy, amyFeedback);
        assertTrue(standardCommand.equals(commandWithSameValues));
        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));
        // null -> returns false
        assertFalse(standardCommand.equals(null));
        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));
        // different student -> returns false
        assertFalse(standardCommand.equals(new FeedbackCommand(bob, amyFeedback)));
        // different feedback -> returns false
        assertFalse(standardCommand.equals(new FeedbackCommand(amy, bobFeedback)));
    }
}

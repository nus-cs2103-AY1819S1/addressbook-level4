package systemtests;

import static org.junit.Assert.assertTrue;
import static tutorhelper.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static tutorhelper.commons.core.Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX;
import static tutorhelper.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static tutorhelper.logic.commands.MarkCommand.MESSAGE_MARK_SUCCESS;
import static tutorhelper.model.Model.PREDICATE_SHOW_ALL_STUDENTS;
import static tutorhelper.model.util.SubjectsUtil.createStudentWithNewSubjects;
import static tutorhelper.testutil.TestUtil.getLastIndex;
import static tutorhelper.testutil.TestUtil.getMidIndex;
import static tutorhelper.testutil.TestUtil.getStudent;
import static tutorhelper.testutil.TypicalIndexes.INDEX_FIRST_STUDENT;
import static tutorhelper.testutil.TypicalIndexes.INDEX_FIRST_SUBJECT;
import static tutorhelper.testutil.TypicalIndexes.INDEX_FIRST_SYLLABUS;
import static tutorhelper.testutil.TypicalStudents.KEYWORD_MATCHING_MEIER;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import tutorhelper.commons.core.index.Index;
import tutorhelper.logic.commands.MarkCommand;
import tutorhelper.logic.commands.RedoCommand;
import tutorhelper.logic.commands.UndoCommand;
import tutorhelper.logic.commands.exceptions.CommandException;
import tutorhelper.model.Model;
import tutorhelper.model.student.Student;
import tutorhelper.model.subject.Subject;

public class MarkCommandSystemTest extends TutorHelperSystemTest {

    private static final String MESSAGE_INVALID_MARK_COMMAND_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkCommand.MESSAGE_USAGE);

    @Test
    public void mark() throws CommandException {
        /* ----------------- Performing mark operation while an unfiltered list is being shown -------------------- */

        /* Case: mark the first student in the list, command with leading spaces and trailing spaces -> marked */
        Model expectedModel = getModel();
        assertCommandSuccess(INDEX_FIRST_STUDENT, INDEX_FIRST_SUBJECT, INDEX_FIRST_SYLLABUS);

        /* Case: mark the last student in the list -> marked */
        Model modelBeforeMarkingLast = getModel();
        Index lastStudentIndex = getLastIndex(modelBeforeMarkingLast);
        assertCommandSuccess(lastStudentIndex, INDEX_FIRST_SUBJECT, INDEX_FIRST_SYLLABUS);

        /* Case: undo marking the last student in the list -> last student restored */
        String command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, modelBeforeMarkingLast, expectedResultMessage);

        /* Case: redo marking the last student in the list -> last student marked again */
        command = RedoCommand.COMMAND_WORD;
        markStudent(modelBeforeMarkingLast, lastStudentIndex, INDEX_FIRST_SUBJECT, INDEX_FIRST_SYLLABUS);
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, modelBeforeMarkingLast, expectedResultMessage);

        /* Case: marking the middle student in the list -> marked */
        Index middleStudentIndex = getMidIndex(getModel());
        assertCommandSuccess(middleStudentIndex, INDEX_FIRST_SUBJECT, INDEX_FIRST_SYLLABUS);

        /* ------------------ Performing mark operation while a filtered list is being shown ---------------------- */

        /* Case: filtered student list, mark index within bounds of TutorHelper and student list -> marked */
        showStudentsWithName(KEYWORD_MATCHING_MEIER);
        Index studentIndex = INDEX_FIRST_STUDENT;
        assertTrue(studentIndex.getZeroBased() < expectedModel.getFilteredStudentList().size());
        assertCommandSuccess(studentIndex, INDEX_FIRST_SUBJECT, INDEX_FIRST_SYLLABUS, KEYWORD_MATCHING_MEIER);

        /* Case: filtered student list, mark index within bounds of TutorHelper but out of bounds of student list
         * -> rejected
         */
        showStudentsWithName(KEYWORD_MATCHING_MEIER);
        int invalidIndex = getModel().getTutorHelper().getStudentList().size();
        command = MarkCommand.COMMAND_WORD + " " + invalidIndex
                + " " + INDEX_FIRST_SYLLABUS.getOneBased()
                + " " + INDEX_FIRST_SYLLABUS.getOneBased();
        assertCommandFailure(command, MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX);

        /* --------------------------------- Performing invalid delete operation ------------------------------------ */

        /* Case: invalid index (0) -> rejected */
        command = MarkCommand.COMMAND_WORD + " 0 0 0";
        assertCommandFailure(command, MESSAGE_INVALID_MARK_COMMAND_FORMAT);

        /* Case: invalid index (-1) -> rejected */
        command = MarkCommand.COMMAND_WORD + " -1 -1 -1";
        assertCommandFailure(command, MESSAGE_INVALID_MARK_COMMAND_FORMAT);

        /* Case: invalid index (size + 1) -> rejected */
        Index outOfBoundsIndex = Index.fromOneBased(
                getModel().getTutorHelper().getStudentList().size() + 1);
        command = MarkCommand.COMMAND_WORD + " " + outOfBoundsIndex.getOneBased() + " 1 1";
        assertCommandFailure(command, MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX);

        /* Case: invalid arguments (alphabets) -> rejected */
        assertCommandFailure(MarkCommand.COMMAND_WORD + " a b c", MESSAGE_INVALID_MARK_COMMAND_FORMAT);

        /* Case: invalid arguments (extra argument) -> rejected */
        assertCommandFailure(MarkCommand.COMMAND_WORD + " 1 a b c", MESSAGE_INVALID_MARK_COMMAND_FORMAT);

        /* Case: mixed case command word -> rejected */
        assertCommandFailure("mARk 1 1 1", MESSAGE_UNKNOWN_COMMAND);
    }

    /**
     * Removes the {@code Student} at the specified {@code index} in {@code model}'s TutorHelper.
     * @return the removed student
     */
    private Student markStudent(Model model, Index studentIndex, Index subjectIndex, Index syllabusIndex)
            throws CommandException {
        Student studentTarget = getStudent(model, studentIndex);
        List<Subject> subjects = new ArrayList<>(studentTarget.getSubjects());

        Subject updatedSubject = subjects.get(subjectIndex.getZeroBased()).toggleState(syllabusIndex);
        subjects.set(subjectIndex.getZeroBased(), updatedSubject);

        Set<Subject> newSubjects = new HashSet<>(subjects);
        Student studentUpdated = createStudentWithNewSubjects(studentTarget, newSubjects);

        model.updateStudent(studentTarget, studentUpdated);
        return studentUpdated;
    }

    private void assertCommandSuccess(Index studentIndex, Index subjectIndex, Index syllabusIndex)
            throws CommandException {
        assertCommandSuccess(studentIndex, subjectIndex, syllabusIndex, null);
    }

    /**
     * Executes {@code command} and in addition,
     * 1. Asserts that the command box displays {@code command}.
     * 2. Asserts that result display box displays {@code expectedResultMessage}.
     * 3. Asserts that the browser url, selected card and status bar remain unchanged.
     * 4. Asserts that the command box has the error style.
     * Verifications 1 and 2 are performed by
     * {@code TutorHelperSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see TutorHelperSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(Index studentIndex, Index subjectIndex, Index syllabusIndex, String filter)
            throws CommandException {
        Model expectedModel = getModel();
        Student markedStudent = markStudent(expectedModel, studentIndex, subjectIndex, syllabusIndex);
        String expectedResultMessage = String.format(MESSAGE_MARK_SUCCESS, markedStudent);
        assertCommandSuccess(MarkCommand.COMMAND_WORD
                + " " + studentIndex.getOneBased() + " " + subjectIndex.getOneBased()
                + " " + syllabusIndex.getOneBased(), expectedModel, expectedResultMessage, null);
    }

    /**
     * Executes {@code command} and in addition,
     * 1. Asserts that the command box displays an empty string.
     * 2. Asserts that the result display box displays {@code expectedResultMessage}.
     * 3. Asserts that the browser url and selected card remains unchanged.
     * 4. Asserts that the status bar's sync status changes.
     * 5. Asserts that the command box has the default style class.
     * Verifications 1 and 2 are performed by
     * {@code TutorHelperSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.
     * @see TutorHelperSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        executeCommand(command);
        expectedModel.updateFilteredStudentList(PREDICATE_SHOW_ALL_STUDENTS);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchangedExceptSyncStatus();
    }

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays an empty string.<br>
     * 2. Asserts that the result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the browser url and selected card update accordingly depending on the card at
     * {@code expectedSelectedCardIndex}.<br>
     * 4. Asserts that the status bar's sync status changes.<br>
     * 5. Asserts that the command box has the default style class.<br>
     * Verifications 1 and 2 are performed by
     * {@code TutorHelperSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see TutorHelperSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     * @see TutorHelperSystemTest#assertSelectedCardChanged(Index)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage,
                                      Index expectedSelectedCardIndex) {
        executeCommand(command);
        expectedModel.updateFilteredStudentList(PREDICATE_SHOW_ALL_STUDENTS);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertCommandBoxShowsDefaultStyle();

        if (expectedSelectedCardIndex != null) {
            assertSelectedCardChanged(expectedSelectedCardIndex);
        } else {
            assertSelectedCardChanged();
        }
        assertStatusBarUnchangedExceptSyncStatus();
    }

    /**
     * Executes {@code command} and in addition,
     * 1. Asserts that the command box displays {@code command}.
     * 2. Asserts that result display box displays {@code expectedResultMessage}.
     * 3. Asserts that the browser url, selected card and status bar remain unchanged.
     * 4. Asserts that the command box has the error style.
     * Verifications 1 and 2 are performed by
     * {@code TutorHelperSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.
     * @see TutorHelperSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();

        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }
}

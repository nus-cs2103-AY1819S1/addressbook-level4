package systemtests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static tutorhelper.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static tutorhelper.commons.core.Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX;
import static tutorhelper.logic.commands.CommandTestUtil.ADDRESS_DESC_AMY;
import static tutorhelper.logic.commands.CommandTestUtil.ADDRESS_DESC_BOB;
import static tutorhelper.logic.commands.CommandTestUtil.DUPLICATE_SYLLABUS;
import static tutorhelper.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static tutorhelper.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static tutorhelper.logic.commands.CommandTestUtil.INVALID_ADDRESS_DESC;
import static tutorhelper.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static tutorhelper.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static tutorhelper.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static tutorhelper.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static tutorhelper.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static tutorhelper.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static tutorhelper.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static tutorhelper.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static tutorhelper.logic.commands.CommandTestUtil.TAG_DESC_EXAM;
import static tutorhelper.logic.commands.CommandTestUtil.TAG_DESC_WEAK;
import static tutorhelper.logic.commands.CommandTestUtil.TAG_DESC_WEAK_EXAM;
import static tutorhelper.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static tutorhelper.logic.commands.CommandTestUtil.VALID_SUBJECT_AMY;
import static tutorhelper.logic.commands.CommandTestUtil.VALID_TAG_WEAK;
import static tutorhelper.logic.commands.CommandTestUtil.VALID_TUITION_TIMING_BOB;
import static tutorhelper.logic.commands.EditCommand.MESSAGE_DUPLICATE_STUDENT;
import static tutorhelper.logic.parser.CliSyntax.PREFIX_TAG;
import static tutorhelper.model.Model.PREDICATE_SHOW_ALL_STUDENTS;
import static tutorhelper.testutil.TypicalIndexes.INDEX_FIRST_STUDENT;
import static tutorhelper.testutil.TypicalIndexes.INDEX_FIRST_SUBJECT;
import static tutorhelper.testutil.TypicalIndexes.INDEX_SECOND_STUDENT;
import static tutorhelper.testutil.TypicalStudents.AMY;
import static tutorhelper.testutil.TypicalStudents.BOB;
import static tutorhelper.testutil.TypicalStudents.KEYWORD_MATCHING_MEIER;

import org.junit.Test;

import tutorhelper.commons.core.index.Index;
import tutorhelper.logic.commands.EditCommand;
import tutorhelper.logic.commands.RedoCommand;
import tutorhelper.logic.commands.UndoCommand;
import tutorhelper.model.Model;
import tutorhelper.model.student.Address;
import tutorhelper.model.student.Email;
import tutorhelper.model.student.Name;
import tutorhelper.model.student.Phone;
import tutorhelper.model.student.Student;
import tutorhelper.model.tag.Tag;
import tutorhelper.testutil.StudentBuilder;
import tutorhelper.testutil.StudentUtil;

public class EditCommandSystemTest extends TutorHelperSystemTest {

    @Test
    public void edit() {
        Model model = getModel();

        /* ----------------- Performing edit operation while an unfiltered list is being shown ---------------------- */

        /* Case: edit all fields, command with leading spaces, trailing spaces and multiple spaces between each field
         * -> edited
         */
        Index index = INDEX_FIRST_STUDENT;
        String command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + " " + NAME_DESC_BOB + " "
                + PHONE_DESC_BOB + " " + EMAIL_DESC_BOB + " " + ADDRESS_DESC_BOB + " " + TAG_DESC_WEAK + " ";
        Student editedStudent = new StudentBuilder(BOB).withTags(VALID_TAG_WEAK).withSubjects(VALID_SUBJECT_AMY)
                .withSyllabus(INDEX_FIRST_SUBJECT, DUPLICATE_SYLLABUS).build();
        assertCommandSuccess(command, index, editedStudent);

        /* Case: undo editing the last student in the list -> last student restored */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: redo editing the last student in the list -> last student edited again */
        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        model.updateStudent(
                getModel().getFilteredStudentList().get(INDEX_FIRST_STUDENT.getZeroBased()), editedStudent);
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: edit a student with new values same as existing values -> edited */
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + TAG_DESC_WEAK;
        assertCommandSuccess(command, index, editedStudent);

        /* Case: edit a student with new values same as another student's values but with different phone and email
         * -> edited
         */
        index = INDEX_SECOND_STUDENT;
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + NAME_DESC_BOB + PHONE_DESC_AMY + EMAIL_DESC_AMY
                + ADDRESS_DESC_BOB + TAG_DESC_WEAK;
        assertCommandFailure(command, MESSAGE_DUPLICATE_STUDENT);

        /* Case: clear tags -> cleared */
        index = INDEX_FIRST_STUDENT;
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + " " + PREFIX_TAG.getPrefix();
        Student studentToEdit = getModel().getFilteredStudentList().get(index.getZeroBased());
        editedStudent = new StudentBuilder(studentToEdit).withTags().build();
        assertCommandSuccess(command, index, editedStudent);

        /* ------------------ Performing edit operation while a filtered list is being shown ------------------------ */

        /* Case: filtered student list, edit index within bounds of TutorHelper and student list -> edited */
        showStudentsWithName(KEYWORD_MATCHING_MEIER);
        index = INDEX_FIRST_STUDENT;
        assertTrue(index.getZeroBased() < getModel().getFilteredStudentList().size());
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + " " + NAME_DESC_BOB;
        studentToEdit = getModel().getFilteredStudentList().get(index.getZeroBased());
        editedStudent = new StudentBuilder(studentToEdit).withName(VALID_NAME_BOB).build();
        assertCommandSuccess(command, index, editedStudent);

        /* Case: filtered student list, edit index within bounds of TutorHelper but out of bounds of student list
         * -> rejected
         */
        showStudentsWithName(KEYWORD_MATCHING_MEIER);
        int invalidIndex = getModel().getTutorHelper().getStudentList().size();
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + invalidIndex + NAME_DESC_BOB,
                MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX);

        /* -------------------- Performing edit operation while a student card is selected -------------------------- */

        /* Case: selects first card in the student list, edit a student -> edited, card selection remains unchanged but
         * browser url changes
         */
        showAllStudents();
        index = INDEX_FIRST_STUDENT;
        selectStudent(index);
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY
                + ADDRESS_DESC_AMY + TAG_DESC_EXAM;
        // this can be misleading: card selection actually remains unchanged but the
        // browser's url is updated to reflect the new student's name
        editedStudent = new StudentBuilder(AMY).withTuitionTiming(VALID_TUITION_TIMING_BOB)
                .withSubjects(VALID_SUBJECT_AMY).withSyllabus(INDEX_FIRST_SUBJECT, DUPLICATE_SYLLABUS).build();
        assertCommandSuccess(command, index, editedStudent, index);

        /* --------------------------------- Performing invalid edit operation -------------------------------------- */

        /* Case: invalid index (0) -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " 0" + NAME_DESC_BOB,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));

        /* Case: invalid index (-1) -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " -1" + NAME_DESC_BOB,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));

        /* Case: invalid index (size + 1) -> rejected */
        invalidIndex = getModel().getFilteredStudentList().size() + 1;
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + invalidIndex + NAME_DESC_BOB,
                MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX);

        /* Case: missing index -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + NAME_DESC_BOB,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));

        /* Case: missing all fields -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + INDEX_FIRST_STUDENT.getOneBased(),
                EditCommand.MESSAGE_NOT_EDITED);

        /* Case: invalid name -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + INDEX_FIRST_STUDENT.getOneBased() + INVALID_NAME_DESC,
                Name.MESSAGE_NAME_CONSTRAINTS);

        /* Case: invalid phone -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + INDEX_FIRST_STUDENT.getOneBased() + INVALID_PHONE_DESC,
                Phone.MESSAGE_PHONE_CONSTRAINTS);

        /* Case: invalid email -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + INDEX_FIRST_STUDENT.getOneBased() + INVALID_EMAIL_DESC,
                Email.MESSAGE_EMAIL_CONSTRAINTS);

        /* Case: invalid address -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + INDEX_FIRST_STUDENT.getOneBased() + INVALID_ADDRESS_DESC,
                Address.MESSAGE_ADDRESS_CONSTRAINTS);

        /* Case: invalid tag -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + INDEX_FIRST_STUDENT.getOneBased() + INVALID_TAG_DESC,
                Tag.MESSAGE_TAG_CONSTRAINTS);

        /* Case: edit a student with new values same as another student's values -> rejected */
        executeCommand(StudentUtil.getAddCommand(BOB));
        assertTrue(getModel().getTutorHelper().getStudentList().contains(BOB));
        index = INDEX_FIRST_STUDENT;
        assertFalse(getModel().getFilteredStudentList().get(index.getZeroBased()).equals(BOB));
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + TAG_DESC_WEAK_EXAM;
        assertCommandFailure(command, MESSAGE_DUPLICATE_STUDENT);

        /* Case: edit a student with new values same as another student's values but with different tags -> rejected */
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + TAG_DESC_WEAK;
        assertCommandFailure(command, MESSAGE_DUPLICATE_STUDENT);


    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Index, Student, Index)} except that
     * the browser url and selected card remain unchanged.
     * @param toEdit the index of the current model's filtered list
     * @see EditCommandSystemTest#assertCommandSuccess(String, Index, Student, Index)
     */
    private void assertCommandSuccess(String command, Index toEdit, Student editedStudent) {
        assertCommandSuccess(command, toEdit, editedStudent, null);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Model, String, Index)} and in addition,<br>
     * 1. Asserts that result display box displays the success message of executing {@code EditCommand}.<br>
     * 2. Asserts that the model related components are updated to reflect the student at index {@code toEdit} being
     * updated to values specified {@code editedStudent}.<br>
     * @param toEdit the index of the current model's filtered list.
     * @see EditCommandSystemTest#assertCommandSuccess(String, Model, String, Index)
     */
    private void assertCommandSuccess(String command, Index toEdit, Student editedStudent,
            Index expectedSelectedCardIndex) {
        Model expectedModel = getModel();
        expectedModel.updateStudent(expectedModel.getFilteredStudentList().get(toEdit.getZeroBased()), editedStudent);
        expectedModel.updateFilteredStudentList(PREDICATE_SHOW_ALL_STUDENTS);

        assertCommandSuccess(command, expectedModel,
                String.format(EditCommand.MESSAGE_EDIT_STUDENT_SUCCESS, editedStudent), expectedSelectedCardIndex);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Model, String, Index)} except that the
     * browser url and selected card remain unchanged.
     * @see EditCommandSystemTest#assertCommandSuccess(String, Model, String, Index)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        assertCommandSuccess(command, expectedModel, expectedResultMessage, null);
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
            assertSelectedCardUnchanged();
        }
        assertStatusBarUnchangedExceptSyncStatus();
    }

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays {@code command}.<br>
     * 2. Asserts that result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the browser url, selected card and status bar remain unchanged.<br>
     * 4. Asserts that the command box has the error style.<br>
     * Verifications 1 and 2 are performed by
     * {@code TutorHelperSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
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

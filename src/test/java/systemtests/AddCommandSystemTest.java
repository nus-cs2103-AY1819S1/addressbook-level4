package systemtests;

import static seedu.superta.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.superta.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.superta.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static seedu.superta.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.superta.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.superta.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.superta.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.superta.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.superta.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.superta.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.superta.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.superta.logic.commands.CommandTestUtil.STUDENT_ID_DESC_AMY;
import static seedu.superta.logic.commands.CommandTestUtil.STUDENT_ID_DESC_BOB;
import static seedu.superta.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.superta.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static seedu.superta.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.superta.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.superta.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.superta.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.superta.testutil.TypicalSuperTaClient.ALICE;
import static seedu.superta.testutil.TypicalSuperTaClient.AMY;
import static seedu.superta.testutil.TypicalSuperTaClient.BOB;
import static seedu.superta.testutil.TypicalSuperTaClient.CARL;
import static seedu.superta.testutil.TypicalSuperTaClient.HOON;
import static seedu.superta.testutil.TypicalSuperTaClient.IDA;
import static seedu.superta.testutil.TypicalSuperTaClient.KEYWORD_MATCHING_MEIER;

import org.junit.Test;

import seedu.superta.commons.core.Messages;
import seedu.superta.commons.core.index.Index;
import seedu.superta.logic.commands.AddCommand;
import seedu.superta.logic.commands.RedoCommand;
import seedu.superta.logic.commands.UndoCommand;
import seedu.superta.model.Model;
import seedu.superta.model.student.Email;
import seedu.superta.model.student.Name;
import seedu.superta.model.student.Phone;
import seedu.superta.model.student.Student;
import seedu.superta.model.tag.Tag;
import seedu.superta.testutil.StudentBuilder;
import seedu.superta.testutil.StudentUtil;

public class AddCommandSystemTest extends SuperTaClientSystemTest {

    @Test
    public void add() {
        Model model = getModel();

        /* ------------------------ Perform add operations on the shown unfiltered list ----------------------------- */

        /* Case: add a student without tags to a non-empty address book, command with leading spaces and trailing spaces
         * -> added
         */
        Student toAdd = AMY;
        String command = "   " + AddCommand.COMMAND_WORD + "  " + NAME_DESC_AMY + "  " + PHONE_DESC_AMY + " "
                + EMAIL_DESC_AMY + "   " + "   " + STUDENT_ID_DESC_AMY + "   " + TAG_DESC_FRIEND
                + " ";
        assertCommandSuccess(command, toAdd);

        /* Case: undo adding Amy to the list -> Amy deleted */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: redo adding Amy to the list -> Amy added again */
        command = RedoCommand.COMMAND_WORD;
        model.addStudent(toAdd);
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: add a student with all fields same as another student in the address book except name and
          student ID -> added */
        toAdd = new StudentBuilder(AMY).withName(VALID_NAME_BOB).withStudentId("A0022446T").build();
        command = AddCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_AMY + EMAIL_DESC_AMY
                + " id/A0022446T" + TAG_DESC_FRIEND;
        assertCommandSuccess(command, toAdd);

        /* Case: add a student with all fields same as another student in the address book except phone and email
         * -> added
         */
        toAdd = new StudentBuilder(AMY).withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB)
                .withStudentId("A0101010Y").build();
        command = StudentUtil.getAddCommand(toAdd);
        assertCommandSuccess(command, toAdd);

        /* Case: add to empty address book -> added */
        deleteAllPersons();
        assertCommandSuccess(ALICE);

        /* Case: add a student with tags, command with parameters in random order -> added */
        toAdd = BOB;
        command = AddCommand.COMMAND_WORD + TAG_DESC_FRIEND + PHONE_DESC_BOB + NAME_DESC_BOB
                + STUDENT_ID_DESC_BOB + TAG_DESC_HUSBAND + EMAIL_DESC_BOB;
        assertCommandSuccess(command, toAdd);

        /* Case: add a student, missing tags -> added */
        assertCommandSuccess(HOON);

        /* -------------------------- Perform add operation on the shown filtered list ----------------------------- */

        /* Case: filters the student list before adding -> added */
        showPersonsWithName(KEYWORD_MATCHING_MEIER);
        assertCommandSuccess(IDA);

        /* ------------------------ Perform add operation while a student card is selected -------------------------- */

        /* Case: selects first card in the student list, add a student -> added, card selection remains unchanged */
        selectPerson(Index.fromOneBased(1));
        assertCommandSuccess(CARL);

        /* ----------------------------------- Perform invalid add operations -------------------------------------- */

        /* Case: add a duplicate student -> rejected */
        command = StudentUtil.getAddCommand(HOON);
        assertCommandFailure(command, AddCommand.MESSAGE_DUPLICATE_STUDENT);

        /* Case: add a duplicate student except with different phone -> rejected */
        toAdd = new StudentBuilder(HOON).withPhone(VALID_PHONE_BOB).build();
        command = StudentUtil.getAddCommand(toAdd);
        assertCommandFailure(command, AddCommand.MESSAGE_DUPLICATE_STUDENT);

        /* Case: add a duplicate student except with different email -> rejected */
        toAdd = new StudentBuilder(HOON).withEmail(VALID_EMAIL_BOB).build();
        command = StudentUtil.getAddCommand(toAdd);
        assertCommandFailure(command, AddCommand.MESSAGE_DUPLICATE_STUDENT);

        /* Case: add a duplicate student except with different tags -> rejected */
        command = StudentUtil.getAddCommand(HOON) + " " + PREFIX_TAG.getPrefix() + "friends";
        assertCommandFailure(command, AddCommand.MESSAGE_DUPLICATE_STUDENT);

        /* Case: missing name -> rejected */
        command = AddCommand.COMMAND_WORD + PHONE_DESC_AMY + EMAIL_DESC_AMY + STUDENT_ID_DESC_AMY;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

        /* Case: missing phone -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + EMAIL_DESC_AMY + STUDENT_ID_DESC_AMY;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

        /* Case: missing email -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + STUDENT_ID_DESC_AMY;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

        /* Case: invalid keyword -> rejected */
        command = "adds " + StudentUtil.getStudentDetails(toAdd);
        assertCommandFailure(command, Messages.MESSAGE_UNKNOWN_COMMAND);

        /* Case: invalid name -> rejected */
        command = AddCommand.COMMAND_WORD + INVALID_NAME_DESC + PHONE_DESC_AMY + EMAIL_DESC_AMY
                + STUDENT_ID_DESC_AMY;
        assertCommandFailure(command, Name.MESSAGE_NAME_CONSTRAINTS);

        /* Case: invalid phone -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + INVALID_PHONE_DESC + EMAIL_DESC_AMY
                + STUDENT_ID_DESC_AMY;
        assertCommandFailure(command, Phone.MESSAGE_PHONE_CONSTRAINTS);

        /* Case: invalid email -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + INVALID_EMAIL_DESC
                + STUDENT_ID_DESC_AMY;
        assertCommandFailure(command, Email.MESSAGE_EMAIL_CONSTRAINTS);

        /* Case: invalid tag -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY
                + STUDENT_ID_DESC_AMY
                + INVALID_TAG_DESC;
        assertCommandFailure(command, Tag.MESSAGE_TAG_CONSTRAINTS);
    }

    /**
     * Executes the {@code AddCommand} that adds {@code toAdd} to the model and asserts that the,<br>
     * 1. Command box displays an empty string.<br>
     * 2. Command box has the default style class.<br>
     * 3. Result display box displays the success message of executing {@code AddCommand} with the details of
     * {@code toAdd}.<br>
     * 4. {@code Storage} and {@code StudentListPanel} equal to the corresponding components in
     * the current model added with {@code toAdd}.<br>
     * 5. Browser url and selected card remain unchanged.<br>
     * 6. Status bar's sync status changes.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code SuperTaClientSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     *
     * @see SuperTaClientSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(Student toAdd) {
        assertCommandSuccess(StudentUtil.getAddCommand(toAdd), toAdd);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(Student)}. Executes {@code command}
     * instead.
     *
     * @see AddCommandSystemTest#assertCommandSuccess(Student)
     */
    private void assertCommandSuccess(String command, Student toAdd) {
        Model expectedModel = getModel();
        expectedModel.addStudent(toAdd);
        String expectedResultMessage = String.format(AddCommand.MESSAGE_SUCCESS, toAdd);

        assertCommandSuccess(command, expectedModel, expectedResultMessage);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Student)} except asserts that
     * the,<br>
     * 1. Result display box displays {@code expectedResultMessage}.<br>
     * 2. {@code Storage} and {@code StudentListPanel} equal to the corresponding components in
     * {@code expectedModel}.<br>
     *
     * @see AddCommandSystemTest#assertCommandSuccess(String, Student)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchangedExceptSyncStatus();
    }

    /**
     * Executes {@code command} and asserts that the,<br>
     * 1. Command box displays {@code command}.<br>
     * 2. Command box has the error style class.<br>
     * 3. Result display box displays {@code expectedResultMessage}.<br>
     * 4. {@code Storage} and {@code StudentListPanel} remain unchanged.<br>
     * 5. Browser url, selected card and status bar remain unchanged.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code SuperTaClientSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     *
     * @see SuperTaClientSystemTest#assertApplicationDisplaysExpected(String, String, Model)
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

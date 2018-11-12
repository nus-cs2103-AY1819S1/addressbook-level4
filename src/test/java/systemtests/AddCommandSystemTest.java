package systemtests;

import static seedu.saveit.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.saveit.logic.commands.CommandTestUtil.DESCRIPTION_DESC_C;
import static seedu.saveit.logic.commands.CommandTestUtil.DESCRIPTION_DESC_JAVA;
import static seedu.saveit.logic.commands.CommandTestUtil.INVALID_DESCRIPTION_DESC;
import static seedu.saveit.logic.commands.CommandTestUtil.INVALID_STATEMENT_DESC;
import static seedu.saveit.logic.commands.CommandTestUtil.MYSQL_ERROR_STATEMENT;
import static seedu.saveit.logic.commands.CommandTestUtil.STATEMENT_DESC_C;
import static seedu.saveit.logic.commands.CommandTestUtil.STATEMENT_DESC_JAVA;
import static seedu.saveit.logic.commands.CommandTestUtil.TAG_DESC_UI;
import static seedu.saveit.logic.commands.CommandTestUtil.VALID_DESCRIPTION_C;
import static seedu.saveit.logic.commands.CommandTestUtil.VALID_STATEMENT_C;
import static seedu.saveit.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.saveit.testutil.TypicalIssues.JAVA_NULL_POINTER;
import static seedu.saveit.testutil.TypicalIssues.MYSQL_ERROR_NO_SOLUTION;
import static seedu.saveit.testutil.TypicalIssues.POSTGRESQL_ERROR_NO_SOLUTION;
import static seedu.saveit.testutil.TypicalIssues.VALID_C_ISSUE_NO_SOLUTION;
import static seedu.saveit.testutil.TypicalIssues.VALID_JAVA_ISSUE_NO_SOLUTION;

import org.junit.Test;

import seedu.saveit.commons.core.Messages;
import seedu.saveit.logic.commands.AddCommand;
import seedu.saveit.logic.commands.CommandTestUtil;
import seedu.saveit.logic.commands.RedoCommand;
import seedu.saveit.logic.commands.UndoCommand;
import seedu.saveit.model.Issue;
import seedu.saveit.model.Model;
import seedu.saveit.model.issue.Description;
import seedu.saveit.model.issue.IssueStatement;
import seedu.saveit.testutil.IssueBuilder;
import seedu.saveit.testutil.IssueUtil;

public class AddCommandSystemTest extends SaveItSystemTest {

    @Test
    public void add() {
        Model model = getModel();

        /* ------------------------ Perform add operations on the shown unfiltered list
        ----------------------------- */

        /* Case: add an issue with tags to a non-empty saveit book, command with leading spaces and
        trailing spaces
         * -> added
         */
        Issue toAdd = VALID_JAVA_ISSUE_NO_SOLUTION;
        String command =
                "   " + AddCommand.COMMAND_WORD + " " + STATEMENT_DESC_JAVA + " " + DESCRIPTION_DESC_JAVA
                        + CommandTestUtil.TAG_DESC_UI + " ";
        assertCommandSuccess(command, toAdd);

        /* Case: undo adding Amy to the list -> Amy deleted */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: redo adding Amy to the list -> Amy added again */
        command = RedoCommand.COMMAND_WORD;
        model.addIssue(toAdd);
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: add a issue with all fields same as another issue in the saveit book except statement ->
        added */
        toAdd = new IssueBuilder(VALID_JAVA_ISSUE_NO_SOLUTION).withStatement(VALID_STATEMENT_C).build();
        command = AddCommand.COMMAND_WORD + STATEMENT_DESC_C + DESCRIPTION_DESC_JAVA
                + CommandTestUtil.TAG_DESC_UI;
        assertCommandSuccess(command, toAdd);

        /* Case: add an issue with all fields same as another issue in the saveit book except description
         * -> failed
         */
        toAdd = new IssueBuilder(VALID_JAVA_ISSUE_NO_SOLUTION).withDescription(VALID_DESCRIPTION_C).build();
        command = IssueUtil.getAddIssueCommand(toAdd);
        assertCommandFailure(command, AddCommand.MESSAGE_DUPLICATE_ISSUE);

        /* Case: add to empty saveit book -> added */
        deleteAllIssues();
        assertCommandSuccess(JAVA_NULL_POINTER);

        /* Case: add an issue with tags, command with parameters in random order -> added */
        toAdd = VALID_C_ISSUE_NO_SOLUTION;
        command = AddCommand.COMMAND_WORD + CommandTestUtil.TAG_DESC_UI
                + DESCRIPTION_DESC_C + STATEMENT_DESC_C + TAG_DESC_UI;
        assertCommandSuccess(command, toAdd);

        /* Case: add an issue, missing tags -> added */
        assertCommandSuccess(MYSQL_ERROR_NO_SOLUTION);

        /* -------------------------- Perform add operation on the shown filtered list
        ------------------------------ */

        /* Case: filters the issue list before adding -> added */
        showIssuesWithName(MYSQL_ERROR_STATEMENT);
        assertCommandSuccess(POSTGRESQL_ERROR_NO_SOLUTION);

        /* ----------------------------------- Perform invalid add operations
        --------------------------------------- */

        /* Case: add a duplicate issue -> rejected */
        command = IssueUtil.getAddIssueCommand(MYSQL_ERROR_NO_SOLUTION);
        assertCommandFailure(command, AddCommand.MESSAGE_DUPLICATE_ISSUE);

        /* Case: add a duplicate issue except with different description -> rejected */
        toAdd = new IssueBuilder(MYSQL_ERROR_NO_SOLUTION).withDescription(VALID_DESCRIPTION_C).build();
        command = IssueUtil.getAddIssueCommand(toAdd);
        assertCommandFailure(command, AddCommand.MESSAGE_DUPLICATE_ISSUE);

        /* Case: add a duplicate issue except with different tags -> rejected */
        command = IssueUtil.getAddIssueCommand(MYSQL_ERROR_NO_SOLUTION) + " " + PREFIX_TAG.getPrefix()
                + "friends";
        assertCommandFailure(command, AddCommand.MESSAGE_DUPLICATE_ISSUE);

        /* Case: missing statement -> rejected */
        command = AddCommand.COMMAND_WORD + DESCRIPTION_DESC_JAVA;
        assertCommandFailure(command,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

        /* Case: missing description -> rejected */
        command = AddCommand.COMMAND_WORD + STATEMENT_DESC_JAVA;
        assertCommandFailure(command,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

        /* Case: invalid keyword -> rejected */
        command = "adds " + IssueUtil.getIssueDetails(toAdd);
        assertCommandFailure(command, Messages.MESSAGE_UNKNOWN_COMMAND);

        /* Case: invalid name -> rejected */
        command = AddCommand.COMMAND_WORD + INVALID_STATEMENT_DESC + DESCRIPTION_DESC_JAVA;
        assertCommandFailure(command, IssueStatement.MESSAGE_ISSUE_STATEMENT_CONSTRAINTS);

        /* Case: invalid descriptions -> rejected */
        command = AddCommand.COMMAND_WORD + STATEMENT_DESC_JAVA + INVALID_DESCRIPTION_DESC;
        assertCommandFailure(command, Description.MESSAGE_DESCRIPTION_CONSTRAINTS);
    }

    /**
     * Executes the {@code AddCommand} that adds {@code toAdd} to the model and asserts that the,<br> 1.
     * Command box displays an empty string.<br> 2. Command box has the default style class.<br> 3. Result
     * display box displays the success message of executing {@code AddCommand} with the details of {@code
     * toAdd}.<br> 4. {@code Storage} and {@code IssueListPanel} equal to the corresponding components in the
     * current model added with {@code toAdd}.<br> 5. Browser url and selected card remain unchanged.<br> 6.
     * Status bar's sync status changes.<br> Verifications 1, 3 and 4 are performed by {@code
     * SaveItSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     *
     * @see SaveItSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(Issue toAdd) {
        assertCommandSuccess(IssueUtil.getAddIssueCommand(toAdd), toAdd);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(Issue)}. Executes {@code command}
     * instead.
     *
     * @see AddCommandSystemTest#assertCommandSuccess(Issue)
     */
    private void assertCommandSuccess(String command, Issue toAdd) {
        Model expectedModel = getModel();
        expectedModel.addIssue(toAdd);
        String expectedResultMessage = String.format(AddCommand.MESSAGE_ISSUE_SUCCESS, toAdd);

        assertCommandSuccess(command, expectedModel, expectedResultMessage);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Issue)} except asserts that
     * the,<br> 1. Result display box displays {@code expectedResultMessage}.<br> 2. {@code Storage} and
     * {@code IssueListPanel} equal to the corresponding components in {@code expectedModel}.<br>
     *
     * @see AddCommandSystemTest#assertCommandSuccess(String, Issue)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchangedExceptSyncStatus();
    }

    /**
     * Executes {@code command} and asserts that the,<br> 1. Command box displays {@code command}.<br> 2.
     * Command box has the error style class.<br> 3. Result display box displays {@code
     * expectedResultMessage}.<br> 4. {@code Storage} and {@code IssueListPanel} remain unchanged.<br> 5.
     * Browser url, selected card and status bar remain unchanged.<br> Verifications 1, 3 and 4 are performed
     * by {@code SaveItSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     *
     * @see SaveItSystemTest#assertApplicationDisplaysExpected(String, String, Model)
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

package systemtests;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DEADLINE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DEADLINE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.FREQUENCY_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.FREQUENCY_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DEADLINE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_FREQUENCY_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PRIORITY_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PRIORITY_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PRIORITY_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_FREQUENCY_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PRIORITY_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_TASKS;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_TASK;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_TASK;
import static seedu.address.testutil.TypicalTasks.AMY;
import static seedu.address.testutil.TypicalTasks.BOB;
import static seedu.address.testutil.TypicalTasks.KEYWORD_MATCHING_MEIER;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.Model;
import seedu.address.model.tag.Tag;
import seedu.address.model.task.Deadline;
import seedu.address.model.task.Frequency;
import seedu.address.model.task.Name;
import seedu.address.model.task.Priority;
import seedu.address.model.task.Task;
import seedu.address.testutil.TaskBuilder;

public class EditCommandSystemTest extends TaskCollectionSystemTest {

    @Test
    public void edit() {
        Model model = getModel();

        /* ----------------- Performing edit operation while an unfiltered list is being shown ---------------------- */

        /* Case: edit all fields, command with leading spaces, trailing spaces and multiple spaces between each field
         * -> edited
         */
        Index index = INDEX_FIRST_TASK;
        String command = " " + EditCommand.COMMAND_WORD + "  " + index.getOneBased() + "   " + NAME_DESC_BOB
                + "  " + PRIORITY_DESC_BOB + "  " + FREQUENCY_DESC_BOB + "  "
                + DEADLINE_DESC_BOB + " " + TAG_DESC_HUSBAND + " ";
        Task uneditedTask = getModel().getFilteredTaskList().get(index.getZeroBased());
        Task bobWithOriginalAttachments = new TaskBuilder(BOB)
                .withAttachments(uneditedTask.getAttachments())
                .build();
        Task editedTask = new TaskBuilder(bobWithOriginalAttachments)
                .withTags(VALID_TAG_HUSBAND)
                .build();
        assertCommandSuccess(command, index, editedTask);

        /* Case: undo editing the last task in the list -> last task restored */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: redo editing the last task in the list -> last task edited again */
        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        model.updateTask(
                getModel().getFilteredTaskList().get(INDEX_FIRST_TASK.getZeroBased()),
                editedTask);
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: edit a task with new values same as existing values -> edited */
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + NAME_DESC_BOB
                + PRIORITY_DESC_BOB + FREQUENCY_DESC_BOB + DEADLINE_DESC_BOB + TAG_DESC_FRIEND + TAG_DESC_HUSBAND;
        assertCommandSuccess(command, index, bobWithOriginalAttachments);

        /* Case: edit a task with new values same as another task's values but with different name -> edited */
        assertTrue(getModel().getTaskCollection().getTaskList().contains(bobWithOriginalAttachments));
        index = INDEX_SECOND_TASK;
        assertNotEquals(getModel().getFilteredTaskList().get(index.getZeroBased()), editedTask);
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + NAME_DESC_AMY
                + PRIORITY_DESC_BOB + FREQUENCY_DESC_BOB + DEADLINE_DESC_BOB + TAG_DESC_FRIEND + TAG_DESC_HUSBAND;
        editedTask = new TaskBuilder(bobWithOriginalAttachments).withName(VALID_NAME_AMY).build();
        assertCommandSuccess(command, index, editedTask);

        /* Case: edit a task with new values same as another task's values
         * -> edited
         */
        index = INDEX_SECOND_TASK;
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + NAME_DESC_BOB
                + PRIORITY_DESC_AMY + FREQUENCY_DESC_AMY + TAG_DESC_FRIEND + TAG_DESC_HUSBAND;
        editedTask =
                new TaskBuilder(bobWithOriginalAttachments)
                        .withPriority(VALID_PRIORITY_AMY)
                        .withFrequency(VALID_FREQUENCY_AMY)
                        .build();
        assertCommandSuccess(command, index, editedTask);

        /* Case: clear tags -> cleared */
        index = INDEX_FIRST_TASK;
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + " " + PREFIX_TAG.getPrefix();
        Task taskToEdit = getModel().getFilteredTaskList().get(index.getZeroBased());
        editedTask = new TaskBuilder(taskToEdit).withTags().build();
        assertCommandSuccess(command, index, editedTask);

        /* ------------------ Performing edit operation while a filtered list is being shown ------------------------ */

        /* Case: filtered task list, edit index within bounds of deadline manager and task list -> edited */
        showTasksWithName(KEYWORD_MATCHING_MEIER);
        index = INDEX_FIRST_TASK;
        assertTrue(index.getZeroBased() < getModel().getFilteredTaskList().size());
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + " " + NAME_DESC_BOB;
        taskToEdit = getModel().getFilteredTaskList().get(index.getZeroBased());
        editedTask = new TaskBuilder(taskToEdit).withName(VALID_NAME_BOB).build();
        assertCommandSuccess(command, index, editedTask);

        /* Case: filtered task list, edit index within bounds of deadline manager but out of bounds of task list
         * -> rejected
         */
        showTasksWithName(KEYWORD_MATCHING_MEIER);
        int invalidIndex = getModel().getTaskCollection().getTaskList().size();
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + invalidIndex + NAME_DESC_BOB,
                Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);

        /* --------------------- Performing edit operation while a task card is selected -------------------------- */

        /* Case: selects first card in the task list, edit a task -> edited, card selection remains unchanged but
         * browser url changes
         */
        showAllTasks();
        index = INDEX_FIRST_TASK;
        selectTask(index);
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + NAME_DESC_AMY
                + PRIORITY_DESC_AMY + FREQUENCY_DESC_AMY + DEADLINE_DESC_AMY + TAG_DESC_FRIEND;
        // this can be misleading: card selection actually remains unchanged but the
        // browser's url is updated to reflect the new task's name
        Task amyWithAttachments = new TaskBuilder(AMY)
                .withAttachments(getModel().getFilteredTaskList().get(index.getZeroBased()).getAttachments())
                .build();
        assertCommandSuccess(command, index, amyWithAttachments, index);

        /* --------------------------------- Performing invalid edit operation -------------------------------------- */

        /* Case: invalid index (0) -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " 0" + NAME_DESC_BOB,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));

        /* Case: invalid index (-1) -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " -1" + NAME_DESC_BOB,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));

        /* Case: invalid index (size + 1) -> rejected */
        invalidIndex = getModel().getFilteredTaskList().size() + 1;
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + invalidIndex + NAME_DESC_BOB,
                Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);

        /* Case: missing index -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + NAME_DESC_BOB,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));

        /* Case: missing all fields -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + INDEX_FIRST_TASK.getOneBased(),
                EditCommand.MESSAGE_NOT_EDITED);

        /* Case: invalid name -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + INDEX_FIRST_TASK.getOneBased()
                + INVALID_NAME_DESC, Name.MESSAGE_NAME_CONSTRAINTS);

        /* Case: invalid priority -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + INDEX_FIRST_TASK.getOneBased()
                + INVALID_PRIORITY_DESC, Priority.MESSAGE_PRIORITY_CONSTRAINTS);

        /* Case: invalid frequency -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + INDEX_FIRST_TASK.getOneBased()
                + INVALID_FREQUENCY_DESC, Frequency.MESSAGE_FREQUENCY_CONSTRAINTS);

        /* Case: invalid deadline -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + INDEX_FIRST_TASK.getOneBased()
                + INVALID_DEADLINE_DESC, Deadline.MESSAGE_DEADLINE_CONSTRAINTS);

        /* Case: invalid tag -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + INDEX_FIRST_TASK.getOneBased()
                + INVALID_TAG_DESC, Tag.MESSAGE_TAG_CONSTRAINTS);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Index, Task, Index)}
     * except that the browser url and selected card remain unchanged.
     *
     * @param toEdit the index of the current model's filtered list
     * @see EditCommandSystemTest#assertCommandSuccess(String, Index, Task, Index)
     */
    private void assertCommandSuccess(String command, Index toEdit, Task editedTask) {
        assertCommandSuccess(command, toEdit, editedTask, null);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Model, String, Index)}
     * and in addition,<br> 1. Asserts that result display box displays the success message of
     * executing {@code EditCommand}.<br> 2. Asserts that the model related components are updated
     * to reflect the task at index {@code toEdit} being updated to values specified {@code
     * editedTask}.<br>
     *
     * @param toEdit the index of the current model's filtered list.
     * @see EditCommandSystemTest#assertCommandSuccess(String, Model, String, Index)
     */
    private void assertCommandSuccess(String command, Index toEdit, Task editedTask,
                                      Index expectedSelectedCardIndex) {
        Model expectedModel = getModel();
        expectedModel.updateTask(expectedModel.getFilteredTaskList().get(toEdit.getZeroBased()),
                editedTask);
        expectedModel.updateFilteredTaskList(PREDICATE_SHOW_ALL_TASKS);

        assertCommandSuccess(command, expectedModel,
                String.format(EditCommand.MESSAGE_EDIT_TASK_SUCCESS, editedTask),
                expectedSelectedCardIndex);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Model, String, Index)}
     * except that the browser url and selected card remain unchanged.
     *
     * @see EditCommandSystemTest#assertCommandSuccess(String, Model, String, Index)
     */
    private void assertCommandSuccess(String command, Model expectedModel,
                                      String expectedResultMessage) {
        assertCommandSuccess(command, expectedModel, expectedResultMessage, null);
    }

    /**
     * Executes {@code command} and in addition,<br> 1. Asserts that the command box displays an
     * empty string.<br> 2. Asserts that the result display box displays {@code
     * expectedResultMessage}.<br> 3. Asserts that the browser url and selected card update
     * accordingly depending on the card at {@code expectedSelectedCardIndex}.<br> 4. Asserts that
     * the status bar's sync status changes.<br> 5. Asserts that the command box has the default
     * style class.<br> Verifications 1 and 2 are performed by {@code TaskCollectionSystemTest
     * #assertApplicationDisplaysExpected(String,
     * String, Model)}.<br>
     *
     * @see TaskCollectionSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     * @see TaskCollectionSystemTest#assertSelectedCardChanged(Index)
     */
    private void assertCommandSuccess(String command, Model expectedModel,
                                      String expectedResultMessage,
                                      Index expectedSelectedCardIndex) {
        executeCommand(command);
        expectedModel.updateFilteredTaskList(PREDICATE_SHOW_ALL_TASKS);
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
     * Executes {@code command} and in addition,<br> 1. Asserts that the command box displays {@code
     * command}.<br> 2. Asserts that result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the browser url, selected card and status bar remain unchanged.<br> 4.
     * Asserts that the command box has the error style.<br> Verifications 1 and 2 are performed by
     * {@code TaskCollectionSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     *
     * @see TaskCollectionSystemTest#assertApplicationDisplaysExpected(String, String, Model)
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

package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DUE_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LABEL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRIORITY_VALUE;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.TaskManager;
import seedu.address.model.task.NameContainsKeywordsPredicate;
import seedu.address.model.task.Status;
import seedu.address.model.task.Task;
import seedu.address.testutil.EditTaskDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_NAME_AMY = "Amy Bee";
    public static final String VALID_NAME_BOB = "Bob Choo";
    public static final String VALID_DUEDATE_AMY = "12-11-19";
    public static final String VALID_DUEDATE_BOB = "02-11-19";
    public static final String VALID_PRIORITY_VALUE_AMY = "1";
    public static final String VALID_PRIORITY_VALUE_BOB = "2";
    public static final String VALID_DESCRIPTION_AMY = "Description of Task A";
    public static final String VALID_DESCRIPTION_BOB = "Description of Task B";
    public static final String VALID_LABEL_HUSBAND = "husband";
    public static final String VALID_LABEL_FRIEND = "friend";
    public static final Status VALID_STATUS_IN_PROGRESS = Status.IN_PROGRESS;

    public static final String NAME_DESC_AMY = " " + PREFIX_NAME + VALID_NAME_AMY;
    public static final String NAME_DESC_BOB = " " + PREFIX_NAME + VALID_NAME_BOB;
    public static final String PHONE_DESC_AMY = " " + PREFIX_DUE_DATE + VALID_DUEDATE_AMY;
    public static final String PHONE_DESC_BOB = " " + PREFIX_DUE_DATE + VALID_DUEDATE_BOB;
    public static final String PRIORITY_VALUE_DESC_AMY = " " + PREFIX_PRIORITY_VALUE + VALID_PRIORITY_VALUE_AMY;
    public static final String PRIORITY_VALUE_DESC_BOB = " " + PREFIX_PRIORITY_VALUE + VALID_PRIORITY_VALUE_BOB;
    public static final String DESCRIPTION_DESC_AMY = " " + PREFIX_DESCRIPTION + VALID_DESCRIPTION_AMY;
    public static final String DESCRIPTION_DESC_BOB = " " + PREFIX_DESCRIPTION + VALID_DESCRIPTION_BOB;
    public static final String LABEL_DESC_FRIEND = " " + PREFIX_LABEL + VALID_LABEL_FRIEND;
    public static final String LABEL_DESC_HUSBAND = " " + PREFIX_LABEL + VALID_LABEL_HUSBAND;

    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "James&"; // '&' not allowed in names
    public static final String INVALID_PHONE_DESC = " " + PREFIX_DUE_DATE + "911a"; // 'a' not allowed in phones
    public static final String INVALID_PRIORITY_VALUE_DESC = " " + PREFIX_PRIORITY_VALUE + "0"; // only positive ints
    // empty string not allowed for addresses
    public static final String INVALID_DESCRIPTION_DESC = " " + PREFIX_DESCRIPTION;
    public static final String INVALID_LABEL_DESC = " " + PREFIX_LABEL + "hubby*"; // '*' not allowed in labels

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditCommand.EditTaskDescriptor DESC_AMY;
    public static final EditCommand.EditTaskDescriptor DESC_BOB;

    static {
        DESC_AMY = new EditTaskDescriptorBuilder().withName(VALID_NAME_AMY)
                .withDueDate(VALID_DUEDATE_AMY)
                .withPriorityValue(VALID_PRIORITY_VALUE_AMY)
                .withAddress(VALID_DESCRIPTION_AMY)
                .withLabels(VALID_LABEL_FRIEND).build();
        DESC_BOB = new EditTaskDescriptorBuilder().withName(VALID_NAME_BOB)
                .withDueDate(VALID_DUEDATE_BOB)
                .withPriorityValue(VALID_PRIORITY_VALUE_BOB)
                .withAddress(VALID_DESCRIPTION_BOB)
                .withLabels(VALID_LABEL_HUSBAND, VALID_LABEL_FRIEND).build();
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the result message matches {@code expectedMessage} <br>
     * - the {@code actualModel} matches {@code expectedModel} <br>
     * - the {@code actualCommandHistory} remains unchanged.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandHistory actualCommandHistory,
                                            String expectedMessage, Model expectedModel) {
        CommandHistory expectedCommandHistory = new CommandHistory(actualCommandHistory);
        try {
            CommandResult result = command.executePrimitive(actualModel, actualCommandHistory);
            assertEquals(expectedMessage, result.feedbackToUser);
            assertEquals(expectedModel, actualModel);
            assertEquals(expectedCommandHistory, actualCommandHistory);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the set of result message tokens matches {@code expectedTokens} <br>
     * - the {@code actualModel} matches {@code expectedModel} <br>
     * - the {@code actualCommandHistory} remains unchanged.
     *
     * Note that the order of result message separated by a new line need not matter.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandHistory actualCommandHistory,
                                            Set<String> expectedTokens, Model expectedModel) {
        CommandHistory expectedCommandHistory = new CommandHistory(actualCommandHistory);
        try {
            CommandResult result = command.executePrimitive(actualModel, actualCommandHistory);
            assertEquals(expectedTokens, feedbackMessageTokenizer(result.feedbackToUser));
            assertEquals(expectedModel, actualModel);
            assertEquals(expectedCommandHistory, actualCommandHistory);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Returns a set of String split by a newline.
     */
    public static Set<String> feedbackMessageTokenizer(String feedbackMessage) {
        return Set.of(feedbackMessage.split("\\R+"));
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the task manager and the filtered task list in the {@code actualModel} remain unchanged
     * <br>
     * - {@code actualCommandHistory} remains unchanged.
     */
    public static void assertCommandFailure(Command command, Model actualModel, CommandHistory actualCommandHistory,
                                            String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        TaskManager expectedAddressBook = new TaskManager(actualModel.getTaskManager());
        List<Task> expectedFilteredList = new ArrayList<>(actualModel.getFilteredTaskList());

        CommandHistory expectedCommandHistory = new CommandHistory(actualCommandHistory);

        try {
            command.executePrimitive(actualModel, actualCommandHistory);
            throw new AssertionError("The expected CommandException was not thrown.");
        } catch (CommandException e) {
            assertEquals(expectedMessage, e.getMessage());
            assertEquals(expectedAddressBook, actualModel.getTaskManager());
            assertEquals(expectedFilteredList, actualModel.getFilteredTaskList());
            assertEquals(expectedCommandHistory, actualCommandHistory);
        }
    }

    /**
     * Updates {@code model}'s filtered list to show only the task at the given {@code targetIndex}
     * in the {@code model}'s task manager.
     */
    public static void showTaskAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredTaskList().size());

        Task task = model.getFilteredTaskList().get(targetIndex.getZeroBased());
        final String[] splitName = task.getName().fullName.split("\\s+");
        model.updateFilteredTaskList(new NameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredTaskList().size());
    }
    /**
     * Updates {@code model}'s filtered list to show only the two tasks at {@code firstTargetIndex}
     * and {@code secondTargetIndex} in the {@code model}'s task manager.
     */
    public static void showTaskAtTwoIndexes(Model model, Index firstTargetIndex, Index secondTargetIndex) {
        assertTrue(firstTargetIndex.getZeroBased() < model.getFilteredTaskList().size());
        assertTrue(secondTargetIndex.getZeroBased() < model.getFilteredTaskList().size());

        Task firstTask = model.getFilteredTaskList().get(firstTargetIndex.getZeroBased());
        final String[] firstSplitName = firstTask.getName().fullName.split("\\s+");

        Task secondTask = model.getFilteredTaskList().get(secondTargetIndex.getZeroBased());
        final String[] secondSplitName = secondTask.getName().fullName.split("\\s+");

        model.updateFilteredTaskList(new NameContainsKeywordsPredicate(Arrays.asList(firstSplitName[0],
                secondSplitName[0])));

        assertEquals(2, model.getFilteredTaskList().size());
    }

    /**
     * Deletes the first task in {@code model}'s filtered list from {@code model}'s task manager.
     */
    public static void deleteFirstTask(Model model) {
        Task firstTask = model.getFilteredTaskList().get(0);
        model.deleteTask(firstTask);
        model.commitTaskManager();
    }

}

package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.feedbackMessageTokenizer;
import static seedu.address.testutil.LabelsBuilder.createLabelsFromKeywords;
import static seedu.address.testutil.TypicalTasks.getTypicalDependentTaskManager;
import static seedu.address.testutil.TypicalTasks.getTypicalTaskManager;

import java.util.Set;
import java.util.function.Predicate;

import org.junit.Test;

import javafx.util.Pair;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.task.LabelMatchesAnyKeywordPredicate;
import seedu.address.model.task.Status;
import seedu.address.model.task.Task;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand, Dependencies)
 * and unit tests for {@code CompleteLabelCommand}.
 */
public class CompleteLabelCommandTest {

    private static final String KEYWORD_FRIENDS = "friends";
    private static final String KEYWORD_OWESMONEY = "owesMoney";
    private static final LabelMatchesAnyKeywordPredicate PREDICATE_FRIENDS =
        new LabelMatchesAnyKeywordPredicate(createLabelsFromKeywords(KEYWORD_FRIENDS));
    private static final LabelMatchesAnyKeywordPredicate PREDICATE_OWESMONEY =
        new LabelMatchesAnyKeywordPredicate(createLabelsFromKeywords(KEYWORD_OWESMONEY));
    private static final LabelMatchesAnyKeywordPredicate PREDICATE_OWESMONEY_FRIENDS =
        new LabelMatchesAnyKeywordPredicate(createLabelsFromKeywords(KEYWORD_OWESMONEY, KEYWORD_FRIENDS));
    private static final LabelMatchesAnyKeywordPredicate PREDICATE_NONSENSE =
        new LabelMatchesAnyKeywordPredicate(createLabelsFromKeywords("AOSDIJPQWEOIDJPQWOiodj120349871238493qw"));
    private Model model = new ModelManager(getTypicalTaskManager(), new UserPrefs());
    private Model dependentModel = new ModelManager(getTypicalDependentTaskManager(), new UserPrefs());


    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_validLabel_success() {
        assertCompleteLabelCommandSuccess(PREDICATE_FRIENDS, model, KEYWORD_FRIENDS);
    }

    @Test
    public void execute_invalidLabel_throwsCommandException() {
        CompleteCommand completeCommand = new CompleteLabelCommand(PREDICATE_NONSENSE);

        assertCommandFailure(completeCommand, model, commandHistory,
            CompleteCommand.MESSAGE_NO_COMPLETABLE_TASK_IDENTIFIED_BY_LABEL);
    }

    @Test
    public void executeValidDependencies_validLabel_success() {
        assertCompleteLabelCommandSuccess(PREDICATE_FRIENDS, dependentModel, KEYWORD_FRIENDS);
        assertCompleteLabelCommandSuccess(
            PREDICATE_OWESMONEY_FRIENDS, dependentModel, KEYWORD_FRIENDS, KEYWORD_OWESMONEY);
    }

    @Test
    public void execute_invalidDependenciesValidLabel_throwsCommandException() {
        CompleteCommand completeCommand = new CompleteLabelCommand(PREDICATE_OWESMONEY);

        assertCommandFailure(completeCommand, dependentModel, commandHistory,
            CompleteCommand.MESSAGE_UNFULFILLED_DEPENDENCIES);
    }

    @Test
    public void executeUndoRedo_validLabel_success() throws Exception {
        CompleteCommand completeCommand = new CompleteLabelCommand(PREDICATE_FRIENDS);

        Model expectedModel = new ModelManager(model.getTaskManager(), new UserPrefs());
        Pair<Model, Set<String>> modelStringPair = produceExpectedModelExpectedMessagePairOnLabelKeywordMatch(
            expectedModel,
            KEYWORD_FRIENDS);
        expectedModel = modelStringPair.getKey();

        // complete -> first task completed
        completeCommand.execute(model, commandHistory);

        // undo -> reverts task manager back to previous state and filtered task list to show all
        // tasks
        expectedModel.undoTaskManager();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first task completed again
        expectedModel.redoTaskManager();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }


    @Test
    public void executeUndoRedo_invalidLabel_failure() {
        CompleteCommand completeCommand = new CompleteLabelCommand(PREDICATE_NONSENSE);

        // execution failed -> task manager state not added into model
        assertCommandFailure(completeCommand, model, commandHistory,
            CompleteCommand.MESSAGE_NO_COMPLETABLE_TASK_IDENTIFIED_BY_LABEL);

        // single task manager state in model -> undoCommand and redoCommand fail
        assertCommandFailure(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_FAILURE);
    }

    @Test
    public void equals() {
        CompleteCommand completeFirstCommand = new CompleteLabelCommand(PREDICATE_NONSENSE);
        CompleteCommand completeSecondCommand = new CompleteLabelCommand(PREDICATE_FRIENDS);

        // same object -> returns true
        assertTrue(completeFirstCommand.equals(completeFirstCommand));

        // same values -> returns true
        CompleteCommand completeFirstCommandCopy = new CompleteLabelCommand(PREDICATE_NONSENSE);
        assertTrue(completeFirstCommand.equals(completeFirstCommandCopy));

        // different types -> returns false
        assertFalse(completeFirstCommand.equals(1));

        // null -> returns false
        assertFalse(completeFirstCommand.equals(null));

        // different task -> returns false
        assertFalse(completeFirstCommand.equals(completeSecondCommand));
    }

    /**
     * Creates a new completed task from given task.
     *
     * @param taskToComplete taskToComplete will be treated as the immutable task to copy attributes from.
     * @return A new completed version of taskToComplete.
     */
    private Task simpleCompleteTask(Task taskToComplete) {
        return new Task(// returns a completed task.
            taskToComplete.getName(),
            taskToComplete.getDueDate(),
            taskToComplete.getPriorityValue(),
            taskToComplete.getDescription(),
            taskToComplete.getLabels(),
            Status.COMPLETED,
            taskToComplete.getDependency()
        );
    }

    /**
     * Wrapper method for the assertion of success of the CompleteLabelCommand
     *
     * @param predicate predicate to be used for the actual model
     * @param model a reference model to be used for both the expected and actual model
     * @param keywords {@code Strings} representing labels for the expected model
     */
    private void assertCompleteLabelCommandSuccess(Predicate<Task> predicate, Model model, String... keywords) {
        // Creating expected data
        Model expectedModel = new ModelManager(model.getTaskManager(), new UserPrefs());

        Pair<Model, Set<String>> modelStringPair =
            produceExpectedModelExpectedMessagePairOnLabelKeywordMatch(expectedModel, keywords);
        expectedModel = modelStringPair.getKey();
        Set<String> expectedTokens = modelStringPair.getValue();

        // Creating actual data.
        // Creates a defensive copy of the model so that no side effects would be present in the
        // test file's model.
        Model actualModel = new ModelManager(model.getTaskManager(), new UserPrefs());

        CompleteCommand completeCommand = new CompleteLabelCommand(predicate);
        assertCommandSuccess(completeCommand, actualModel, commandHistory, expectedTokens, expectedModel);
    }

    /**
     * Helper method for more complicated batch completion on {@code Label} match.
     *
     * @param model model to apply side effects and to read data from.
     * @param labelStrings {@code Strings} represent the labels to be checked for a match
     * @return an Expected-Model Expected-String pair
     */
    private Pair<Model, Set<String>> produceExpectedModelExpectedMessagePairOnLabelKeywordMatch(
        Model model, String... labelStrings) {

        ModelManager expectedModel = new ModelManager(model.getTaskManager(), new UserPrefs());
        int oldXp = expectedModel.getXpValue();
        StringBuilder completedTasksOutput = new StringBuilder();
        completeTasksUpdateModelAndProcessCompleteTasksOutput(expectedModel, completedTasksOutput, labelStrings);

        // get change in xp
        int newXp = expectedModel.getXpValue();
        int xpChange = newXp - oldXp;

        expectedModel.commitTaskManager();

        String expectedMessage = String.format(
            CompleteCommand.MESSAGE_SUCCESS,
            xpChange,
            completedTasksOutput.toString().trim());

        Set<String> expectedTokens = feedbackMessageTokenizer(expectedMessage);

        return new Pair<>(expectedModel, expectedTokens);
    }

    /**
     * Completes the tasks to complete in the model and updating the changes via side effects
     *
     * @param expectedModel model to update and fetch tasks to complete from
     * @param completedTasksOutput a StringBuilder to allow the updating of output through sideeffects
     * @param labelStrings {@String} representation of labels to complete
     */
    private void completeTasksUpdateModelAndProcessCompleteTasksOutput(
        ModelManager expectedModel, StringBuilder completedTasksOutput, String... labelStrings) {
        // Updates the model with completable tasks that fulfils the predicate completed and append
        // each of their String representation to expectedMessage
        expectedModel
            .getFilteredTaskList()
            .stream()
            .map(task -> new Pair<>(task, simpleCompleteTask(task)))
            // filters for label match and completable tasks
            .filter(pairOfTasks -> {
                Task taskToComplete = pairOfTasks.getKey();

                return !taskToComplete.isStatusCompleted()
                    && Set.of(labelStrings)
                    .stream()
                    .anyMatch(labelString ->
                        taskToComplete
                            .getLabels()
                            .stream()
                            .anyMatch(label -> label.labelName.toLowerCase().equals(labelString.toLowerCase())));
            })
            .forEach(pairOfTasks -> {
                Task taskToComplete = pairOfTasks.getKey();
                Task completedTask = pairOfTasks.getValue();
                expectedModel.updateTaskStatus(taskToComplete, completedTask);
                completedTasksOutput.append(completedTask.toString() + "\n");
            });
    }

}

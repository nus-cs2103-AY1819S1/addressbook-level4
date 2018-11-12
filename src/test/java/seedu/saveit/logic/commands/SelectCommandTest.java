package seedu.saveit.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.saveit.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.saveit.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.saveit.logic.commands.CommandTestUtil.showIssueAtIndex;
import static seedu.saveit.testutil.TypicalIndexes.INDEX_FIRST_ISSUE;
import static seedu.saveit.testutil.TypicalIndexes.INDEX_FIRST_SOLUTION;
import static seedu.saveit.testutil.TypicalIndexes.INDEX_SECOND_ISSUE;
import static seedu.saveit.testutil.TypicalIndexes.INDEX_THIRD_ISSUE;
import static seedu.saveit.testutil.TypicalIssues.getTypicalSaveIt;

import org.junit.Rule;
import org.junit.Test;

import seedu.saveit.commons.core.Messages;
import seedu.saveit.commons.core.directory.Directory;
import seedu.saveit.commons.core.index.Index;
import seedu.saveit.commons.events.ui.JumpToListRequestEvent;
import seedu.saveit.commons.events.ui.JumpToSolutionListRequestEvent;
import seedu.saveit.logic.CommandHistory;
import seedu.saveit.model.Model;
import seedu.saveit.model.ModelManager;
import seedu.saveit.model.UserPrefs;
import seedu.saveit.testutil.DirectoryBuilder;
import seedu.saveit.ui.testutil.EventsCollectorRule;

/**
 * Contains integration tests (interaction with the Model) for {@code SelectCommand}.
 */
public class SelectCommandTest {
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    private Model model = new ModelManager(getTypicalSaveIt(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalSaveIt(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_validIssueIndexUnfilteredList_success() {
        Index lastIssueIndex = Index.fromOneBased(model.getFilteredAndSortedIssueList().size());

        assertExecutionSuccess(INDEX_FIRST_ISSUE);
        model.resetDirectory(Directory.formRootDirectory());
        assertExecutionSuccess(INDEX_THIRD_ISSUE);
        model.resetDirectory(Directory.formRootDirectory());
        assertExecutionSuccess(lastIssueIndex);
    }

    @Test
    public void execute_invalidIssueIndexUnfilteredList_failure() {
        Index outOfBoundsIndex = Index.fromOneBased(model.getFilteredAndSortedIssueList().size() + 1);

        assertExecutionFailure(outOfBoundsIndex,
                Messages.MESSAGE_INVALID_ISSUE_DISPLAYED_INDEX + "\n" + SelectCommand.MESSAGE_USAGE);
    }

    @Test
    public void execute_validIssueIndexFilteredList_success() {
        showIssueAtIndex(model, INDEX_FIRST_ISSUE);
        showIssueAtIndex(expectedModel, INDEX_FIRST_ISSUE);

        assertExecutionSuccess(INDEX_FIRST_ISSUE);
    }

    @Test
    public void execute_invalidIssueIndexFilteredList_failure() {
        showIssueAtIndex(model, INDEX_FIRST_ISSUE);
        showIssueAtIndex(expectedModel, INDEX_FIRST_ISSUE);

        Index outOfBoundsIndex = INDEX_SECOND_ISSUE;
        // ensures that outOfBoundIndex is still in bounds of saveit book list
        assertTrue(outOfBoundsIndex.getZeroBased() < model.getSaveIt().getIssueList().size());

        assertExecutionFailure(outOfBoundsIndex,
                Messages.MESSAGE_INVALID_ISSUE_DISPLAYED_INDEX + "\n" + SelectCommand.MESSAGE_USAGE);
    }

    @Test
    public void execute_validSolutionIndex_success() {
        model.resetDirectory(new DirectoryBuilder().withIssueIndex(INDEX_THIRD_ISSUE).build());
        expectedModel.resetDirectory(new DirectoryBuilder().withIssueIndex(INDEX_THIRD_ISSUE).build());
        eventsCollectorRule.eventsCollector.reset();

        Index lastSolutionIndex = Index.fromOneBased(model.getFilteredAndSortedIssueList()
                .get(INDEX_THIRD_ISSUE.getZeroBased()).getSolutions().size());
        assertExecutionSuccess(INDEX_THIRD_ISSUE, INDEX_FIRST_SOLUTION);
        assertExecutionSuccess(INDEX_THIRD_ISSUE, lastSolutionIndex);
    }

    @Test
    public void execute_invalidSolutionIndex_failure() {
        model.resetDirectory(new DirectoryBuilder().withIssueIndex(INDEX_THIRD_ISSUE).build());
        expectedModel.resetDirectory(new DirectoryBuilder().withIssueIndex(INDEX_THIRD_ISSUE).build());
        eventsCollectorRule.eventsCollector.reset();

        Index outOfBoundsIndex = Index.fromOneBased(model.getFilteredAndSortedIssueList()
                .get(INDEX_THIRD_ISSUE.getZeroBased()).getSolutions().size() + 1);
        assertExecutionFailure(outOfBoundsIndex,
                Messages.MESSAGE_INVALID_SOLUTION_DISPLAYED_INDEX + "\n" + SelectCommand.MESSAGE_USAGE);
    }

    @Test
    public void equals() {
        SelectCommand selectFirstCommand = new SelectCommand(INDEX_FIRST_ISSUE);
        SelectCommand selectSecondCommand = new SelectCommand(INDEX_SECOND_ISSUE);

        // same object -> returns true
        assertTrue(selectFirstCommand.equals(selectFirstCommand));

        // same values -> returns true
        SelectCommand selectFirstCommandCopy = new SelectCommand(INDEX_FIRST_ISSUE);
        assertTrue(selectFirstCommand.equals(selectFirstCommandCopy));

        // different types -> returns false
        assertFalse(selectFirstCommand.equals(1));

        // null -> returns false
        assertFalse(selectFirstCommand.equals(null));

        // different issue -> returns false
        assertFalse(selectFirstCommand.equals(selectSecondCommand));
    }

    /**
     * Executes a {@code SelectCommand} with the given {@code index}, and checks that {@code JumpToListRequestEvent}
     * is raised with the correct index.
     */
    private void assertExecutionSuccess(Index index) {
        SelectCommand selectCommand = new SelectCommand(index);
        String expectedMessage = String.format(SelectCommand.MESSAGE_SELECT_ISSUE_SUCCESS, index.getOneBased());

        assertCommandSuccess(selectCommand, model, commandHistory, expectedMessage, expectedModel);

        JumpToListRequestEvent lastEvent = (JumpToListRequestEvent) eventsCollectorRule.eventsCollector.getMostRecent();
        assertEquals(index, Index.fromZeroBased(lastEvent.targetIndex));
    }

    /**
     * Executes a {@code SelectCommand} with the given {@code index}, and checks that {@code JumpToListRequestEvent}
     * is raised with the correct index.
     */
    private void assertExecutionSuccess(Index issueIndex, Index solutionIndex) {
        SelectCommand selectCommand = new SelectCommand(solutionIndex);
        String expectedMessage = String.format(SelectCommand.MESSAGE_SELECT_ISSUE_SUCCESS, issueIndex.getOneBased())
                + String.format(SelectCommand.MESSAGE_SELECT_SOLUTION_SUCCESS, solutionIndex.getOneBased());

        assertCommandSuccess(selectCommand, model, commandHistory, expectedMessage, expectedModel);

        JumpToSolutionListRequestEvent lastEvent = (JumpToSolutionListRequestEvent) eventsCollectorRule
                .eventsCollector.getMostRecent();
        assertEquals(solutionIndex, Index.fromZeroBased(lastEvent.targetIndex));
    }

    /**
     * Executes a {@code SelectCommand} with the given {@code index}, and checks that a {@code CommandException}
     * is thrown with the {@code expectedMessage}.
     */
    private void assertExecutionFailure(Index index, String expectedMessage) {
        SelectCommand selectCommand = new SelectCommand(index);
        assertCommandFailure(selectCommand, model, commandHistory, expectedMessage);
        assertTrue(eventsCollectorRule.eventsCollector.isEmpty());
    }
}

package seedu.saveit.logic.commands;

import static seedu.saveit.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.saveit.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.saveit.testutil.TypicalIndexes.INDEX_FIRST_SOLUTION;
import static seedu.saveit.testutil.TypicalIssues.getTypicalSaveIt;

import org.junit.Before;
import org.junit.Test;

import seedu.saveit.commons.core.Messages;
import seedu.saveit.commons.core.directory.Directory;
import seedu.saveit.commons.core.index.Index;
import seedu.saveit.logic.CommandHistory;
import seedu.saveit.model.Issue;
import seedu.saveit.model.Model;
import seedu.saveit.model.ModelManager;
import seedu.saveit.model.SaveIt;
import seedu.saveit.model.UserPrefs;
import seedu.saveit.testutil.DirectoryBuilder;

public class ResetPrimaryCommandTest {
    private Model model;
    private Model expectedModel;
    private CommandHistory commandHistory;
    private Index selectedIssueOneBasedIndex;
    private Issue issueSelected;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalSaveIt(), new UserPrefs());
        expectedModel = new ModelManager(new SaveIt(model.getSaveIt()), new UserPrefs());
        commandHistory = new CommandHistory();
    }

    @Test
    public void execute_onPrimarySolutionUnderIssueLevel_success() {
        setUpIssueLevel();

        assertExecutionSuccess();
    }

    @Test
    public void execute_hasPrimarySolutionUnderIssueLevel_success() {
        setUpIssueLevel();
        expectedModel.updateIssue(issueSelected, issueSelected.setPrimarySolution(INDEX_FIRST_SOLUTION.getOneBased()));
        model.updateIssue(issueSelected, issueSelected.setPrimarySolution(INDEX_FIRST_SOLUTION.getOneBased()));

        assertExecutionSuccess();
    }

    @Test
    public void execute_underRootLevel_success() {
        Directory rootDirectory = Directory.formRootDirectory();
        model.resetDirectory(rootDirectory);
        expectedModel.resetDirectory(rootDirectory);

        assertCommandFailure(new ResetPrimaryCommand(), model, commandHistory, Messages.MESSAGE_WRONG_DIRECTORY);
    }

    /**
     * Executes a {@code ReSetPrimaryCommand} with the given {@code index}.
     */
    private void assertExecutionSuccess() {
        ResetPrimaryCommand resetPrimaryCommand = new ResetPrimaryCommand();
        expectedModel.updateIssue(issueSelected,
                issueSelected.resetPrimarySolution());
        expectedModel.commitSaveIt();
        String expectedMessage = ResetPrimaryCommand.MESSAGE_SUCCESS;

        assertCommandSuccess(resetPrimaryCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    /**
     * Reset the directory of model and expected to Issue Level.
     */
    private void setUpIssueLevel() {
        selectedIssueOneBasedIndex = Index.fromOneBased(4);
        Directory newDirectory = new DirectoryBuilder().withIssueIndex(selectedIssueOneBasedIndex).build();
        model.resetDirectory(newDirectory);
        expectedModel.resetDirectory(newDirectory);
        issueSelected = expectedModel.getFilteredAndSortedIssueList().get(selectedIssueOneBasedIndex.getZeroBased());
    }
}

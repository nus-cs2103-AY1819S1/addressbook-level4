package seedu.saveit.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static seedu.saveit.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.saveit.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.saveit.testutil.TypicalIndexes.INDEX_FIRST_ISSUE;
import static seedu.saveit.testutil.TypicalIndexes.INDEX_FIRST_SOLUTION;
import static seedu.saveit.testutil.TypicalIndexes.INDEX_SECOND_ISSUE;
import static seedu.saveit.testutil.TypicalIndexes.INDEX_SECOND_SOLUTION;
import static seedu.saveit.testutil.TypicalIndexes.INDEX_THIRD_ISSUE;
import static seedu.saveit.testutil.TypicalIndexes.INDEX_THIRD_SOLUTION;
import static seedu.saveit.testutil.TypicalIssues.getTypicalSaveIt;

import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;

import org.junit.Before;
import org.junit.Test;

import seedu.saveit.logic.CommandHistory;
import seedu.saveit.model.Model;
import seedu.saveit.model.ModelManager;
import seedu.saveit.model.UserPrefs;
import seedu.saveit.model.issue.solution.SolutionLink;
import seedu.saveit.testutil.DirectoryBuilder;

public class RetrieveCommandTest {

    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();
    private Model model;
    private Model expectedModel;
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalSaveIt(), new UserPrefs());
        expectedModel = new ModelManager(model.getSaveIt(), new UserPrefs());
    }

    @Test
    public void execute_retrieveValidIndexIssueLevel_success() {
        try {
            model.resetDirectory(new DirectoryBuilder().withIssueIndex(INDEX_THIRD_ISSUE).build());
            String expectedMessage = String.format(RetrieveCommand.MESSAGE_RETRIEVE_LINK_SUCCESS,
                    INDEX_FIRST_SOLUTION.getOneBased());
            RetrieveCommand retrieveCommand = new RetrieveCommand(INDEX_FIRST_SOLUTION);
            assertCommandSuccess(retrieveCommand, model, commandHistory, expectedMessage, model);
            SolutionLink link = new SolutionLink((String) Toolkit.getDefaultToolkit()
                    .getSystemClipboard().getData(DataFlavor.stringFlavor));
            SolutionLink expectedSolutionLink = model.getFilteredAndSortedIssueList()
                    .get(INDEX_THIRD_ISSUE.getZeroBased())
                    .getSolutions().get(INDEX_FIRST_SOLUTION.getZeroBased()).getLink();
            assertEquals(expectedSolutionLink, link);
            assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);
        } catch (Exception e) {
            throw new AssertionError("There should not be an error retrieving the solution link", e);
        }
    }

    @Test
    public void execute_retrieveValidIndexSolutionLevel_success() {
        try {
            model.resetDirectory(new DirectoryBuilder().withIssueIndex(INDEX_THIRD_ISSUE)
                    .withSolutionIndex(INDEX_FIRST_SOLUTION).build());
            String expectedMessage = String.format(RetrieveCommand.MESSAGE_RETRIEVE_LINK_SUCCESS,
                    INDEX_FIRST_SOLUTION.getOneBased());
            RetrieveCommand retrieveCommand = new RetrieveCommand(INDEX_FIRST_SOLUTION);
            assertCommandSuccess(retrieveCommand, model, commandHistory, expectedMessage, model);
            SolutionLink link = new SolutionLink((String) Toolkit.getDefaultToolkit()
                    .getSystemClipboard().getData(DataFlavor.stringFlavor));
            SolutionLink expectedSolutionLink = model.getFilteredAndSortedIssueList()
                    .get(INDEX_THIRD_ISSUE.getZeroBased())
                    .getSolutions().get(INDEX_FIRST_SOLUTION.getZeroBased()).getLink();
            assertEquals(expectedSolutionLink, link);
            assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);
        } catch (Exception e) {
            System.out.println(e);
            throw new AssertionError("There should not be an error retrieving the solution link", e);
        }
    }

    @Test
    public void execute_retrieveInvalidIndex_failure() {
        model.resetDirectory(new DirectoryBuilder().withIssueIndex(INDEX_THIRD_ISSUE).build());
        String expectedMessage = RetrieveCommand.MESSAGE_FAILED_SOLUTION;
        RetrieveCommand retrieveCommand = new RetrieveCommand(INDEX_THIRD_SOLUTION);
        assertCommandFailure(retrieveCommand, model, commandHistory, expectedMessage);
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);
    }

    @Test
    public void execute_retrieveInvalidDirectory_failure() {
        String expectedMessage = RetrieveCommand.MESSAGE_FAILED_SELECTION;
        RetrieveCommand retrieveCommand = new RetrieveCommand(INDEX_SECOND_SOLUTION);
        assertCommandFailure(retrieveCommand, model, commandHistory, expectedMessage);
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);
    }

    @Test
    public void equals() {
        final RetrieveCommand standardCommand = new RetrieveCommand(INDEX_FIRST_ISSUE);

        RetrieveCommand sameCommand = new RetrieveCommand(INDEX_FIRST_ISSUE);
        assertEquals(standardCommand, sameCommand);
        assertEquals(sameCommand, sameCommand);
        assertFalse(standardCommand.equals(null));
        assertFalse(standardCommand.equals(new RetrieveCommand(INDEX_SECOND_ISSUE)));
    }
}

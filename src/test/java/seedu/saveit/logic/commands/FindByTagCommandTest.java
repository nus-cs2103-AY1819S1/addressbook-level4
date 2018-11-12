package seedu.saveit.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.saveit.commons.core.Messages.MESSAGE_ISSUES_LISTED_OVERVIEW;
import static seedu.saveit.commons.core.Messages.MESSAGE_WRONG_DIRECTORY;
import static seedu.saveit.logic.commands.CommandTestUtil.VALID_TAG_SYNTAX;
import static seedu.saveit.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.saveit.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.saveit.testutil.TypicalIssues.CHECKSTYLE_ERROR;
import static seedu.saveit.testutil.TypicalIssues.C_RACE_CONDITION;
import static seedu.saveit.testutil.TypicalIssues.C_SEGMENTATION_FAULT;
import static seedu.saveit.testutil.TypicalIssues.JAVA_NULL_POINTER;
import static seedu.saveit.testutil.TypicalIssues.QUICKSORT_BUG;
import static seedu.saveit.testutil.TypicalIssues.RUBY_HASH_BUG;
import static seedu.saveit.testutil.TypicalIssues.TRAVIS_BUILD;
import static seedu.saveit.testutil.TypicalIssues.getTypicalSaveIt;

import java.util.Arrays;
import java.util.Collections;

import org.junit.Test;

import seedu.saveit.commons.core.directory.Directory;
import seedu.saveit.commons.core.index.Index;
import seedu.saveit.logic.CommandHistory;
import seedu.saveit.model.Model;
import seedu.saveit.model.ModelManager;
import seedu.saveit.model.SaveIt;
import seedu.saveit.model.UserPrefs;
import seedu.saveit.model.issue.IssueHasTagsPredicate;
import seedu.saveit.testutil.DirectoryBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code FindByTagCommand}.
 */
public class FindByTagCommandTest {
    private Model model = new ModelManager(getTypicalSaveIt(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalSaveIt(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void equals() {
        IssueHasTagsPredicate firstPredicate =
                new IssueHasTagsPredicate(Collections.singletonList("first"));
        IssueHasTagsPredicate secondPredicate =
                new IssueHasTagsPredicate(Collections.singletonList("second"));

        FindByTagCommand findByTagFirstCommand = new FindByTagCommand(firstPredicate);
        FindByTagCommand findByTagSecondCommand = new FindByTagCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findByTagFirstCommand.equals(findByTagFirstCommand));

        // save value -> returns true
        FindByTagCommand findByTagFirstCommandCopy = new FindByTagCommand(firstPredicate);
        assertTrue(findByTagFirstCommand.equals(findByTagFirstCommandCopy));

        // different types -> returns false
        assertFalse(findByTagFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findByTagFirstCommand.equals(null));

        // different tag -> returns false
        assertFalse(findByTagFirstCommand.equals(findByTagSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_allIssuesListed() {
        String expectedMessage = String.format(MESSAGE_ISSUES_LISTED_OVERVIEW, 7);
        IssueHasTagsPredicate predicate = new IssueHasTagsPredicate(Arrays.asList());
        FindByTagCommand command = new FindByTagCommand(predicate);
        expectedModel.updateFilteredIssueList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(
                Arrays.asList(JAVA_NULL_POINTER, C_SEGMENTATION_FAULT, RUBY_HASH_BUG, TRAVIS_BUILD,
                        CHECKSTYLE_ERROR, QUICKSORT_BUG, C_RACE_CONDITION),
                model.getFilteredAndSortedIssueList());
    }

    @Test
    public void execute_validKeywords_oneIssueListed() {
        String expectedMessage = String.format(MESSAGE_ISSUES_LISTED_OVERVIEW, 1);
        IssueHasTagsPredicate predicate = new IssueHasTagsPredicate(Arrays.asList(VALID_TAG_SYNTAX));
        FindByTagCommand command = new FindByTagCommand(predicate);
        expectedModel.updateFilteredIssueList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(JAVA_NULL_POINTER), model.getFilteredAndSortedIssueList());
    }

    @Test
    public void execute_nonExistentTagKeyword_noIssuesListed() {
        String expectedMessage = String.format(MESSAGE_ISSUES_LISTED_OVERVIEW, 0);
        IssueHasTagsPredicate predicate = new IssueHasTagsPredicate(Arrays.asList("coolTag"));
        FindByTagCommand command = new FindByTagCommand(predicate);
        expectedModel.updateFilteredIssueList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredAndSortedIssueList());
    }

    @Test
    public void execute_issueLevel_wrongDirectoryError() {
        SaveIt saveIt = getTypicalSaveIt();
        Directory issueLevelDirectory = new DirectoryBuilder()
                .withIssueIndex(Index.fromZeroBased(1)).build();
        saveIt.setCurrentDirectory(issueLevelDirectory);
        Model modelIssueLevelDirectory = new ModelManager(saveIt, new UserPrefs());

        IssueHasTagsPredicate predicate = new IssueHasTagsPredicate(Arrays.asList(VALID_TAG_SYNTAX));
        FindByTagCommand command = new FindByTagCommand(predicate);
        assertCommandFailure(command, modelIssueLevelDirectory, commandHistory, MESSAGE_WRONG_DIRECTORY);
    }
}

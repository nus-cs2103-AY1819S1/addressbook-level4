package seedu.restaurant.logic.commands.account;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.restaurant.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.restaurant.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.restaurant.logic.commands.account.FindAccountCommand.MESSAGE_ONE_KEYWORD_ONLY;
import static seedu.restaurant.logic.commands.account.FindAccountCommand.MESSAGE_SUCCESS;
import static seedu.restaurant.testutil.TypicalRestaurantBook.getTypicalRestaurantBook;
import static seedu.restaurant.testutil.account.TypicalAccounts.DEMO_ADMIN;
import static seedu.restaurant.testutil.account.TypicalAccounts.DEMO_ONE;
import static seedu.restaurant.testutil.account.TypicalAccounts.DEMO_TWO;

import java.util.Arrays;
import java.util.Collections;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.restaurant.logic.CommandHistory;
import seedu.restaurant.model.Model;
import seedu.restaurant.model.ModelManager;
import seedu.restaurant.model.UserPrefs;
import seedu.restaurant.model.account.UsernameContainsKeywordPredicate;
import seedu.restaurant.testutil.account.AccountBuilder;

//@@author AZhiKai

/**
 * Contains integration tests (interaction with the Model) for {@code FindAccountCommand}.
 */
public class FindAccountCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Model model = new ModelManager(getTypicalRestaurantBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalRestaurantBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void equals() {
        UsernameContainsKeywordPredicate firstPredicate =
                new UsernameContainsKeywordPredicate("first");
        UsernameContainsKeywordPredicate secondPredicate =
                new UsernameContainsKeywordPredicate("second");

        FindAccountCommand findFirstCommand = new FindAccountCommand(firstPredicate);
        FindAccountCommand findSecondCommand = new FindAccountCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindAccountCommand findFirstCommandCopy = new FindAccountCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different item -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_oneKeyword_oneAccountsFound() {
        String expectedMessage = String.format(MESSAGE_SUCCESS, 1);
        UsernameContainsKeywordPredicate predicate = preparePredicate(AccountBuilder.DEFAULT_USERNAME);
        FindAccountCommand command = new FindAccountCommand(predicate);
        expectedModel.updateFilteredAccountList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Collections.singletonList(DEMO_ADMIN), model.getFilteredAccountList());
    }

    @Test
    public void execute_oneKeyword_multipleAccountsFound() {
        String expectedMessage = String.format(MESSAGE_SUCCESS, 2);
        UsernameContainsKeywordPredicate predicate = preparePredicate("Demo");
        FindAccountCommand command = new FindAccountCommand(predicate);
        expectedModel.updateFilteredAccountList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(DEMO_ONE, DEMO_TWO), model.getFilteredAccountList());
    }

    @Test
    public void execute_zeroKeywords_failure() {
        UsernameContainsKeywordPredicate predicate = preparePredicate("");
        FindAccountCommand command = new FindAccountCommand(predicate);
        assertCommandFailure(command, model, commandHistory, MESSAGE_ONE_KEYWORD_ONLY);
    }

    /**
     * Parses {@code userInput} into a {@code UsernameContainsKeywordPredicate}.
     */
    private UsernameContainsKeywordPredicate preparePredicate(String userInput) {
        return new UsernameContainsKeywordPredicate(userInput);
    }
}

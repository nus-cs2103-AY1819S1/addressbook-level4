package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_WISHES_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalWishes.CARL;
import static seedu.address.testutil.TypicalWishes.ELLE;
import static seedu.address.testutil.TypicalWishes.FIONA;
import static seedu.address.testutil.TypicalWishes.getTypicalWishBook;
import static seedu.address.testutil.TypicalWishes.getTypicalWishTransaction;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.util.WishComparator;
import seedu.address.model.wish.Wish;
import seedu.address.model.wish.WishContainsKeywordsPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code FindCommand}.
 */
public class FindCommandTest {
    private Model model = new ModelManager(getTypicalWishBook(), getTypicalWishTransaction(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalWishBook(), getTypicalWishTransaction(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void equals() {
        List<String> nameKeywords = Arrays.asList("iph", "x");
        List<String> tagKeywords = Arrays.asList("fam", "impo");
        List<String> remarkKeywords = Arrays.asList("pink", "wrap");
        WishContainsKeywordsPredicate firstPredicate = new WishContainsKeywordsPredicate(nameKeywords, tagKeywords,
                remarkKeywords, true);

        nameKeywords = Arrays.asList("iph", "x", "wakanda");
        tagKeywords = Arrays.asList("fam", "impo");
        remarkKeywords = Arrays.asList("pink", "wrap");
        WishContainsKeywordsPredicate secondPredicate = new WishContainsKeywordsPredicate(nameKeywords, tagKeywords,
                remarkKeywords, true);


        FindCommand findFirstCommand = new FindCommand(firstPredicate);
        FindCommand findSecondCommand = new FindCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindCommand findFirstCommandCopy = new FindCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different wish -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noWishFound() {
        String expectedMessage = String.format(MESSAGE_WISHES_LISTED_OVERVIEW, 0);
        WishContainsKeywordsPredicate predicate = new WishContainsKeywordsPredicate(Arrays.asList("sloane", "peterson"),
                Arrays.asList(), Arrays.asList(), true);
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredWishList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredSortedWishList());
    }

    @Test
    public void execute_multipleKeywords_multipleWishesFound() {
        String expectedMessage = String.format(MESSAGE_WISHES_LISTED_OVERVIEW, 3);
        WishContainsKeywordsPredicate predicate = new WishContainsKeywordsPredicate(Arrays.asList("car", "lle", "fion"),
                Arrays.asList(), Arrays.asList(), false);
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredWishList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        List<Wish> expectedWishList = Arrays.asList(CARL, ELLE, FIONA);
        expectedWishList.sort(new WishComparator());
        assertEquals(expectedWishList, model.getFilteredSortedWishList());
    }
}

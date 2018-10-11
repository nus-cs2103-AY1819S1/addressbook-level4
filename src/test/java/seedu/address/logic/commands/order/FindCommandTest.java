package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_ORDERS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalDeliverymen.getTypicalDeliverymenList;
import static seedu.address.testutil.TypicalOrders.CARL;
import static seedu.address.testutil.TypicalOrders.ELLE;
import static seedu.address.testutil.TypicalOrders.FIONA;
import static seedu.address.testutil.TypicalOrders.getTypicalOrderBook;
import static seedu.address.testutil.TypicalRoutes.getTypicalRouteList;
import static seedu.address.testutil.user.TypicalUsers.getTypicalUsersList;

import java.util.Arrays;
import java.util.Collections;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.order.FindCommand;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.order.OrderContainsAnyKeywordsPredicate;
import seedu.address.model.order.OrderNameContainsKeywordPredicate;
import seedu.address.model.order.OrderPhoneContainsKeywordPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code OrderFindCommand}.
 */
public class FindCommandTest {
    private Model model = new ModelManager(getTypicalOrderBook(), getTypicalRouteList(), getTypicalUsersList(),
            getTypicalDeliverymenList(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalOrderBook(), getTypicalRouteList(), getTypicalUsersList(),
            getTypicalDeliverymenList(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void equals() {
        OrderContainsAnyKeywordsPredicate firstPredicate =
                new OrderNameContainsKeywordPredicate(Collections.singletonList("first"));
        OrderContainsAnyKeywordsPredicate secondPredicate =
                new OrderNameContainsKeywordPredicate(Collections.singletonList("second"));

        FindCommand findFirstOrderCommand = new FindCommand(firstPredicate);
        FindCommand findSecondOrderCommand = new FindCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstOrderCommand.equals(findFirstOrderCommand));

        // same values -> returns true
        FindCommand findFirstOrderCommandCopy = new FindCommand(firstPredicate);
        assertTrue(findFirstOrderCommand.equals(findFirstOrderCommandCopy));

        // different types -> returns false
        assertFalse(findFirstOrderCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstOrderCommand.equals(null));

        // different person -> returns false
        assertFalse(findFirstOrderCommand.equals(findSecondOrderCommand));
    }

    @Test
    public void execute_zeroKeywords_noOrderFound() {
        String expectedMessage = String.format(MESSAGE_ORDERS_LISTED_OVERVIEW, 0);

        OrderContainsAnyKeywordsPredicate namePredicate = prepareNamePredicate(" ");
        FindCommand commandName = new FindCommand(namePredicate);
        expectedModel.updateFilteredOrderList(namePredicate);
        assertCommandSuccess(commandName, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredOrderList());

        OrderContainsAnyKeywordsPredicate phonePredicate = preparePhonePredicate(" ");
        FindCommand commandPhone = new FindCommand(phonePredicate);
        expectedModel.updateFilteredOrderList(phonePredicate);
        assertCommandSuccess(commandPhone, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredOrderList());
    }

    @Test
    public void execute_multipleKeywords_multipleOrdersFound() {
        String expectedMessage = String.format(MESSAGE_ORDERS_LISTED_OVERVIEW, 3);
        OrderContainsAnyKeywordsPredicate predicate = prepareNamePredicate("Kurz Elle Kunz");
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredOrderList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(CARL, ELLE, FIONA), model.getFilteredOrderList());
    }

    @Test
    public void execute_fullNameKeyword_singleOrderFound() {
        String expectedMessage = String.format(MESSAGE_ORDERS_LISTED_OVERVIEW, 1);
        OrderContainsAnyKeywordsPredicate predicate = prepareNamePredicate("Carl Kurz");
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredOrderList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(CARL), model.getFilteredOrderList());
    }

    @Test
    public void execute_phoneKeyword_singleOrderFound() {
        String expectedMessage = String.format(MESSAGE_ORDERS_LISTED_OVERVIEW, 1);
        OrderContainsAnyKeywordsPredicate predicate = preparePhonePredicate(" 8765 2533 ");
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredOrderList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
    }

    /**
     * Parses {@code userInput} into a {@code OrderContainsAnyKeywordsPredicate}.
     */
    private OrderContainsAnyKeywordsPredicate prepareNamePredicate(String userInput) {
        return new OrderNameContainsKeywordPredicate(Arrays.asList(userInput.split("\\s+")));
    }

    /**
     * Parses {@code userInput} into a {@code OrderContainsAnyKeywordsPredicate}.
     */
    private OrderContainsAnyKeywordsPredicate preparePhonePredicate(String userInput) {
        return new OrderPhoneContainsKeywordPredicate(userInput.trim().replaceAll("\\s+", ""));
    }
}



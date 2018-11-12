package seedu.address.logic.commands.order;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_ORDER_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_ORDERS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FOOD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.testutil.TypicalDeliverymen.getTypicalDeliverymenList;
import static seedu.address.testutil.TypicalOrders.ALICE;
import static seedu.address.testutil.TypicalOrders.BENSON;
import static seedu.address.testutil.TypicalOrders.CARL;
import static seedu.address.testutil.TypicalOrders.FIONA;
import static seedu.address.testutil.TypicalOrders.GEORGE;
import static seedu.address.testutil.TypicalOrders.getTypicalOrderBook;
import static seedu.address.testutil.user.TypicalUsers.getTypicalUsersList;

import java.util.Arrays;
import java.util.Collections;
import java.util.function.Predicate;
import java.util.stream.Stream;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.logic.parser.order.OrderPredicateUtil;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.order.Order;
import seedu.address.model.order.OrderNameContainsKeywordPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code FindCommand}.
 */
public class FindCommandTest {
    private Model model = new ModelManager(getTypicalOrderBook(), getTypicalUsersList(),
            getTypicalDeliverymenList(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalOrderBook(), getTypicalUsersList(),
            getTypicalDeliverymenList(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void equals() {
        Predicate<Order> firstPredicate =
                new OrderNameContainsKeywordPredicate("first");
        Predicate<Order> secondPredicate =
                new OrderNameContainsKeywordPredicate("second");

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

        // different common -> returns false
        assertFalse(findFirstOrderCommand.equals(findSecondOrderCommand));
    }

    @Test
    public void execute_singlePrefixSingleKeyword_oneOrderFound() throws ParseException {
        String expectedMessage = String.format(MESSAGE_ORDERS_LISTED_OVERVIEW, 1);
        Predicate<Order> predicate = preparePredicate(" n/alice");
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredOrderList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ALICE), model.getFilteredOrderList());
    }

    @Test
    public void execute_singlePrefixSingleKeyword_multipleOrderFound() throws ParseException {
        String expectedMessage = String.format(MESSAGE_ORDERS_LISTED_OVERVIEW, 2);
        Predicate<Order> predicate = preparePredicate(" dt/01-10-2018 10:00:00");
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredOrderList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ALICE, FIONA), model.getFilteredOrderList());
    }

    @Test
    public void execute_singlePrefixMultipleKeywords_multipleOrdersFound() throws ParseException {
        String expectedMessage = String.format(MESSAGE_ORDERS_LISTED_OVERVIEW, 2);
        Predicate<Order> predicate = preparePredicate(" p/9435 9535");
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredOrderList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ALICE, CARL), model.getFilteredOrderList());
    }

    @Test
    public void execute_multiplePrefixSingleKeywords_oneOrderFound() throws ParseException {
        String expectedMessage = String.format(MESSAGE_ORDERS_LISTED_OVERVIEW, 1);
        Predicate<Order> predicate = preparePredicate(" p/94351253 f/Prata");
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredOrderList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ALICE), model.getFilteredOrderList());
    }

    @Test
    public void execute_multiplePrefixSingleKeywords_zeroOrderFound() throws ParseException {
        String expectedMessage = String.format(MESSAGE_ORDERS_LISTED_OVERVIEW, 0);
        Predicate<Order> predicate = preparePredicate(" dt/01-10-2018 10:00:00 f/Chicken");
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredOrderList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredOrderList());
    }

    @Test
    public void execute_multiplePrefixMultipleKeywords_zeroOrderFound() throws ParseException {
        String expectedMessage = String.format(MESSAGE_ORDERS_LISTED_OVERVIEW, 0);
        Predicate<Order> predicate =
                preparePredicate(" f/Milo Chicken a/Jurong");
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredOrderList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredOrderList());
    }

    @Test
    public void execute_singleRepeatedPrefixSingleKeywordTakeLast_multipleOrdersFound() throws ParseException {
        String expectedMessage = String.format(MESSAGE_ORDERS_LISTED_OVERVIEW, 1);
        Predicate<Order> predicate = preparePredicate(" f/Prata f/Sandwich");
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredOrderList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(FIONA), model.getFilteredOrderList());
    }

    @Test
    public void execute_singleRepeatedPrefixSingleKeywordTakeRange_multipleOrdersFound() throws ParseException {
        String expectedMessage = String.format(MESSAGE_ORDERS_LISTED_OVERVIEW, 3);
        Predicate<Order> predicate = preparePredicate(" dt/02-10-2018 10:00:00 dt/03-10-2018 14:00:00");
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredOrderList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(BENSON, CARL, GEORGE), model.getFilteredOrderList());
    }

    @Test
    public void execute_allSupportedPrefixesSingleKeyword_oneOrderFound() throws ParseException {
        String expectedMessage = String.format(MESSAGE_ORDERS_LISTED_OVERVIEW, 1);
        Predicate<Order> predicate =
                preparePredicate(" n/alice p/94351253 a/Jurong West Ave 6 dt/01-10-2018 10:00:00 f/prata");
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredOrderList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ALICE), model.getFilteredOrderList());
    }

    @Test
    public void execute_allSupportedPrefixesSingleKeyword_zeroOrderFound() throws ParseException {
        String expectedMessage = String.format(MESSAGE_ORDERS_LISTED_OVERVIEW, 0);
        Predicate<Order> predicate =
                preparePredicate(" n/alice dt/01-10-2018 10:00:00 f/Chicken p/1223214 a/Block 38");
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredOrderList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredOrderList());
    }

    /**
     * Parses {@code userInput} into a {@code FindCommand}.
     */
    private Predicate<Order> preparePredicate(String userInput) throws ParseException {
        ArgumentMultimap argMultimap = parseStringInput(userInput);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_PHONE, PREFIX_ADDRESS, PREFIX_DATE, PREFIX_FOOD)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_ORDER_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        return new OrderPredicateUtil().parsePredicate(argMultimap);
    }

    private ArgumentMultimap parseStringInput(String input) {
        return ArgumentTokenizer.tokenize(input, PREFIX_NAME, PREFIX_PHONE, PREFIX_ADDRESS, PREFIX_DATE, PREFIX_FOOD);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).anyMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}



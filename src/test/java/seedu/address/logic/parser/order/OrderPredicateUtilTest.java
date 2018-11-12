package seedu.address.logic.parser.order;

import static org.junit.Assert.assertEquals;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FOOD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STATUS;

import java.util.function.Predicate;

import org.junit.Test;

import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.order.Order;
import seedu.address.model.order.OrderNameContainsKeywordPredicate;

public class OrderPredicateUtilTest {
    @Test
    public void test_emptyTags_throwsParseException() throws ParseException {
        assertParseFailure(" n/", OrderPredicateUtil.MESSAGE_EMPTY_KEYWORD);
        assertParseFailure(" p/", OrderPredicateUtil.MESSAGE_EMPTY_KEYWORD);
        assertParseFailure(" a/", OrderPredicateUtil.MESSAGE_EMPTY_KEYWORD);
        assertParseFailure(" dt/", OrderPredicateUtil.MESSAGE_EMPTY_KEYWORD);
        assertParseFailure(" f/", OrderPredicateUtil.MESSAGE_EMPTY_KEYWORD);
    }

    @Test
    public void test_singleValidPredicate_returnsTrue() throws ParseException {
        String name = "alex";
        Predicate<Order> expectedPredicate = new OrderNameContainsKeywordPredicate(name);

        ArgumentMultimap argMultimap = tokenizeInput(" n/alex");
        Predicate<Order> predicate = new OrderPredicateUtil().parsePredicate(argMultimap);

        assertEquals(predicate, expectedPredicate);
    }

    private ArgumentMultimap tokenizeInput(String input) {
        return ArgumentTokenizer.tokenize(
                input, PREFIX_NAME, PREFIX_PHONE, PREFIX_ADDRESS, PREFIX_DATE, PREFIX_FOOD, PREFIX_STATUS);
    }

    /**
     * Asserts that the parsing of {@code userInput} is unsuccessful and the error message
     * equals to {@code expectedMessage}.
     */
    private void assertParseFailure(String userInput, String expectedMessage) {
        try {
            ArgumentMultimap emptyField = tokenizeInput(userInput);
            Predicate<Order> predicate = new OrderPredicateUtil().parsePredicate(emptyField);
            throw new AssertionError("The expected ParseException was not thrown.");
        } catch (ParseException pe) {
            assertEquals(String.format(expectedMessage, userInput.trim()), pe.getMessage());
        }
    }
}

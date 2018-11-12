package seedu.address.logic.parser.order;

import static org.junit.Assert.assertFalse;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_ORDER_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import org.junit.Test;

import seedu.address.logic.commands.order.FindCommand;
import seedu.address.model.order.OrderAddressContainsKeywordPredicate;
import seedu.address.model.order.OrderDatePredicate;
import seedu.address.model.order.OrderFoodContainsKeywordPredicate;
import seedu.address.model.order.OrderNameContainsKeywordPredicate;
import seedu.address.model.order.OrderPhoneContainsKeywordPredicate;
import seedu.address.model.order.OrderStatus;
import seedu.address.model.order.OrderStatusContainsKeywordPredicate;

public class FindCommandParserTest {

    private FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_ORDER_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "find ",
                String.format(MESSAGE_INVALID_ORDER_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        FindCommand expectedNameFindCommand =
                new FindCommand(new OrderNameContainsKeywordPredicate("Alex"));
        assertParseSuccess(parser, " n/Alex", expectedNameFindCommand);

        FindCommand expectedPhoneFindCommand =
                new FindCommand(new OrderPhoneContainsKeywordPredicate(Arrays.asList("81223455")));
        assertParseSuccess(parser, " p/81223455", expectedPhoneFindCommand);

        FindCommand expectedAddressFindCommand =
                new FindCommand(new OrderAddressContainsKeywordPredicate("123, Jurong West Ave 6, #08-111"));
        assertParseSuccess(parser, " a/123, Jurong West Ave 6, #08-111", expectedAddressFindCommand);

        FindCommand expectedFoodFindCommand =
                new FindCommand(new OrderFoodContainsKeywordPredicate("rice"));
        assertParseSuccess(parser, " f/rice", expectedFoodFindCommand);

        FindCommand expectedStatusFindCommand =
                new FindCommand(new OrderStatusContainsKeywordPredicate(Arrays.asList(new OrderStatus("PENDING"))));
        assertParseSuccess(parser, " st/PENDING", expectedStatusFindCommand);

        SimpleDateFormat sf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        Date date = null;

        try {
            date = sf.parse("01-10-2018 10:00:00");
        } catch (ParseException pE) {
            assertFalse(true);
        }

        FindCommand expectedDateFindCommand =
                new FindCommand(new OrderDatePredicate(Arrays.asList(date)));
        assertParseSuccess(parser, " dt/01-10-2018 10:00:00", expectedDateFindCommand);
    }

    @Test
    public void parse_invalidArgs_throwParseException() {
        assertParseFailure(parser, "w/",
                String.format(MESSAGE_INVALID_ORDER_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }
}

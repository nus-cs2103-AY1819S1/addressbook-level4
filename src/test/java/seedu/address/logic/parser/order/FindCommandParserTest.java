package seedu.address.logic.parser.order;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_ORDER_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.Test;

import seedu.address.logic.commands.order.FindCommand;
import seedu.address.model.order.OrderNameContainsKeywordPredicate;
import seedu.address.model.order.OrderPhoneContainsKeywordPredicate;

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
                new FindCommand(new OrderNameContainsKeywordPredicate(Arrays.asList("Alex")));
        assertParseSuccess(parser, " n/Alex", expectedNameFindCommand);

        FindCommand expectedPhoneFindCommand =
                new FindCommand(new OrderPhoneContainsKeywordPredicate("81223455"));
        assertParseSuccess(parser, " p/81223455", expectedPhoneFindCommand);
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        // Supplying both name and phone
        assertParseFailure(parser, " n/testname p/12345",
                String.format(MESSAGE_INVALID_ORDER_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }
}

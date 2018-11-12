package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.time.LocalDate;
import java.time.Month;

import org.junit.Test;

import seedu.address.logic.commands.EarningsCommand;

public class EarningsCommandParserTest {

    private static final String MESSAGE_INVALID_DATE = "The given start date is later than the end date";

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EarningsCommand.MESSAGE_USAGE);

    private EarningsCommandParser parser = new EarningsCommandParser();

    @Test
    public void parseValidDate() {
        LocalDate startDate = LocalDate.of(2018, Month.JANUARY, 1);
        LocalDate endDate = LocalDate.of(2018, Month.FEBRUARY, 12);

        String userInput = "0101 1202";
        EarningsCommand expectedCommand = new EarningsCommand(startDate, endDate);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parseInvalidDate() {
        String userInput = "3110 0510";
        assertParseFailure(parser, userInput, MESSAGE_INVALID_DATE);

        userInput = "0105";
        assertParseFailure(parser, userInput, MESSAGE_INVALID_FORMAT);

        userInput = "";
        assertParseFailure(parser, userInput, MESSAGE_INVALID_FORMAT);

        userInput = "12!#%@$^%&";
        assertParseFailure(parser, userInput, MESSAGE_INVALID_FORMAT);

        userInput = "-3.14 2.78qwe";
        assertParseFailure(parser, userInput, MESSAGE_INVALID_FORMAT);

        // Will assertParseSuccess in a leap year
        userInput = "2902 0503";
        assertParseFailure(parser, userInput, MESSAGE_INVALID_FORMAT);
    }
}

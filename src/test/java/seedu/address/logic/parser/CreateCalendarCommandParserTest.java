package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.FOUR_DIGIT_YEAR;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_MONTH_CHAR_NO;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_MONTH_VALUE;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NEGATIVE_YEAR;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_YEAR_LESS_THAN_FOUR_DIGIT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_YEAR_MORE_THAN_FOUR_DIGIT;
import static seedu.address.logic.commands.CommandTestUtil.LOWER_CASE_MONTH;
import static seedu.address.logic.commands.CommandTestUtil.MIX_CASE_MONTH;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.UPPER_CASE_MONTH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MONTH_JAN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STRING_JAN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STRING_YEAR;
import static seedu.address.logic.commands.CommandTestUtil.VALID_YEAR_2018;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.CreateCalendarCommand;
import seedu.address.model.calendar.Month;
import seedu.address.model.calendar.Year;

//@@author GilgameshTC
public class CreateCalendarCommandParserTest {
    private CreateCalendarCommandParser parser = new CreateCalendarCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Month expectedMonth = VALID_MONTH_JAN;
        Year expectedYear = VALID_YEAR_2018;

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + UPPER_CASE_MONTH + FOUR_DIGIT_YEAR,
                new CreateCalendarCommand(expectedMonth, expectedYear));

        // lower-case month
        assertParseSuccess(parser, LOWER_CASE_MONTH + FOUR_DIGIT_YEAR,
                new CreateCalendarCommand(expectedMonth, expectedYear));

        // mix-case month
        assertParseSuccess(parser, MIX_CASE_MONTH + FOUR_DIGIT_YEAR,
                new CreateCalendarCommand(expectedMonth, expectedYear));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, CreateCalendarCommand.MESSAGE_USAGE);

        // missing month prefix
        assertParseFailure(parser, VALID_STRING_JAN + FOUR_DIGIT_YEAR, expectedMessage);

        // missing year prefix
        assertParseFailure(parser, UPPER_CASE_MONTH + " " + VALID_STRING_YEAR, expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_STRING_JAN + " " + VALID_STRING_YEAR, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid month (More than 3 characters)
        assertParseFailure(parser, INVALID_MONTH_CHAR_NO + FOUR_DIGIT_YEAR, Month.MESSAGE_MONTH_CONSTRAINTS);

        // invalid month (Not a valid month)
        assertParseFailure(parser, INVALID_MONTH_VALUE + FOUR_DIGIT_YEAR, Month.MESSAGE_MONTH_CONSTRAINTS);

        // invalid year (Negative year)
        assertParseFailure(parser, UPPER_CASE_MONTH + INVALID_NEGATIVE_YEAR, Year.MESSAGE_YEAR_CONSTRAINTS);

        // invalid year (Less than 4 characters)
        assertParseFailure(parser, UPPER_CASE_MONTH + INVALID_YEAR_LESS_THAN_FOUR_DIGIT,
                Year.MESSAGE_YEAR_CONSTRAINTS);

        // invalid year (More than 4 characters)
        assertParseFailure(parser, UPPER_CASE_MONTH + INVALID_YEAR_MORE_THAN_FOUR_DIGIT,
                Year.MESSAGE_YEAR_CONSTRAINTS);

        // non-empty preamble
        // invalid year (Less than 4 characters)
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + UPPER_CASE_MONTH + FOUR_DIGIT_YEAR,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, CreateCalendarCommand.MESSAGE_USAGE));
    }
}

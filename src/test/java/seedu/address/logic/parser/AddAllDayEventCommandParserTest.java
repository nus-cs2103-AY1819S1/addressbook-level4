package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.FOUR_DIGIT_YEAR;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DATE_MORE_THAN_31;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DATE_NEGATIVE;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DATE_ZERO;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EMPTY_TITLE;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_MONTH_CHAR_NO;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_MONTH_VALUE;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NEGATIVE_YEAR;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_YEAR_LESS_THAN_FOUR_DIGIT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_YEAR_MORE_THAN_FOUR_DIGIT;
import static seedu.address.logic.commands.CommandTestUtil.LOWER_CASE_MONTH;
import static seedu.address.logic.commands.CommandTestUtil.MIX_CASE_MONTH;
import static seedu.address.logic.commands.CommandTestUtil.ONE_DIGIT_DATE;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.UPPER_CASE_MONTH;
import static seedu.address.logic.commands.CommandTestUtil.USER_INPUT_TITLE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CALENDAR_DATE_1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CALENDAR_TITLE_OCAMP;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MONTH_JAN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STRING_DATE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STRING_JAN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STRING_YEAR;
import static seedu.address.logic.commands.CommandTestUtil.VALID_YEAR_2018;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.AddAllDayEventCommand;
import seedu.address.model.calendar.Month;
import seedu.address.model.calendar.Year;

//@@author GilgameshTC
public class AddAllDayEventCommandParserTest {
    private AddAllDayEventCommandParser parser = new AddAllDayEventCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Month expectedMonth = VALID_MONTH_JAN;
        Year expectedYear = VALID_YEAR_2018;
        int expectedDate = VALID_CALENDAR_DATE_1;
        String expectedTitle = VALID_CALENDAR_TITLE_OCAMP;

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + UPPER_CASE_MONTH + FOUR_DIGIT_YEAR + ONE_DIGIT_DATE
                + USER_INPUT_TITLE,
                new AddAllDayEventCommand(expectedMonth, expectedYear, expectedDate, expectedTitle));

        // lower-case month
        assertParseSuccess(parser, LOWER_CASE_MONTH + FOUR_DIGIT_YEAR + ONE_DIGIT_DATE
                + USER_INPUT_TITLE,
                new AddAllDayEventCommand(expectedMonth, expectedYear, expectedDate, expectedTitle));

        // mix-case month
        assertParseSuccess(parser, MIX_CASE_MONTH + FOUR_DIGIT_YEAR + ONE_DIGIT_DATE
                + USER_INPUT_TITLE,
                new AddAllDayEventCommand(expectedMonth, expectedYear, expectedDate, expectedTitle));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddAllDayEventCommand.MESSAGE_USAGE);

        // missing month prefix
        assertParseFailure(parser, VALID_STRING_JAN + FOUR_DIGIT_YEAR + ONE_DIGIT_DATE + USER_INPUT_TITLE,
                expectedMessage);

        // missing year prefix
        assertParseFailure(parser, UPPER_CASE_MONTH + " " + VALID_STRING_YEAR + ONE_DIGIT_DATE
                + USER_INPUT_TITLE, expectedMessage);

        // missing date prefix
        assertParseFailure(parser, UPPER_CASE_MONTH + FOUR_DIGIT_YEAR + " " + VALID_STRING_DATE
                + USER_INPUT_TITLE, expectedMessage);

        // missing title prefix
        assertParseFailure(parser, UPPER_CASE_MONTH + FOUR_DIGIT_YEAR + ONE_DIGIT_DATE + " "
                + VALID_CALENDAR_TITLE_OCAMP, expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_STRING_JAN + " " + VALID_STRING_YEAR + " " + VALID_STRING_DATE
                + " " + VALID_CALENDAR_TITLE_OCAMP, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid month (More than 3 characters)
        assertParseFailure(parser, INVALID_MONTH_CHAR_NO + FOUR_DIGIT_YEAR + ONE_DIGIT_DATE + USER_INPUT_TITLE,
                Month.MESSAGE_MONTH_CONSTRAINTS);

        // invalid month (Not a valid month)
        assertParseFailure(parser, INVALID_MONTH_VALUE + FOUR_DIGIT_YEAR + ONE_DIGIT_DATE + USER_INPUT_TITLE,
                Month.MESSAGE_MONTH_CONSTRAINTS);

        // invalid year (Negative year)
        assertParseFailure(parser, UPPER_CASE_MONTH + INVALID_NEGATIVE_YEAR + ONE_DIGIT_DATE
                + USER_INPUT_TITLE, Year.MESSAGE_YEAR_CONSTRAINTS);

        // invalid year (Less than 4 characters)
        assertParseFailure(parser, UPPER_CASE_MONTH + INVALID_YEAR_LESS_THAN_FOUR_DIGIT + ONE_DIGIT_DATE
                + USER_INPUT_TITLE, Year.MESSAGE_YEAR_CONSTRAINTS);

        // invalid year (More than 4 characters)
        assertParseFailure(parser, UPPER_CASE_MONTH + INVALID_YEAR_MORE_THAN_FOUR_DIGIT + ONE_DIGIT_DATE
                + USER_INPUT_TITLE, Year.MESSAGE_YEAR_CONSTRAINTS);

        // invalid date (Greater than 31)
        assertParseFailure(parser, UPPER_CASE_MONTH + FOUR_DIGIT_YEAR + INVALID_DATE_MORE_THAN_31
                + USER_INPUT_TITLE, ParserUtil.MESSAGE_DATE_CONSTRAINTS);

        // invalid date (0)
        assertParseFailure(parser, UPPER_CASE_MONTH + FOUR_DIGIT_YEAR + INVALID_DATE_ZERO + USER_INPUT_TITLE,
                ParserUtil.MESSAGE_DATE_CONSTRAINTS);

        // invalid date (Negative date)
        assertParseFailure(parser, UPPER_CASE_MONTH + FOUR_DIGIT_YEAR + INVALID_DATE_NEGATIVE
                + USER_INPUT_TITLE, ParserUtil.MESSAGE_DATE_CONSTRAINTS);

        // empty title
        assertParseFailure(parser, UPPER_CASE_MONTH + FOUR_DIGIT_YEAR + ONE_DIGIT_DATE + INVALID_EMPTY_TITLE,
                ParserUtil.MESSAGE_TITLE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + UPPER_CASE_MONTH + FOUR_DIGIT_YEAR + ONE_DIGIT_DATE
                + USER_INPUT_TITLE, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddAllDayEventCommand.MESSAGE_USAGE));
    }
}

package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.EIGHT_START_HOUR;
import static seedu.address.logic.commands.CommandTestUtil.FOUR_DIGIT_YEAR;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EDATE_MORE_THAN_31;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EDATE_NEGATIVE;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EDATE_ZERO;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EHOUR_NEGATIVE;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EHOUR_OUTOFBOUND;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EMIN_NEGATIVE;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EMIN_OUTOFBOUND;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EMPTY_TITLE;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_MONTH_CHAR_NO;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_MONTH_VALUE;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NEGATIVE_YEAR;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_SDATE_MORE_THAN_31;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_SDATE_NEGATIVE;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_SDATE_ZERO;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_SHOUR_NEGATIVE;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_SHOUR_OUTOFBOUND;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_SMIN_NEGATIVE;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_SMIN_OUTOFBOUND;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_YEAR_LESS_THAN_FOUR_DIGIT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_YEAR_MORE_THAN_FOUR_DIGIT;
import static seedu.address.logic.commands.CommandTestUtil.LOWER_CASE_MONTH;
import static seedu.address.logic.commands.CommandTestUtil.MIX_CASE_MONTH;
import static seedu.address.logic.commands.CommandTestUtil.ONE_DIGIT_EDATE;
import static seedu.address.logic.commands.CommandTestUtil.ONE_DIGIT_SDATE;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.SEVENTEEN_END_HOUR;
import static seedu.address.logic.commands.CommandTestUtil.THIRTY_END_MIN;
import static seedu.address.logic.commands.CommandTestUtil.UPPER_CASE_MONTH;
import static seedu.address.logic.commands.CommandTestUtil.USER_INPUT_TITLE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CALENDAR_DATE_1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CALENDAR_DATE_2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CALENDAR_TITLE_OCAMP;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EHOUR;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMIN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MONTH_JAN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SHOUR;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SMIN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STRING_JAN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STRING_YEAR;
import static seedu.address.logic.commands.CommandTestUtil.VALID_YEAR_2018;
import static seedu.address.logic.commands.CommandTestUtil.ZERO_START_MIN;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.AddEventCommand;
import seedu.address.model.calendar.Month;
import seedu.address.model.calendar.Year;

//@@author GilgameshTC
public class AddEventCommandParserTest {
    private AddEventCommandParser parser = new AddEventCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Month expectedMonth = VALID_MONTH_JAN;
        Year expectedYear = VALID_YEAR_2018;
        int expectedStartDate = VALID_CALENDAR_DATE_1;
        int expectedStartHour = VALID_SHOUR;
        int expectedStartMin = VALID_SMIN;
        int expectedEndDate = VALID_CALENDAR_DATE_2;
        int expectedEndHour = VALID_EHOUR;
        int expectedEndMin = VALID_EMIN;
        String expectedTitle = VALID_CALENDAR_TITLE_OCAMP;

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + UPPER_CASE_MONTH + FOUR_DIGIT_YEAR + ONE_DIGIT_SDATE
                + EIGHT_START_HOUR + ZERO_START_MIN + ONE_DIGIT_EDATE + SEVENTEEN_END_HOUR + THIRTY_END_MIN
                + USER_INPUT_TITLE, new AddEventCommand(expectedMonth, expectedYear, expectedStartDate,
                expectedStartHour, expectedStartMin, expectedEndDate, expectedEndHour, expectedEndMin, expectedTitle));

        // lower-case month
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + LOWER_CASE_MONTH + FOUR_DIGIT_YEAR + ONE_DIGIT_SDATE
                + EIGHT_START_HOUR + ZERO_START_MIN + ONE_DIGIT_EDATE + SEVENTEEN_END_HOUR + THIRTY_END_MIN
                + USER_INPUT_TITLE, new AddEventCommand(expectedMonth, expectedYear, expectedStartDate,
                expectedStartHour, expectedStartMin, expectedEndDate, expectedEndHour, expectedEndMin, expectedTitle));

        // mix-case month
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + MIX_CASE_MONTH + FOUR_DIGIT_YEAR + ONE_DIGIT_SDATE
                + EIGHT_START_HOUR + ZERO_START_MIN + ONE_DIGIT_EDATE + SEVENTEEN_END_HOUR + THIRTY_END_MIN
                + USER_INPUT_TITLE, new AddEventCommand(expectedMonth, expectedYear, expectedStartDate,
                expectedStartHour, expectedStartMin, expectedEndDate, expectedEndHour, expectedEndMin, expectedTitle));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddEventCommand.MESSAGE_USAGE);

        // missing month prefix
        assertParseFailure(parser, VALID_STRING_JAN + FOUR_DIGIT_YEAR + ONE_DIGIT_SDATE + EIGHT_START_HOUR
                + ZERO_START_MIN + ONE_DIGIT_EDATE + SEVENTEEN_END_HOUR + THIRTY_END_MIN + USER_INPUT_TITLE,
                expectedMessage);

        // missing year prefix
        assertParseFailure(parser, UPPER_CASE_MONTH + " " + VALID_STRING_YEAR + ONE_DIGIT_SDATE
                + EIGHT_START_HOUR + ZERO_START_MIN + ONE_DIGIT_EDATE + SEVENTEEN_END_HOUR + THIRTY_END_MIN
                + USER_INPUT_TITLE, expectedMessage);

        // missing sdate prefix
        assertParseFailure(parser, UPPER_CASE_MONTH + FOUR_DIGIT_YEAR + " " + VALID_CALENDAR_DATE_1
                + EIGHT_START_HOUR + ZERO_START_MIN + ONE_DIGIT_EDATE + SEVENTEEN_END_HOUR + THIRTY_END_MIN
                + USER_INPUT_TITLE, expectedMessage);

        // missing shour prefix
        assertParseFailure(parser, UPPER_CASE_MONTH + FOUR_DIGIT_YEAR + ONE_DIGIT_SDATE + " "
                + VALID_SHOUR + ZERO_START_MIN + ONE_DIGIT_EDATE + SEVENTEEN_END_HOUR + THIRTY_END_MIN
                + USER_INPUT_TITLE, expectedMessage);

        // missing smin prefix
        assertParseFailure(parser, UPPER_CASE_MONTH + FOUR_DIGIT_YEAR + ONE_DIGIT_SDATE + EIGHT_START_HOUR
                + " " + VALID_SMIN + ONE_DIGIT_EDATE + SEVENTEEN_END_HOUR + THIRTY_END_MIN + USER_INPUT_TITLE,
                expectedMessage);

        // missing edate prefix
        assertParseFailure(parser, UPPER_CASE_MONTH + FOUR_DIGIT_YEAR + ONE_DIGIT_SDATE
                + EIGHT_START_HOUR + ZERO_START_MIN + " " + VALID_CALENDAR_DATE_2 + SEVENTEEN_END_HOUR + THIRTY_END_MIN
                + USER_INPUT_TITLE, expectedMessage);

        // missing ehour prefix
        assertParseFailure(parser, UPPER_CASE_MONTH + FOUR_DIGIT_YEAR + ONE_DIGIT_SDATE + EIGHT_START_HOUR
                + ZERO_START_MIN + ONE_DIGIT_EDATE + " " + VALID_EHOUR + THIRTY_END_MIN + USER_INPUT_TITLE,
                expectedMessage);

        // missing emin prefix
        assertParseFailure(parser, UPPER_CASE_MONTH + FOUR_DIGIT_YEAR + ONE_DIGIT_SDATE + EIGHT_START_HOUR
                + ZERO_START_MIN + ONE_DIGIT_EDATE + SEVENTEEN_END_HOUR + " " + VALID_EMIN + USER_INPUT_TITLE,
                expectedMessage);

        // missing title prefix
        assertParseFailure(parser, UPPER_CASE_MONTH + FOUR_DIGIT_YEAR + ONE_DIGIT_SDATE + EIGHT_START_HOUR
                + ZERO_START_MIN + ONE_DIGIT_EDATE + SEVENTEEN_END_HOUR + THIRTY_END_MIN + " "
                + VALID_CALENDAR_TITLE_OCAMP, expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_STRING_JAN + " " + VALID_STRING_YEAR + " " + VALID_CALENDAR_DATE_1
                + " " + VALID_SHOUR + " " + VALID_SMIN + " " + VALID_CALENDAR_DATE_2 + " " + VALID_EHOUR + " "
                + VALID_EMIN + " " + VALID_CALENDAR_TITLE_OCAMP, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid month (More than 3 characters)
        assertParseFailure(parser, INVALID_MONTH_CHAR_NO + FOUR_DIGIT_YEAR + ONE_DIGIT_SDATE + EIGHT_START_HOUR
                + ZERO_START_MIN + ONE_DIGIT_EDATE + SEVENTEEN_END_HOUR + THIRTY_END_MIN + USER_INPUT_TITLE,
                Month.MESSAGE_MONTH_CONSTRAINTS);

        // invalid month (Not a valid month)
        assertParseFailure(parser, INVALID_MONTH_VALUE + FOUR_DIGIT_YEAR + ONE_DIGIT_SDATE + EIGHT_START_HOUR
                + ZERO_START_MIN + ONE_DIGIT_EDATE + SEVENTEEN_END_HOUR + THIRTY_END_MIN + USER_INPUT_TITLE,
                Month.MESSAGE_MONTH_CONSTRAINTS);

        // invalid year (Negative year)
        assertParseFailure(parser, UPPER_CASE_MONTH + INVALID_NEGATIVE_YEAR + ONE_DIGIT_SDATE + EIGHT_START_HOUR
                + ZERO_START_MIN + ONE_DIGIT_EDATE + SEVENTEEN_END_HOUR + THIRTY_END_MIN + USER_INPUT_TITLE,
                Year.MESSAGE_YEAR_CONSTRAINTS);

        // invalid year (Less than 4 characters)
        assertParseFailure(parser, UPPER_CASE_MONTH + INVALID_YEAR_LESS_THAN_FOUR_DIGIT + ONE_DIGIT_SDATE
                + EIGHT_START_HOUR + ZERO_START_MIN + ONE_DIGIT_EDATE + SEVENTEEN_END_HOUR + THIRTY_END_MIN
                + USER_INPUT_TITLE, Year.MESSAGE_YEAR_CONSTRAINTS);

        // invalid year (More than 4 characters)
        assertParseFailure(parser, UPPER_CASE_MONTH + INVALID_YEAR_MORE_THAN_FOUR_DIGIT + ONE_DIGIT_SDATE
                + EIGHT_START_HOUR + ZERO_START_MIN + ONE_DIGIT_EDATE + SEVENTEEN_END_HOUR + THIRTY_END_MIN
                + USER_INPUT_TITLE, Year.MESSAGE_YEAR_CONSTRAINTS);

        // invalid start date (Greater than 31)
        assertParseFailure(parser, UPPER_CASE_MONTH + FOUR_DIGIT_YEAR + INVALID_SDATE_MORE_THAN_31
                + EIGHT_START_HOUR + ZERO_START_MIN + ONE_DIGIT_EDATE + SEVENTEEN_END_HOUR + THIRTY_END_MIN
                + USER_INPUT_TITLE, ParserUtil.MESSAGE_DATE_CONSTRAINTS);

        // invalid start date (0)
        assertParseFailure(parser, UPPER_CASE_MONTH + FOUR_DIGIT_YEAR + INVALID_SDATE_ZERO
                + EIGHT_START_HOUR + ZERO_START_MIN + ONE_DIGIT_EDATE + SEVENTEEN_END_HOUR + THIRTY_END_MIN
                + USER_INPUT_TITLE, ParserUtil.MESSAGE_DATE_CONSTRAINTS);

        // invalid start date (Negative date)
        assertParseFailure(parser, UPPER_CASE_MONTH + FOUR_DIGIT_YEAR + INVALID_SDATE_NEGATIVE
                + EIGHT_START_HOUR + ZERO_START_MIN + ONE_DIGIT_EDATE + SEVENTEEN_END_HOUR + THIRTY_END_MIN
                + USER_INPUT_TITLE, ParserUtil.MESSAGE_DATE_CONSTRAINTS);

        // invalid end date (Greater than 31)
        assertParseFailure(parser, UPPER_CASE_MONTH + FOUR_DIGIT_YEAR + ONE_DIGIT_SDATE + EIGHT_START_HOUR
                + ZERO_START_MIN + INVALID_EDATE_MORE_THAN_31 + SEVENTEEN_END_HOUR + THIRTY_END_MIN
                + USER_INPUT_TITLE, ParserUtil.MESSAGE_DATE_CONSTRAINTS);

        // invalid end date (0)
        assertParseFailure(parser, UPPER_CASE_MONTH + FOUR_DIGIT_YEAR + ONE_DIGIT_SDATE
                + EIGHT_START_HOUR + ZERO_START_MIN + INVALID_EDATE_ZERO + SEVENTEEN_END_HOUR + THIRTY_END_MIN
                + USER_INPUT_TITLE, ParserUtil.MESSAGE_DATE_CONSTRAINTS);

        // invalid end date (Negative date)
        assertParseFailure(parser, UPPER_CASE_MONTH + FOUR_DIGIT_YEAR + ONE_DIGIT_SDATE
                + EIGHT_START_HOUR + ZERO_START_MIN + INVALID_EDATE_NEGATIVE + SEVENTEEN_END_HOUR + THIRTY_END_MIN
                + USER_INPUT_TITLE, ParserUtil.MESSAGE_DATE_CONSTRAINTS);

        // invalid start hour (out of bound)
        assertParseFailure(parser, UPPER_CASE_MONTH + FOUR_DIGIT_YEAR + ONE_DIGIT_SDATE
                + INVALID_SHOUR_OUTOFBOUND + ZERO_START_MIN + ONE_DIGIT_EDATE + SEVENTEEN_END_HOUR + THIRTY_END_MIN
                + USER_INPUT_TITLE, ParserUtil.MESSAGE_HOUR_CONSTRAINTS);

        // invalid start hour (negative)
        assertParseFailure(parser, UPPER_CASE_MONTH + FOUR_DIGIT_YEAR + ONE_DIGIT_SDATE
                + INVALID_SHOUR_NEGATIVE + ZERO_START_MIN + ONE_DIGIT_EDATE + SEVENTEEN_END_HOUR + THIRTY_END_MIN
                + USER_INPUT_TITLE, ParserUtil.MESSAGE_HOUR_CONSTRAINTS);

        // invalid end hour (out of bound)
        assertParseFailure(parser, UPPER_CASE_MONTH + FOUR_DIGIT_YEAR + ONE_DIGIT_SDATE
                + EIGHT_START_HOUR + ZERO_START_MIN + ONE_DIGIT_EDATE + INVALID_EHOUR_OUTOFBOUND + THIRTY_END_MIN
                + USER_INPUT_TITLE, ParserUtil.MESSAGE_HOUR_CONSTRAINTS);

        // invalid end hour (negative)
        assertParseFailure(parser, UPPER_CASE_MONTH + FOUR_DIGIT_YEAR + ONE_DIGIT_SDATE
                + EIGHT_START_HOUR + ZERO_START_MIN + ONE_DIGIT_EDATE + INVALID_EHOUR_NEGATIVE + THIRTY_END_MIN
                + USER_INPUT_TITLE, ParserUtil.MESSAGE_HOUR_CONSTRAINTS);

        // invalid start min (out of bound)
        assertParseFailure(parser, UPPER_CASE_MONTH + FOUR_DIGIT_YEAR + ONE_DIGIT_SDATE
                + EIGHT_START_HOUR + INVALID_SMIN_OUTOFBOUND + ONE_DIGIT_EDATE + SEVENTEEN_END_HOUR + THIRTY_END_MIN
                + USER_INPUT_TITLE, ParserUtil.MESSAGE_MINUTE_CONSTRAINTS);

        // invalid start min (negative)
        assertParseFailure(parser, UPPER_CASE_MONTH + FOUR_DIGIT_YEAR + ONE_DIGIT_SDATE
                + EIGHT_START_HOUR + INVALID_SMIN_NEGATIVE + ONE_DIGIT_EDATE + SEVENTEEN_END_HOUR + THIRTY_END_MIN
                + USER_INPUT_TITLE, ParserUtil.MESSAGE_MINUTE_CONSTRAINTS);

        // invalid end min (out of bound)
        assertParseFailure(parser, UPPER_CASE_MONTH + FOUR_DIGIT_YEAR + ONE_DIGIT_SDATE
                + EIGHT_START_HOUR + ZERO_START_MIN + ONE_DIGIT_EDATE + SEVENTEEN_END_HOUR + INVALID_EMIN_OUTOFBOUND
                + USER_INPUT_TITLE, ParserUtil.MESSAGE_MINUTE_CONSTRAINTS);

        // invalid end min (negative)
        assertParseFailure(parser, UPPER_CASE_MONTH + FOUR_DIGIT_YEAR + ONE_DIGIT_SDATE
                + EIGHT_START_HOUR + ZERO_START_MIN + ONE_DIGIT_EDATE + SEVENTEEN_END_HOUR + INVALID_EMIN_NEGATIVE
                + USER_INPUT_TITLE, ParserUtil.MESSAGE_MINUTE_CONSTRAINTS);

        // empty title
        assertParseFailure(parser, UPPER_CASE_MONTH + FOUR_DIGIT_YEAR + ONE_DIGIT_SDATE + EIGHT_START_HOUR
                + ZERO_START_MIN + ONE_DIGIT_EDATE + SEVENTEEN_END_HOUR + THIRTY_END_MIN + INVALID_EMPTY_TITLE,
                ParserUtil.MESSAGE_TITLE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + UPPER_CASE_MONTH + FOUR_DIGIT_YEAR + ONE_DIGIT_SDATE
                + EIGHT_START_HOUR + ZERO_START_MIN + ONE_DIGIT_EDATE + SEVENTEEN_END_HOUR + THIRTY_END_MIN
                + USER_INPUT_TITLE, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddEventCommand.MESSAGE_USAGE));
    }
}

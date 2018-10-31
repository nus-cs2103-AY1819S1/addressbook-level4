package ssp.scheduleplanner.logic.parser;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static ssp.scheduleplanner.logic.parser.CommandParserTestUtil.assertParseFailure;
import static ssp.scheduleplanner.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import ssp.scheduleplanner.commons.core.Messages;
import ssp.scheduleplanner.logic.commands.FirstDayCommand;

public class FirstDayCommandParserTest {
    private FirstDayCommandParser parser = new FirstDayCommandParser();

    @Test
    public void parse_empty_failure() {

        assertParseFailure(parser, " ", String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                FirstDayCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgument_success() {
        //all the following testDate is valid as it is valid date format "ddMMyy" and is a monday
        String testMonday = " 130818 ";
        String testMonday2 = " 200818 ";
        String testMonday3 = " 270818 ";

        assertParseSuccess(parser, testMonday, new FirstDayCommand(testMonday.trim()));

        assertParseSuccess(parser, testMonday2, new FirstDayCommand(testMonday2.trim()));

        assertParseSuccess(parser, testMonday3, new FirstDayCommand(testMonday3.trim()));
    }

    @Test
    public void parse_invalidArgument_failure() {
        //all the following test day are invalid as it is not monday
        String testTuesday = " 140818";
        String testSunday = " 190818 ";

        //all the following test date are invalid
        String testInvalidDate1 = " 321018 "; //invalid day
        String testInvalidDate2 = " 311318 "; //invalid month
        String testInvalidDate3 = " 321318 "; //invalid day and month
        String testInvalidDate4 = " a12#12 "; //invalid format with alphabet and special character
        String testInvalidDate5 = " 12101 "; //date not in ddMMyy format
        String testInvalidDate6 = " 1210188 "; //date not in ddMMyy format

        assertParseFailure(parser, testTuesday, FirstDayCommand.MESSAGE_NOT_MONDAY);

        assertParseFailure(parser, testSunday, FirstDayCommand.MESSAGE_NOT_MONDAY);

        assertParseFailure(parser, testInvalidDate1, FirstDayCommand.MESSAGE_INVALID_DATE);

        assertParseFailure(parser, testInvalidDate2, FirstDayCommand.MESSAGE_INVALID_DATE);

        assertParseFailure(parser, testInvalidDate3, FirstDayCommand.MESSAGE_INVALID_DATE);

        assertParseFailure(parser, testInvalidDate4, FirstDayCommand.MESSAGE_INVALID_DATE);

        assertParseFailure(parser, testInvalidDate5, FirstDayCommand.MESSAGE_INVALID_DATE);

        assertParseFailure(parser, testInvalidDate6, FirstDayCommand.MESSAGE_INVALID_DATE);
    }

    @Test
    public void parse_moreThanOneArgument_failure() {
        String testDate1 = " 130818 ";
        String testDate2 = " 140818 ";
        String testDate3 = " 150818 ";

        assertParseFailure(parser, testDate1 + testDate2, FirstDayCommand.MESSAGE_ONLY_ONE_ARGUMENT);

        assertParseFailure(parser, testDate1 + testDate2 + testDate3, FirstDayCommand.MESSAGE_ONLY_ONE_ARGUMENT);
    }

    @Test
    public void isMonday_test() {
        //the following tests are Monday
        assertTrue(parser.isMonday("130818"));
        assertTrue(parser.isMonday("200818"));

        //the following tests are Tuesday to Sunday
        assertFalse(parser.isMonday("140818"));
        assertFalse(parser.isMonday("150818"));
        assertFalse(parser.isMonday("160818"));
        assertFalse(parser.isMonday("170818"));
        assertFalse(parser.isMonday("180818"));
        assertFalse(parser.isMonday("190818"));
    }


}

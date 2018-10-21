package ssp.scheduleplanner.logic.parser;

import static ssp.scheduleplanner.logic.parser.CommandParserTestUtil.assertParseFailure;
import static ssp.scheduleplanner.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import ssp.scheduleplanner.logic.commands.FirstDayCommand;

public class FirstDayCommandParserTest {
    private FirstDayCommandParser parser = new FirstDayCommandParser();

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

        assertParseFailure(parser, testTuesday, FirstDayCommand.MESSAGE_NOT_MONDAY);

        assertParseFailure(parser, testSunday, FirstDayCommand.MESSAGE_NOT_MONDAY);

        assertParseFailure(parser, testInvalidDate1, FirstDayCommand.MESSAGE_INVALID_DATE);

        assertParseFailure(parser, testInvalidDate2, FirstDayCommand.MESSAGE_INVALID_DATE);

        assertParseFailure(parser, testInvalidDate3, FirstDayCommand.MESSAGE_INVALID_DATE);
    }


}

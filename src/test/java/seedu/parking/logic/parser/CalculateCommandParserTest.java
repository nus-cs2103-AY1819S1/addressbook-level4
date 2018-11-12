package seedu.parking.logic.parser;

import static seedu.parking.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.parking.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.parking.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;

import seedu.parking.commons.core.Messages;
import seedu.parking.logic.commands.CalculateCommand;


public class CalculateCommandParserTest {

    private CalculateCommandParser parser = new CalculateCommandParser();

    @Test
    public void parse_validArgs_returnsCalculateCommand() {

        Date inputStart = null;
        Date inputEnd = null;
        try {
            SimpleDateFormat dateFormat1 = new SimpleDateFormat("hh.mmaa");
            inputStart = dateFormat1.parse("9.30am");
            inputEnd = dateFormat1.parse("10.30am");

        } catch (java.text.ParseException pe) {
            System.out.println("Parse exception");
        }
        assertParseSuccess(parser, "TJ39 sun 9.30am 10.30am",
                new CalculateCommand("TJ39", "SUN", inputStart, inputEnd));
    }

    @Test
    public void parse_invalidNumberOfArgs_throwsParseException() {
        assertParseFailure(parser, "TJ39 9.30am 10.30am",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, CalculateCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidDay_throwsParseException() {
        assertParseFailure(parser, "TJ39 asd 9.30am 10.30am", Messages.MESSAGE_DAY_IS_INVALID);
    }

    @Test
    public void parse_invalidTime_throwsParseException() {
        assertParseFailure(parser, "TJ39 sun 9.ddam 10.30am",
                Messages.MESSAGE_START_OR_END_TIME_HAS_INCORRECT_FORMAT);
    }
}

package seedu.parking.logic.parser;

import static seedu.parking.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.parking.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.parking.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import seedu.parking.commons.core.Messages;
import seedu.parking.logic.commands.FilterCommand;


public class FilterCommandParserTest {

    private FilterCommandParser parser = new FilterCommandParser();

    @Test
    public void parseFreeParking_validArgs_returnsFilterCommand() { // filter f/ sun 8.30am 5.30pm
        List<String> flagList = new ArrayList<>();
        flagList.add("f/");

        FreeParkingParameter freeParking = null;
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("hh.mmaa");
            Date inputStart = dateFormat.parse("8.30am");
            Date inputEnd = dateFormat.parse("5.30pm");
            freeParking = new FreeParkingParameter("SUN", inputStart, inputEnd);
        } catch (ParseException e) {
            System.out.println("Parse exception");
        }

        FilterCommand command = new FilterCommand(flagList, freeParking, null, null);
        assertParseSuccess(parser, "f/ sun 8.30am 5.30pm", command);
    }

    @Test
    public void parseNightParking_validArgs_returnsFilterCommand() { // filter n/
        List<String> flagList = new ArrayList<>();
        flagList.add("n/");

        FilterCommand command = new FilterCommand(flagList, null, null, null);
        assertParseSuccess(parser, "n/", command);
    }

    @Test
    public void parseAvailableParking_validArgs_returnsFilterCommand() { // filter a/
        List<String> flagList = new ArrayList<>();
        flagList.add("a/");

        FilterCommand command = new FilterCommand(flagList, null, null, null);
        assertParseSuccess(parser, "a/", command);
    }

    @Test
    public void parseCarparkType_validArgs_returnsFilterCommand() { // filter ct/ multistorey
        List<String> flagList = new ArrayList<>();
        flagList.add("ct/");

        CarparkTypeParameter carparkType = new CarparkTypeParameter("MULTISTOREY");

        FilterCommand command = new FilterCommand(flagList, null, carparkType, null);
        assertParseSuccess(parser, "ct/ multistorey", command);
    }

    @Test
    public void parseParkingSystem_validArgs_returnsFilterCommand() { // filter ps/ coupon
        List<String> flagList = new ArrayList<>();
        flagList.add("ps/");

        ParkingSystemTypeParameter parkingSystemType = new ParkingSystemTypeParameter("COUPON");

        FilterCommand command = new FilterCommand(flagList, null, null, parkingSystemType);
        assertParseSuccess(parser, "ps/ coupon", command);
    }

    @Test
    public void parseShortTermParking_validArgs_returnsFilterCommand() { // filter s/
        List<String> flagList = new ArrayList<>();
        flagList.add("s/");

        FilterCommand command = new FilterCommand(flagList, null, null, null);
        assertParseSuccess(parser, "s/", command);
    }

    @Test
    public void parseMultipleFlags_validArgs_returnsFilterCommand() { // filter n/ f/ sun 8.30am 5.30pm ps/ coupon
        List<String> flagList = new ArrayList<>();
        flagList.add("n/");
        flagList.add("f/");
        flagList.add("ps/");

        FreeParkingParameter freeParking = null;
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("hh.mmaa");
            Date inputStart = dateFormat.parse("8.30am");
            Date inputEnd = dateFormat.parse("5.30pm");
            freeParking = new FreeParkingParameter("SUN", inputStart, inputEnd);
        } catch (ParseException e) {
            System.out.println("Parse exception");
        }

        ParkingSystemTypeParameter parkingSystemType = new ParkingSystemTypeParameter("COUPON");

        FilterCommand command = new FilterCommand(flagList, freeParking, null, parkingSystemType);
        assertParseSuccess(parser, "n/ f/ sun 8.30am 5.30pm ps/ coupon", command);
    }

    @Test
    public void parseNoArguments_invalidArgs_throwsParseException() {
        assertParseFailure(parser, " ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
    }

    @Test
    public void parseFreeParkingInvalidParameters_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "f/ asdasd", Messages.MESSAGE_FREEPARKING_HAS_INVALID_PARAMETERS);
    }

    @Test
    public void parseFreeParkingInvalidTime_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "f/ sun wx.yzam 9.30pm", Messages.MESSAGE_START_OR_END_TIME_HAS_INCORRECT_FORMAT);
    }

    @Test
    public void parseFreeParkingInvalidDay_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "f/ asd 10.30am 9.30pm", Messages.MESSAGE_DAY_IS_INVALID);
    }

    @Test
    public void parseInvalidCarparkType_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "ct/ asdasd", Messages.MESSAGE_CARPARK_TYPE_IS_INVALID);
    }

    @Test
    public void parseInvalidParkingSystemType_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "ps/ asdasd", Messages.MESSAGE_PARKINGSYSTEM_TYPE_IS_INVALID);
    }
}


package seedu.parking.logic.parser;

import static seedu.parking.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import seedu.parking.commons.core.Messages;
import seedu.parking.logic.commands.FilterCommand;
import seedu.parking.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new FilterCommand object
 */
public class FilterCommandParser implements Parser<FilterCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FilterCommand
     * and returns an FilterCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FilterCommand parse(String args) throws ParseException {

        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
        }

        String[] arguments = trimmedArgs.split("\\s+");
        List<String> argumentsList = Arrays.asList(arguments);
        List<String> flagList = new ArrayList<>();

        FreeParkingParameter freeParkingParameter = null; // variable null if not initialised?
        CarparkTypeParameter carparkTypeParameter = null;
        ParkingSystemTypeParameter parkingSystemTypeParameter = null;

        if (argumentsList.contains("n/")) {
            flagList.add("n/");
        }
        if (argumentsList.contains("a/")) {
            flagList.add("a/");
        }
        if (argumentsList.contains("s/")) {
            flagList.add("s/");
        }
        if (argumentsList.contains("f/")) {
            flagList.add("f/");

            int index = argumentsList.indexOf("f/");

            String day;
            String startTime;
            String endTime;

            // check for invalid number of parameters
            try {
                day = argumentsList.get(index + 1).toUpperCase();
                startTime = argumentsList.get(index + 2);
                endTime = argumentsList.get(index + 3);
            } catch (IndexOutOfBoundsException e) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
            }

            SimpleDateFormat dateFormat = new SimpleDateFormat("hh.mmaa");

            Date inputStart;
            Date inputEnd;

            //check for invalid date
            try {
                // Input time of user can only be of dateFormat2
                inputStart = dateFormat.parse(startTime);
                inputEnd = dateFormat.parse(endTime);
            } catch (java.text.ParseException pe) {
                throw new ParseException(Messages.MESSAGE_START_OR_END_TIME_HAS_INCORRECT_FORMAT, pe);
            }

            // check for invalid day
            String[] validDays = {"SUN", "MON", "TUE", "WED", "THU", "FRI", "SAT"};
            boolean isValidDay = Arrays.stream(validDays).anyMatch(x -> x.equals(day));
            if (!isValidDay) {
                throw new ParseException(Messages.MESSAGE_DAY_IS_INVALID);
            }

            freeParkingParameter = new FreeParkingParameter(day, inputStart, inputEnd);
        }
        if (argumentsList.contains("ct/")) {
            flagList.add("ct/");

            int index2 = argumentsList.indexOf("ct/");

            String selectedCarparkType = argumentsList.get(index2 + 1).toUpperCase();

            // check that selected car park type matches at least one of the strings
            String[] validCarParkTypes = {"SURFACE", "MULTISTOREY", "BASEMENT", "MECHANISED", "COVERED"};
            boolean isValidCarparkType = Arrays.stream(validCarParkTypes).anyMatch(x -> x.equals(selectedCarparkType));
            if (!isValidCarparkType) {
                throw new ParseException(Messages.MESSAGE_CARPARK_TYPE_IS_INVALID);
            }

            carparkTypeParameter = new CarparkTypeParameter(selectedCarparkType);
        }
        if (argumentsList.contains("ps/")) {
            flagList.add("ps/");

            int index2 = argumentsList.indexOf("ps/");

            String selectedParkingSystemType = argumentsList.get(index2 + 1).toUpperCase();

            // check that selected car park type matches at least one of the strings
            String[] validParkingSystemTypes = {"COUPON", "ELECTRONIC"};
            boolean isValidParkingSystemType = Arrays.stream(validParkingSystemTypes)
                                                     .anyMatch(x -> x.equals(selectedParkingSystemType));
            if (!isValidParkingSystemType) {
                throw new ParseException(Messages.MESSAGE_CARPARK_TYPE_IS_INVALID);
            }

            parkingSystemTypeParameter = new ParkingSystemTypeParameter(selectedParkingSystemType);
        }

        // check if there's at least one flag
        if (flagList.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
        }

        return new FilterCommand(flagList, freeParkingParameter, carparkTypeParameter, parkingSystemTypeParameter);
    }
}

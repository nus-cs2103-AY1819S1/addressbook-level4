package seedu.parking.logic.parser;

import static seedu.parking.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import seedu.parking.commons.core.Messages;
import seedu.parking.logic.commands.CalculateCommand;
import seedu.parking.logic.parser.exceptions.ParseException;

/**
 * To be added
 */
public class CalculateCommandParser implements Parser<CalculateCommand> {

    /**
     * To be added
     */
    public CalculateCommand parse(String args) throws ParseException {

        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, CalculateCommand.MESSAGE_USAGE));
        }

        String[] flags = trimmedArgs.split("\\s+");

        String carparkNumber;
        String day;
        String startTime;
        String endTime;

        // Check if right number of parameters input
        try {
            carparkNumber = flags[0].toUpperCase();
            day = flags[1].toUpperCase();
            startTime = flags[2];
            endTime = flags[3];
        } catch (IndexOutOfBoundsException e) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, CalculateCommand.MESSAGE_USAGE));
        }

        // Check that input day is valid
        String[] validDays = {"SUN", "MON", "TUE", "WED", "THU", "FRI", "SAT"};
        boolean isValidDay = Arrays.stream(validDays).anyMatch(x -> x.equals(day));
        if (!isValidDay) {
            throw new ParseException(Messages.MESSAGE_DAY_IS_INVALID);
        }

        // Check that start and end time are valid
        Date inputStart;
        Date inputEnd;
        try {
            SimpleDateFormat dateFormat1 = new SimpleDateFormat("hh.mmaa");
            inputStart = dateFormat1.parse(startTime);
            inputEnd = dateFormat1.parse(endTime);

        } catch (java.text.ParseException pe) {
            throw new ParseException(Messages.MESSAGE_START_OR_END_TIME_HAS_INCORRECT_FORMAT);
        }

        return new CalculateCommand(carparkNumber, day, inputStart, inputEnd);
    }
}

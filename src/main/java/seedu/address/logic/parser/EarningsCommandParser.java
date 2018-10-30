package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.Year;

import seedu.address.logic.commands.EarningsCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new EarningsCommand object
 */
public class EarningsCommandParser implements Parser<EarningsCommand> {

    public static final String MESSAGE_INVALID_DATE = "The given start date is later than the end date";


    public LocalDate getLocalDateFromString(String date) throws DateTimeException {
        int day = Integer.parseInt(date.substring(0, 2));
        int month = Integer.parseInt(date.substring(2, 4));

        return Year.now().atMonth(month).atDay(day);
    }

    /**
     * Parses the given {@code String} of arguments in the context of the EarningsCommand
     * and returns an EarningsCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EarningsCommand parse(String args) throws ParseException {
        requireNonNull(args);
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, EarningsCommand.MESSAGE_USAGE));
        }

        LocalDate startDate;
        LocalDate endDate;

        try {
            startDate = getLocalDateFromString(trimmedArgs.substring(0, 4));
            endDate = getLocalDateFromString(trimmedArgs.substring(5, 9));
            if (startDate.isAfter(endDate)) {
                throw new ParseException(MESSAGE_INVALID_DATE);
            }
        } catch (DateTimeException | IllegalArgumentException | StringIndexOutOfBoundsException e) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EarningsCommand.MESSAGE_USAGE));
        }

        return new EarningsCommand(startDate, endDate);
    }
}

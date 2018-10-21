package seedu.address.logic.parser;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.Year;

import seedu.address.logic.commands.EarningsCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new EarningsCommand object
 */
public class EarningsCommandParser implements Parser<EarningsCommand> {

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
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format("Enter error message here"));
        }

        LocalDate startDate;
        LocalDate endDate;

        try {
            startDate = getLocalDateFromString(trimmedArgs.substring(0, 4));
            endDate = getLocalDateFromString(trimmedArgs.substring(5, 9));
        } catch (DateTimeException | IllegalArgumentException | StringIndexOutOfBoundsException e) {
            throw new ParseException("Wrong date format.");
        }

        return new EarningsCommand(startDate, endDate);
    }
}

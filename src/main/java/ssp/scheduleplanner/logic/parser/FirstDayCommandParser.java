package ssp.scheduleplanner.logic.parser;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import ssp.scheduleplanner.commons.core.Messages;
import ssp.scheduleplanner.logic.commands.FirstDayCommand;
import ssp.scheduleplanner.logic.parser.exceptions.ParseException;
import ssp.scheduleplanner.model.task.Date;

/**
 * Parses input arguments and creates a new FirstDayCommand object based on first argument
 */
public class FirstDayCommandParser implements Parser<FirstDayCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FirstDayCommand
     * and returns an FirstDayCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FirstDayCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, FirstDayCommand.MESSAGE_USAGE));
        }

        if (!onlyOneSetArgument(trimmedArgs)) {
            throw new ParseException(
                    String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, FirstDayCommand.MESSAGE_ONLY_ONE_ARGUMENT));
        }

        if (!Date.isValidDate(trimmedArgs)) {
            throw new ParseException(FirstDayCommand.MESSAGE_INVALID_DATE);
        }

        if (LocalDate.parse(trimmedArgs, DateTimeFormatter.ofPattern("ddMMyy")).getDayOfWeek().name() != "MONDAY") {
            throw new ParseException(FirstDayCommand.MESSAGE_NOT_MONDAY);
        }


        return new FirstDayCommand(trimmedArgs);
    }

    /**
     * If user input only one set of argument in correct format, there should be no space as it
     * suggest more than one set of argument
     * @param string
     * @return
     */
    private boolean onlyOneSetArgument(String string) {
        return containsWhiteSpace(string);
    }

    /**
     * Helper method to check if a string contains white space
     * @param string
     * @return boolean value
     */
    private boolean containsWhiteSpace(String string) {
        for (int i = 0; i < string.length(); i++) {
            if (Character.isWhitespace(string.charAt(i))) {
                return false;
            }
        }
        return true;
    }

}

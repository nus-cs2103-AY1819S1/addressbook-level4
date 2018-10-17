package seedu.address.logic.parser;

import seedu.address.logic.commands.FilterByFeeCommand;
import seedu.address.logic.commands.FilterByTimeCommand;
import seedu.address.logic.parser.exceptions.ParseException;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

/**
 * FilterByFeeCommandParser
 */
public class FilterByFeeCommandParser implements Parser<FilterByFeeCommand> {
    /**
     * FilterByFeeCommand
     * @param args
     * @return
     * @throws ParseException
     */
    public FilterByFeeCommand parse(String args) throws ParseException {

        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterByFeeCommand.MESSAGE_USAGE));
        }

        return new FilterByFeeCommand((double) Integer.parseInt(trimmedArgs));
    }

}

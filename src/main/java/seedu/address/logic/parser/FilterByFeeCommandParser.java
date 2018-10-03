package seedu.address.logic.parser;

import seedu.address.logic.commands.FilterByFeeCommand;
import seedu.address.logic.parser.exceptions.ParseException;

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
            throw new ParseException(
                    String.format("Enter error message here"));
        }

        return new FilterByFeeCommand((double) Integer.parseInt(trimmedArgs));
    }

}

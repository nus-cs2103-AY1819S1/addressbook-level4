package seedu.parking.logic.parser;

import static seedu.parking.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.parking.logic.commands.FilterCommand;
import seedu.parking.logic.parser.exceptions.ParseException;

/**
 * To be added
 */
public class FilterCommandParser implements Parser<FilterCommand> {

    /**
     * To be added
     */
    public FilterCommand parse(String args) throws ParseException {

        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
        }

        String[] flags = trimmedArgs.split("\\s+");

        //System.out.println(flags[0]);   // [space]
        //System.out.println(flags[1]);   // flag 1
        //System.out.println(flags[2]);   // flag 2

        return new FilterCommand(flags);

    }

}

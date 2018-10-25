package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.FilterByTimeCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Time;


/**
 * FilterByTime.
 */
public class FilterByTimeCommandParser implements Parser<FilterByTimeCommand> {
    /**
     * FilterByTimeCommand
     * @param args
     * @return
     * @throws ParseException
     */
    public FilterByTimeCommand parse(String args) throws ParseException {


        Time currTime = new Time(args);
        if (currTime.toString().isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterByTimeCommand.MESSAGE_USAGE));
        }

        return new FilterByTimeCommand(args);

    }
}

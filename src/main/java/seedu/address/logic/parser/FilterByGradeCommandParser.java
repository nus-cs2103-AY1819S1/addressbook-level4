package seedu.address.logic.parser;

import seedu.address.logic.commands.FilterByGradeCommand;
import seedu.address.logic.commands.FilterByTimeCommand;
import seedu.address.logic.parser.exceptions.ParseException;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

/**
 * FilterByGradeCommandParser
 */
public class FilterByGradeCommandParser implements Parser<FilterByGradeCommand> {
    /**
     * FilterByGradeCommand
     * @param args
     * @return
     * @throws ParseException
     */
    public FilterByGradeCommand parse(String args) throws ParseException {

        String[] stringCommand = args.trim().split(" ");
        if (stringCommand[0].isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterByGradeCommand.MESSAGE_USAGE));
        }

        return new FilterByGradeCommand(args.trim());
    }
}

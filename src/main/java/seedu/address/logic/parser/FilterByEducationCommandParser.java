package seedu.address.logic.parser;

import seedu.address.logic.commands.FilterByEducationCommand;
import seedu.address.logic.commands.FilterByTimeCommand;
import seedu.address.logic.parser.exceptions.ParseException;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;


public class FilterByEducationCommandParser implements Parser<FilterByEducationCommand> {
    /**
     * FilterByGradeCommand
     * @param args
     * @return
     * @throws ParseException
     */
    public FilterByEducationCommand parse(String args) throws ParseException {

        String StringCommand = args.trim();

        if(!(StringCommand.equals("pri")||StringCommand.equals("sec")||StringCommand.equals("jc"))){
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterByEducationCommand.MESSAGE_USAGE));
        }
        return new FilterByEducationCommand(StringCommand);
    }
}

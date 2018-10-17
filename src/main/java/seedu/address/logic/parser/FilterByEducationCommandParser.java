package seedu.address.logic.parser;
import seedu.address.logic.commands.EditTimeCommand;
import seedu.address.logic.commands.FilterByEducationCommand;
import seedu.address.logic.commands.FilterByTimeCommand;
import seedu.address.logic.parser.exceptions.ParseException;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

public class FilterByEducationCommandParser {


    /**
     * FilterByGradeCommand
     * @param args
     * @return
     * @throws ParseException
     */
    public FilterByEducationCommand parse(String args) throws ParseException {

        String StringCommand = args.trim();
        if (StringCommand.isEmpty()) {
            throw new ParseException(
                    String.format("Enter error message here"));
        }
        if(!(StringCommand.equals("pri")||StringCommand.equals("sec")||StringCommand.equals("jc"))){
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterByTimeCommand.MESSAGE_USAGE));
        }
        return new FilterByEducationCommand(StringCommand);
    }
}

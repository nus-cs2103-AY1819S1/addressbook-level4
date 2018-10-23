package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import seedu.address.logic.commands.FilterByEducationCommand;
import seedu.address.logic.parser.exceptions.ParseException;



public class FilterByEducationCommandParser implements Parser<FilterByEducationCommand> {

    /**
     * FilterByGradeCommand
     * @param args
     * @return
     * @throws ParseException
     */
    public FilterByEducationCommand parse(String args) throws ParseException {

        String stringCommand = args.trim();
        if (stringCommand.isEmpty()) {
            throw new ParseException(
                    String.format("Enter error message here"));
        }

        if (!(stringCommand.equals("pri") || stringCommand.equals("sec") ||
              stringCommand.equals("jc"))) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, 
                                                   FilterByEducationCommand.MESSAGE_USAGE));
        }
        return new FilterByEducationCommand(stringCommand);
    }
}

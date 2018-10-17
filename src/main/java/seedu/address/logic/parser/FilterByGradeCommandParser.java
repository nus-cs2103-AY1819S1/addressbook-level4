package seedu.address.logic.parser;

import seedu.address.logic.commands.FilterByGradeCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * FilterByGradeCommandParser
 */
public class FilterByGradeCommandParser {
    /**
     * FilterByGradeCommand
     * @param args
     * @return
     * @throws ParseException
     */
    public FilterByGradeCommand parse(String args) throws ParseException {

        String[] stringCommand = args.trim().split(" ");
        if (stringCommand[0].isEmpty()) {
            throw new ParseException(
                    String.format("Enter error message here"));
        }

        return new FilterByGradeCommand(Double.parseDouble(stringCommand[0]), Double.parseDouble(stringCommand[1]));
    }
}

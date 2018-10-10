package seedu.address.logic.parser;

import seedu.address.logic.commands.FilterByFeeCommand;
import seedu.address.logic.commands.FilterByGradeCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class FilterByGradeCommandParser {
    /**
     * FilterByGradeCommand
     * @param args
     * @return
     * @throws ParseException
     */
    public FilterByGradeCommand parse(String args) throws ParseException {

        String[] StringCommand = args.trim().split(" ");
        if (StringCommand[0].isEmpty()) {
            throw new ParseException(
                    String.format("Enter error message here"));
        }

        return new FilterByGradeCommand(Double.parseDouble(StringCommand[0]),Double.parseDouble(StringCommand[1]));
    }
}

package seedu.address.logic.parser;
import seedu.address.logic.commands.FilterByEducationCommand;
import seedu.address.logic.commands.FilterByFeeCommand;
import seedu.address.logic.parser.exceptions.ParseException;

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

        return new FilterByEducationCommand(StringCommand);
    }
}

package seedu.address.logic.parser;
import seedu.address.logic.commands.FilterByTimeCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Time;

/**
 * Finds and lists the list of person whose time is between A to B.
 */

public class FilterByTimeCommandParser {
    /**
     * FilterByGradeCommand
     *
     * @param args
     * @return
     * @throws ParseException
     */
    public FilterByTimeCommand parse(String args) throws ParseException {

        Time currTime = new Time(args);
        if (currTime.toString().isEmpty()) {
            throw new ParseException(
                    String.format("Enter error message here"));
        }

        return new FilterByTimeCommand(currTime);
    }

}

package seedu.address.logic.parser;

import seedu.address.logic.commands.FilterByTimeCommand;
import seedu.address.logic.parser.exceptions.ParseException;

import seedu.address.model.person.Time;


public class FilterByTimeCommandParser implements Parser<FilterByTimeCommand> {
    /** filterByTime
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

        return new FilterByTimeCommand(args);
    }
}

package seedu.address.logic.parser;
import seedu.address.logic.commands.FilterByTimeCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Name;
import seedu.address.model.person.Time;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIME;
import static seedu.address.logic.parser.TimeAddCommandParser.arePrefixesPresent;


public class FilterByTimeCommandParser {/**
 * FilterByGradeCommand
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

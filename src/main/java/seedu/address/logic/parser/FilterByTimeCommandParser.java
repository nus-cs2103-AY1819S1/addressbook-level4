package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIME;

import seedu.address.logic.commands.FilterByTimeCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Time;


/**
 * FilterByTime.
 */
public class FilterByTimeCommandParser implements Parser<FilterByTimeCommand> {
    /**
     * FilterByTimeCommand
     *
     * @param args
     * @return
     * @throws ParseException
     */
    public FilterByTimeCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_TIME);

        if (!argMultimap.getValue(PREFIX_TIME).isPresent()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterByTimeCommand.MESSAGE_USAGE));
        }
        argMultimap.getValue(PREFIX_TIME).get();

        Time time = ParserUtil.parseTime(argMultimap.getValue(PREFIX_TIME).get());
        if (time.toString().isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterByTimeCommand.MESSAGE_USAGE + "1" + args));
        }
        return new FilterByTimeCommand(time);
    }
}

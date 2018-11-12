package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RECORD_HOUR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RECORD_REMARK;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddRecordCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.record.Hour;
import seedu.address.model.record.Record;
import seedu.address.model.record.Remark;

/**
 * Parses input arguments and creates a new AddRecordCommand object
 */
public class AddRecordCommandParser implements Parser<AddRecordCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddRecordCommand
     * and returns an AddRecordCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddRecordCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_RECORD_HOUR, PREFIX_RECORD_REMARK);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddRecordCommand.MESSAGE_USAGE), pe);
        }

        Hour hour;
        Remark remark;

        if (argMultimap.getValue(PREFIX_RECORD_HOUR).isPresent()) {
            hour = ParserRecordUtil.parseHour((argMultimap.getValue(PREFIX_RECORD_HOUR).get()));
        } else {
            hour = new Hour("0");
        }

        if (argMultimap.getValue(PREFIX_RECORD_REMARK).isPresent()) {
            remark = ParserRecordUtil.parseRemark((argMultimap.getValue(PREFIX_RECORD_REMARK).get()));
        } else {
            remark = new Remark("-");
        }

        Record record = new Record(hour, remark);

        return new AddRecordCommand(index, record);
    }
}

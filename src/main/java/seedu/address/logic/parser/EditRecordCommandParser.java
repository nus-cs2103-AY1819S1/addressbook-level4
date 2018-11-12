package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RECORD_HOUR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RECORD_REMARK;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditRecordCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new EditRecordCommand object
 */
public class EditRecordCommandParser implements Parser<EditRecordCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditRecordCommand
     * and returns an EditRecordCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditRecordCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_RECORD_HOUR, PREFIX_RECORD_REMARK);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    EditRecordCommand.MESSAGE_USAGE), pe);
        }

        EditRecordCommand.EditRecordDescriptor editRecordDescriptor =
                new EditRecordCommand.EditRecordDescriptor();
        if (argMultimap.getValue(PREFIX_RECORD_HOUR).isPresent()) {
            editRecordDescriptor.setHour(ParserRecordUtil.parseHour(
                    argMultimap.getValue(PREFIX_RECORD_HOUR).get()));
        }
        if (argMultimap.getValue(PREFIX_RECORD_REMARK).isPresent()) {
            editRecordDescriptor.setRemark(ParserRecordUtil.parseRemark(
                    argMultimap.getValue(PREFIX_RECORD_REMARK).get()));
        }

        if (!editRecordDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditRecordCommand.MESSAGE_NOT_EDITED);
        }

        return new EditRecordCommand(index, editRecordDescriptor);
    }
}

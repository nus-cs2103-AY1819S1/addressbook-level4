package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MARK;

import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.MarkAssignmentCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.common.Mark;

/**
 * Parses input arguments and creates a new MarkAssignmentCommand object
 */
public class MarkAssignmentCommandParser implements Parser<MarkAssignmentCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the MarkAssignmentCommand
     * and returns an MarkAssignmentCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public MarkAssignmentCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_ID, PREFIX_MARK);

        if (!arePrefixesPresent(argMultimap, PREFIX_ID, PREFIX_MARK)
                || argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkAssignmentCommand.MESSAGE_USAGE)
            );
        }

        Index index = ParserUtil.parseIndex(argMultimap.getPreamble());
        Index assignmentIndex = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_ID).get());
        Mark assignmentMark = ParserUtil.parseMark(argMultimap.getValue(PREFIX_MARK).get());

        return new MarkAssignmentCommand(index, assignmentIndex, assignmentMark);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}

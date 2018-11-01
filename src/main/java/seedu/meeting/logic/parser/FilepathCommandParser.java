package seedu.meeting.logic.parser;

import static seedu.meeting.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.meeting.logic.parser.CliSyntax.PREFIX_PATH;

import java.nio.file.Path;
import java.util.stream.Stream;

import seedu.meeting.logic.commands.FilepathCommand;
import seedu.meeting.logic.parser.exceptions.ParseException;


/**
 * Parses input arguments and creates a new ExportCommand object
 */
public class FilepathCommandParser implements Parser<FilepathCommand> {


    @Override
    public FilepathCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_PATH);
        if (argMultimap.getPreamble().equalsIgnoreCase(FilepathCommand.OPTION_SHOW)) {
            return new FilepathCommand(null);
        }

        if (!arePrefixesPresent(argMultimap, PREFIX_PATH)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilepathCommand.MESSAGE_USAGE));
        }

        Path filePath = ParserUtil.parsePath(argMultimap.getValue(PREFIX_PATH).get());
        return new FilepathCommand(filePath);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}


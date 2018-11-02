package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PATH;

import java.nio.file.Path;
import java.util.stream.Stream;

import seedu.address.logic.commands.ImportCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ExportCommand object
 */
public class ImportCommandParser implements Parser<ImportCommand> {

    @Override
    public ImportCommand parse(String args) throws ParseException {

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_PATH);
        if (argMultimap.getPreamble().equalsIgnoreCase(ImportCommand.OPTION_OVERWRITE)) {

            Path filePath = ParserUtil.parsePath(argMultimap.getValue(PREFIX_PATH).get());
            return new ImportCommand(filePath, true);
        }
        if (!arePrefixesPresent(argMultimap, PREFIX_PATH)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ImportCommand.MESSAGE_USAGE));
        }

        Path filePath = ParserUtil.parsePath(argMultimap.getValue(PREFIX_PATH).get());
        return new ImportCommand(filePath, false);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}


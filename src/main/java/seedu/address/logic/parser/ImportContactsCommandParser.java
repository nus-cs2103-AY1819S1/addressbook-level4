package seedu.address.logic.parser;

import seedu.address.logic.commands.ImportContactsCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.*;

import java.util.stream.Stream;
import java.io.File;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.*;

public class ImportContactsCommandParser {
    /**
     * Parses the given {@code String} of arguments in the context of the ImportContactsCommand
     * and returns an ImportContactsCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ImportContactsCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_FILE);

        if (!arePrefixesPresent(argMultimap, PREFIX_FILE)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ImportContactsCommand.MESSAGE_USAGE));
        }

        File file = ParserUtil.parseFilePath(argMultimap.getValue(PREFIX_FILE).get());

        return new ImportContactsCommand(file);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}

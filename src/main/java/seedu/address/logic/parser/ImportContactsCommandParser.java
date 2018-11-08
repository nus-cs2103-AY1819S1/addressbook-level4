package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_FILE_READER_INVALID_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.ArgumentMultimap.arePrefixesPresent;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FILE;

import seedu.address.logic.commands.ImportContactsCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.filereader.FilePath;
import seedu.address.model.filereader.FileReader;

/**
 * Parses input arguments and creates a new ImportContactsCommand object
 */
public class ImportContactsCommandParser implements Parser<ImportContactsCommand> {
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
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    ImportContactsCommand.MESSAGE_USAGE));
        }
        FilePath filePath = ParserUtil.parseFilePath(argMultimap.getValue(PREFIX_FILE).get());
        FileReader fileReader = new FileReader(filePath);

        if (!fileReader.isValidFile()) {
            throw new ParseException(String.format(MESSAGE_FILE_READER_INVALID_FORMAT,
                    ImportContactsCommand.MESSAGE_WRONG_FILE_FORMAT));
        }

        return new ImportContactsCommand(fileReader);
    }
}

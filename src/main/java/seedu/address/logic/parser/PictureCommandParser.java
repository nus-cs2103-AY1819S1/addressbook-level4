package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FILE_LOCATION;

import java.nio.file.Path;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.PictureCommand;
import seedu.address.logic.parser.exceptions.ParseException;

//@@author denzelchung
/**
 * Parses input arguments and creates a new PictureCommand object
 */
public class PictureCommandParser implements Parser<PictureCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the PictureCommand
     * and returns an PictureCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public PictureCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
            ArgumentTokenizer.tokenize(args, PREFIX_FILE_LOCATION);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, PictureCommand.MESSAGE_USAGE), pe);
        }

        if (!argMultimap.getValue(PREFIX_FILE_LOCATION).isPresent()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, PictureCommand.MESSAGE_USAGE));
        }

        Path fileLocation = ParserUtil.parseFileLocation(argMultimap.getValue(PREFIX_FILE_LOCATION).get());

        return new PictureCommand(index, fileLocation);
    }
}

package seedu.jxmusic.logic.parser;

import static seedu.jxmusic.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.jxmusic.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.jxmusic.logic.parser.CliSyntax.PREFIX_PLAYLIST;

import java.util.stream.Stream;

import seedu.jxmusic.commons.core.index.Index;
import seedu.jxmusic.logic.commands.TrackDeleteCommand;
import seedu.jxmusic.logic.parser.exceptions.ParseException;
import seedu.jxmusic.model.Playlist;

/**
 * Parses input arguments and creates a new TrackDeleteCommand object
 */
public class TrackDeleteCommandParser implements Parser<TrackDeleteCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the TrackDeleteCommand
     * and returns an TrackDeleteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public TrackDeleteCommand parse(String args) throws ParseException {
        try {
            ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_PLAYLIST, PREFIX_INDEX);

            if (!arePrefixesPresent(argMultimap, PREFIX_PLAYLIST, PREFIX_INDEX)
                    || !argMultimap.getPreamble().isEmpty()) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                                TrackDeleteCommand.MESSAGE_USAGE));
            }

            Playlist targetPlaylist = ParserUtil.parsePlaylist(argMultimap.getValue(PREFIX_PLAYLIST).get());
            Index index = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_INDEX).get());
            return new TrackDeleteCommand(targetPlaylist, index);
        } catch (ArrayIndexOutOfBoundsException | NullPointerException ex) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT + " args: " + args,
                    TrackDeleteCommand.MESSAGE_USAGE));
        }
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}

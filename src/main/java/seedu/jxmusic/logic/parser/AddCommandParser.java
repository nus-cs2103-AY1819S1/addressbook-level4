package seedu.jxmusic.logic.parser;

import static seedu.jxmusic.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.jxmusic.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.jxmusic.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.jxmusic.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.jxmusic.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.jxmusic.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.jxmusic.logic.parser.CliSyntax.PREFIX_TRACK;

import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import seedu.jxmusic.logic.commands.AddCommand;
import seedu.jxmusic.logic.parser.exceptions.ParseException;
import seedu.jxmusic.model.Name;
import seedu.jxmusic.model.Playlist;
import seedu.jxmusic.model.Track;
import seedu.jxmusic.model.tag.Tag;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser implements Parser<AddCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_TAG);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_ADDRESS, PREFIX_PHONE, PREFIX_EMAIL)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        List<Track> trackList = ParserUtil.parseTrack(argMultimap.getAllValues(PREFIX_TRACK));

        Playlist playlist = new Playlist(name, trackList);

        return new AddCommand(playlist);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}

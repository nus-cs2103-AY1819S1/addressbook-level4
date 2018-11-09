package seedu.jxmusic.logic.parser;

import static seedu.jxmusic.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.jxmusic.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.jxmusic.logic.commands.ClearCommand;
import seedu.jxmusic.logic.commands.Command;
//import seedu.jxmusic.logic.commands.EditCommand;      //todo
import seedu.jxmusic.logic.commands.ExitCommand;
import seedu.jxmusic.logic.commands.HelpCommand;
import seedu.jxmusic.logic.commands.PauseCommand;
import seedu.jxmusic.logic.commands.PlayCommand;
import seedu.jxmusic.logic.commands.PlaylistDelCommand;
import seedu.jxmusic.logic.commands.PlaylistListCommand;
import seedu.jxmusic.logic.commands.PlaylistNewCommand;
import seedu.jxmusic.logic.commands.PlaylistSearchCommand;
import seedu.jxmusic.logic.commands.SeekCommand;
import seedu.jxmusic.logic.commands.StopCommand;
import seedu.jxmusic.logic.commands.TrackAddCommand;
import seedu.jxmusic.logic.commands.TrackDeleteCommand;
import seedu.jxmusic.logic.commands.TrackListCommand;
import seedu.jxmusic.logic.commands.TrackSearchCommand;
import seedu.jxmusic.logic.parser.exceptions.ParseException;

/**
 * Parses user input.
 */
public class LibraryParser {

    /**
     * Used for initial separation of command phrase and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT =
            Pattern.compile("(?<commandPhrase>(?:[a-zA-Z]+\\s+){0,1}[a-zA-Z]+(?!/))(?<arguments>.*)");

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parseCommand(String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandPhrase").trim();
        final String arguments = matcher.group("arguments"); // ArgumentTokenizer requires space prefixed string
        switch (commandWord) {

        case PlayCommand.COMMAND_PHRASE:
            return new PlayCommandParser().parse(arguments);

        case PauseCommand.COMMAND_WORD:
            return new PauseCommand(); // todo parse (argument)

        case StopCommand.COMMAND_WORD:
            return new StopCommand();

        case SeekCommand.COMMAND_WORD:
            //double time = arguments.toInt() sth like this, change the string to time in double
            //return new SeekCommand(time);

        case PlaylistListCommand.COMMAND_PHRASE:
            return new PlaylistListCommand();

        case PlaylistNewCommand.COMMAND_PHRASE:
            return new PlaylistNewCommandParser().parse(arguments);

        case TrackListCommand.COMMAND_PHRASE:
            return new TrackListCommand();

        case TrackAddCommand.COMMAND_PHRASE:
            return new TrackAddCommandParser().parse(arguments);

        case TrackDeleteCommand.COMMAND_PHRASE:
            return new TrackDeleteCommandParser().parse(arguments);

        case TrackSearchCommand.COMMAND_PHRASE:
            return new TrackSearchCommandParser().parse(arguments);

        // case EditCommand.COMMAND_PHRASE:
        //     return new EditCommandParser().parse(arguments);

        // case SelectCommand.COMMAND_WORD:
        //     return new SelectCommandParser().parse(arguments);

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        // case ListCommand.COMMAND_PHRASE:
        //     return new ListCommand();

        case PlaylistDelCommand.COMMAND_PHRASE:
            return new PlaylistDelCommandParser().parse(arguments);

        case ClearCommand.COMMAND_PHRASE:
            return new ClearCommand();

        case PlaylistSearchCommand.COMMAND_PHRASE:
            return new PlaylistSearchCommandParser().parse(arguments);

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }
}

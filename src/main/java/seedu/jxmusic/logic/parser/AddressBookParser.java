package seedu.jxmusic.logic.parser;

import static seedu.jxmusic.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.jxmusic.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.jxmusic.logic.commands.AddCommand;
import seedu.jxmusic.logic.commands.ClearCommand;
import seedu.jxmusic.logic.commands.Command;
import seedu.jxmusic.logic.commands.DeleteCommand;
import seedu.jxmusic.logic.commands.EditCommand;
import seedu.jxmusic.logic.commands.ExitCommand;
import seedu.jxmusic.logic.commands.FindCommand;
import seedu.jxmusic.logic.commands.HelpCommand;
import seedu.jxmusic.logic.commands.HistoryCommand;
import seedu.jxmusic.logic.commands.ListCommand;
import seedu.jxmusic.logic.commands.PauseCommand;
import seedu.jxmusic.logic.commands.PlayPlaylistCommand;
import seedu.jxmusic.logic.commands.RedoCommand;
import seedu.jxmusic.logic.commands.SeekCommand;
import seedu.jxmusic.logic.commands.SelectCommand;
import seedu.jxmusic.logic.commands.StopCommand;
import seedu.jxmusic.logic.commands.UndoCommand;
import seedu.jxmusic.logic.parser.exceptions.ParseException;

/**
 * Parses user input.
 */
public class AddressBookParser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

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

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");
        switch (commandWord) {

        case PlayPlaylistCommand.COMMAND_WORD:
            return new PlayPlaylistCommand(); // todo parse (argument)

        case PauseCommand.COMMAND_WORD:
            return new PauseCommand(); // todo parse (argument)

        case StopCommand.COMMAND_WORD:
            return new StopCommand();

        case SeekCommand.COMMAND_WORD:
            //double time = arguments.toInt() sth like this, change the string to time in double
            //return new SeekCommand(time);

        case AddCommand.COMMAND_WORD:
            return new AddCommandParser().parse(arguments);

        case EditCommand.COMMAND_WORD:
            return new EditCommandParser().parse(arguments);

        case SelectCommand.COMMAND_WORD:
            return new SelectCommandParser().parse(arguments);

        case DeleteCommand.COMMAND_WORD:
            return new DeleteCommandParser().parse(arguments);

        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();

        case FindCommand.COMMAND_WORD:
            return new FindCommandParser().parse(arguments);

        case ListCommand.COMMAND_WORD:
            return new ListCommand();

        case HistoryCommand.COMMAND_WORD:
            return new HistoryCommand();

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        case UndoCommand.COMMAND_WORD:
            return new UndoCommand();

        case RedoCommand.COMMAND_WORD:
            return new RedoCommand();

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}

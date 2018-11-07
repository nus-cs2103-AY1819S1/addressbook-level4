package seedu.parking.logic.parser;

import static seedu.parking.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.parking.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.parking.logic.commands.CalculateCommand;
import seedu.parking.logic.commands.ClearCommand;
import seedu.parking.logic.commands.Command;
import seedu.parking.logic.commands.ExitCommand;
import seedu.parking.logic.commands.FilterCommand;
import seedu.parking.logic.commands.FindCommand;
import seedu.parking.logic.commands.HelpCommand;
import seedu.parking.logic.commands.HistoryCommand;
import seedu.parking.logic.commands.ListCommand;
import seedu.parking.logic.commands.NotifyCommand;
import seedu.parking.logic.commands.QueryCommand;
import seedu.parking.logic.commands.RedoCommand;
import seedu.parking.logic.commands.SelectCommand;
import seedu.parking.logic.commands.UndoCommand;
import seedu.parking.logic.parser.exceptions.ParseException;

/**
 * Parses user input.
 */
public class CarparkFinderParser {

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

        case SelectCommand.COMMAND_WORD:
        case SelectCommand.COMMAND_ABBREVIATION:
            return new SelectCommandParser().parse(arguments);

        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();

        case FindCommand.COMMAND_WORD:
        case FindCommand.COMMAND_ABBREVIATION:
            return new FindCommandParser().parse(arguments);

        case ListCommand.COMMAND_WORD:
        case ListCommand.COMMAND_ABBREVIATION:
            return new ListCommand();

        case HistoryCommand.COMMAND_WORD:
            return new HistoryCommand();

        case ExitCommand.COMMAND_WORD:
        case ExitCommand.COMMAND_ABBREVIATION:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
        case HelpCommand.COMMAND_ABBREVIATION:
            return new HelpCommand();

        case UndoCommand.COMMAND_WORD:
            return new UndoCommand();

        case RedoCommand.COMMAND_WORD:
            return new RedoCommand();

        case FilterCommand.COMMAND_WORD:
        case FilterCommand.COMMAND_ABBREVIATION:
            return new FilterCommandParser().parse(arguments);

        case CalculateCommand.COMMAND_WORD:
        case CalculateCommand.COMMAND_ABBREVIATION:
            return new CalculateCommandParser().parse(arguments);

        case QueryCommand.COMMAND_WORD:
        case QueryCommand.COMMAND_ABBREVIATION:
            return new QueryCommand();

        case NotifyCommand.COMMAND_WORD:
        case NotifyCommand.COMMAND_ABBREVIATION:
            return new NotifyCommandParser().parse(arguments);

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}

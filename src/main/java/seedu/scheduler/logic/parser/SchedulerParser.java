package seedu.scheduler.logic.parser;

import static seedu.scheduler.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.scheduler.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.scheduler.logic.commands.AddCommand;
import seedu.scheduler.logic.commands.AddReminderCommand;
import seedu.scheduler.logic.commands.ClearCommand;
import seedu.scheduler.logic.commands.Command;
import seedu.scheduler.logic.commands.DeleteCommand;
import seedu.scheduler.logic.commands.DeleteReminderCommand;
import seedu.scheduler.logic.commands.EditCommand;
import seedu.scheduler.logic.commands.EnterGoogleCalendarModeCommand;
import seedu.scheduler.logic.commands.ExitCommand;
import seedu.scheduler.logic.commands.FindCommand;
import seedu.scheduler.logic.commands.HelpCommand;
import seedu.scheduler.logic.commands.HistoryCommand;
import seedu.scheduler.logic.commands.ListCommand;
import seedu.scheduler.logic.commands.PostponeReminderCommand;
import seedu.scheduler.logic.commands.RedoCommand;
import seedu.scheduler.logic.commands.SelectCommand;
import seedu.scheduler.logic.commands.UndoCommand;
import seedu.scheduler.logic.parser.exceptions.ParseException;

/**
 * Parses user input.
 */
public class SchedulerParser {

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

        case AddCommand.COMMAND_WORD:
        case AddCommand.COMMAND_ALIAS_ONE:
        case AddCommand.COMMAND_ALIAS_TWO:
            return new AddCommandParser().parse(arguments);

        case EditCommand.COMMAND_WORD:
        case EditCommand.COMMAND_ALIAS_ONE:
        case EditCommand.COMMAND_ALIAS_TWO:
            return new EditCommandParser().parse(arguments);


        case AddReminderCommand.COMMAND_WORD:
            return new AddReminderCommandParser().parse(arguments);

        case DeleteReminderCommand.COMMAND_WORD:
            return new DeleteReminderCommandParser().parse(arguments);

        case PostponeReminderCommand.COMMAND_WORD:
            return new PostponeReminderCommandParser().parse(arguments);

        case SelectCommand.COMMAND_WORD:
        case SelectCommand.COMMAND_ALIAS_ONE:
        case SelectCommand.COMMAND_ALIAS_TWO:
        case SelectCommand.COMMAND_ALIAS_THREE:
        case SelectCommand.COMMAND_ALIAS_FOUR:
        case SelectCommand.COMMAND_ALIAS_FIVE:
            return new SelectCommandParser().parse(arguments);

        case DeleteCommand.COMMAND_WORD:
        case DeleteCommand.COMMAND_ALIAS_ONE:
        case DeleteCommand.COMMAND_ALIAS_TWO:
        case DeleteCommand.COMMAND_ALIAS_THREE:
        case DeleteCommand.COMMAND_ALIAS_FOUR:
        case DeleteCommand.COMMAND_ALIAS_FIVE:
            return new DeleteCommandParser().parse(arguments);

        case ClearCommand.COMMAND_WORD:
        case ClearCommand.COMMAND_ALIAS_ONE:
        case ClearCommand.COMMAND_ALIAS_TWO:
        case ClearCommand.COMMAND_ALIAS_THREE:
        case ClearCommand.COMMAND_ALIAS_FOUR:
            return new ClearCommand();

        case FindCommand.COMMAND_WORD:
        case FindCommand.COMMAND_ALIAS_ONE:
        case FindCommand.COMMAND_ALIAS_TWO:
        case FindCommand.COMMAND_ALIAS_THREE:
            return new FindCommandParser().parse(arguments);

        case EnterGoogleCalendarModeCommand.COMMAND_WORD:
            return new EnterGoogleCalendarModeCommand();

        case ListCommand.COMMAND_WORD:
        case ListCommand.COMMAND_ALIAS_ONE:
        case ListCommand.COMMAND_ALIAS_TWO:
        case ListCommand.COMMAND_ALIAS_THREE:
            return new ListCommand();

        case HistoryCommand.COMMAND_WORD:
        case HistoryCommand.COMMAND_ALIAS_ONE:
        case HistoryCommand.COMMAND_ALIAS_TWO:
        case HistoryCommand.COMMAND_ALIAS_THREE:
        case HistoryCommand.COMMAND_ALIAS_FOUR:
        case HistoryCommand.COMMAND_ALIAS_FIVE:
            return new HistoryCommand();

        case ExitCommand.COMMAND_WORD:
        case ExitCommand.COMMAND_ALIAS_ONE:
        case ExitCommand.COMMAND_ALIAS_TWO:
        case ExitCommand.COMMAND_ALIAS_THREE:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
        case HelpCommand.COMMAND_ALIAS_ONE:
        case HelpCommand.COMMAND_ALIAS_TWO:
        case HelpCommand.COMMAND_ALIAS_THREE:
            return new HelpCommand();

        case UndoCommand.COMMAND_WORD:
        case UndoCommand.COMMAND_ALIAS_ONE:
        case UndoCommand.COMMAND_ALIAS_TWO:
        case UndoCommand.COMMAND_ALIAS_THREE:
            return new UndoCommand();

        case RedoCommand.COMMAND_WORD:
        case RedoCommand.COMMAND_ALIAS_ONE:
        case RedoCommand.COMMAND_ALIAS_TWO:
        case RedoCommand.COMMAND_ALIAS_THREE:
            return new RedoCommand();

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}

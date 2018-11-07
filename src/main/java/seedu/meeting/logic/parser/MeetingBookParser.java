package seedu.meeting.logic.parser;

import static seedu.meeting.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.meeting.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.meeting.logic.commands.AddCommand;
import seedu.meeting.logic.commands.AddGroupCommand;
import seedu.meeting.logic.commands.CancelCommand;
import seedu.meeting.logic.commands.ClearCommand;
import seedu.meeting.logic.commands.Command;
import seedu.meeting.logic.commands.DeleteCommand;
import seedu.meeting.logic.commands.DeleteGroupCommand;
import seedu.meeting.logic.commands.EditCommand;
import seedu.meeting.logic.commands.ExitCommand;
import seedu.meeting.logic.commands.ExportCommand;
import seedu.meeting.logic.commands.FilepathCommand;
import seedu.meeting.logic.commands.FindCommand;
import seedu.meeting.logic.commands.HelpCommand;
import seedu.meeting.logic.commands.HistoryCommand;
import seedu.meeting.logic.commands.ImportCommand;
import seedu.meeting.logic.commands.JoinCommand;
import seedu.meeting.logic.commands.LeaveCommand;
import seedu.meeting.logic.commands.ListCommand;
import seedu.meeting.logic.commands.MeetCommand;
import seedu.meeting.logic.commands.RedoCommand;
import seedu.meeting.logic.commands.SelectCommand;
import seedu.meeting.logic.commands.SortCommand;
import seedu.meeting.logic.commands.UndoCommand;
import seedu.meeting.logic.parser.exceptions.ParseException;

/**
 * Parses user input.
 */
public class MeetingBookParser {

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
            return new AddCommandParser().parse(arguments);

        case AddGroupCommand.COMMAND_WORD:
            return new AddGroupCommandParser().parse(arguments);

        case EditCommand.COMMAND_WORD:
            return new EditCommandParser().parse(arguments);

        case SelectCommand.COMMAND_WORD:
            return new SelectCommandParser().parse(arguments);

        case DeleteCommand.COMMAND_WORD:
        case DeleteCommand.COMMAND_ALIAS:
            return new DeleteCommandParser().parse(arguments);

        case DeleteGroupCommand.COMMAND_WORD:
            return new DeleteGroupCommandParser().parse(arguments);

        case JoinCommand.COMMAND_WORD:
            return new JoinCommandParser().parse(arguments);

        case LeaveCommand.COMMAND_WORD:
            return new LeaveCommandParser().parse(arguments);

        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();

        case FindCommand.COMMAND_WORD:
            return new FindCommandParser().parse(arguments);

        case SortCommand.COMMAND_WORD:
            return new SortCommandParser().parse(arguments);

        case ListCommand.COMMAND_WORD:
            return new ListCommandParser().parse(arguments);

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

        case ExportCommand.COMMAND_WORD:
            return new ExportCommandParser().parse(arguments);

        case MeetCommand.COMMAND_WORD:
            return new MeetCommandParser().parse(arguments);

        case CancelCommand.COMMAND_WORD:
            return new CancelCommandParser().parse(arguments);

        case FilepathCommand.COMMAND_WORD:
            return new FilepathCommandParser().parse(arguments);

        case ImportCommand.COMMAND_WORD:
            return new ImportCommandParser().parse(arguments);

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}

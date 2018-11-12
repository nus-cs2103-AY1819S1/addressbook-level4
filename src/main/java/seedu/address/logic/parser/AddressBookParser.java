package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.ActiveCommand;
import seedu.address.logic.commands.AddAssignmentCommand;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.ArchiveCommand;
import seedu.address.logic.commands.AssignmentCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.DeleteAssignmentCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditAssignmentCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.LeaveApplyCommand;
import seedu.address.logic.commands.LeaveApproveCommand;
import seedu.address.logic.commands.LeaveListCommand;
import seedu.address.logic.commands.LeaveRejectCommand;
import seedu.address.logic.commands.ListAssignmentCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.LogoutCommand;
import seedu.address.logic.commands.ModifyPermissionCommand;
import seedu.address.logic.commands.PasswordCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.RestoreCommand;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.logic.commands.SelfEditCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.commands.ViewPermissionCommand;
import seedu.address.logic.parser.exceptions.ParseException;

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

        case AddCommand.COMMAND_WORD:
            return new AddCommandParser().parse(arguments);

        case ArchiveCommand.COMMAND_WORD:
            return new ArchiveCommand();

        case ActiveCommand.COMMAND_WORD:
            return new ActiveCommand();

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

        case RestoreCommand.COMMAND_WORD:
            return new RestoreCommandParser().parse(arguments);

        case ModifyPermissionCommand.COMMAND_WORD:
            return new ModifyPermissionCommandParser().parse(arguments);

        case LeaveApplyCommand.COMMAND_WORD:
            return new LeaveApplyCommandParser().parse(arguments);

        case LeaveListCommand.COMMAND_WORD:
            return new LeaveListCommand();

        case LeaveApproveCommand.COMMAND_WORD:
            return new LeaveApproveCommandParser().parse(arguments);

        case LeaveRejectCommand.COMMAND_WORD:
            return new LeaveRejectCommandParser().parse(arguments);

        case LogoutCommand.COMMAND_WORD:
            return new LogoutCommand();

        case AddAssignmentCommand.COMMAND_WORD:
            return new AddAssignmentParser().parse(arguments);

        case ListAssignmentCommand.COMMAND_WORD:
            return new ListAssignmentCommand();

        case DeleteAssignmentCommand.COMMAND_WORD:
            return new DeleteAssignmentCommandParser().parse(arguments);

        case EditAssignmentCommand.COMMAND_WORD:
            return new EditAssignmentCommandParser().parse(arguments);

        case PasswordCommand.COMMAND_WORD:
            return new PasswordCommand();

        case ViewPermissionCommand.COMMAND_WORD:
            return new ViewPermissionCommandParser().parse(arguments);

        case SelfEditCommand.COMMAND_WORD:
            return new SelfEditCommandParser().parse(arguments);

        case AssignmentCommand.COMMAND_WORD:
            return new AssignmentCommand();

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}

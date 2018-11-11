package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.DecryptCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EncryptCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.LoginCommand;
import seedu.address.logic.commands.MassEditCommand;
import seedu.address.logic.commands.NotificationCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.logic.commands.SetBudgetCommand;
import seedu.address.logic.commands.SetCategoryBudgetCommand;
import seedu.address.logic.commands.SetPasswordCommand;
import seedu.address.logic.commands.SetRecurringBudgetCommand;
import seedu.address.logic.commands.SignUpCommand;
import seedu.address.logic.commands.StatsCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses user input.
 */
public class ExpenseTrackerParser {

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

        case AddCommand.COMMAND_ALIAS:
            //Fallthrough
        case AddCommand.COMMAND_WORD:
            return new AddCommandParser().parse(arguments);

        case EditCommand.COMMAND_ALIAS:
            //Fallthrough
        case EditCommand.COMMAND_WORD:
            return new EditCommandParser().parse(arguments);

        case MassEditCommand.COMMAND_ALIAS:
            //Fallthrough
        case MassEditCommand.COMMAND_WORD:
            return new MassEditCommandParser().parse(arguments);

        case SelectCommand.COMMAND_ALIAS:
            //Fallthrough
        case SelectCommand.COMMAND_WORD:
            return new SelectCommandParser().parse(arguments);

        case DeleteCommand.COMMAND_ALIAS:
            //Fallthrough
        case DeleteCommand.COMMAND_WORD:
            return new DeleteCommandParser().parse(arguments);

        case ClearCommand.COMMAND_ALIAS:
            //Fallthrough
        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();

        case FindCommand.COMMAND_ALIAS:
            //Fallthrough
        case FindCommand.COMMAND_WORD:
            return new FindCommandParser().parse(arguments);

        case ListCommand.COMMAND_ALIAS:
            //Fallthrough
        case ListCommand.COMMAND_WORD:
            return new ListCommand();

        case HistoryCommand.COMMAND_ALIAS:
            //Fallthrough
        case HistoryCommand.COMMAND_WORD:
            return new HistoryCommand();

        case ExitCommand.COMMAND_ALIAS:
            //Fallthrough
        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        case UndoCommand.COMMAND_ALIAS:
            //Fallthrough
        case UndoCommand.COMMAND_WORD:
            return new UndoCommand();

        case RedoCommand.COMMAND_ALIAS:
            //Fallthrough
        case RedoCommand.COMMAND_WORD:
            return new RedoCommand();

        case LoginCommand.COMMAND_ALIAS:
            //Fallthrough
        case LoginCommand.COMMAND_WORD:
            return new LoginCommandParser().parse(arguments);

        case SignUpCommand.COMMAND_ALIAS:
            //Fallthrough
        case SignUpCommand.COMMAND_WORD:
            return new SignUpCommandParser().parse(arguments);

        case SetBudgetCommand.COMMAND_ALIAS:
            //Fallthrough
        case SetBudgetCommand.COMMAND_WORD:
            return new SetBudgetCommandParser().parse(arguments);

        case StatsCommand.COMMAND_ALIAS:
            //Fallthrough
        case StatsCommand.COMMAND_WORD:
            return new StatsCommandParser().parse(arguments);

        case SetRecurringBudgetCommand.COMMAND_ALIAS:
            //Fallthrough
        case SetRecurringBudgetCommand.COMMAND_WORD:
            return new SetRecurringBudgetCommandParser().parse(arguments);

        case SetCategoryBudgetCommand.COMMAND_ALIAS:
            //Fallthrough
        case SetCategoryBudgetCommand.COMMAND_WORD:
            return new SetCategoryBudgetCommandParser().parse(arguments);

        case SetPasswordCommand.COMMAND_ALIAS:
            //Fallthrough
        case SetPasswordCommand.COMMAND_WORD:
            return new SetPasswordCommandParser().parse(arguments);

        case NotificationCommand.COMMAND_ALIAS:
            //Fallthrough
        case NotificationCommand.COMMAND_WORD:
            return new NotificationCommandParser().parse(arguments);

        case EncryptCommand.COMMAND_ALIAS:
            //Fallthrough
        case EncryptCommand.COMMAND_WORD:
            return new EncryptCommandParser().parse(arguments);

        case DecryptCommand.COMMAND_ALIAS:
            //Fallthrough
        case DecryptCommand.COMMAND_WORD:
            return new DecryptCommandParser().parse(arguments);

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}

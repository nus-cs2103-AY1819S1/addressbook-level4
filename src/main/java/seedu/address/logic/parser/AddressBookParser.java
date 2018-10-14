package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.commands.accounting.AcceptDebtRequestCommand;
import seedu.address.logic.commands.accounting.AddDebtCommand;
import seedu.address.logic.commands.accounting.ClearDebtCommand;
import seedu.address.logic.commands.accounting.DeleteDebtRequestCommand;
import seedu.address.logic.commands.accounting.ListCreditorCommand;
import seedu.address.logic.commands.accounting.ListDebtHistoryCommand;
import seedu.address.logic.commands.accounting.ListDebtRequestReceivedCommand;
import seedu.address.logic.commands.accounting.ListDebtRequestSentCommand;
import seedu.address.logic.commands.accounting.ListDebtorCommand;
import seedu.address.logic.commands.jio.CreateJioCommand;
import seedu.address.logic.commands.user.LoginCommand;
import seedu.address.logic.commands.user.LogoutCommand;
import seedu.address.logic.commands.user.SignUpCommand;
import seedu.address.logic.parser.accounting.AcceptDebtRequestCommandParser;
import seedu.address.logic.parser.accounting.AddDebtCommandParser;
import seedu.address.logic.parser.accounting.ClearDebtCommandParser;
import seedu.address.logic.parser.accounting.DeleteDebtRequestCommandParser;
import seedu.address.logic.parser.accounting.ListCreditorCommandParser;
import seedu.address.logic.parser.accounting.ListDebtHistoryCommandParser;
import seedu.address.logic.parser.accounting.ListDebtRequestReceivedCommandParser;
import seedu.address.logic.parser.accounting.ListDebtRequestSentCommandParser;
import seedu.address.logic.parser.accounting.ListDebtorCommandParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.logic.parser.jio.CreateJioCommandParser;


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

        case SignUpCommand.COMMAND_WORD:
            return new SignUpCommandParser().parse(arguments);

        case LoginCommand.COMMAND_WORD:
            return new LoginCommandParser().parse(arguments);

        case LogoutCommand.COMMAND_WORD:
            return new LogoutCommand();

        // Restaurant Commands

        // Friend Commands

        // Jio Commands
        case CreateJioCommand.COMMAND_WORD:
            return new CreateJioCommandParser().parse(arguments);

        // Group Commands

        // Timetable Commands

        // Accounting Commands
        case AddDebtCommand.COMMAND_WORD:
            return new AddDebtCommandParser().parse(arguments);

        case ClearDebtCommand.COMMAND_WORD:
            return new ClearDebtCommandParser().parse(arguments);

        case AcceptDebtRequestCommand.COMMAND_WORD:
            return new AcceptDebtRequestCommandParser().parse(arguments);

        case DeleteDebtRequestCommand.COMMAND_WORD:
            return new DeleteDebtRequestCommandParser().parse(arguments);

        case ListCreditorCommand.COMMAND_WORD:
            return new ListCreditorCommandParser().parse(arguments);

        case ListDebtorCommand.COMMAND_WORD:
            return new ListDebtorCommandParser().parse(arguments);

        case ListDebtHistoryCommand.COMMAND_WORD:
            return new ListDebtHistoryCommandParser().parse(arguments);

        case ListDebtRequestSentCommand.COMMAND_WORD:
            return new ListDebtRequestSentCommandParser().parse(arguments);

        case ListDebtRequestReceivedCommand.COMMAND_WORD:
            return new ListDebtRequestReceivedCommandParser().parse(arguments);

        // Other Additional Makan Book Commands

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}

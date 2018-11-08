package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.AddTimeCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.DeleteGradeCommand;
import seedu.address.logic.commands.DeleteTimeCommand;
import seedu.address.logic.commands.EarningsCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditTimeCommand;
import seedu.address.logic.commands.ExchangeTimeCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FilterByEducationCommand;
import seedu.address.logic.commands.FilterByFeeCommand;
import seedu.address.logic.commands.FilterByGradeCommand;
import seedu.address.logic.commands.FilterByTimeCommand;
import seedu.address.logic.commands.FindAddressCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.FindEmailCommand;
import seedu.address.logic.commands.FindPhoneCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.PromoteCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.logic.commands.SuggestionCommand;
import seedu.address.logic.commands.UndoCommand;
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

        case EarningsCommand.COMMAND_WORD:
            return new EarningsCommandParser().parse(arguments);

        case EditCommand.COMMAND_WORD:
            return new EditCommandParser().parse(arguments);

        case SelectCommand.COMMAND_WORD:
            return new SelectCommandParser().parse(arguments);

        case DeleteCommand.COMMAND_WORD:
            return new DeleteCommandParser().parse(arguments);

        case DeleteTimeCommand.COMMAND_WORD:
            return new DeleteTimeCommandParser().parse(arguments);

        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();

        //@@author LZYAndy
        case FindAddressCommand.COMMAND_WORD:
            return new FindAddressCommandParser().parse(arguments);
        //@@author LZYAndy

        case FindCommand.COMMAND_WORD:
            return new FindCommandParser().parse(arguments);

        //@@author LZYAndy
        case FindEmailCommand.COMMAND_WORD:
            return new FindEmailCommandParser().parse(arguments);

        case FindPhoneCommand.COMMAND_WORD:
            return new FindPhoneCommandParser().parse(arguments);
        //@@author LZYAndy

        case ListCommand.COMMAND_WORD:
            return new ListCommand();

        case DeleteGradeCommand.COMMAND_WORD:
            return new DeleteGradesCommandParser().parse(arguments);

        case HistoryCommand.COMMAND_WORD:
            return new HistoryCommand();

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        case AddTimeCommand.COMMAND_WORD:
            return new AddTimeCommandParser().parse(arguments);

        case UndoCommand.COMMAND_WORD:
            return new UndoCommand();

        case RedoCommand.COMMAND_WORD:
            return new RedoCommand();

        case FilterByFeeCommand.COMMAND_WORD:
            return new FilterByFeeCommandParser().parse(arguments);

        case FilterByGradeCommand.COMMAND_WORD:
            return new FilterByGradeCommandParser().parse(arguments);

        case FilterByEducationCommand.COMMAND_WORD:
            return new FilterByEducationCommandParser().parse(arguments);

        case FilterByTimeCommand.COMMAND_WORD:
            return new FilterByTimeCommandParser().parse(arguments);

        case EditTimeCommand.COMMAND_WORD:
            return new EditCommandParser().parse(arguments);

        case PromoteCommand.COMMAND_WORD:
            return new PromoteCommandParser().parse(arguments);

        case ExchangeTimeCommand.COMMAND_WORD:
            return new ExchangeTimeCommandParser().parse(arguments);

        case SuggestionCommand.COMMAND_WORD:
            return new SuggestionCommandParser().parse(arguments);

        default:

            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}

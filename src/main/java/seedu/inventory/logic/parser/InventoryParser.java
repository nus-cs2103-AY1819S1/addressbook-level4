package seedu.inventory.logic.parser;

import static seedu.inventory.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.inventory.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.inventory.logic.commands.AddCommand;
import seedu.inventory.logic.commands.AddStaffCommand;
import seedu.inventory.logic.commands.ClearCommand;
import seedu.inventory.logic.commands.Command;
import seedu.inventory.logic.commands.CreateSaleCommand;
import seedu.inventory.logic.commands.DeleteCommand;
import seedu.inventory.logic.commands.DeleteStaffCommand;
import seedu.inventory.logic.commands.EditCommand;
import seedu.inventory.logic.commands.ExitCommand;
import seedu.inventory.logic.commands.FindCommand;
import seedu.inventory.logic.commands.HelpCommand;
import seedu.inventory.logic.commands.HistoryCommand;
import seedu.inventory.logic.commands.ListCommand;
import seedu.inventory.logic.commands.LoginCommand;
import seedu.inventory.logic.commands.RedoCommand;
import seedu.inventory.logic.commands.SelectCommand;
import seedu.inventory.logic.commands.UndoCommand;
import seedu.inventory.logic.commands.purchaseorder.GeneratePurchaseOrderCommand;
import seedu.inventory.logic.commands.purchaseorder.ListPurchaseOrderCommand;
import seedu.inventory.logic.parser.exceptions.ParseException;
import seedu.inventory.logic.parser.purchaseorder.GeneratePurchaseOrderCommandParser;

/**
 * Parses user input.
 */
public class InventoryParser {

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
        case LoginCommand.COMMAND_WORD:
            return new LoginCommandParser().parse(arguments);

        case AddStaffCommand.COMMAND_WORD:
            return new AddStaffCommandParser().parse(arguments);

        case DeleteStaffCommand.COMMAND_WORD:
            return new DeleteStaffCommandParser().parse(arguments);

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

        case ListPurchaseOrderCommand.COMMAND_WORD:
            return new ListPurchaseOrderCommand();

        case GeneratePurchaseOrderCommand.COMMAND_WORD:
            return new GeneratePurchaseOrderCommandParser().parse(arguments);

        case CreateSaleCommand.COMMAND_WORD:
            return new CreateSaleCommandParser().parse(arguments);

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}

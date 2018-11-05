package seedu.restaurant.logic.parser;

import static seedu.restaurant.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.restaurant.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.restaurant.logic.commands.AddCommand;
import seedu.restaurant.logic.commands.ClearCommand;
import seedu.restaurant.logic.commands.Command;
import seedu.restaurant.logic.commands.DeleteCommand;
import seedu.restaurant.logic.commands.EditCommand;
import seedu.restaurant.logic.commands.ExitCommand;
import seedu.restaurant.logic.commands.FindCommand;
import seedu.restaurant.logic.commands.HelpCommand;
import seedu.restaurant.logic.commands.HistoryCommand;
import seedu.restaurant.logic.commands.ListCommand;
import seedu.restaurant.logic.commands.RedoCommand;
import seedu.restaurant.logic.commands.RemarkCommand;
import seedu.restaurant.logic.commands.SelectCommand;
import seedu.restaurant.logic.commands.UndoCommand;
import seedu.restaurant.logic.commands.account.ChangePasswordCommand;
import seedu.restaurant.logic.commands.account.DeregisterCommand;
import seedu.restaurant.logic.commands.account.ListAccountsCommand;
import seedu.restaurant.logic.commands.account.LoginCommand;
import seedu.restaurant.logic.commands.account.LogoutCommand;
import seedu.restaurant.logic.commands.account.RegisterCommand;
import seedu.restaurant.logic.commands.ingredient.AddIngredientCommand;
import seedu.restaurant.logic.commands.ingredient.DeleteIngredientCommand;
import seedu.restaurant.logic.commands.ingredient.EditIngredientCommand;
import seedu.restaurant.logic.commands.ingredient.ListIngredientsCommand;
import seedu.restaurant.logic.commands.ingredient.LowStockCommand;
import seedu.restaurant.logic.commands.ingredient.SelectIngredientCommand;
import seedu.restaurant.logic.commands.ingredient.StockUpCommand;
import seedu.restaurant.logic.commands.menu.AddItemCommand;
import seedu.restaurant.logic.commands.menu.AddRequiredIngredientsCommand;
import seedu.restaurant.logic.commands.menu.ClearMenuCommand;
import seedu.restaurant.logic.commands.menu.DeleteItemByIndexCommand;
import seedu.restaurant.logic.commands.menu.DeleteItemByNameCommand;
import seedu.restaurant.logic.commands.menu.DiscountItemCommand;
import seedu.restaurant.logic.commands.menu.EditItemCommand;
import seedu.restaurant.logic.commands.menu.FilterMenuCommand;
import seedu.restaurant.logic.commands.menu.FindItemCommand;
import seedu.restaurant.logic.commands.menu.ListItemsCommand;
import seedu.restaurant.logic.commands.menu.RecipeItemCommand;
import seedu.restaurant.logic.commands.menu.SelectItemCommand;
import seedu.restaurant.logic.commands.menu.SortMenuCommand;
import seedu.restaurant.logic.commands.menu.TodaySpecialCommand;
import seedu.restaurant.logic.commands.reservation.AddReservationCommand;
import seedu.restaurant.logic.commands.reservation.DeleteReservationCommand;
import seedu.restaurant.logic.commands.reservation.EditReservationCommand;
import seedu.restaurant.logic.commands.reservation.ListReservationsCommand;
import seedu.restaurant.logic.commands.reservation.SelectReservationCommand;
import seedu.restaurant.logic.commands.reservation.SortReservationsCommand;
import seedu.restaurant.logic.commands.sales.DeleteSalesCommand;
import seedu.restaurant.logic.commands.sales.DisplaySalesCommand;
import seedu.restaurant.logic.commands.sales.EditSalesCommand;
import seedu.restaurant.logic.commands.sales.RecordSalesCommand;
import seedu.restaurant.logic.parser.account.ChangePasswordCommandParser;
import seedu.restaurant.logic.parser.account.DeregisterCommandParser;
import seedu.restaurant.logic.parser.account.LoginCommandParser;
import seedu.restaurant.logic.parser.account.RegisterCommandParser;
import seedu.restaurant.logic.parser.exceptions.ParseException;
import seedu.restaurant.logic.parser.ingredient.AddIngredientCommandParser;
import seedu.restaurant.logic.parser.ingredient.DeleteIngredientCommandParser;
import seedu.restaurant.logic.parser.ingredient.EditIngredientCommandParser;
import seedu.restaurant.logic.parser.ingredient.SelectIngredientCommandParser;
import seedu.restaurant.logic.parser.ingredient.StockUpCommandParser;
import seedu.restaurant.logic.parser.menu.AddItemCommandParser;
import seedu.restaurant.logic.parser.menu.AddRequiredIngredientsCommandParser;
import seedu.restaurant.logic.parser.menu.DeleteItemByIndexCommandParser;
import seedu.restaurant.logic.parser.menu.DeleteItemByNameCommandParser;
import seedu.restaurant.logic.parser.menu.DiscountItemCommandParser;
import seedu.restaurant.logic.parser.menu.EditItemCommandParser;
import seedu.restaurant.logic.parser.menu.FilterMenuCommandParser;
import seedu.restaurant.logic.parser.menu.FindItemCommandParser;
import seedu.restaurant.logic.parser.menu.RecipeItemCommandParser;
import seedu.restaurant.logic.parser.menu.SelectItemCommandParser;
import seedu.restaurant.logic.parser.menu.SortMenuCommandParser;
import seedu.restaurant.logic.parser.reservation.AddReservationCommandParser;
import seedu.restaurant.logic.parser.reservation.DeleteReservationCommandParser;
import seedu.restaurant.logic.parser.reservation.EditReservationCommandParser;
import seedu.restaurant.logic.parser.reservation.SelectReservationCommandParser;
import seedu.restaurant.logic.parser.sales.DeleteSalesCommandParser;
import seedu.restaurant.logic.parser.sales.DisplaySalesCommandParser;
import seedu.restaurant.logic.parser.sales.EditSalesCommandParser;
import seedu.restaurant.logic.parser.sales.RecordSalesCommandParser;

/**
 * Parses user input.
 */
public class RestaurantBookParser {

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
        case AddCommand.COMMAND_ALIAS:
            return new AddCommandParser().parse(arguments);

        case EditCommand.COMMAND_WORD:
        case EditCommand.COMMAND_ALIAS:
            return new EditCommandParser().parse(arguments);

        case SelectCommand.COMMAND_WORD:
        case SelectCommand.COMMAND_ALIAS:
            return new SelectCommandParser().parse(arguments);

        case DeleteCommand.COMMAND_WORD:
        case DeleteCommand.COMMAND_ALIAS:
            return new DeleteCommandParser().parse(arguments);

        case ClearCommand.COMMAND_WORD:
        case ClearCommand.COMMAND_ALIAS:
            return new ClearCommand();

        case FindCommand.COMMAND_WORD:
        case FindCommand.COMMAND_ALIAS:
            return new FindCommandParser().parse(arguments);

        case ListCommand.COMMAND_WORD:
        case ListCommand.COMMAND_ALIAS:
            return new ListCommand();

        case HistoryCommand.COMMAND_WORD:
        case HistoryCommand.COMMAND_ALIAS:
            return new HistoryCommand();

        case ExitCommand.COMMAND_WORD:
        case ExitCommand.COMMAND_ALIAS:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
        case HelpCommand.COMMAND_ALIAS:
            return new HelpCommand();

        case UndoCommand.COMMAND_WORD:
        case UndoCommand.COMMAND_ALIAS:
            return new UndoCommand();

        case RedoCommand.COMMAND_WORD:
        case RedoCommand.COMMAND_ALIAS:
            return new RedoCommand();

        case RemarkCommand.COMMAND_WORD:
            return new RemarkCommandParser().parse(arguments);

        case RecordSalesCommand.COMMAND_WORD:
        case RecordSalesCommand.COMMAND_ALIAS:
            return new RecordSalesCommandParser().parse(arguments);

        case DisplaySalesCommand.COMMAND_WORD:
        case DisplaySalesCommand.COMMAND_ALIAS:
            return new DisplaySalesCommandParser().parse(arguments);

        case DeleteSalesCommand.COMMAND_WORD:
        case DeleteSalesCommand.COMMAND_ALIAS:
            return new DeleteSalesCommandParser().parse(arguments);

        case EditSalesCommand.COMMAND_WORD:
        case EditSalesCommand.COMMAND_ALIAS:
            return new EditSalesCommandParser().parse(arguments);

        case RegisterCommand.COMMAND_WORD:
        case RegisterCommand.COMMAND_ALIAS:
            return new RegisterCommandParser().parse(arguments);

        case DeregisterCommand.COMMAND_WORD:
        case DeregisterCommand.COMMAND_ALIAS:
            return new DeregisterCommandParser().parse(arguments);

        case ChangePasswordCommand.COMMAND_WORD:
        case ChangePasswordCommand.COMMAND_ALIAS:
            return new ChangePasswordCommandParser().parse(arguments);

        case LoginCommand.COMMAND_WORD:
            return new LoginCommandParser().parse(arguments);

        case LogoutCommand.COMMAND_WORD:
            return new LogoutCommand();

        case ListAccountsCommand.COMMAND_WORD:
        case ListAccountsCommand.COMMAND_ALIAS:
            return new ListAccountsCommand();

        case AddIngredientCommand.COMMAND_WORD:
        case AddIngredientCommand.COMMAND_ALIAS:
            return new AddIngredientCommandParser().parse(arguments);

        case ListIngredientsCommand.COMMAND_WORD:
        case ListIngredientsCommand.COMMAND_ALIAS:
            return new ListIngredientsCommand();

        case LowStockCommand.COMMAND_WORD:
        case LowStockCommand.COMMAND_ALIAS:
            return new LowStockCommand();

        case DeleteIngredientCommand.COMMAND_WORD:
        case DeleteIngredientCommand.COMMAND_ALIAS:
            return new DeleteIngredientCommandParser().parse(arguments);

        case EditIngredientCommand.COMMAND_WORD:
        case EditIngredientCommand.COMMAND_ALIAS:
            return new EditIngredientCommandParser().parse(arguments);

        case StockUpCommand.COMMAND_WORD:
            return new StockUpCommandParser().parse(arguments);

        case SelectIngredientCommand.COMMAND_WORD:
        case SelectIngredientCommand.COMMAND_ALIAS:
            return new SelectIngredientCommandParser().parse(arguments);

        case AddItemCommand.COMMAND_WORD:
        case AddItemCommand.COMMAND_ALIAS:
            return new AddItemCommandParser().parse(arguments);

        case DeleteItemByIndexCommand.COMMAND_WORD:
        case DeleteItemByIndexCommand.COMMAND_ALIAS:
            return new DeleteItemByIndexCommandParser().parse(arguments);

        case DeleteItemByNameCommand.COMMAND_WORD:
        case DeleteItemByNameCommand.COMMAND_ALIAS:
            return new DeleteItemByNameCommandParser().parse(arguments);

        case EditItemCommand.COMMAND_WORD:
        case EditItemCommand.COMMAND_ALIAS:
            return new EditItemCommandParser().parse(arguments);

        case ListItemsCommand.COMMAND_WORD:
        case ListItemsCommand.COMMAND_ALIAS:
            return new ListItemsCommand();

        case SelectItemCommand.COMMAND_WORD:
        case SelectItemCommand.COMMAND_ALIAS:
            return new SelectItemCommandParser().parse(arguments);

        case FindItemCommand.COMMAND_WORD:
        case FindItemCommand.COMMAND_ALIAS:
            return new FindItemCommandParser().parse(arguments);

        case RecipeItemCommand.COMMAND_WORD:
        case RecipeItemCommand.COMMAND_ALIAS:
            return new RecipeItemCommandParser().parse(arguments);

        case SortMenuCommand.COMMAND_WORD:
        case SortMenuCommand.COMMAND_ALIAS:
            return new SortMenuCommandParser().parse(arguments);

        case FilterMenuCommand.COMMAND_WORD:
        case FilterMenuCommand.COMMAND_ALIAS:
            return new FilterMenuCommandParser().parse(arguments);

        case TodaySpecialCommand.COMMAND_WORD:
        case TodaySpecialCommand.COMMAND_ALIAS:
            return new TodaySpecialCommand();

        case DiscountItemCommand.COMMAND_WORD:
        case DiscountItemCommand.COMMAND_ALIAS:
            return new DiscountItemCommandParser().parse(arguments);

        case AddRequiredIngredientsCommand.COMMAND_WORD:
        case AddRequiredIngredientsCommand.COMMAND_ALIAS:
            return new AddRequiredIngredientsCommandParser().parse(arguments);

        case ClearMenuCommand.COMMAND_WORD:
        case ClearMenuCommand.COMMAND_ALIAS:
            return new ClearMenuCommand();

        case AddReservationCommand.COMMAND_WORD:
        case AddReservationCommand.COMMAND_ALIAS:
            return new AddReservationCommandParser().parse(arguments);

        case EditReservationCommand.COMMAND_WORD:
        case EditReservationCommand.COMMAND_ALIAS:
            return new EditReservationCommandParser().parse(arguments);

        case DeleteReservationCommand.COMMAND_WORD:
        case DeleteReservationCommand.COMMAND_ALIAS:
            return new DeleteReservationCommandParser().parse(arguments);

        case SelectReservationCommand.COMMAND_WORD:
        case SelectReservationCommand.COMMAND_ALIAS:
            return new SelectReservationCommandParser().parse(arguments);

        case ListReservationsCommand.COMMAND_WORD:
        case ListReservationsCommand.COMMAND_ALIAS:
            return new ListReservationsCommand();

        case SortReservationsCommand.COMMAND_WORD:
        case SortReservationsCommand.COMMAND_ALIAS:
            return new SortReservationsCommand();

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }
}

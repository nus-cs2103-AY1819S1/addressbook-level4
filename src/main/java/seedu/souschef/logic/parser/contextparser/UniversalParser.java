package seedu.souschef.logic.parser.contextparser;

import static seedu.souschef.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Pattern;

import seedu.souschef.logic.History;
import seedu.souschef.logic.commands.Command;
import seedu.souschef.logic.commands.ContextCommand;
import seedu.souschef.logic.commands.ExitCommand;
import seedu.souschef.logic.commands.HelpCommand;
import seedu.souschef.logic.commands.HistoryCommand;
import seedu.souschef.logic.parser.Context;
import seedu.souschef.logic.parser.exceptions.ParseException;
import seedu.souschef.ui.Ui;

/**
 * Parses user input.
 */
public class UniversalParser {
    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    /**
     * Parses user input into command for execution.
     *
     *
     * @param history
     * @param userInput full user input string
     * @param ui
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parseCommand(History history, String userInput, Ui ui) throws ParseException {
        final String commandWord = userInput.substring(1);
        switch (commandWord) {


        case RecipeParser.COMMAND_WORD:
            ui.switchToRecipeListPanel();
            return new ContextCommand(Context.RECIPE);

        case IngredientParser.COMMAND_WORD:
            ui.switchToIngredientListPanel();
            return new ContextCommand(Context.INGREDIENT);

        case CrossParser.COMMAND_WORD:
            ui.switchToCrossRecipeListPanel();
            return new ContextCommand(Context.CROSS);

        case HealthPlanParser.COMMAND_WORD:
            ui.switchToHealthPlanListPanel();
            return new ContextCommand(Context.HEALTH_PLAN);


        case MealPlannerParser.COMMAND_WORD:
            ui.switchToMealPlanListPanel();
            return new ContextCommand(Context.MEAL_PLANNER);


        case FavouritesParser.COMMAND_WORD:
            ui.switchToFavouritesListPanel();
            return new ContextCommand(Context.FAVOURITES);

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HistoryCommand.COMMAND_WORD:
            return new HistoryCommand();

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }
}

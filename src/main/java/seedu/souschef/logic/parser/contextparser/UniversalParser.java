package seedu.souschef.logic.parser.contextparser;

import static seedu.souschef.commons.core.Messages.MESSAGE_ALREADY_IN_CONTEXT;
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
        final Context historyContext = history.getContext();
        final Context nextContext;
        switch (commandWord) {


        case RecipeParser.COMMAND_WORD:
            nextContext = Context.RECIPE;
            checkContext(historyContext, nextContext);
            ui.switchToRecipeListPanel();
            return new ContextCommand(nextContext);

        case IngredientParser.COMMAND_WORD:
            nextContext = Context.INGREDIENT;
            checkContext(historyContext, nextContext);
            ui.switchToIngredientListPanel();
            return new ContextCommand(nextContext);

        case CrossParser.COMMAND_WORD:
            nextContext = Context.CROSS;
            checkContext(historyContext, nextContext);
            ui.switchToCrossRecipeListPanel();
            return new ContextCommand(nextContext);

        case HealthPlanParser.COMMAND_WORD:
            nextContext = Context.HEALTH_PLAN;
            checkContext(historyContext, nextContext);
            ui.switchToHealthPlanListPanel();
            return new ContextCommand(nextContext);


        case MealPlannerParser.COMMAND_WORD:
            nextContext = Context.MEAL_PLAN;
            checkContext(historyContext, nextContext);
            ui.switchToMealPlanListPanel();
            return new ContextCommand(nextContext);


        case FavouritesParser.COMMAND_WORD:
            nextContext = Context.FAVOURITES;
            checkContext(historyContext, nextContext);
            ui.switchToFavouritesListPanel();
            return new ContextCommand(nextContext);

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

    private void checkContext(Context present, Context next) throws ParseException {
        if (present == next) {
            throw new ParseException(String.format(MESSAGE_ALREADY_IN_CONTEXT, next));
        }
    }
}

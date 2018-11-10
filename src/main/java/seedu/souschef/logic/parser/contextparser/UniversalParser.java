package seedu.souschef.logic.parser.contextparser;

import static seedu.souschef.commons.core.Messages.MESSAGE_ALREADY_IN_CONTEXT;
import static seedu.souschef.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Pattern;

import seedu.souschef.commons.core.EventsCenter;
import seedu.souschef.commons.events.ui.ListPanelSwitchEvent;
import seedu.souschef.logic.History;
import seedu.souschef.logic.commands.Command;
import seedu.souschef.logic.commands.ContextCommand;
import seedu.souschef.logic.commands.ExitCommand;
import seedu.souschef.logic.commands.HelpCommand;
import seedu.souschef.logic.commands.HistoryCommand;
import seedu.souschef.logic.parser.Context;
import seedu.souschef.logic.parser.exceptions.ParseException;

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
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parseCommand(History history, String userInput) throws ParseException {
        final String commandWord = userInput.substring(1);
        final Context historyContext = history.getContext();
        final Context nextContext;
        switch (commandWord) {


        case RecipeParser.COMMAND_WORD:
            nextContext = Context.RECIPE;
            checkContext(historyContext, nextContext);
            EventsCenter.getInstance().post(new ListPanelSwitchEvent(Context.RECIPE));
            return new ContextCommand(nextContext);

        case IngredientParser.COMMAND_WORD:
            nextContext = Context.INGREDIENT;
            checkContext(historyContext, nextContext);
            EventsCenter.getInstance().post(new ListPanelSwitchEvent(Context.INGREDIENT));
            return new ContextCommand(nextContext);

        case CrossParser.COMMAND_WORD:
            nextContext = Context.CROSS;
            checkContext(historyContext, nextContext);
            EventsCenter.getInstance().post(new ListPanelSwitchEvent(Context.CROSS));
            return new ContextCommand(nextContext);

        case HealthPlanParser.COMMAND_WORD:
            nextContext = Context.HEALTH_PLAN;
            checkContext(historyContext, nextContext);
            EventsCenter.getInstance().post(new ListPanelSwitchEvent(Context.HEALTH_PLAN));
            return new ContextCommand(nextContext);


        case MealPlannerParser.COMMAND_WORD:
            nextContext = Context.MEAL_PLAN;
            checkContext(historyContext, nextContext);
            EventsCenter.getInstance().post(new ListPanelSwitchEvent(Context.MEAL_PLAN));
            return new ContextCommand(nextContext);


        case FavouritesParser.COMMAND_WORD:
            nextContext = Context.FAVOURITES;
            checkContext(historyContext, nextContext);
            EventsCenter.getInstance().post(new ListPanelSwitchEvent(Context.FAVOURITES));
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

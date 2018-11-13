package seedu.souschef.logic.parser.contextparser;

import static seedu.souschef.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.souschef.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.souschef.logic.History;
import seedu.souschef.logic.commands.ClearMealPlannerCommand;
import seedu.souschef.logic.commands.Command;
import seedu.souschef.logic.commands.DeleteCommand;
import seedu.souschef.logic.commands.FindCommand;
import seedu.souschef.logic.commands.HelpCommand;
import seedu.souschef.logic.commands.ListCommand;
import seedu.souschef.logic.commands.PlanMealCommand;
import seedu.souschef.logic.commands.SelectCommand;
import seedu.souschef.logic.parser.Context;
import seedu.souschef.logic.parser.commandparser.DeleteCommandParser;
import seedu.souschef.logic.parser.commandparser.FindCommandParser;
import seedu.souschef.logic.parser.commandparser.PlanMealCommandParser;
import seedu.souschef.logic.parser.commandparser.SelectCommandParser;
import seedu.souschef.logic.parser.exceptions.ParseException;
import seedu.souschef.model.Model;


/**
 * Parses user input.
 */
public class MealPlannerParser {
    public static final String COMMAND_WORD = "mealplanner";
    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    /**
     * Parses userInput into command for execution.
     *
     * @param mealPlannerModel
     * @param userInput
     * @return
     * @throws ParseException
     */
    public Command parseCommand(Model mealPlannerModel, Model recipeModel,
                                String userInput, History history) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments").trim();

        switch(commandWord) {
        case ClearMealPlannerCommand.COMMAND_WORD:
            return new ClearMealPlannerCommand(mealPlannerModel);
        case DeleteCommand.COMMAND_WORD:
            return new DeleteCommandParser().parseMealPlan(mealPlannerModel, arguments);
        case PlanMealCommand.COMMAND_WORD:
            if (history.getContext().equals(Context.MEAL_PLAN)) {
                throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
            } else {
                return new PlanMealCommandParser().parseMealPlan(mealPlannerModel, recipeModel, arguments);
            }
        case SelectCommand.COMMAND_WORD:
            return new SelectCommandParser().parseMealPlan(mealPlannerModel, recipeModel, arguments);
        case FindCommand.COMMAND_WORD:
            return new FindCommandParser().parseMealPlan(mealPlannerModel, arguments);
        case ListCommand.COMMAND_WORD:
            return new ListCommand(mealPlannerModel);
        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

    /**
     * To check if the user input is a cross context command.
     */
    public static boolean isCrossContextCommand(String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }
        final String commandWord = matcher.group("commandWord");
        return commandWord.equals(PlanMealCommand.COMMAND_WORD);
    }
}

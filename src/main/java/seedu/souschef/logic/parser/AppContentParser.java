package seedu.souschef.logic.parser;

import static seedu.souschef.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Pattern;

import seedu.souschef.logic.CommandHistory;
import seedu.souschef.logic.commands.Command;
import seedu.souschef.logic.commands.PlanMealCommand;
import seedu.souschef.logic.parser.contextparser.HealthPlanParser;
import seedu.souschef.logic.parser.contextparser.MealPlannerParser;
import seedu.souschef.logic.parser.contextparser.RecipeParser;
import seedu.souschef.logic.parser.contextparser.UniversalParser;
import seedu.souschef.logic.parser.exceptions.ParseException;
import seedu.souschef.model.ModelSet;
import seedu.souschef.storage.Storage;
import seedu.souschef.storage.StorageManager;


/**
 * Parses user input.
 */
public class AppContentParser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    /**
     * Parses user input into command for execution.
     *
     * @param modelSet
     * @param userInput full user input string
     * @param history
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parseCommand(ModelSet modelSet, String userInput, CommandHistory history,
                                Storage storage) throws ParseException {
        String context = history.getContext();

        if (storage == null) {
            storage = new StorageManager();
        }

        if (userInput.charAt(0) == '-') {
            return new UniversalParser().parseCommand(history, userInput);
            //TODO: Refine condition to redirect for other meal planner commands (clearplanner, displayplanner, etc...)
        } else if (context.equals("Meal Planner")) {
            return new MealPlannerParser()
                .parseCommand(modelSet.getMealPlannerModel(), modelSet.getRecipeModel(), userInput);
        } else if (context.equals("Recipe")) {
            if (storage.getListOfFeatureStorage().size() > 0) {
                storage.setMainFeatureStorage(storage.getListOfFeatureStorage().get(0));
            }
            return new RecipeParser().parseCommand(modelSet.getRecipeModel(), userInput);
        } else if (context.equals("Health Plan")) {
            if (storage.getListOfFeatureStorage().size() > 0) {
                storage.setMainFeatureStorage(storage.getListOfFeatureStorage().get(2));
            }
            return new HealthPlanParser().parseCommand(modelSet.getHealthPlanModel(), userInput);
        } else {
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}

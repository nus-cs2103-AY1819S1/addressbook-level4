package seedu.souschef.logic.parser;

import static seedu.souschef.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.Optional;

import seedu.souschef.commons.core.EventsCenter;
import seedu.souschef.commons.events.storage.SwitchFeatureStorageEvent;
import seedu.souschef.logic.History;
import seedu.souschef.logic.commands.Command;
import seedu.souschef.logic.parser.contextparser.CrossParser;
import seedu.souschef.logic.parser.contextparser.FavouritesParser;
import seedu.souschef.logic.parser.contextparser.HealthPlanParser;
import seedu.souschef.logic.parser.contextparser.IngredientParser;
import seedu.souschef.logic.parser.contextparser.MealPlannerParser;
import seedu.souschef.logic.parser.contextparser.RecipeParser;
import seedu.souschef.logic.parser.contextparser.UniversalParser;
import seedu.souschef.logic.parser.exceptions.ParseException;
import seedu.souschef.model.ModelSet;

/**
 * Parses user input.
 */
public class AppContentParser {
    /**
     * Parses user input into command for execution.
     * Based on the context and command, the parser determines the specific model and storage to be modified or
     * accessed.
     *
     * @param modelSet
     * @param userInput full user input string
     * @param history
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parseCommand(ModelSet modelSet, String userInput, History history) throws ParseException {
        Context context = history.getContext();

        if (userInput.charAt(0) == '-') {

            return new UniversalParser().parseCommand(history, userInput);
        }

        switch (context) {
        case RECIPE:
            setFeatureStorage(context);
            Optional<Command> optionalCommand = getCrossContextCommand(userInput, modelSet, history);
            return optionalCommand.isPresent() ? optionalCommand.get()
                    : new RecipeParser().parseCommand(modelSet.getRecipeModel(), userInput, history);
        case INGREDIENT:
            setFeatureStorage(context);
            return new IngredientParser().parseCommand(modelSet.getIngredientModel(), userInput);
        case CROSS:
            return new CrossParser().parseCommand(modelSet.getCrossRecipeModel(), modelSet.getIngredientModel(),
                    userInput);
        case HEALTH_PLAN:
            setFeatureStorage(context);
            return new HealthPlanParser().parseCommand(modelSet.getHealthPlanModel(), modelSet.getMealPlannerModel(),
                    userInput);
        case MEAL_PLAN:
            setFeatureStorage(context);
            return new MealPlannerParser()
                    .parseCommand(modelSet.getMealPlannerModel(), modelSet.getRecipeModel(), userInput, history);
        case FAVOURITES:
            setFeatureStorage(context);
            return new FavouritesParser().parseCommand(modelSet.getFavouriteModel(), userInput);
        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

    private void setFeatureStorage(Context context) {
        EventsCenter.getInstance().post(new SwitchFeatureStorageEvent(context));
    }

    /**
     * Based on user input, get cross context command.
     */
    private Optional<Command> getCrossContextCommand(String userInput,
                                                     ModelSet modelSet, History history)
        throws ParseException {
        Command command = null;
        if (FavouritesParser.isCrossContextCommand(userInput)) {
            // Consider to use Favorite command instead and remove history from param
            setFeatureStorage(Context.FAVOURITES);
            command = new RecipeParser().parseCommand(modelSet.getFavouriteModel(), userInput, history);
        } else if (MealPlannerParser.isCrossContextCommand(userInput)) {
            setFeatureStorage(Context.MEAL_PLAN);
            command = new MealPlannerParser().parseCommand(modelSet.getMealPlannerModel(),
                    modelSet.getRecipeModel(), userInput, history);
        }
        // Add other cross context command and set ur storage here.

        return Optional.ofNullable(command);
    }

}

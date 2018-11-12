package seedu.souschef.logic.parser;

import seedu.souschef.logic.parser.contextparser.CrossParser;
import seedu.souschef.logic.parser.contextparser.FavouritesParser;
import seedu.souschef.logic.parser.contextparser.HealthPlanParser;
import seedu.souschef.logic.parser.contextparser.IngredientParser;
import seedu.souschef.logic.parser.contextparser.MealPlannerParser;
import seedu.souschef.logic.parser.contextparser.RecipeParser;

/**
 * Pre-defined set of context for sous chef.
 **/
public enum Context {
    RECIPE(RecipeParser.COMMAND_WORD),
    INGREDIENT(IngredientParser.COMMAND_WORD),
    CROSS(CrossParser.COMMAND_WORD),
    HEALTH_PLAN(HealthPlanParser.COMMAND_WORD),
    MEAL_PLAN(MealPlannerParser.COMMAND_WORD),
    FAVOURITES(FavouritesParser.COMMAND_WORD);

    public final String command;

    Context(String command) {
        this.command = command;
    }
}

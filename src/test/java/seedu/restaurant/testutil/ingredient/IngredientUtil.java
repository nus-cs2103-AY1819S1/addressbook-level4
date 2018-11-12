package seedu.restaurant.testutil.ingredient;

import static seedu.restaurant.logic.parser.util.CliSyntax.PREFIX_INGREDIENT_MINIMUM;
import static seedu.restaurant.logic.parser.util.CliSyntax.PREFIX_INGREDIENT_NAME;
import static seedu.restaurant.logic.parser.util.CliSyntax.PREFIX_INGREDIENT_PRICE;
import static seedu.restaurant.logic.parser.util.CliSyntax.PREFIX_INGREDIENT_UNIT;

import seedu.restaurant.logic.commands.ingredient.AddIngredientCommand;
import seedu.restaurant.logic.commands.ingredient.EditIngredientCommand.EditIngredientDescriptor;
import seedu.restaurant.model.ingredient.Ingredient;

//@@author rebstan97
/**
 * A utility class for Ingredient.
 */
public class IngredientUtil {

    /**
     * Returns an add command string for adding the {@code ingredient}.
     */
    public static String getAddIngredientCommand(Ingredient ingredient) {
        return AddIngredientCommand.COMMAND_WORD + " " + getIngredientDetails(ingredient);
    }

    /**
     * Returns the part of command string for the given {@code ingredient}'s details.
     */
    public static String getIngredientDetails(Ingredient ingredient) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_INGREDIENT_NAME + ingredient.getName().toString() + " ");
        sb.append(PREFIX_INGREDIENT_UNIT + ingredient.getUnit().toString() + " ");
        sb.append(PREFIX_INGREDIENT_PRICE + ingredient.getPrice().toString() + " ");
        sb.append(PREFIX_INGREDIENT_MINIMUM + ingredient.getMinimum().toString() + " ");

        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditIngredientDescriptor}'s details.
     */
    public static String getEditIngredientDescriptorDetails(EditIngredientDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getName().ifPresent(name -> sb.append(PREFIX_INGREDIENT_NAME).append(name.toString()).append(" "));
        descriptor.getUnit().ifPresent(unit -> sb.append(PREFIX_INGREDIENT_UNIT).append(unit.toString()).append(" "));
        descriptor.getPrice().ifPresent(price -> sb.append(PREFIX_INGREDIENT_PRICE)
                .append(price.toString()).append(" "));
        descriptor.getMinimum().ifPresent(minimum -> sb.append(PREFIX_INGREDIENT_MINIMUM)
                .append(minimum.toString()).append(" "));
        return sb.toString();
    }
}

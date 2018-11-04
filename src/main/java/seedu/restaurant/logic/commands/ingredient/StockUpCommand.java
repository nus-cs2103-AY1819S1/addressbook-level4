package seedu.restaurant.logic.commands.ingredient;

import static java.util.Objects.requireNonNull;
import static seedu.restaurant.commons.core.Messages.MESSAGE_INGREDIENT_NAME_NOT_FOUND;
import static seedu.restaurant.logic.commands.ingredient.EditIngredientCommand.createEditedIngredient;
import static seedu.restaurant.logic.parser.util.CliSyntax.PREFIX_INGREDIENT_NAME;
import static seedu.restaurant.logic.parser.util.CliSyntax.PREFIX_INGREDIENT_NUM;
import static seedu.restaurant.model.Model.PREDICATE_SHOW_ALL_INGREDIENTS;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import seedu.restaurant.commons.core.EventsCenter;
import seedu.restaurant.commons.core.Messages;
import seedu.restaurant.commons.events.ui.ingredient.DisplayIngredientListRequestEvent;
import seedu.restaurant.logic.CommandHistory;
import seedu.restaurant.logic.commands.Command;
import seedu.restaurant.logic.commands.CommandResult;
import seedu.restaurant.logic.commands.exceptions.CommandException;
import seedu.restaurant.logic.commands.ingredient.EditIngredientCommand.EditIngredientDescriptor;
import seedu.restaurant.model.Model;
import seedu.restaurant.model.ingredient.Ingredient;
import seedu.restaurant.model.ingredient.IngredientName;
import seedu.restaurant.model.ingredient.NumUnits;
import seedu.restaurant.model.ingredient.exceptions.IngredientNotFoundException;

/**
 * Stocks up an ingredient in the restaurant book.
 */
public class StockUpCommand extends Command {

    public static final String COMMAND_WORD = "stockup";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Stocks up an ingredient in the restaurant book "
            + "Parameters: "
            + PREFIX_INGREDIENT_NAME + "NAME... "
            + PREFIX_INGREDIENT_NUM + "NUM_OF_UNITS... \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_INGREDIENT_NAME + "Cod Fish "
            + PREFIX_INGREDIENT_NUM + "20 ";

    public static final String MESSAGE_STOCKUP_INGREDIENT_SUCCESS = "Ingredient(s) stocked up: %1$s";

    private final Map<IngredientName, Integer> hashMap;

    /**
     * Creates a StockUpCommand to stock up the specified {@code Ingredient}
     */
    public StockUpCommand(Map<IngredientName, Integer> ingredientHashMap) {
        requireNonNull(ingredientHashMap);
        this.hashMap = ingredientHashMap;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        try {
            model.stockUpIngredients(hashMap);
        } catch (IngredientNotFoundException e) {
            throw new CommandException(Messages.MESSAGE_STOCKUP_INGREDIENT_NOT_FOUND);
        }

        StringBuilder ingredientList = buildIngredientList();

        model.commitRestaurantBook();
        EventsCenter.getInstance().post(new DisplayIngredientListRequestEvent());
        return new CommandResult(String.format(MESSAGE_STOCKUP_INGREDIENT_SUCCESS, ingredientList));
    }

    private StringBuilder buildIngredientList() {
        StringBuilder stringBuilder = new StringBuilder();
        for (HashMap.Entry<IngredientName, Integer> argPair : hashMap.entrySet()) {
            IngredientName name = argPair.getKey();
            Integer numUnits = argPair.getValue();
            stringBuilder.append("\n" + numUnits.toString() + " units of " + name.toString());
        }
        return stringBuilder;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof StockUpCommand // instanceof handles nulls
                    && hashMap.equals(((StockUpCommand) other).hashMap));
    }

}

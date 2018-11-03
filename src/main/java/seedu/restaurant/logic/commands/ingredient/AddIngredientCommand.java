package seedu.restaurant.logic.commands.ingredient;

import static java.util.Objects.requireNonNull;
import static seedu.restaurant.logic.parser.util.CliSyntax.PREFIX_INGREDIENT_MINIMUM;
import static seedu.restaurant.logic.parser.util.CliSyntax.PREFIX_INGREDIENT_NAME;
import static seedu.restaurant.logic.parser.util.CliSyntax.PREFIX_INGREDIENT_PRICE;
import static seedu.restaurant.logic.parser.util.CliSyntax.PREFIX_INGREDIENT_UNIT;

import seedu.restaurant.commons.core.EventsCenter;
import seedu.restaurant.commons.events.ui.ingredient.DisplayIngredientListRequestEvent;
import seedu.restaurant.logic.CommandHistory;
import seedu.restaurant.logic.commands.Command;
import seedu.restaurant.logic.commands.CommandResult;
import seedu.restaurant.logic.commands.exceptions.CommandException;
import seedu.restaurant.model.Model;
import seedu.restaurant.model.ingredient.Ingredient;

/**
 * Adds an ingredient to the restaurant book.
 */
public class AddIngredientCommand extends Command {

    public static final String COMMAND_WORD = "add-ingredient";

    public static final String COMMAND_ALIAS = "add-ing";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an ingredient to the restaurant book. "
            + "Parameters: "
            + PREFIX_INGREDIENT_NAME + "NAME "
            + PREFIX_INGREDIENT_UNIT + "UNIT "
            + PREFIX_INGREDIENT_PRICE + "PRICE "
            + PREFIX_INGREDIENT_MINIMUM + "MINIMUM\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_INGREDIENT_NAME + "Cod Fish "
            + PREFIX_INGREDIENT_UNIT + "kilogram "
            + PREFIX_INGREDIENT_PRICE + "20.00 "
            + PREFIX_INGREDIENT_MINIMUM + "5 ";

    public static final String MESSAGE_SUCCESS = "New ingredient added: %1$s";
    public static final String MESSAGE_DUPLICATE_INGREDIENT = "This ingredient already exists in the restaurant book";

    private final Ingredient toAdd;

    /**
     * Creates an AddIngredientCommand to add the specified {@code Ingredient}
     */
    public AddIngredientCommand(Ingredient ingredient) {
        requireNonNull(ingredient);
        toAdd = ingredient;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (model.hasIngredient(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_INGREDIENT);
        }

        model.addIngredient(toAdd);
        model.commitRestaurantBook();
        EventsCenter.getInstance().post(new DisplayIngredientListRequestEvent());
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddIngredientCommand // instanceof handles nulls
                && toAdd.equals(((AddIngredientCommand) other).toAdd));
    }
}

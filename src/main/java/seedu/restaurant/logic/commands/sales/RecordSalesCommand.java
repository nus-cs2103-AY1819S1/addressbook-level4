package seedu.restaurant.logic.commands.sales;

import static java.util.Objects.requireNonNull;
import static seedu.restaurant.logic.parser.util.CliSyntax.PREFIX_DATE;
import static seedu.restaurant.logic.parser.util.CliSyntax.PREFIX_ITEM_NAME;
import static seedu.restaurant.logic.parser.util.CliSyntax.PREFIX_ITEM_PRICE;
import static seedu.restaurant.logic.parser.util.CliSyntax.PREFIX_QUANTITY_SOLD;

import java.util.HashMap;
import java.util.Map;

import seedu.restaurant.commons.core.EventsCenter;
import seedu.restaurant.commons.events.ui.sales.DisplayRecordListRequestEvent;
import seedu.restaurant.logic.CommandHistory;
import seedu.restaurant.logic.commands.Command;
import seedu.restaurant.logic.commands.CommandResult;
import seedu.restaurant.logic.commands.exceptions.CommandException;
import seedu.restaurant.model.Model;
import seedu.restaurant.model.ingredient.IngredientName;
import seedu.restaurant.model.ingredient.exceptions.IngredientNotEnoughException;
import seedu.restaurant.model.ingredient.exceptions.IngredientNotFoundException;
import seedu.restaurant.model.menu.Item;
import seedu.restaurant.model.menu.Name;
import seedu.restaurant.model.menu.exceptions.ItemNotFoundException;
import seedu.restaurant.model.sales.SalesRecord;
import seedu.restaurant.model.sales.exceptions.RequiredIngredientsNotFoundException;

//@@author HyperionNKJ
/**
 * Record the sales volume of a menu item
 */
public class RecordSalesCommand extends Command {
    public static final String COMMAND_WORD = "record-sales";

    public static final String COMMAND_ALIAS = "rs";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Records the sales volume of a menu item within a day"
            + " into the sales book.\n"
            + "Parameters: "
            + PREFIX_DATE + "DATE (must be in DD-MM-YYYY format) "
            + PREFIX_ITEM_NAME + "ITEM_NAME "
            + PREFIX_QUANTITY_SOLD + "QUANTITY SOLD "
            + PREFIX_ITEM_PRICE + "ITEM_PRICE\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_DATE + " 25-09-2018 "
            + PREFIX_ITEM_NAME + "Fried Rice "
            + PREFIX_QUANTITY_SOLD + "35 "
            + PREFIX_ITEM_PRICE + "5.50";

    public static final String MESSAGE_RECORD_SALES_SUCCESS = "Sales volume recorded.\n%1$s";
    public static final String MESSAGE_DUPLICATE_SALES_RECORD = "Sales record of \"%1$s\" already exists on the same "
            + "date.";
    public static final String MESSAGE_ITEM_NOT_FOUND = "However, the item does not exist in the menu section. "
            + "Please add the item into the menu and specify the ingredients it requires to enable auto-ingredient "
            + "update.";
    public static final String MESSAGE_REQUIRED_INGREDIENTS_NOT_FOUND = "However, the ingredients required to make "
            + "this item have not been specified. Please specify the ingredients required to enable auto-ingredient."
            + "update.";
    public static final String MESSAGE_INGREDIENT_NOT_FOUND = "However, some ingredient(s) required to make this item"
            + " were not found in the ingredient list. Please add the ingredient(s) to enable auto-ingredient update.";
    public static final String MESSAGE_INGREDIENT_NOT_ENOUGH = "However, some ingredient(s) in the ingredient section"
            + " fall short of that required to make the amount of item specified in the quantity sold field. Please "
            + "stockup your ingredients to enable the auto-ingredient update.";
    public static final String MESSAGE_INGREDIENT_UPDATE_SUCCESS = "Ingredient list has been updated.";

    private SalesRecord toAdd;

    /**
     * @param salesRecord to be appended to the sales book
     */
    public RecordSalesCommand(SalesRecord salesRecord) {
        requireNonNull(salesRecord);
        this.toAdd = salesRecord;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (model.hasRecord(toAdd)) {
            throw new CommandException(String.format(MESSAGE_DUPLICATE_SALES_RECORD, toAdd.getName()));
        }

        String ingredientsUpdateStatus = MESSAGE_INGREDIENT_UPDATE_SUCCESS;

        try {
            updateIngredientList(model);
        } catch (ItemNotFoundException e) {
            ingredientsUpdateStatus = MESSAGE_ITEM_NOT_FOUND;
        } catch (RequiredIngredientsNotFoundException e) {
            ingredientsUpdateStatus = MESSAGE_REQUIRED_INGREDIENTS_NOT_FOUND;
        } catch (IngredientNotFoundException e) {
            ingredientsUpdateStatus = MESSAGE_INGREDIENT_NOT_FOUND;
        } catch (IngredientNotEnoughException e) {
            ingredientsUpdateStatus = MESSAGE_INGREDIENT_NOT_ENOUGH;
        }

        model.addRecord(toAdd); // record will be added even if one of the four exceptions above was caught
        model.commitRestaurantBook();
        EventsCenter.getInstance().post(new DisplayRecordListRequestEvent());
        return new CommandResult(String.format(MESSAGE_RECORD_SALES_SUCCESS, toAdd) + "\n"
                + ingredientsUpdateStatus);
    }

    /**
     * Updates the ingredient list based on info specified in the sales record
     */
    private void updateIngredientList(Model model) throws ItemNotFoundException, RequiredIngredientsNotFoundException,
            IngredientNotFoundException, IngredientNotEnoughException {

        Map<IngredientName, Integer> requiredIngredients = getRequiredIngredients(model);

        if (requiredIngredients.isEmpty()) {
            throw new RequiredIngredientsNotFoundException();
        }

        Map<IngredientName, Integer> ingredientsUsed = computeIngredientsUsed(requiredIngredients);
        toAdd = toAdd.setIngredientsUsed(ingredientsUsed); // saves the ingredientsUsed in the SalesRecord
        model.consumeIngredients(ingredientsUsed); // update ingredient list
    }

    /**
     * Retrieves the name of ingredients and their corresponding quantity required to make one unit of "item"
     * @return A Map representation of the required ingredients per unit of "item"
     */
    private Map<IngredientName, Integer> getRequiredIngredients(Model model) throws ItemNotFoundException {
        Name itemName = new Name(toAdd.getName().toString());
        Item item = model.findItem(itemName);
        return model.getRequiredIngredients(item);
    }

    /**
     * Computes the total quantity of each ingredient used based on the quantity sold specified in the record
     * @return A Map representation of all ingredients used
     */
    private Map<IngredientName, Integer> computeIngredientsUsed(Map<IngredientName, Integer> requiredIngredients) {
        Map<IngredientName, Integer> ingredientsUsed = new HashMap<>(requiredIngredients);
        int quantitySold = toAdd.getQuantitySold().getValue();
        ingredientsUsed.replaceAll((ingredient, quantityUsed) -> quantityUsed * quantitySold);
        return ingredientsUsed;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RecordSalesCommand // instanceof handles nulls
                    && toAdd.equals(((RecordSalesCommand) other).toAdd));
    }
}

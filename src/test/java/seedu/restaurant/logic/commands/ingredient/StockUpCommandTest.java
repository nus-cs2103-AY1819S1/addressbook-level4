package seedu.restaurant.logic.commands.ingredient;

import static seedu.restaurant.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.restaurant.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.restaurant.logic.commands.ingredient.StockUpCommand.MESSAGE_STOCKUP_INGREDIENT_SUCCESS;
import static seedu.restaurant.testutil.TypicalRestaurantBook.getTypicalRestaurantBook;
import static seedu.restaurant.testutil.ingredient.TypicalIngredients.AVOCADO;
import static seedu.restaurant.testutil.ingredient.TypicalIngredients.BEANS;
import static seedu.restaurant.testutil.ingredient.TypicalIngredients.CABBAGE;
import static seedu.restaurant.testutil.ingredient.TypicalIngredients.DUCK;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import seedu.restaurant.commons.core.Messages;
import seedu.restaurant.logic.CommandHistory;
import seedu.restaurant.model.Model;
import seedu.restaurant.model.ModelManager;
import seedu.restaurant.model.RestaurantBook;
import seedu.restaurant.model.UserPrefs;
import seedu.restaurant.model.ingredient.Ingredient;
import seedu.restaurant.model.ingredient.IngredientName;
import seedu.restaurant.testutil.ingredient.IngredientBuilder;

public class StockUpCommandTest {
    private Model model = new ModelManager(getTypicalRestaurantBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_validIngredient_success() {
        Map<IngredientName, Integer> ingredientHashMap = new HashMap<>();
        ingredientHashMap.put(AVOCADO.getName(), 20);

        StockUpCommand stockUpCommand = new StockUpCommand(ingredientHashMap);

        Ingredient updatedIngredient = new IngredientBuilder(AVOCADO).withNumUnits(20).build();
        String expectedMessage = String.format(MESSAGE_STOCKUP_INGREDIENT_SUCCESS, "\n"
                + "20 units of " + updatedIngredient.getName());

        Model expectedModel = new ModelManager(new RestaurantBook(model.getRestaurantBook()), new UserPrefs());
        expectedModel.updateIngredient(model.findIngredient(AVOCADO.getName()), updatedIngredient);
        expectedModel.commitRestaurantBook();

        assertCommandSuccess(stockUpCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_multipleValidIngredients_success() {
        Map<IngredientName, Integer> ingredientHashMap = new HashMap<>();
        ingredientHashMap.put(CABBAGE.getName(), 10);
        ingredientHashMap.put(BEANS.getName(), 87);
        ingredientHashMap.put(DUCK.getName(), 59);

        StockUpCommand stockUpCommand = new StockUpCommand(ingredientHashMap);

        Model expectedModel = new ModelManager(new RestaurantBook(model.getRestaurantBook()), new UserPrefs());
        StringBuilder updatedIngredients = new StringBuilder();

        Ingredient updatedIngredient = new IngredientBuilder(BEANS).withNumUnits(87).build();
        updatedIngredients.append("\n" + "87 units of " + updatedIngredient.getName());
        expectedModel.updateIngredient(BEANS, updatedIngredient);

        updatedIngredient = new IngredientBuilder(DUCK).withNumUnits(59).build();
        updatedIngredients.append("\n" + "59 units of " + updatedIngredient.getName());
        expectedModel.updateIngredient(DUCK, updatedIngredient);

        updatedIngredient = new IngredientBuilder(CABBAGE).withNumUnits(10).build();
        updatedIngredients.append("\n" + "10 units of " + updatedIngredient.getName());
        expectedModel.updateIngredient(CABBAGE, updatedIngredient);

        String expectedMessage = String.format(MESSAGE_STOCKUP_INGREDIENT_SUCCESS, updatedIngredients);
        expectedModel.commitRestaurantBook();

        assertCommandSuccess(stockUpCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIngredient_failure() {
        Map<IngredientName, Integer> ingredientHashMap = new HashMap<>();
        ingredientHashMap.put(new IngredientName("Iceberg Lettuce"), 10);

        StockUpCommand stockUpCommand = new StockUpCommand(ingredientHashMap);
        String expectedMessage = String.format(Messages.MESSAGE_STOCKUP_INGREDIENT_NOT_FOUND);

        assertCommandFailure(stockUpCommand, model, commandHistory, expectedMessage);
    }


}

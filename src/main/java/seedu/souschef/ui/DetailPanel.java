package seedu.souschef.ui;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.souschef.commons.core.LogsCenter;
import seedu.souschef.commons.events.ui.CrossRecipePanelSelectionChangedEvent;
import seedu.souschef.commons.events.ui.RecipePanelSelectionChangedEvent;
import seedu.souschef.model.ingredient.IngredientDefinition;
import seedu.souschef.model.ingredient.IngredientPortion;
import seedu.souschef.model.recipe.CrossRecipe;
import seedu.souschef.model.recipe.Instruction;
import seedu.souschef.model.recipe.Recipe;

/**
 * An UI component that displays information of a {@code Recipe}.
 */
public class DetailPanel extends UiPart<Region> {

    private static final String FXML = "DetailPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(getClass());
    private Recipe recipe;
    private CrossRecipe crossRecipe;

    @FXML
    private Label name;
    @FXML
    private Label ingredientLabel;
    @FXML
    private Label ingredients;
    @FXML
    private Label ingredientToShopLabel;
    @FXML
    private Label ingredientsToShop;
    @FXML
    private Label instructionLabel;
    @FXML
    private Label instructions;

    public DetailPanel() {
        super(FXML);
        getRoot().setOnKeyPressed(Event::consume);

        loadDefaultRecipePage();
        registerAsAnEventHandler(this);
    }

    /**
     * Loads a placeholders into details panel.
     */
    public void loadDefaultRecipePage() {
        name.setText("Select a recipe for viewing...");
        loadEmptyFields();
    }

    /**
     * Loads a placeholders into details panel.
     */
    public void loadDefaultICrossPage() {
        name.setText("Select recipe to view needed ingredients and its amount...");
        loadEmptyFields();
    }

    /**
     * Loads a placeholders into details panel.
     */
    public void loadBlankPage() {
        name.setText("");
        loadEmptyFields();
    }

    /**
     * Fill fields with empty string.
     */
    private void loadEmptyFields() {
        ingredientLabel.setText("");
        ingredients.setText("");
        ingredientToShopLabel.setText("");
        ingredientsToShop.setText("");
        instructionLabel.setText("");
        instructions.setText("");
    }

    /**
     * Loads a recipe into details panel.
     */
    private void loadRecipeDetail(Recipe recipe) {
        this.recipe = recipe;
        name.setText(recipe.getName().fullName);
        ingredientLabel.setText("Ingredients: ");
        instructionLabel.setText("Instruction: ");
        ingredients.setText(ingredientsDisplay(recipe.getIngredients()));
        instructions.setText(instructionsDisplay(recipe.getInstructions()));
    }

    /**
     * Loads a result of inventory command into details panel.
     */
    private void loadCrossDetail(CrossRecipe crossRecipe) {
        this.crossRecipe = crossRecipe;
        Recipe targetRecipe = crossRecipe.getRecipe();
        Map<IngredientDefinition, IngredientPortion> neededIngredients = crossRecipe.getNeededIngredients();
        name.setText(targetRecipe.getName().fullName);
        ingredientLabel.setText("Ingredients: ");
        ingredientToShopLabel.setText("Ingredients to shop: ");
        instructionLabel.setText("Instruction: ");
        ingredients.setText(ingredientsDisplay(targetRecipe.getIngredients()));
        ingredientsToShop.setText(ingredientsDisplay(neededIngredients));
        instructions.setText(instructionsDisplay(targetRecipe.getInstructions()));

    }

    /**
     * Generate screen-friendly text format for ingredients.
     */
    private String ingredientsDisplay(Map<IngredientDefinition, IngredientPortion> ingredientSet) {
        StringBuilder builder = new StringBuilder();
        ingredientSet.forEach((def, portion) -> {
            builder.append(def.getName());
            builder.append(", ");
            if (portion.getAmount().getValue() % 1 > 0) {
                builder.append(String.format("%.1f", portion.getAmount().getValue()));
            } else {
                builder.append(String.format("%.1f", portion.getAmount().getValue()));
            }
            builder.append(" ");
            builder.append(portion.getUnit());
            builder.append("\n");
        });
        return builder.toString();
    }

    /**
     * Generate screen-friendly text format for instructions.
     */
    private String instructionsDisplay(List<Instruction> instructions) {
        StringBuilder builder = new StringBuilder();
        Iterator it = instructions.iterator();
        int count = 1;
        while (it.hasNext()) {
            builder.append(count + ". ");
            builder.append(((Instruction) it.next()).value + "\n\n");
            count++;
        }
        return builder.toString();
    }

    @Subscribe
    private void handlePersonPanelSelectionChangedEvent(RecipePanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        loadRecipeDetail(event.getNewSelection());
    }

    @Subscribe
    private void handlePersonPanelSelectionChangedEvent(CrossRecipePanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        loadCrossDetail(event.getNewSelection());
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DetailPanel)) {
            return false;
        }

        // state check
        DetailPanel panel = (DetailPanel) other;
        return recipe.equals(panel.recipe);
    }
}

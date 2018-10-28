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
import seedu.souschef.commons.events.ui.RecipePanelSelectionChangedEvent;
import seedu.souschef.model.ingredient.IngredientDefinition;
import seedu.souschef.model.recipe.Instruction;
import seedu.souschef.model.recipe.Recipe;

/**
 * An UI component that displays information of a {@code Recipe}.
 */
public class DetailPanel extends UiPart<Region> {

    private static final String FXML = "DetailPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(getClass());
    private Recipe recipe;

    @FXML
    private Label name;
    @FXML
    private Label ingredientLabel;
    @FXML
    private Label ingredients;
    @FXML
    private Label instructionLabel;
    @FXML
    private Label instructions;

    public DetailPanel() {
        super(FXML);
        getRoot().setOnKeyPressed(Event::consume);

        loadDefaultPage();
        registerAsAnEventHandler(this);
    }

    /**
     * Loads a placeholders into details panel.
     */
    public void loadDefaultPage() {
        name.setText("Select a recipe for viewing...");
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
     * Generate screen-friendly text format for ingredients.
     */
    private String ingredientsDisplay(Map<IngredientDefinition, Double> ingredientSet) {
        StringBuilder builder = new StringBuilder();
        ingredientSet.forEach((def, amount) -> {
            builder.append(def.getName().toString());
            builder.append(", ");
            if (amount % 1 > 0) {
                builder.append(amount);
            } else {
                builder.append(amount.intValue());
            }
            builder.append("\n");
            // To append unit too
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

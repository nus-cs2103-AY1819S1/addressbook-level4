package seedu.restaurant.ui.ingredient;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import seedu.restaurant.model.ingredient.Ingredient;
import seedu.restaurant.ui.UiPart;

/**
 * The Ingredient Stack Panel of the App.
 */
public class IngredientStackPanel extends UiPart<Region> {

    private static final String FXML = "IngredientStackPanel.fxml";

    public final Ingredient ingredient;

    @FXML
    private StackPane stackPane;
    @FXML
    private Label name;
    @FXML
    private Label price;
    @FXML
    private Label unit;
    @FXML
    private Label minimum;
    @FXML
    private Label numUnits;

    public IngredientStackPanel(Ingredient ingredient) {
        super(FXML);
        this.ingredient = ingredient;
        name.setText(ingredient.getName().toString());
        unit.setText("Unit: " + ingredient.getUnit());
        price.setText("Price per unit: $" + ingredient.getPrice().toString());
        minimum.setText("Minimum stock: " + ingredient.getMinimum().toString());
        numUnits.setText("Number of units available: " + ingredient.getNumUnits().toString());
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof IngredientStackPanel)) {
            return false;
        }

        // state check
        IngredientStackPanel ingredientStackPanel = (IngredientStackPanel) other;
        return ingredient.equals(ingredientStackPanel.ingredient);
    }
}

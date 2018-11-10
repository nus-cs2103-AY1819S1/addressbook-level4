package seedu.restaurant.ui.ingredient;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.restaurant.model.ingredient.Ingredient;
import seedu.restaurant.ui.UiPart;

/**
 * An UI component that displays information of an {@code Ingredient}.
 */
public class IngredientCard extends UiPart<Region> {

    private static final String FXML = "IngredientListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Ingredient ingredient;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label price;
    @FXML
    private Label numUnits;
    @FXML
    private Label minimum;

    public IngredientCard(Ingredient ingredient, int displayedIndex) {
        super(FXML);
        this.ingredient = ingredient;
        id.setText(displayedIndex + ". ");
        name.setText(ingredient.getName().toString());
        price.setText("$" + ingredient.getPrice().toString() + " / " + ingredient.getUnit().toString());
        numUnits.setText(ingredient.getNumUnits().toString() + " " + ingredient.getUnit().toString()
                + "(s) available");
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof IngredientCard)) {
            return false;
        }

        // state check
        IngredientCard card = (IngredientCard) other;
        return id.getText().equals(card.id.getText())
                && ingredient.equals(card.ingredient);
    }
}

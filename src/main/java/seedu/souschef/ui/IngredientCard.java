package seedu.souschef.ui;

import java.text.SimpleDateFormat;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import seedu.souschef.model.ingredient.Ingredient;

/**
 * An UI component that displays information of a {@code Ingredient}.
 */
public class IngredientCard extends GenericCard<Ingredient> {

    private static final String FXML = "IngredientListCard.fxml";

    public final Ingredient ingredient;

    @FXML
    private HBox cardPane;
    @FXML
    private Label id;
    @FXML
    private Label name;
    @FXML
    private Label amount;
    @FXML
    private Label unit;
    @FXML
    private Label date;

    public IngredientCard(Ingredient ingredient, int displayedIndex) {
        super(FXML);
        this.ingredient = ingredient;
        id.setText(displayedIndex + ". ");
        name.setText(ingredient.getName());
        amount.setText(Double.toString(ingredient.getAmount()));
        unit.setText(ingredient.getUnit().toString());
        date.setText(new SimpleDateFormat("MM-dd-yyyy").format(ingredient.getDate()));
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

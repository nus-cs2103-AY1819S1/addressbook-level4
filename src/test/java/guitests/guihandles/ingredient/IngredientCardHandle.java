package guitests.guihandles.ingredient;

import guitests.guihandles.NodeHandle;
import javafx.scene.Node;
import javafx.scene.control.Label;
import seedu.restaurant.model.ingredient.Ingredient;

/**
 * Provides a handle to an ingredient card in the ingredient list panel.
 */
public class IngredientCardHandle extends NodeHandle<Node> {
    private static final String ID_FIELD_ID = "#id";
    private static final String NAME_FIELD_ID = "#name";
    private static final String PRICE_FIELD_ID = "#price";
    private static final String NUM_UNITS_FIELD_ID = "#numUnits";

    private final Label idLabel;
    private final Label nameLabel;
    private final Label priceLabel;
    private final Label numUnitsLabel;

    public IngredientCardHandle(Node cardNode) {
        super(cardNode);

        idLabel = getChildNode(ID_FIELD_ID);
        nameLabel = getChildNode(NAME_FIELD_ID);
        priceLabel = getChildNode(PRICE_FIELD_ID);
        numUnitsLabel = getChildNode(NUM_UNITS_FIELD_ID);
    }

    public String getId() {
        return idLabel.getText();
    }

    public String getName() {
        return nameLabel.getText();
    }

    public String getPrice() {
        return priceLabel.getText();
    }

    public String getNumUnits() {
        return numUnitsLabel.getText();
    }

    /**
     * Returns true if this handle contains {@code ingredient}.
     */
    public boolean equals(Ingredient ingredient) {
        return getName().equals(ingredient.getName().toString());
    }
}

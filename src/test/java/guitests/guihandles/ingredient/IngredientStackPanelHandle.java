package guitests.guihandles.ingredient;

import guitests.guihandles.NodeHandle;
import javafx.scene.Node;
import javafx.scene.control.Label;
import seedu.restaurant.model.ingredient.Ingredient;

//@@author rebstan97
/**
 * Provides a handle to an item card in the item list panel.
 */
public class IngredientStackPanelHandle extends NodeHandle<Node> {
    private static final String NAME_FIELD_ID = "#name";
    private static final String UNIT_FIELD_ID = "#unit";
    private static final String PRICE_FIELD_ID = "#price";
    private static final String MINIMUM_FIELD_ID = "#minimum";
    private static final String NUMUNITS_FIELD_ID = "#numUnits";

    private final Label nameLabel;
    private final Label unitLabel;
    private final Label priceLabel;
    private final Label minimumLabel;
    private final Label numUnitsLabel;

    public IngredientStackPanelHandle(Node cardNode) {
        super(cardNode);

        nameLabel = getChildNode(NAME_FIELD_ID);
        unitLabel = getChildNode(UNIT_FIELD_ID);
        priceLabel = getChildNode(PRICE_FIELD_ID);
        minimumLabel = getChildNode(MINIMUM_FIELD_ID);
        numUnitsLabel = getChildNode(NUMUNITS_FIELD_ID);
    }

    public String getName() {
        return nameLabel.getText();
    }

    public String getUnit() {
        return unitLabel.getText();
    }

    public String getPrice() {
        return priceLabel.getText();
    }

    public String getMinimum() {
        return minimumLabel.getText();
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

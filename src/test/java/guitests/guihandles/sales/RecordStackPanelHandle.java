package guitests.guihandles.sales;

import guitests.guihandles.NodeHandle;
import javafx.scene.Node;
import javafx.scene.control.Label;
import seedu.restaurant.model.sales.SalesRecord;

/**
 * Provides a handle to the stack panel in sales list
 */
public class RecordStackPanelHandle extends NodeHandle<Node> {
    private static final String DATE_FIELD_ID = "#date";
    private static final String ITEM_NAME_FIELD_ID = "#itemName";
    private static final String QUANTITY_SOLD_FIELD_ID = "#quantitySold";
    private static final String PRICE_FIELD_ID = "#price";
    private static final String REVENUE_FIELD_ID = "#totalRevenue";
    private static final String INGREDIENTS_USED_FIELD_ID = "#ingredientUsed";
    private final Label dateLabel;
    private final Label itemNameLabel;
    private final Label quantitySoldLabel;
    private final Label priceLabel;
    private final Label totalRevenueLabel;
    private final Label ingredientUsedLabel;

    public RecordStackPanelHandle(Node cardNode) {
        super(cardNode);
        dateLabel = getChildNode(DATE_FIELD_ID);
        itemNameLabel = getChildNode(ITEM_NAME_FIELD_ID);
        quantitySoldLabel = getChildNode(QUANTITY_SOLD_FIELD_ID);
        priceLabel = getChildNode(PRICE_FIELD_ID);
        totalRevenueLabel = getChildNode(REVENUE_FIELD_ID);
        ingredientUsedLabel = getChildNode(INGREDIENTS_USED_FIELD_ID);
    }

    public String getDate() {
        return dateLabel.getText();
    }

    public String getItemName() {
        return itemNameLabel.getText();
    }

    public String getQuantitySold() {
        return quantitySoldLabel.getText();
    }

    public String getPrice() {
        return priceLabel.getText();
    }

    public String getTotalRevenue() {
        return totalRevenueLabel.getText();
    }

    public String getIngredientUsed() {
        return ingredientUsedLabel.getText();
    }

    /**
     * Returns true if this handle contains {@code salesRecord}.
     */
    public boolean equals(SalesRecord salesRecord) {
        return getDate().equals(salesRecord.getDate().toString())
                && getItemName().equals(salesRecord.getName().toString());
    }
}

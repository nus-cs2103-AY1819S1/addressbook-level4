package seedu.restaurant.ui.sales;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.Map;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import seedu.restaurant.model.ingredient.IngredientName;
import seedu.restaurant.model.sales.SalesRecord;
import seedu.restaurant.ui.UiPart;

//@@author HyperionNKJ
/**
 * Stack panel to display each sales record's details
 */
public class RecordStackPanel extends UiPart<Region> {

    public static final String MESSAGE_REQUIRED_INGREDIENTS_NOT_FOUND = "Data unavailable due to one of the "
            + "following reasons - \n\n1) The ingredients required to make this item were not specified at the time of "
            + "recording.\n\n2) This record was edited some time in the past.";

    private static final String FXML = "RecordStackPanel.fxml";

    public final SalesRecord salesRecord;

    @FXML
    private StackPane stackPane;
    @FXML
    private Label date;
    @FXML
    private Label itemName;
    @FXML
    private Label quantitySold;
    @FXML
    private Label price;
    @FXML
    private Label totalRevenue;
    @FXML
    private Label ingredientUsed;

    public RecordStackPanel(SalesRecord salesRecord) {
        super(FXML);
        this.salesRecord = salesRecord;
        date.setText(salesRecord.getDate().toString() + " (" + salesRecord.getDate().getDayOfWeek() + ")");
        itemName.setText(salesRecord.getName().toString());
        quantitySold.setText(salesRecord.getQuantitySold().toString());

        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(Locale.US);
        price.setText(currencyFormatter.format(salesRecord.getPrice().getValue()));
        totalRevenue.setText(currencyFormatter.format(salesRecord.getRevenue()));
        ingredientUsed.setText(ingredientUsedToString(salesRecord.getIngredientsUsed()));
    }

    /**
     * Returns the string representation of the given {@code ingredientUsed}
     */
    private String ingredientUsedToString(Map<IngredientName, Integer> ingredientUsed) {
        if (ingredientUsed.isEmpty()) {
            return MESSAGE_REQUIRED_INGREDIENTS_NOT_FOUND;
        }
        StringBuilder stringBuilder = new StringBuilder();
        int index = 1;
        for (Map.Entry<IngredientName, Integer> entry : ingredientUsed.entrySet()) {
            stringBuilder.append(index).append(") ").append(entry.getKey().toString())
                    .append(" - ").append(entry.getValue().toString()).append(" units").append("\n");
            index++;
        }
        return stringBuilder.toString();
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }
        // instanceof handles nulls
        if (!(other instanceof RecordStackPanel)) {
            return false;
        }
        // state check
        RecordStackPanel recordStackPanel = (RecordStackPanel) other;
        return salesRecord.equals(recordStackPanel.salesRecord);
    }
}

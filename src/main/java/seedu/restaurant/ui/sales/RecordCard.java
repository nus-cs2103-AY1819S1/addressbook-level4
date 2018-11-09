package seedu.restaurant.ui.sales;

import java.text.NumberFormat;
import java.util.Locale;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.restaurant.model.sales.SalesRecord;
import seedu.restaurant.ui.UiPart;

//@@author HyperionNKJ
/**
 * An UI component that displays information of a {@code SalesRecord}.
 */
public class RecordCard extends UiPart<Region> {

    private static final String FXML = "RecordListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final SalesRecord record;

    @FXML
    private HBox cardPane;
    @FXML
    private Label id;
    @FXML
    private Label date;
    @FXML
    private Label itemName;
    @FXML
    private Label quantitySold;
    @FXML
    private Label price;

    public RecordCard(SalesRecord record, int displayedIndex) {
        super(FXML);
        this.record = record;
        id.setText(displayedIndex + ". ");
        date.setText(record.getDate().toString());
        itemName.setText(record.getName().toString());
        quantitySold.setText("Quantity Sold: " + String.valueOf(record.getQuantitySold().toString()));

        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(Locale.US);
        price.setText("Item Price: " + currencyFormatter.format(record.getPrice().getValue()));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof RecordCard)) {
            return false;
        }

        // state check
        RecordCard card = (RecordCard) other;
        return id.getText().equals(card.id.getText())
                && record.equals(card.record);
    }
}

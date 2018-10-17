package seedu.inventory.ui.purchaseorder;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.inventory.model.purchaseorder.PurchaseOrder;
import seedu.inventory.ui.UiPart;

/**
 * An UI component that displays information of a {@code PurchaseOrder}.
 */
public class PurchaseOrderCard extends UiPart<Region> {

    private static final String FXML = "PurchaseOrderListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on Inventory level 4</a>
     */

    public final PurchaseOrder purchaseOrder;

    @FXML
    private HBox cardPane;
    @FXML
    private Label id;
    @FXML
    private Label sku;
    @FXML
    private Label name;
    @FXML
    private Label quantity;
    @FXML
    private Label reqDate;
    @FXML
    private Label supplier;
    @FXML
    private Label status;

    public PurchaseOrderCard(PurchaseOrder purchaseOrder, int displayedIndex) {
        super(FXML);
        this.purchaseOrder = purchaseOrder;
        id.setText(displayedIndex + ". ");
        sku.setText(purchaseOrder.getSku().value);
        name.setText(purchaseOrder.getName().fullName);
        quantity.setText(purchaseOrder.getQuantity().value);
        reqDate.setText(purchaseOrder.getReqDate().requiredDate);
        supplier.setText(purchaseOrder.getSupplier().supplierName);
        status.setText(purchaseOrder.getStatus().name());
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PurchaseOrder)) {
            return false;
        }

        // state check
        PurchaseOrderCard card = (PurchaseOrderCard) other;
        return id.getText().equals(card.id.getText())
                && purchaseOrder.equals(card.purchaseOrder);
    }

}

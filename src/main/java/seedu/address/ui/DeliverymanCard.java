package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.address.model.deliveryman.Deliveryman;

/**
 * An UI component that displays information of a {@code Order}.
 */
public class DeliverymanCard extends UiPart<Region> {

    private static final String FXML = "DeliverymanListCard.fxml";
    private static final String BUSY_LABEL_CLASS = "busy";
    private static final String AVAILABLE_LABEL_CLASS = "available";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on OrderBook level 4</a>
     */

    public final Deliveryman deliveryman;

    @FXML
    private VBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label deliverymanIndicator;

    public DeliverymanCard(Deliveryman deliveryman, int displayedIndex) {
        super(FXML);
        this.deliveryman = deliveryman;
        name.setText(deliveryman.getName().fullName);
        setDeliverymanStatus();
    }

    private void setDeliverymanStatus() {
        if (deliveryman.getOrders().size() > 0) {
            deliverymanIndicator.getStyleClass().clear();
            deliverymanIndicator.getStyleClass().add(BUSY_LABEL_CLASS);
            deliverymanIndicator.setText("Assigned: " + deliveryman.getOrders().size());
        } else {
            deliverymanIndicator.getStyleClass().clear();
            deliverymanIndicator.getStyleClass().add(AVAILABLE_LABEL_CLASS);
            deliverymanIndicator.setText("Available");
        }
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeliverymanCard)) {
            return false;
        }

        // state check
        DeliverymanCard card = (DeliverymanCard) other;
        return deliveryman.equals(card.deliveryman);
    }
}

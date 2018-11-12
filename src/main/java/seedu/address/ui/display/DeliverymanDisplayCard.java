package seedu.address.ui.display;

import java.util.Set;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.address.model.deliveryman.Deliveryman;
import seedu.address.model.order.Order;
import seedu.address.ui.UiPart;

/**
 * UI Component that displays relevant information of a deliveryman.
 */
public class DeliverymanDisplayCard extends UiPart<Region> {
    private static final String FXML = "display/DeliverymanDisplayCard.fxml";
    private static final String BUSY_LABEL_CLASS = "busy";
    private static final String AVAILABLE_LABEL_CLASS = "available";

    public final Deliveryman deliveryman;

    @FXML
    private Label namePrintOut;
    @FXML
    private Label deliverymanStatusPrintOut;
    @FXML
    private VBox orderListDisplay;


    public DeliverymanDisplayCard(Deliveryman deliveryman) {
        super(FXML);
        this.deliveryman = deliveryman;
        namePrintOut.setText(deliveryman.getName().fullName);

        setDeliverymanStatus();
        setOrders(deliveryman.getOrders());
    }

    private void setDeliverymanStatus() {
        if (deliveryman.getOrders().size() > 0) {
            deliverymanStatusPrintOut.setText("Delivering");
            deliverymanStatusPrintOut.getStyleClass().clear();
            deliverymanStatusPrintOut.getStyleClass().add(BUSY_LABEL_CLASS);
        } else {
            deliverymanStatusPrintOut.setText("Available");
            deliverymanStatusPrintOut.getStyleClass().clear();
            deliverymanStatusPrintOut.getStyleClass().add(AVAILABLE_LABEL_CLASS);
        }
    }

    private void setOrders(Set<Order> orders) {
        for (Order o : orders) {

            OrderPrintOut orderPrintOut = new OrderPrintOut(o);
            orderListDisplay.getChildren().add(orderPrintOut.getRoot());
        }
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeliverymanDisplayCard)) {
            return false;
        }

        // state check
        DeliverymanDisplayCard card = (DeliverymanDisplayCard) other;
        return deliveryman.equals(card.deliveryman);
    }
}

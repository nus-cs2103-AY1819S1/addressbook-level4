package seedu.address.ui.display;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;
import seedu.address.model.deliveryman.Deliveryman;
import seedu.address.model.order.Order;
import seedu.address.ui.UiPart;

/**
 * UI Component that represents the display for an Order
 */
public class OrderDisplayCard extends UiPart<Region> {
    private static final String FXML = "display/OrderDisplayCard.fxml";

    public final Order order;

    @FXML
    private Label foodL;
    @FXML
    private Label dateL;
    @FXML
    private Label phoneL;
    @FXML
    private Text addressL;
    @FXML
    private Label nameL;
    @FXML
    private Label deliverymanL;

    public OrderDisplayCard(Order order) {
        super(FXML);
        this.order = order;

        nameL.setText("Name: " + order.getName().fullName);
        dateL.setText("Date: " + order.getDate().toString());
        foodL.setText("Food: " + order.getFood().toString());
        phoneL.setText("Phone: " + order.getPhone().toString());
        addressL.setText("Address: " + order.getAddress().toString());
        deliverymanL.setText("Deliveryman: " + getFullNameOrNull(order.getDeliveryman()));
    }

    private String getFullNameOrNull(Deliveryman deliveryman) {
        if (deliveryman == null) {
            return "Not assigned.";
        }
        return deliveryman.getName().fullName;
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof OrderDisplayCard)) {
            return false;
        }

        // state check
        OrderDisplayCard card = (OrderDisplayCard) other;
        return order.equals(card.order);
    }
}

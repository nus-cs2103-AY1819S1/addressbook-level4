package seedu.address.ui.display;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;
import seedu.address.model.order.Order;
import seedu.address.ui.UiPart;

/**
 * UI Component representing the display of a single order in the deliveryman's list of orders.
 */
public class OrderPrintOut extends UiPart<Region> {

    private static final String FXML = "display/OrderPrintOut.fxml";

    private final Order order;

    @FXML
    private Label nameP;

    @FXML
    private Label dateP;

    @FXML
    private Text addressP;

    @FXML
    private Label phoneP;

    public OrderPrintOut(Order order) {
        super(FXML);
        this.order = order;

        nameP.setText("Order from " + order.getName().fullName);
        dateP.setText(order.getDate().toString());
        phoneP.setText(order.getPhone().toString());
        addressP.setText(order.getAddress().toString());
    }



    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof OrderPrintOut)) {
            return false;
        }

        // state check
        OrderPrintOut card = (OrderPrintOut) other;
        return order.equals(card.order);
    }

}




package seedu.address.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.Logic;

/**
 * Panel containing the main split pane.
 */
public class Dashboard extends UiPart<Region> {

    private static final String FXML = "Dashboard.fxml";
    private final Logger logger = LogsCenter.getLogger(LoginPanel.class);

    private Display display;
    private OrderListPanel orderListPanel;
    private DeliverymanListPanel deliveryMenListPanel;
    private LoginPanel loginPanel;

    @FXML
    private StackPane displayPlaceholder;

    @FXML
    private StackPane orderListPanelPlaceholder;

    @FXML
    private StackPane deliveryMenListPanelPlaceholder;


    public Dashboard(Logic logic) {
        super(FXML);
        display = new Display(logic.getFilteredOrderList());
        displayPlaceholder.getChildren().add(display.getRoot());

        orderListPanel = new OrderListPanel(logic.getFilteredOrderList());
        orderListPanelPlaceholder.getChildren().add(orderListPanel.getRoot());

        deliveryMenListPanel = new DeliverymanListPanel(logic.getFilteredDeliverymanList());
        deliveryMenListPanelPlaceholder.getChildren().add(deliveryMenListPanel.getRoot());
    }
}

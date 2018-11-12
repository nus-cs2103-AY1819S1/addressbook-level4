package seedu.address.ui;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.BackToHomeEvent;
import seedu.address.commons.events.ui.DeliveryManPanelSelectionChangedEvent;
import seedu.address.commons.events.ui.JumpToOrderListRequestEvent;
import seedu.address.commons.events.ui.OrderPanelSelectionChangedEvent;
import seedu.address.model.order.Order;

/**
 * Panel containing the list of orders.
 */
public class OrderListPanel extends UiPart<Region> {
    private static final String FXML = "OrderListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(OrderListPanel.class);

    @FXML
    private ListView<Order> orderListView;

    public OrderListPanel(ObservableList<Order> orderList) {
        super(FXML);

        setConnections(orderList);
        registerAsAnEventHandler(this);
    }

    private void setConnections(ObservableList<Order> orderList) {
        orderListView.setItems(orderList);
        orderListView.setCellFactory(listView -> new OrderListViewCell());
        setEventHandlerForSelectionChangeEvent();
    }

    private void setEventHandlerForSelectionChangeEvent() {
        orderListView.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        logger.fine("Selection in order list panel changed to : '" + newValue + "'");
                        raise(new OrderPanelSelectionChangedEvent(newValue));
                    }
                });
    }

    /**
     * Scrolls to the {@code OrderCard} at the {@code index} and selects it.
     */
    private void scrollTo(int index) {
        Platform.runLater(() -> {
            orderListView.scrollTo(index);
            orderListView.getSelectionModel().clearAndSelect(index);
        });
    }

    @Subscribe
    private void handleJumpToOrderListRequestEvent(JumpToOrderListRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        scrollTo(event.targetIndex);
    }

    @Subscribe
    private void handleDeliverymanPanelSelectionChangedEvent(DeliveryManPanelSelectionChangedEvent event) {
        orderListView.getSelectionModel().clearSelection();
    }

    @Subscribe
    private void handleBackToHomeRequest(BackToHomeEvent event) {
        orderListView.getSelectionModel().clearSelection();
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Order} using a {@code OrderCard}.
     */
    class OrderListViewCell extends ListCell<Order> {
        @Override
        protected void updateItem(Order order, boolean empty) {
            super.updateItem(order, empty);

            if (empty || order == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new OrderCard(order, getIndex() + 1).getRoot());
            }
        }
    }

}

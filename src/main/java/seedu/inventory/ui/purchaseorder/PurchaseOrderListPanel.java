package seedu.inventory.ui.purchaseorder;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.inventory.commons.core.LogsCenter;
import seedu.inventory.commons.events.ui.JumpToListRequestEvent;
import seedu.inventory.commons.events.ui.PurchaseOrderSelectionChangedEvent;
import seedu.inventory.model.purchaseorder.PurchaseOrder;
import seedu.inventory.ui.UiPart;

/**
 * Panel containing the list of persons.
 */
public class PurchaseOrderListPanel extends UiPart<Region> {
    private static final String FXML = "PurchaseOrderListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(PurchaseOrderListPanel.class);

    @FXML
    private ListView<PurchaseOrder> purchaseOrderListView;

    public PurchaseOrderListPanel(ObservableList<PurchaseOrder> poList) {
        super(FXML);
        setConnections(poList);
        registerAsAnEventHandler(this);
    }

    private void setConnections(ObservableList<PurchaseOrder> poList) {
        purchaseOrderListView.setItems(poList);
        purchaseOrderListView.setCellFactory(listView -> new PurchaseOrderViewCell());
        setEventHandlerForSelectionChangeEvent();
    }

    private void setEventHandlerForSelectionChangeEvent() {
        purchaseOrderListView.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        logger.fine("Selection in item list panel changed to : '" + newValue + "'");
                        raise(new PurchaseOrderSelectionChangedEvent(newValue));
                    }
                });
    }

    /**
     * Scrolls to the {@code PurchaseOrderCard} at the {@code index} and selects it.
     */
    private void scrollTo(int index) {
        Platform.runLater(() -> {
            purchaseOrderListView.scrollTo(index);
            purchaseOrderListView.getSelectionModel().clearAndSelect(index);
        });
    }

    @Subscribe
    private void handleJumpToListRequestEvent(JumpToListRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        scrollTo(event.targetIndex);
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code PurchaseOrder} using a {@code PurchaseOrderCard}.
     */
    class PurchaseOrderViewCell extends ListCell<PurchaseOrder> {
        @Override
        protected void updateItem(PurchaseOrder po, boolean empty) {
            super.updateItem(po, empty);

            if (empty || po == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new PurchaseOrderCard(po, getIndex() + 1).getRoot());
            }
        }
    }

}

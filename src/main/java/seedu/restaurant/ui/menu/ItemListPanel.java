package seedu.restaurant.ui.menu;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.restaurant.commons.core.LogsCenter;
import seedu.restaurant.commons.events.ui.menu.ItemPanelSelectionChangedEvent;
import seedu.restaurant.commons.events.ui.menu.JumpToItemListRequestEvent;
import seedu.restaurant.model.menu.Item;
import seedu.restaurant.ui.UiPart;

/**
 * Panel containing the list of items.
 */
public class ItemListPanel extends UiPart<Region> {

    private static final String FXML = "ItemListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(ItemListPanel.class);

    @FXML
    private ListView<Item> itemListView;

    public ItemListPanel(ObservableList<Item> itemList) {
        super(FXML);
        setConnections(itemList);
        registerAsAnEventHandler(this);
    }

    @FXML
    private void handleMouseClick() {
        Item item = itemListView.getSelectionModel().getSelectedItem();
        logger.fine("Selection in item list panel changed to : '" + item + "'");
        raise(new ItemPanelSelectionChangedEvent(item));
    }

    private void setConnections(ObservableList<Item> itemList) {
        itemListView.setItems(itemList);
        itemListView.setCellFactory(listView -> new ItemListViewCell());
    }

    /**
     * Scrolls to the {@code ItemCard} at the {@code index} and selects it.
     */
    private void scrollTo(int index) {
        Platform.runLater(() -> {
            itemListView.scrollTo(index);
            itemListView.getSelectionModel().clearAndSelect(index);
            raise(new ItemPanelSelectionChangedEvent(itemListView.getItems().get(index)));
        });
    }

    @Subscribe
    private void handleJumpToItemListRequestEvent(JumpToItemListRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        scrollTo(event.targetIndex);
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Item} using a {@code ItemCard}.
     */
    private class ItemListViewCell extends ListCell<Item> {

        @Override
        protected void updateItem(Item item, boolean empty) {
            super.updateItem(item, empty);

            if (empty || item == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new ItemCard(item, getIndex() + 1).getRoot());
            }
        }
    }
}

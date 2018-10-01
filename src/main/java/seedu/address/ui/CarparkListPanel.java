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
import seedu.address.commons.events.ui.CarparkPanelSelectionChangedEvent;
import seedu.address.commons.events.ui.JumpToListRequestEvent;
import seedu.address.model.carpark.Carpark;

/**
 * Panel containing the list of persons.
 */
public class CarparkListPanel extends UiPart<Region> {
    private static final String FXML = "CarparkListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(CarparkListPanel.class);

    @FXML
    private ListView<Carpark> carparkListView;

    public CarparkListPanel(ObservableList<Carpark> carparkList) {
        super(FXML);
        setConnections(carparkList);
        registerAsAnEventHandler(this);
    }

    private void setConnections(ObservableList<Carpark> carparkList) {
        carparkListView.setItems(carparkList);
        carparkListView.setCellFactory(listView -> new CarparkListViewCell());
        setEventHandlerForSelectionChangeEvent();
    }

    private void setEventHandlerForSelectionChangeEvent() {
        carparkListView.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        logger.fine("Selection in carpark list panel changed to : '" + newValue + "'");
                        raise(new CarparkPanelSelectionChangedEvent(newValue));
                    }
                });
    }

    /**
     * Scrolls to the {@code CarparkCard} at the {@code index} and selects it.
     */
    private void scrollTo(int index) {
        Platform.runLater(() -> {
            carparkListView.scrollTo(index);
            carparkListView.getSelectionModel().clearAndSelect(index);
        });
    }

    @Subscribe
    private void handleJumpToListRequestEvent(JumpToListRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        scrollTo(event.targetIndex);
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Person} using a {@code CarparkCard}.
     */
    class CarparkListViewCell extends ListCell<Carpark> {
        @Override
        protected void updateItem(Carpark carpark, boolean empty) {
            super.updateItem(carpark, empty);

            if (empty || carpark == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new CarparkCard(carpark, getIndex() + 1).getRoot());
            }
        }
    }

}

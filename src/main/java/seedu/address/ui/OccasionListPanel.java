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
import seedu.address.commons.events.ui.JumpToOccasionListRequestEvent;
import seedu.address.commons.events.ui.OccasionPanelSelectionChangedEvent;
import seedu.address.model.occasion.Occasion;

/**
 * Panel containing the list of occasions.
 * @author xueyantian
 */
public class OccasionListPanel extends UiPart<Region> {
    private static final String FXML = "OccasionListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(OccasionListPanel.class);

    @FXML
    private ListView<Occasion> occasionListView;

    public OccasionListPanel(ObservableList<Occasion> occasionList) {
        super(FXML);
        setConnections(occasionList);

        registerAsAnEventHandler(this);
    }

    /**
     * Clears selection of the view.
     */
    public void clearSelection() {
        Platform.runLater(() -> {
            occasionListView.getSelectionModel().clearSelection();
        });
    }

    /**
     * Change list of view.
     * @param occasionList updated list.
     */
    public void updatePanel(ObservableList<Occasion> occasionList) {
        occasionListView.setItems(occasionList);
        occasionListView.setCellFactory(listView -> new OccasionListViewCell());
    }

    private void setConnections(ObservableList<Occasion> occasionList) {
        occasionListView.setItems(occasionList);
        occasionListView.setCellFactory(listView -> new OccasionListViewCell());
        setEventHandlerForSelectionChangeEvent();
    }

    private void setEventHandlerForSelectionChangeEvent() {
        occasionListView.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        logger.fine("Selection in occasion list panel changed to : '" + newValue + "'");
                        raise(new OccasionPanelSelectionChangedEvent(newValue));
                    }
                });
    }

    /**
     * Scrolls to the {@code OccasionCard} at the {@code index} and selects it.
     */
    private void scrollTo(int index) {
        Platform.runLater(() -> {
            occasionListView.scrollTo(index);
            occasionListView.getSelectionModel().clearAndSelect(index);
        });
    }

    @Subscribe
    private void handleJumpToListRequestEvent(JumpToOccasionListRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        scrollTo(event.targetIndex);
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Occasion} using a {@code OccasionCard}.
     */
    class OccasionListViewCell extends ListCell<Occasion> {
        @Override
        protected void updateItem(Occasion occasion, boolean empty) {
            super.updateItem(occasion, empty);

            if (empty || occasion == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new OccasionCard(occasion, getIndex() + 1).getRoot());
            }
        }
    }
}

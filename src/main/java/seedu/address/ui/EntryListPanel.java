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
import seedu.address.commons.events.ui.JumpToEntryListRequestEvent;
import seedu.address.commons.events.ui.UpdateExpandedEntryRequestEvent;
import seedu.address.model.entry.ResumeEntry;

/**
 * panel containing a list of entries
 */
public class EntryListPanel extends UiPart<Region> {
    private static final String FXML = "EntryListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(EntryListPanel.class);

    @FXML
    private ListView<ResumeEntry> entryListView;

    public EntryListPanel(ObservableList<ResumeEntry> entryList) {
        super(FXML);
        setConnections(entryList);
        registerAsAnEventHandler(this);
    }

    private void setConnections(ObservableList<ResumeEntry> entryList) {
        entryListView.setItems(entryList);
        entryListView.setCellFactory(listView -> new EntryListViewCell());
        setEventHandlerForSelectionChangeEvent();
    }


    private void setEventHandlerForSelectionChangeEvent() {
        entryListView.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        logger.fine("Selection in entry list panel changed to : '" + newValue + "'");
                        raise(new UpdateExpandedEntryRequestEvent(newValue));
                    }
                });
    }

    /**
     * Scrolls to the {@code EntryCard} at the {@code index} and selects it.
     */
    private void scrollTo(int index) {
        Platform.runLater(() -> {
            entryListView.scrollTo(index);
            entryListView.getSelectionModel().clearAndSelect(index);
        });
    }

    @Subscribe
    private void handleJumpToEntryListRequestEvent(JumpToEntryListRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        scrollTo(event.targetIndex);
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Person} using a {@code PersonCard}.
     */
    class EntryListViewCell extends ListCell<ResumeEntry> {
        @Override
        protected void updateItem(ResumeEntry entry, boolean empty) {
            super.updateItem(entry, empty);

            if (empty || entry == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new EntryCard(entry, getIndex() + 1).getRoot());
            }
        }
    }

}

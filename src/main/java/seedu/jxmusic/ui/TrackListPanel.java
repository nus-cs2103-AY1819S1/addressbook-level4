package seedu.jxmusic.ui;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.jxmusic.commons.core.LogsCenter;
import seedu.jxmusic.commons.events.ui.JumpToListRequestEvent;
import seedu.jxmusic.commons.events.ui.TrackListPanelSelectionChangedEvent;
import seedu.jxmusic.model.Track;

/**
 * Panel containing the list of tracks.
 */
public class TrackListPanel extends UiPart<Region> {
    private static final String FXML = "TrackListPanel.fxml";
    private Logger logger = LogsCenter.getLogger(TrackListPanel.class);

    @FXML
    private ListView<Track> trackListView;

    public TrackListPanel(ObservableList<Track> tracks) {
        super(FXML);
        setConnections(tracks);
        registerAsAnEventHandler(this);
    }

    private void setConnections(ObservableList<Track> tracks) {
        trackListView.setItems(tracks);
        trackListView.setCellFactory(listView -> new TrackListViewCell());
        setEventHandlerForSelectionChangeEvent();
    }

    private void setEventHandlerForSelectionChangeEvent() {
        trackListView.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        logger.fine("Selection in track list panel changed to : '" + newValue + "'");
                        raise(new TrackListPanelSelectionChangedEvent(newValue));
                    }
                });
    }


    /**
     *  Scrolls to the {@code TrackCard} at the {@code index} and selects it.
     */
    private void scrollTo(int index) {
        Platform.runLater(() -> {
            trackListView.scrollTo(index);
            trackListView.getSelectionModel().clearAndSelect(index);
        });
    }

    @Subscribe
    private void handleJumpToListRequestEvent(JumpToListRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        scrollTo(event.targetIndex);
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Track} using a {@code TrackCard}.
     */
    class TrackListViewCell extends ListCell<Track> {
        @Override
        protected void updateItem(Track track, boolean empty) {
            super.updateItem(track, empty);

            if (empty || track == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new TrackCard(track, getIndex() + 1).getRoot());
            }
        }
    }

}



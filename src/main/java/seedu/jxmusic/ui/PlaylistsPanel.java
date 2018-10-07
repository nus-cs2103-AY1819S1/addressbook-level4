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
import seedu.jxmusic.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.jxmusic.model.Playlist;

/**
 * Panel containing the list of persons.
 */
public class PlaylistsPanel extends UiPart<Region> {
    private static final String FXML = "PersonListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(PlaylistsPanel.class);

    @FXML
    private ListView<Playlist> playlistsView;

    public PlaylistsPanel(ObservableList<Playlist> playlists) {
        super(FXML);
        setConnections(playlists);
        registerAsAnEventHandler(this);
    }

    private void setConnections(ObservableList<Playlist> playlists) {
        playlistsView.setItems(playlists);
        playlistsView.setCellFactory(listView -> new PlaylistsViewCell());
        setEventHandlerForSelectionChangeEvent();
    }

    private void setEventHandlerForSelectionChangeEvent() {
        playlistsView.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        logger.fine("Selection in playlist list panel changed to : '" + newValue + "'");
                        raise(new PersonPanelSelectionChangedEvent(newValue));
                    }
                });
    }

    /**
     * Scrolls to the {@code PlaylistCard} at the {@code index} and selects it.
     */
    private void scrollTo(int index) {
        Platform.runLater(() -> {
            playlistsView.scrollTo(index);
            playlistsView.getSelectionModel().clearAndSelect(index);
        });
    }

    @Subscribe
    private void handleJumpToListRequestEvent(JumpToListRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        scrollTo(event.targetIndex);
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Person} using a {@code PlaylistCard}.
     */
    class PlaylistsViewCell extends ListCell<Playlist> {
        @Override
        protected void updateItem(Playlist playlist, boolean empty) {
            super.updateItem(playlist, empty);

            if (empty || playlist == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new PlaylistCard(playlist, getIndex() + 1).getRoot());
            }
        }
    }

}

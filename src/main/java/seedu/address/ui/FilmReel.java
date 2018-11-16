package seedu.address.ui;

import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.FilmReelSelectionChangeEvent;
import seedu.address.commons.events.ui.UpdateFilmReelEvent;

//@@author chivent

/**
 * Panel containing the list of images.
 */
public class FilmReel extends UiPart<Region> {
    private static final String FXML = "FilmReelPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(HistoryListPanel.class);
    private ObservableList<Path> items = FXCollections.observableArrayList();

    @FXML
    private ListView<Path> imageListView;

    public FilmReel() {
        super(FXML);

        imageListView.setItems(items);
        imageListView.setCellFactory(listView -> new FilmReelCell());
        registerAsAnEventHandler(this);
    }


    /**
     * Event that triggers when new images are previewed with next
     *
     * @param event
     */
    @Subscribe
    private void handleUpdateFilmReelEvent(UpdateFilmReelEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        items.removeAll();
        items.setAll(event.paths);

        imageListView.scrollTo(0);
        imageListView.getSelectionModel().clearSelection();
    }

    /**
     * Event that triggers an image is selected with select
     *
     * @param event
     */
    @Subscribe
    private void handleFilmReelSelectionChangeEvent(FilmReelSelectionChangeEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        Platform.runLater(() -> {
            imageListView.scrollTo(event.index);
            imageListView.getSelectionModel().clearAndSelect(event.index);
        });
    }


    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Path} using a {@code FilmReelCard}.
     */
    class FilmReelCell extends ListCell<Path> {
        @Override
        protected void updateItem(Path image, boolean empty) {
            super.updateItem(image, empty);

            if (empty || image == null) {
                setGraphic(null);
                setText(null);
            } else {
                try {
                    setGraphic(new FilmReelCard(image, getIndex() + 1).getRoot());
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}

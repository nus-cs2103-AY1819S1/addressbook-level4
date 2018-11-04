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
import seedu.address.commons.events.ui.JumpToListRequestEvent;
import seedu.address.commons.events.ui.ReplaceWithContextPanelEvent;
import seedu.address.commons.events.ui.VolunteerPanelSelectionChangedEvent;
import seedu.address.model.volunteer.Volunteer;

/**
 * Panel containing the list of persons.
 */
public class VolunteerListPanel extends UiPart<Region> {
    private static final String FXML = "VolunteerListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(VolunteerListPanel.class);

    @FXML
    private ListView<Volunteer> volunteerListView;

    public VolunteerListPanel(ObservableList<Volunteer> volunteerList) {
        super(FXML);
        setConnections(volunteerList);
        registerAsAnEventHandler(this);
    }

    private void setConnections(ObservableList<Volunteer> volunteerList) {
        volunteerListView.setItems(volunteerList);
        volunteerListView.setCellFactory(listView -> new VolunteerListViewCell());
        setEventHandlerForSelectionChangeEvent();
    }

    private void setEventHandlerForSelectionChangeEvent() {
        volunteerListView.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        logger.fine("Selection in volunteer list panel changed to : '" + newValue + "'");
                        raise(new VolunteerPanelSelectionChangedEvent(newValue));
                        raise(new ReplaceWithContextPanelEvent());
                    }
                });
    }

    /**
     * Scrolls to the {@code VolunteerCard} at the {@code index} and selects it.
     */
    private void scrollTo(int index) {
        Platform.runLater(() -> {
            volunteerListView.scrollTo(index);
            volunteerListView.getSelectionModel().clearAndSelect(index);
        });
    }

    /**
     * Clears the selection in the ListView.
     */
    public void clearSelection() {
        volunteerListView.getSelectionModel().clearSelection();
    }

    @Subscribe
    private void handleJumpToListRequestEvent(JumpToListRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        scrollTo(event.targetIndex);
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Person} using a {@code VolunteerCard}.
     */
    class VolunteerListViewCell extends ListCell<Volunteer> {
        @Override
        protected void updateItem(Volunteer volunteer, boolean empty) {
            super.updateItem(volunteer, empty);

            if (empty || volunteer == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new VolunteerCard(volunteer, getIndex() + 1).getRoot());
            }
        }
    }

}

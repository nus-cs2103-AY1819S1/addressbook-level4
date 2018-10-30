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
import seedu.address.commons.events.ui.LeavePanelSelectionChangedEvent;
import seedu.address.model.leaveapplication.LeaveApplication;

/**
 * Panel containing the list of leave applications.
 */
public class LeaveListPanel extends UiPart<Region> {
    private static final String FXML = "LeaveListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(LeaveListPanel.class);

    @FXML
    private ListView<LeaveApplication> leaveListView;

    public LeaveListPanel(ObservableList<LeaveApplication> leaveApplicationList) {
        super(FXML);
        setConnections(leaveApplicationList);
        registerAsAnEventHandler(this);
    }

    private void setConnections(ObservableList<LeaveApplication> leaveApplicationList) {
        leaveListView.setItems(leaveApplicationList);
        leaveListView.setCellFactory(listView -> new LeaveListViewCell());
        setEventHandlerForSelectionChangeEvent();
    }

    private void setEventHandlerForSelectionChangeEvent() {
        leaveListView.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        logger.fine("Selection in leave list panel changed to : '" + newValue + "'");
                        raise(new LeavePanelSelectionChangedEvent(newValue));
                    }
                });
    }

    /**
     * Scrolls to the {@code LeaveApplicationCard} at the {@code index} and selects it.
     */
    private void scrollTo(int index) {
        Platform.runLater(() -> {
            leaveListView.scrollTo(index);
            leaveListView.getSelectionModel().clearAndSelect(index);
        });
    }

    @Subscribe
    private void handleJumpToListRequestEvent(JumpToListRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        scrollTo(event.targetIndex);
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code LeaveApplication} using
     * a {@code LeaveApplicationCard}.
     */
    class LeaveListViewCell extends ListCell<LeaveApplication> {
        @Override
        protected void updateItem(LeaveApplication leaveApplication, boolean empty) {
            super.updateItem(leaveApplication, empty);

            if (empty || leaveApplication == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new LeaveApplicationCard(leaveApplication, getIndex() + 1).getRoot());
            }
        }
    }

}

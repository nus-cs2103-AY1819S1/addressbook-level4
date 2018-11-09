package seedu.address.ui;

import java.util.logging.Logger;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.ListPickerSelectionChangedEvent;

/**
 * A ui for the status bar that is displayed at the header of the application.
 */
public class ListPicker extends UiPart<Region> {
    private static final Logger logger = LogsCenter.getLogger(ResultDisplay.class);
    private static final String FXML = "ListPicker.fxml";

    @FXML
    private Button archiveList;

    @FXML
    private Button activeList;

    @FXML
    private Button assignmentList;

    public ListPicker() {
        super(FXML);
        // Archive button is selected
        archiveList.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                archiveList.setStyle("-fx-background-color: #28B463; -fx-border-color: #28B463;");
                //archiveList.setDisable(true);
                activeList.setStyle("-fx-background-color: #95A5A6; -fx-border-color: #95A5A6;");
                //activeList.setDisable(false);
                assignmentList.setStyle("-fx-background-color: #95A5A6; -fx-border-color: #95A5A6;");
                //assignmentList.setDisable(false);
                raise(new ListPickerSelectionChangedEvent(2));
            }
        });
        // Active button is selected
        activeList.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                activeList.setStyle("-fx-border-color: #28B463; -fx-background-color: #28B463;");
                //activeList.setDisable(true);
                archiveList.setStyle("-fx-background-color: #95A5A6; -fx-border-color: #95A5A6;");
                //archiveList.setDisable(false);
                assignmentList.setStyle("-fx-background-color: #95A5A6; -fx-border-color: #95A5A6;");
                //assignmentList.setDisable(false);
                raise(new ListPickerSelectionChangedEvent(1));
            }
        });
        // Assignment button is selected
        assignmentList.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                assignmentList.setStyle("-fx-border-color: #28B463; -fx-background-color: #28B463;");
                //assignmentList.setDisable(true);
                archiveList.setStyle("-fx-background-color: #95A5A6; -fx-border-color: #95A5A6;");
                //archiveList.setDisable(false);
                activeList.setStyle("-fx-background-color: #95A5A6; -fx-border-color: #95A5A6;");
                //activeList.setDisable(false);
                raise(new ListPickerSelectionChangedEvent(3));
            }
        });
        registerAsAnEventHandler(this);
    }

    public void setArchiveList() {
        archiveList.setStyle("-fx-background-color: #28B463; -fx-border-color: #28B463;");
        activeList.setStyle("-fx-background-color: #95A5A6; -fx-border-color: #95A5A6;");
        assignmentList.setStyle("-fx-background-color: #95A5A6; -fx-border-color: #95A5A6;");
    }

    public void setActiveList() {
        activeList.setStyle("-fx-border-color: #28B463; -fx-background-color: #28B463;");
        archiveList.setStyle("-fx-background-color: #95A5A6; -fx-border-color: #95A5A6;");
        assignmentList.setStyle("-fx-background-color: #95A5A6; -fx-border-color: #95A5A6;");
    }
}

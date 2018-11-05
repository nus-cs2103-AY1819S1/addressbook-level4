package seedu.address.ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.calendarevent.CalendarEvent;

import java.util.logging.Logger;

public class CalendarEventDialog extends UiPart<Region> {

    private static final String FXML = "CalendarEventDialog.fxml";
    private final Logger logger = LogsCenter.getLogger(CommandBox.class);

    private CalendarEvent calendarEvent;

    @FXML
    private TextField tfId;

    @FXML
    private TextField tfName;

    @FXML
    private TextField tfAge;

    public CalendarEventDialog(CalendarEvent calendarEvent) {
        super(FXML);

        this.calendarEvent = calendarEvent;
    }

    @FXML
    public void onOkButtonClicked(ActionEvent event) {
        System.out.println("huehuehue");

        closeStage(event);
    }

    private void closeStage(javafx.event.ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage stage  = (Stage) source.getScene().getWindow();
        stage.close();
    }
}

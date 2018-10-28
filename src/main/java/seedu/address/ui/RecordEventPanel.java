package seedu.address.ui;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.RecordChangeEvent;
import seedu.address.model.record.Record;
import seedu.address.model.volunteer.Volunteer;

/**
 * Panel containing the list of volunteers.
 */
public class RecordEventPanel extends UiPart<Region> {
    private static final String FXML = "RecordEventPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(RecordEventPanel.class);

    @FXML
    private Label eventNameLabel;
    @FXML
    private Label numOfVolunteersLabel;
    @FXML
    private TableView<Record> volunteerRecordTableView;
    @FXML
    private TableColumn<String, Integer> indexColumn;
    @FXML
    private TableColumn<Record, String> nameColumn;
    @FXML
    private TableColumn<Record, String> numberColumn;
    @FXML
    private TableColumn<Record, String> hourColumn;
    @FXML
    private TableColumn<Record, String> remarkColumn;


    private ObservableList<Volunteer> volunteerList;
    private ObservableList<Record> recordList;


    public RecordEventPanel(ObservableList<Record> recordList, ObservableList<Volunteer> volunteerList) {
        super(FXML);
        this.recordList = recordList;
        this.volunteerList = volunteerList;

        mapVolunteerToRecord();
        setConnections();
        registerAsAnEventHandler(this);
    }

    @Subscribe
    private void handleRecordChangeEvent(RecordChangeEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        eventNameLabel.setText(event.getCurrentEvent().getName().fullName);
        numOfVolunteersLabel.setText(String.valueOf(recordList.size()));

        mapVolunteerToRecord();
    }

    /**
     * Map volunteers to record.
     */
    private void mapVolunteerToRecord() {
        for (int i = 0; i < recordList.size(); i++) {
            for (int j = 0; j < volunteerList.size(); j++) {
                if (recordList.get(i).getVolunteerId().id == volunteerList.get(j).getVolunteerId().id) {
                    recordList.get(i).setLocalIndex(i + 1);
                    recordList.get(i).setVolunteerName(volunteerList.get(j).getName().fullName);
                    recordList.get(i).setPhoneNo(volunteerList.get(j).getPhone().value);
                    break;
                }
            }
        }
    }

    private void setConnections() {
        indexColumn.setCellValueFactory(new PropertyValueFactory<>("localIndex"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("volunteerName"));
        numberColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNo"));
        hourColumn.setCellValueFactory(new PropertyValueFactory<>("hour"));
        remarkColumn.setCellValueFactory(new PropertyValueFactory<>("remark"));

        volunteerRecordTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        volunteerRecordTableView.setItems(recordList);
    }
}

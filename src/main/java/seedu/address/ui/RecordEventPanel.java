package seedu.address.ui;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.ObjectBinding;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.RecordChangeEvent;
import seedu.address.model.person.Person;
import seedu.address.model.record.Record;

/**
 * Panel containing the list of persons.
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


    private ObservableList<Person> volunteerList;
    private ObservableList<Record> recordList;


    public RecordEventPanel(ObservableList<Record> recordList, ObservableList<Person> personList) {
        super(FXML);
        this.recordList = recordList;
        this.volunteerList = personList;

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
                if (recordList.get(i).getVolunteerId().id == volunteerList.get(j).getPersonId().id) {
                    recordList.get(i).setVolunteerName(volunteerList.get(j).getName().fullName);
                    recordList.get(i).setPhoneNo(volunteerList.get(j).getPhone().value);
                    break;
                }
            }
        }
    }

    private void setConnections() {
        indexColumn.setCellFactory(col -> {
            TableCell<String, Integer> indexCell = new TableCell<>();
            ReadOnlyObjectProperty<TableRow<String>> rowProperty = indexCell.tableRowProperty();
            ObjectBinding<String> rowBinding = Bindings.createObjectBinding(() -> {
                TableRow<String> row = rowProperty.get();
                if (row != null) {
                    int rowIndex = row.getIndex();
                    if (rowIndex < row.getTableView().getItems().size()) {
                        return Integer.toString(rowIndex + 1);
                    }
                }
                return null;
            }, rowProperty);
            indexCell.textProperty().bind(rowBinding);
            return indexCell;
        });
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("volunteerName"));
        numberColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNo"));
        hourColumn.setCellValueFactory(new PropertyValueFactory<>("hour"));
        remarkColumn.setCellValueFactory(new PropertyValueFactory<>("remark"));

        volunteerRecordTableView.setItems(recordList);
    }
}

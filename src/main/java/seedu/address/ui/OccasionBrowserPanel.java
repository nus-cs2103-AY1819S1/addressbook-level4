package seedu.address.ui;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.OccasionPanelSelectionChangedEvent;
import seedu.address.model.person.Person;

/**
 * The browser panel for when the UI switches to the Occasion's tab.
 * The panel displays the attendance list of a particular occasion upon the user's
 * selection.
 */
public class OccasionBrowserPanel extends UiPart<Region> {

    private static final String FXML = "OccasionBrowserPanel.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());

    @FXML
    private TableView<Person> personTableView;

    @FXML
    private TableColumn<Person, String> nameStringTableColumn;

    public OccasionBrowserPanel(ObservableList<Person> personList) {
        super(FXML);

        // To prevent triggering events for typing inside the loaded Web page.
        getRoot().setOnKeyPressed(Event::consume);

        loadTable(personList);
        registerAsAnEventHandler(this);
    }

    private void loadTable(ObservableList<Person> newAttendanceList) {
        personTableView.setItems(newAttendanceList);
        setCellFactories();
    }

    private void setCellFactories() {
        nameStringTableColumn.setCellValueFactory(new PropertyValueFactory<>("fullName"));
    }

    @Subscribe
    private void handleModulePanelSelectionChangedEvent(OccasionPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        loadTable(event.getNewSelection().getAttendanceList().asUnmodifiableObservableList());
    }
}

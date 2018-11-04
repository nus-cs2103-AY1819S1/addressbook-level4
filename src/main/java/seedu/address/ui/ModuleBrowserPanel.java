package seedu.address.ui;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.ModulePanelSelectionChangedEvent;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;

public class ModuleBrowserPanel extends UiPart<Region> {

    private static final String FXML = "ModuleBrowserPanel.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());

    @FXML
    private TableView<Person> personTableView;

    @FXML
    private TableColumn<Person, String> nameStringTableColumn;

    public ModuleBrowserPanel(ObservableList<Person> personList) {
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

    // TODO must handle the events where there are updates to this list.

    @Subscribe
    private void handleModulePanelSelectionChangedEvent(ModulePanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        loadTable(event.getNewSelection().getStudents().asUnmodifiableObservableList());
    }
}

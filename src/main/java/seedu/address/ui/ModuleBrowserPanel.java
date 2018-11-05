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
import seedu.address.commons.events.ui.ModulePanelSelectionChangedEvent;
import seedu.address.model.person.Person;

/**
 * The browser panel for when the UI switches to the Module Tab.
 * Shows the student list for each module upon a particular module's selection.
 */
public class ModuleBrowserPanel extends UiPart<Region> {

    private static final String FXML = "ModuleBrowserPanel.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());

    @FXML
    private TableView<Person> personTableView;

    @FXML
    private TableColumn<Person, String> nameStringTableColumn;

    public ModuleBrowserPanel(ObservableList<Person> studentList) {
        super(FXML);

        // To prevent triggering events for typing inside the loaded Web page.
        getRoot().setOnKeyPressed(Event::consume);

        loadTable(studentList);
        registerAsAnEventHandler(this);
    }

    private void loadTable(ObservableList<Person> newStudentList) {
        personTableView.setItems(newStudentList);
        setCellFactories();
    }

    private void setCellFactories() {
        nameStringTableColumn.setCellValueFactory(new PropertyValueFactory<>("fullName"));
    }

    @Subscribe
    private void handleModulePanelSelectionChangedEvent(ModulePanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        loadTable(event.getNewSelection().getStudents().asUnmodifiableObservableList());
    }
}

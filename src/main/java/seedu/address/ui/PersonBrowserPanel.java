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
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.model.module.Module;
import seedu.address.model.occasion.Occasion;
import seedu.address.model.person.Person;

/**
 * The person browser panel for when the UI switches to the persons tab.
 * The panel displays all the registered modules and occasions a particular person
 * within the addressbook is associated with upon the user's selection.
 */
public class PersonBrowserPanel extends UiPart<Region> {

    private static final String FXML = "PersonBrowserPanel.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());

    @FXML
    private TableView<Occasion> occasionTableView;

    @FXML
    private TableView<Module> moduleTableView;

    @FXML
    private TableColumn<Occasion, String> occasionNameStringTableColumn;

    @FXML
    private TableColumn<Module, String> moduleCodeStringTableColumn;

    @FXML
    private TableColumn<Module, String> moduleTitleStringTableColumn;

    public PersonBrowserPanel(ObservableList<Module> moduleList, ObservableList<Occasion> occasionList) {
        super(FXML);

        // To prevent triggering events for typing inside the loaded Web page.
        getRoot().setOnKeyPressed(Event::consume);

        loadTable(moduleList, occasionList);
        registerAsAnEventHandler(this);
    }

    private void loadTable(ObservableList<Module> moduleList, ObservableList<Occasion> occasionList) {
        occasionTableView.setItems(occasionList);
        moduleTableView.setItems(moduleList);
        setCellFactories();
    }

    private void setCellFactories() {
        occasionNameStringTableColumn.setCellValueFactory(new PropertyValueFactory<>("occasionName"));
        moduleCodeStringTableColumn.setCellValueFactory(new PropertyValueFactory<>("moduleCode"));
        moduleTitleStringTableColumn.setCellValueFactory(new PropertyValueFactory<>("moduleTitle"));
    }

    @Subscribe
    private void handleModulePanelSelectionChangedEvent(PersonPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        Person nextPersonSelected = event.getNewSelection();
        loadTable(nextPersonSelected.getModuleList().asUnmodifiableObservableList(),
                    nextPersonSelected.getOccasionList().asUnmodifiableObservableList());
    }
}

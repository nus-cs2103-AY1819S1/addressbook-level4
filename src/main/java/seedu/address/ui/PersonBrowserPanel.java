package seedu.address.ui;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.model.module.Module;
import seedu.address.model.module.ModuleCode;
import seedu.address.model.module.ModuleTitle;
import seedu.address.model.occasion.Occasion;
import seedu.address.model.occasion.OccasionName;
import seedu.address.model.person.Person;

public class PersonBrowserPanel extends UiPart<Region> {

    private static final String FXML = "PersonBrowserPanel.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());

    @FXML
    private TableView<Occasion> occasionTableView;

    @FXML
    private TableView<Module> moduleTableView;

    @FXML
    private TableColumn<OccasionName, String> occasionNameStringTableColumn;

    @FXML
    private TableColumn<ModuleCode, String> moduleCodeStringTableColumn;

    @FXML
    private TableColumn<ModuleTitle, String> moduleTitleStringTableColumn;

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
        occasionNameStringTableColumn.setCellValueFactory((value) -> new SimpleStringProperty(value.toString()));
        moduleCodeStringTableColumn.setCellValueFactory((value) -> new SimpleStringProperty(value.toString()));
        moduleTitleStringTableColumn.setCellValueFactory((value) -> new SimpleStringProperty(value.toString()));
    }

    // TODO must handle the events where there are updates to this list.

    @Subscribe
    private void handleModulePanelSelectionChangedEvent(PersonPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        Person nextPersonSelected = event.getNewSelection();
        loadTable(nextPersonSelected.getModuleList().asUnmodifiableObservableList(),
                    nextPersonSelected.getOccasionList().asUnmodifiableObservableList());
    }
}

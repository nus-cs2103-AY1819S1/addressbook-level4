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
import seedu.address.commons.events.ui.JumpToModuleListRequestEvent;
import seedu.address.commons.events.ui.ModuleBrowserChangeEvent;
import seedu.address.commons.events.ui.ModulePanelSelectionChangedEvent;
import seedu.address.commons.events.ui.RefreshModuleBrowserEvent;
import seedu.address.model.module.Module;

/**
 * Panel containing the list of modules.
 *
 * @author alistair
 */
public class ModuleListPanel extends UiPart<Region> {
    private static final String FXML = "ModuleListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(ModuleListPanel.class);

    @FXML
    private ListView<Module> moduleListView;

    public ModuleListPanel(ObservableList<Module> moduleList) {
        super(FXML);

        setConnections(moduleList);

        registerAsAnEventHandler(this);
    }

    /**
     * Clears selection of the view.
     */
    public void clearSelection() {
        Platform.runLater(() -> {
            moduleListView.getSelectionModel().clearSelection();
        });
    }

    /**
     * Change list of view.
     * @param moduleList updated list.
     */
    public void updatePanel(ObservableList<Module> moduleList) {
        moduleListView.setItems(moduleList);
        moduleListView.setCellFactory(listView -> new ModuleListViewCell());
    }

    private void setConnections(ObservableList<Module> moduleList) {
        moduleListView.setItems(moduleList);
        moduleListView.setCellFactory(listView -> new ModuleListViewCell());
        setEventHandlerForSelectionChangeEvent();
    }

    private void setEventHandlerForSelectionChangeEvent() {
        moduleListView.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        logger.fine("Selection in module list panel changed to : '" + newValue + "'");
                        raise(new ModulePanelSelectionChangedEvent(newValue));
                    }
                });
    }

    private boolean isListSelected() {
        return moduleListView.getSelectionModel().getSelectedIndex() > -1;
    }

    @Subscribe
    private void handleModuleBrowserChangeEvent(RefreshModuleBrowserEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        if (isListSelected()) {
            int index = moduleListView.getSelectionModel().getSelectedIndex();
            moduleListView.getSelectionModel().clearSelection();
            moduleListView.getSelectionModel().clearAndSelect(index);
            Module currModule = moduleListView.getSelectionModel().getSelectedItem();
            raise(new ModuleBrowserChangeEvent(currModule));
        }
    }

    /**
     * Scrolls to the {@code ModuleCard} at the {@code index} and selects it.
     */
    private void scrollTo(int index) {
        Platform.runLater(() -> {
            moduleListView.scrollTo(index);
            moduleListView.getSelectionModel().clearAndSelect(index);
        });
    }

    @Subscribe
    private void handleJumpToListRequestEvent(JumpToModuleListRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        scrollTo(event.targetIndex);
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Module} using a {@code ModuleCard}.
     */
    class ModuleListViewCell extends ListCell<Module> {
        @Override
        protected void updateItem(Module module, boolean empty) {
            super.updateItem(module, empty);

            if (empty || module == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new ModuleCard(module, getIndex() + 1).getRoot());
            }
        }
    }

}

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
import seedu.address.commons.events.ui.JumpToListRequestEvent;
import seedu.address.model.medicine.Medicine;

/**
 * Panel containing the list of medicines.
 */
public class MedicineListPanel extends UiPart<Region> {

    private static final String FXML = "MedicineListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(MedicineListPanel.class);

    @FXML
    private ListView<Medicine> medicineListView;

    public MedicineListPanel(ObservableList<Medicine> medicineList) {
        super(FXML);
        setConnections(medicineList);
        registerAsAnEventHandler(this);
    }

    private void setConnections(ObservableList<Medicine> medicineList) {
        medicineListView.setItems(medicineList);
        medicineListView.setCellFactory(listView -> new MedicineListPanel.MedicineListViewCell());
        setEventHandlerForSelectionChangeEvent();
    }

    private void setEventHandlerForSelectionChangeEvent() {
        medicineListView.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        logger.fine("Selection in medicine list panel changed to : '" + newValue + "'");
                        // raise(new PersonPanelSelectionChangedEvent(newValue)); <-- [old code]
                    }
                });
    }

    /**
     * Scrolls to the {@code PersonCard} at the {@code index} and selects it.
     */
    private void scrollTo(int index) {
        Platform.runLater(() -> {
            medicineListView.scrollTo(index);
            medicineListView.getSelectionModel().clearAndSelect(index);
        });
    }

    @Subscribe
    private void handleJumpToListRequestEvent(JumpToListRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        scrollTo(event.targetIndex);
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Patient} using a {@code PersonCard}.
     */
    class MedicineListViewCell extends ListCell<Medicine> {
        @Override
        protected void updateItem(Medicine medicine, boolean empty) {
            super.updateItem(medicine, empty);

            if (empty || medicine == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new MedicineCard(medicine, getIndex() + 1).getRoot());
            }
        }
    }

}

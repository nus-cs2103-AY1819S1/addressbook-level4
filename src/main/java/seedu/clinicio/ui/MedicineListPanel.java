package seedu.clinicio.ui;

//@@author aaronseahyh

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.clinicio.commons.core.LogsCenter;
import seedu.clinicio.commons.events.ui.JumpToListRequestEvent;
import seedu.clinicio.commons.events.ui.MedicinePanelSelectionChangedEvent;
import seedu.clinicio.model.medicine.Medicine;

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
        medicineListView.setCellFactory(listView -> new MedicineListViewCell());
        setEventHandlerForSelectionChangeEvent();
    }

    private void setEventHandlerForSelectionChangeEvent() {
        medicineListView.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        logger.fine("Selection in medicine list panel changed to : '" + newValue + "'");
                        raise(new MedicinePanelSelectionChangedEvent(newValue));
                    }
                });
    }

    /**
     * Scrolls to the {@code MedicineCard} at the {@code index} and selects it.
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
     * Custom {@code ListCell} that displays the graphics of a {@code Medicine} using a {@code MedicineCard}.
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

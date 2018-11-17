package guitests.guihandles;

//@@author aaronseahyh

import java.util.List;
import java.util.Optional;
import java.util.Set;

import javafx.scene.Node;
import javafx.scene.control.ListView;
import seedu.clinicio.model.medicine.Medicine;

/**
 * Provides a handle for {@code MedicineListPanel} containing the list of {@code MedicineCard}.
 */
public class MedicineListPanelHandle extends NodeHandle<ListView<Medicine>> {

    public static final String MEDICINE_LIST_VIEW_ID = "#medicineListView";

    private static final String CARD_PANE_ID = "#cardPane";

    private Optional<Medicine> lastRememberedSelectedMedicineCard;

    public MedicineListPanelHandle(ListView<Medicine> medicineListPanelNode) {
        super(medicineListPanelNode);
    }

    /**
     * Returns a handle to the selected {@code DedicineCardHandle}.
     * A maximum of 1 item can be selected at any time.
     * @throws AssertionError if no card is selected, or more than 1 card is selected.
     * @throws IllegalStateException if the selected card is currently not in the scene graph.
     */
    public MedicineCardHandle getHandleToSelectedCard() {
        List<Medicine> selectedMedicineList = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedMedicineList.size() != 1) {
            throw new AssertionError("Medicine list size expected 1.");
        }

        return getAllCardNodes().stream()
                .map(MedicineCardHandle::new)
                .filter(handle -> handle.equals(selectedMedicineList.get(0)))
                .findFirst()
                .orElseThrow(IllegalStateException::new);
    }

    /**
     * Returns the index of the selected card.
     */
    public int getSelectedCardIndex() {
        return getRootNode().getSelectionModel().getSelectedIndex();
    }

    /**
     * Returns true if a card is currently selected.
     */
    public boolean isAnyCardSelected() {
        List<Medicine> selectedCardsList = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedCardsList.size() > 1) {
            throw new AssertionError("Card list size expected 0 or 1.");
        }

        return !selectedCardsList.isEmpty();
    }

    /**
     * Navigates the listview to display {@code medicine}.
     */
    public void navigateToCard(Medicine medicine) {
        if (!getRootNode().getItems().contains(medicine)) {
            throw new IllegalArgumentException("Medicine does not exist.");
        }

        guiRobot.interact(() -> {
            getRootNode().scrollTo(medicine);
        });
        guiRobot.pauseForHuman();
    }

    /**
     * Navigates the listview to {@code index}.
     */
    public void navigateToCard(int index) {
        if (index < 0 || index >= getRootNode().getItems().size()) {
            throw new IllegalArgumentException("Index is out of bounds.");
        }

        guiRobot.interact(() -> {
            getRootNode().scrollTo(index);
        });
        guiRobot.pauseForHuman();
    }

    /**
     * Selects the {@code MedicineCard} at {@code index} in the list.
     */
    public void select(int index) {
        getRootNode().getSelectionModel().select(index);
    }

    /**
     * Returns the medicine card handle of a medicine associated with the {@code index} in the list.
     * @throws IllegalStateException if the selected card is currently not in the scene graph.
     */
    public MedicineCardHandle getMedicineCardHandle(int index) {
        return getAllCardNodes().stream()
                .map(MedicineCardHandle::new)
                .filter(handle -> handle.equals(getMedicine(index)))
                .findFirst()
                .orElseThrow(IllegalStateException::new);
    }

    private Medicine getMedicine(int index) {
        return getRootNode().getItems().get(index);
    }

    /**
     * Returns all card nodes in the scene graph.
     * Card nodes that are visible in the listview are definitely in the scene graph, while some nodes that are not
     * visible in the listview may also be in the scene graph.
     */
    private Set<Node> getAllCardNodes() {
        return guiRobot.lookup(CARD_PANE_ID).queryAll();
    }

    /**
     * Remembers the selected {@code MedicineCard} in the list.
     */
    public void rememberSelectedMedicineCard() {
        List<Medicine> selectedItems = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedItems.size() == 0) {
            lastRememberedSelectedMedicineCard = Optional.empty();
        } else {
            lastRememberedSelectedMedicineCard = Optional.of(selectedItems.get(0));
        }
    }

    /**
     * Returns true if the selected {@code MedicineCard} is different from the value remembered by the most recent
     * {@code rememberSelectedMedicineCard()} call.
     */
    public boolean isSelectedMedicineCardChanged() {
        List<Medicine> selectedItems = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedItems.size() == 0) {
            return lastRememberedSelectedMedicineCard.isPresent();
        } else {
            return !lastRememberedSelectedMedicineCard.isPresent()
                    || !lastRememberedSelectedMedicineCard.get().equals(selectedItems.get(0));
        }
    }

    /**
     * Returns the size of the list.
     */
    public int getListSize() {
        return getRootNode().getItems().size();
    }

}

package guitests.guihandles;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import javafx.scene.Node;
import javafx.scene.control.ListView;
import seedu.parking.model.carpark.Carpark;

/**
 * Provides a handle for {@code CarparkListPanel} containing the list of {@code CarparkCard}.
 */
public class CarparkListPanelHandle extends NodeHandle<ListView<Carpark>> {
    public static final String CARPARK_LIST_VIEW_ID = "#carparkListView";

    private static final String CARD_PANE_ID = "#cardPane";

    private Optional<Carpark> lastRememberedSelectedCarparkCard;

    public CarparkListPanelHandle(ListView<Carpark> carparkListPanelNode) {
        super(carparkListPanelNode);
    }

    /**
     * Returns a handle to the selected {@code CarparkCardHandle}.
     * A maximum of 1 item can be selected at any time.
     * @throws AssertionError if no card is selected, or more than 1 card is selected.
     * @throws IllegalStateException if the selected card is currently not in the scene graph.
     */
    public CarparkCardHandle getHandleToSelectedCard() {
        List<Carpark> selectedCarparkList = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedCarparkList.size() != 1) {
            throw new AssertionError("Car park list size expected 1.");
        }

        return getAllCardNodes().stream()
                .map(CarparkCardHandle::new)
                .filter(handle -> handle.equals(selectedCarparkList.get(0)))
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
        List<Carpark> selectedCardsList = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedCardsList.size() > 1) {
            throw new AssertionError("Card list size expected 0 or 1.");
        }

        return !selectedCardsList.isEmpty();
    }

    /**
     * Navigates the listview to display {@code carpark}.
     */
    public void navigateToCard(Carpark carpark) {
        if (!getRootNode().getItems().contains(carpark)) {
            throw new IllegalArgumentException("Car park does not exist.");
        }

        guiRobot.interact(() -> {
            getRootNode().scrollTo(carpark);
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
     * Selects the {@code CarparkCard} at {@code index} in the list.
     */
    public void select(int index) {
        getRootNode().getSelectionModel().select(index);
    }

    /**
     * Returns the car park card handle of a car park associated with the {@code index} in the list.
     * @throws IllegalStateException if the selected card is currently not in the scene graph.
     */
    public CarparkCardHandle getCarparkCardHandle(int index) {
        return getAllCardNodes().stream()
                .map(CarparkCardHandle::new)
                .filter(handle -> handle.equals(getCarpark(index)))
                .findFirst()
                .orElseThrow(IllegalStateException::new);
    }

    private Carpark getCarpark(int index) {
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
     * Remembers the selected {@code CarparkCard} in the list.
     */
    public void rememberSelectedCarparkCard() {
        List<Carpark> selectedItems = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedItems.size() == 0) {
            lastRememberedSelectedCarparkCard = Optional.empty();
        } else {
            lastRememberedSelectedCarparkCard = Optional.of(selectedItems.get(0));
        }
    }

    /**
     * Returns true if the selected {@code CarparkCard} is different from the value remembered by the most recent
     * {@code rememberSelectedCarparkCard()} call.
     */
    public boolean isSelectedCarparkCardChanged() {
        List<Carpark> selectedItems = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedItems.size() == 0) {
            return lastRememberedSelectedCarparkCard.isPresent();
        } else {
            return !lastRememberedSelectedCarparkCard.isPresent()
                    || !lastRememberedSelectedCarparkCard.get().equals(selectedItems.get(0));
        }
    }

    /**
     * Returns the size of the list.
     */
    public int getListSize() {
        return getRootNode().getItems().size();
    }
}

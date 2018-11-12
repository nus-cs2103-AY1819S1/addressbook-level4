package guitests.guihandles;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import javafx.scene.Node;
import javafx.scene.control.ListView;
import seedu.address.model.cca.Cca;

//@@author ericyjw

/**
 * Provides a handle for {@code CcaListPanel} containing the list of {@code CcaCard}.
 */
public class CcaListPanelHandle extends NodeHandle<ListView<Cca>> {
    public static final String CCA_LIST_VIEW_ID = "#ccaListView";

    private static final String CARD_PANE_ID = "#cardPane";

    private Optional<Cca> lastRememberedSelectedCcaCard;

    public CcaListPanelHandle(ListView<Cca> ccaListPanelNode) {
        super(ccaListPanelNode);
    }

    /**
     * Returns a handle to the selected {@code CcaCardHandle}.
     * A maximum of 1 item can be selected at any time.
     *
     * @throws AssertionError if no card is selected, or more than 1 card is selected.
     * @throws IllegalStateException if the selected card is currently not in the scene graph.
     */
    public CcaCardHandle getHandleToSelectedCard() {
        List<Cca> selectedCcaList = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedCcaList.size() != 1) {
            throw new AssertionError("Cca list size expected 1.");
        }

        return getAllCardNodes().stream()
            .map(CcaCardHandle::new)
            .filter(handle -> handle.equals(selectedCcaList.get(0)))
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
        List<Cca> selectedCardsList = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedCardsList.size() > 1) {
            throw new AssertionError("Card list size expected 0 or 1.");
        }

        return !selectedCardsList.isEmpty();
    }

    /**
     * Navigates the listview to display {@code cca}.
     */
    public void navigateToCard(Cca cca) {
        if (!getRootNode().getItems().contains(cca)) {
            throw new IllegalArgumentException("Cca does not exist.");
        }

        guiRobot.interact(() -> {
            getRootNode().scrollTo(cca);
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
     * Selects the {@code CcaCard} at {@code index} in the list.
     */
    public void select(int index) {
        getRootNode().getSelectionModel().select(index);
    }

    /**
     * Returns the cca card handle of a cca associated with the {@code index} in the list.
     *
     * @throws IllegalStateException if the selected card is currently not in the scene graph.
     */
    public CcaCardHandle getCcaCardHandle(int index) {
        return getAllCardNodes().stream()
            .map(CcaCardHandle::new)
            .filter(handle -> handle.equals(getCca(index)))
            .findFirst()
            .orElseThrow(IllegalStateException::new);
    }

    private Cca getCca(int index) {
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
     * Remembers the selected {@code CcaCard} in the list.
     */
    public void rememberSelectedCcaCard() {
        List<Cca> selectedItems = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedItems.size() == 0) {
            lastRememberedSelectedCcaCard = Optional.empty();
        } else {
            lastRememberedSelectedCcaCard = Optional.of(selectedItems.get(0));
        }
    }

    /**
     * Returns true if the selected {@code CcaCard} is different from the value remembered by the most recent
     * {@code rememberSelectedCcaCard()} call.
     */
    public boolean isSelectedCcaCardChanged() {
        List<Cca> selectedItems = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedItems.size() == 0) {
            return lastRememberedSelectedCcaCard.isPresent();
        } else {
            return !lastRememberedSelectedCcaCard.isPresent()
                || !lastRememberedSelectedCcaCard.get().equals(selectedItems.get(0));
        }
    }

    /**
     * Returns the size of the list.
     */
    public int getListSize() {
        return getRootNode().getItems().size();
    }
}

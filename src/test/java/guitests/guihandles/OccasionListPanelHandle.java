package guitests.guihandles;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import javafx.scene.Node;
import javafx.scene.control.ListView;
import seedu.address.model.occasion.Occasion;

/**
 * Represents a List of OccasionCards within this addressbook.
 */
public class OccasionListPanelHandle extends NodeHandle<ListView<Occasion>> {
    public static final String OCCASION_LIST_VIEW_ID = "#occasionListView";

    private static final String CARD_PANE_ID = "#cardPane";

    private Optional<Occasion> lastRememberedSelectedOccasionCard;

    public OccasionListPanelHandle(ListView<Occasion> occasionListPanelNode) {
        super(occasionListPanelNode);
    }

    /**
     * Returns a handle to the selected {@code OccasionCardHandle}.
     * A maximum of 1 item can be selected at any time.
     * @throws AssertionError if no card is selected, or more than 1 card is selected.
     * @throws IllegalStateException if the selected card is currently not in the scene graph.
     */
    public OccasionCardHandle getHandleToSelectedCard() {
        List<Occasion> selectedOccasionList = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedOccasionList.size() != 1) {
            throw new AssertionError("Occasion list size expected 1.");
        }

        return getAllCardNodes().stream()
                .map(OccasionCardHandle::new)
                .filter(handle -> handle.equals(selectedOccasionList.get(0)))
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
        List<Occasion> selectedCardsList = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedCardsList.size() > 1) {
            throw new AssertionError("Card list size expected 0 or 1.");
        }

        return !selectedCardsList.isEmpty();
    }

    /**
     * Navigates the listview to display {@code occasion}.
     */
    public void navigateToCard(Occasion occasion) {
        if (!getRootNode().getItems().contains(occasion)) {
            throw new IllegalArgumentException("Occasion does not exist.");
        }

        guiRobot.interact(() -> {
            getRootNode().scrollTo(occasion);
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
     * Selects the {@code OccasionCard} at {@code index} in the list.
     */
    public void select(int index) {
        getRootNode().getSelectionModel().select(index);
    }

    /**
     * Returns the occasion card handle of an occasion associated with the {@code index} in the list.
     * @throws IllegalStateException if the selected card is currently not in the scene graph.
     */
    public OccasionCardHandle getOccasionCardHandle(int index) {
        return getAllCardNodes().stream()
                .map(OccasionCardHandle::new)
                .filter(handle -> handle.equals(getOccasion(index)))
                .findFirst()
                .orElseThrow(IllegalStateException::new);
    }

    private Occasion getOccasion(int index) {
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
     * Remembers the selected {@code OccasionCard} in the list.
     */
    public void rememberSelectedOccasionCard() {
        List<Occasion> selectedItems = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedItems.size() == 0) {
            lastRememberedSelectedOccasionCard = Optional.empty();
        } else {
            lastRememberedSelectedOccasionCard = Optional.of(selectedItems.get(0));
        }
    }

    /**
     * Returns true if the selected {@code OccasionCard} is different from the value remembered by the most recent
     * {@code rememberSelectedOccasionCard()} call.
     */
    public boolean isSelectedOccasionCardChanged() {
        List<Occasion> selectedItems = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedItems.size() == 0) {
            return lastRememberedSelectedOccasionCard.isPresent();
        } else {
            return !lastRememberedSelectedOccasionCard.isPresent()
                    || !lastRememberedSelectedOccasionCard.get().equals(selectedItems.get(0));
        }
    }

    /**
     * Returns the size of the list.
     */
    public int getListSize() {
        return getRootNode().getItems().size();
    }
}

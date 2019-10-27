package guitests.guihandles;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import javafx.scene.Node;
import javafx.scene.control.ListView;
import seedu.address.model.entry.ResumeEntry;


/**
 * Provides a handle for {@code EntryListPanel} containing the list of {@code EntryCard}.
 */
public class EntryListPanelHandle extends NodeHandle<ListView<ResumeEntry>> {

    public static final String ENTRY_LIST_VIEW_ID = "#entryListView";

    private static final String CARD_PANE_ID = "#cardPane";

    private Optional<ResumeEntry> lastRememberedSelectedEntryCard;

    public EntryListPanelHandle(ListView<ResumeEntry> entryListPanelNode) {
        super(entryListPanelNode);
    }

    /**
     * Returns a handle to the selected {@code EntryCardHandle}.
     * A maximum of 1 item can be selected at any time.
     * @throws AssertionError if no card is selected, or more than 1 card is selected.
     * @throws IllegalStateException if the selected card is currently not in the scene graph.
     */
    public EntryCardHandle getHandleToSelectedCard() {
        List<ResumeEntry> selectedEntryList = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedEntryList.size() != 1) {
            throw new AssertionError("Entry list size expected 1.");
        }

        return getAllCardNodes().stream()
                .map(EntryCardHandle::new)
                .filter(handle -> handle.equals(selectedEntryList.get(0)))
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
        List<ResumeEntry> selectedCardsList = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedCardsList.size() > 1) {
            throw new AssertionError("Card list size expected 0 or 1.");
        }

        return !selectedCardsList.isEmpty();
    }

    /**
     * Navigates the listview to display {@code person}.
     */
    public void navigateToCard(ResumeEntry entry) {
        if (!getRootNode().getItems().contains(entry)) {
            throw new IllegalArgumentException("Entry does not exist.");
        }

        guiRobot.interact(() -> {
            getRootNode().scrollTo(entry);
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
     * Selects the {@code PersonCard} at {@code index} in the list.
     */
    public void select(int index) {
        getRootNode().getSelectionModel().select(index);
    }

    /**
     * Returns the entry card handle of an entry associated with the {@code index} in the list.
     * @throws IllegalStateException if the selected card is currently not in the scene graph.
     */
    public EntryCardHandle getEntryCardHandle(int index) {
        return getAllCardNodes().stream()
                .map(EntryCardHandle::new)
                .filter(handle -> handle.equals(getEntry(index)))
                .findFirst()
                .orElseThrow(IllegalStateException::new);
    }

    private ResumeEntry getEntry(int index) {
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
     * Remembers the selected {@code entryCard} in the list.
     */
    public void rememberSelectedEntryCard() {
        List<ResumeEntry> selectedItems = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedItems.size() == 0) {
            lastRememberedSelectedEntryCard = Optional.empty();
        } else {
            lastRememberedSelectedEntryCard = Optional.of(selectedItems.get(0));
        }
    }

    /**
     * Returns true if the selected {@code PersonCard} is different from the value remembered by the most recent
     * {@code rememberSelectedPersonCard()} call.
     */
    public boolean isSelectedEntryCardChanged() {
        List<ResumeEntry> selectedItems = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedItems.size() == 0) {
            return lastRememberedSelectedEntryCard.isPresent();
        } else {
            return !lastRememberedSelectedEntryCard.isPresent()
                    || !lastRememberedSelectedEntryCard.get().equals(selectedItems.get(0));
        }
    }

    /**
     * Returns the size of the list.
     */
    public int getListSize() {
        return getRootNode().getItems().size();
    }

}


package guitests.guihandles;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import javafx.scene.Node;
import javafx.scene.control.ListView;
import seedu.address.model.card.Card;

/**
 * Provides a handle for {@code CardListPanel} containing the list of {@code CardView}.
 */
public class CardListPanelHandle extends NodeHandle<ListView<Card>> {
    public static final String CARD_LIST_VIEW_ID = "#cardListView";

    private static final String CARD_PANE_ID = "#cardViewPane";

    private Optional<Card> lastRememberedSelectedCardView;

    public CardListPanelHandle(ListView<Card> cardListPanelNode) {
        super(cardListPanelNode);
    }

    /**
     * Returns a handle to the selected {@code CardViewHandle}.
     * A maximum of 1 item can be selected at any time.
     * @throws AssertionError if no card is selected, or more than 1 card is selected.
     * @throws IllegalStateException if the selected card is currently not in the scene graph.
     */
    public CardViewHandle getHandleToSelectedCard() {
        List<Card> selectedCardList = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedCardList.size() != 1) {
            throw new AssertionError("Card list size expected 1.");
        }

        return getAllCardNodes().stream()
                .map(CardViewHandle::new)
                .filter(handle -> handle.equals(selectedCardList.get(0)))
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
        List<Card> selectedCardsList = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedCardsList.size() > 1) {
            throw new AssertionError("Card list size expected 0 or 1.");
        }

        return !selectedCardsList.isEmpty();
    }

    /**
     * Navigates the listview to display {@code person}.
     */
    public void navigateToCard(Card card) {
        if (!getRootNode().getItems().contains(card)) {
            throw new IllegalArgumentException("Card does not exist.");
        }

        guiRobot.interact(() -> {
            getRootNode().scrollTo(card);
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
     * Selects the {@code CardView} at {@code index} in the list.
     */
    public void select(int index) {
        getRootNode().getSelectionModel().select(index);
    }

    /**
     * Returns the card view handle of a card associated with the {@code index} in the list.
     * @throws IllegalStateException if the selected card is currently not in the scene graph.
     */
    public CardViewHandle getCardViewHandle(int index) {
        return getAllCardNodes().stream()
                .map(CardViewHandle::new)
                .filter(handle -> handle.equals(getCard(index)))
                .findFirst()
                .orElseThrow(IllegalStateException::new);
    }

    private Card getCard(int index) {
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
     * Remembers the selected {@code CardView} in the list.
     */
    public void rememberSelectedCardView() {
        List<Card> selectedItems = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedItems.size() == 0) {
            lastRememberedSelectedCardView = Optional.empty();
        } else {
            lastRememberedSelectedCardView = Optional.of(selectedItems.get(0));
        }
    }

    /**
     * Returns true if the selected {@code CardView} is different from the value remembered by the most recent
     * {@code rememberSelectedCardView()} call.
     */
    public boolean isSelectedCardViewChanged() {
        List<Card> selectedItems = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedItems.size() == 0) {
            return lastRememberedSelectedCardView.isPresent();
        } else {
            return !lastRememberedSelectedCardView.isPresent()
                    || !lastRememberedSelectedCardView.get().equals(selectedItems.get(0));
        }
    }

    /**
     * Returns the size of the list.
     */
    public int getListSize() {
        return getRootNode().getItems().size();
    }
}

package guitests.guihandles;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import seedu.address.model.tag.Tag;

public class GroupListPanelHandle extends NodeHandle<ListView<Tag>> {
    public static final String GROUP_LIST_VIEW_ID = "#groupListView";

    private static final String CARD_PANE_ID = "#cardPane";

    private Optional<Tag> lastRememberedSelectedGroupCard;

    public GroupListPanelHandle(ListView<Tag> groupListPanelNode) {
        super(groupListPanelNode);
    }

    /**
     * Returns a handle to the selected {@code GroupCardHandle}.
     * A maximum of 1 item can be selected at any time.
     * @throws AssertionError if no card is selected, or more than 1 card is selected.
     * @throws IllegalStateException if the selected card is currently not in the scene graph.
     */
    public GroupCardHandle getHandleToSelectedCard() {
        List<Tag> selectedGroupList = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedGroupList.size() != 1) {
            throw new AssertionError("Group list size expected 1.");
        }

        return getAllCardNodes().stream()
                .map(GroupCardHandle::new)
                .filter(handle -> handle.equals(selectedGroupList.get(0)))
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
        List<Tag> selectedCardsList = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedCardsList.size() > 1) {
            throw new AssertionError("Card list size expected 0 or 1.");
        }

        return !selectedCardsList.isEmpty();
    }

    /**
     * Navigates the listview to display {@code group}.
     * @param group
     */
    public void navigateToCard(Tag group) {
        if (!getRootNode().getItems().contains(group)) {
            throw new IllegalArgumentException("Group does not exist.");
        }

        guiRobot.interact(() -> {
            getRootNode().scrollTo(group);
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
     * Returns the person card handle of a person associated with the {@code index} in the list.
     * @throws IllegalStateException if the selected card is currently not in the scene graph.
     */
    public GroupCardHandle getGroupCardHandle(int index) {
        return getAllCardNodes().stream()
                .map(GroupCardHandle::new)
                .filter(handle -> handle.equals(getGroup(index)))
                .findFirst()
                .orElseThrow(IllegalStateException::new);
    }

    private Tag getGroup(int index) {
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
     * Remembers the selected {@code GroupCard} in the list.
     */
    public void rememberSelectedGroupCard() {
        List<Tag> selectedItems = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedItems.size() == 0) {
            lastRememberedSelectedGroupCard = Optional.empty();
        } else {
            lastRememberedSelectedGroupCard = Optional.of(selectedItems.get(0));
        }
    }

    /**
     * Returns true if the selected {@code GroupCard} is different from the value remembered by the most recent
     * {@code rememberSelectedGroupCard()} call.
     */
    public boolean isSelectedGroupCardChanged() {
        List<Tag> selectedItems = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedItems.size() == 0) {
            return lastRememberedSelectedGroupCard.isPresent();
        } else {
            return !lastRememberedSelectedGroupCard.isPresent()
                    || !lastRememberedSelectedGroupCard.get().equals(selectedItems.get(0));
        }
    }

    /**
     * Returns the size of the list.
     */
    public int getListSize() {
        return getRootNode().getItems().size();
    }
}

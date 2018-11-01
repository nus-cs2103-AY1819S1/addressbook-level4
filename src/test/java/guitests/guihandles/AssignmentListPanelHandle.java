package guitests.guihandles;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import javafx.scene.Node;
import javafx.scene.control.ListView;
import seedu.address.model.project.Assignment;

/**
 * Provides a handle for {@code AssignmentListPanel} containing the list of {@code AssignmentCard}.
 */
public class AssignmentListPanelHandle extends NodeHandle<ListView<Assignment>> {

    public static final String ASSIGNMENT_LIST_VIEW_ID = "#assignmentListView";

    private static final String CARD_PANE_ID = "#cardPane";

    private Optional<Assignment> lastRememberedSelectedAssignmentCard;

    public AssignmentListPanelHandle(ListView<Assignment> assignmentListPanelNode) {
        super(assignmentListPanelNode);
    }

    /**
     * Returns a handle to the selected {@code AssignmentCardHandle}.
     * A maximum of 1 item can be selected at any time.
     * @throws AssertionError if no card is selected, or more than 1 card is selected.
     * @throws IllegalStateException if the selected card is currently not in the scene graph.
     */
    public AssignmentCardHandle getHandleToSelectedCard() {
        List<Assignment> selectedAssignmentList = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedAssignmentList.size() != 1) {
            throw new AssertionError("Assignment list size expected 1.");
        }

        return getAllCardNodes().stream()
                .map(AssignmentCardHandle::new)
                .filter(handle -> handle.equals(selectedAssignmentList.get(0)))
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
        List<Assignment> selectedCardsList = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedCardsList.size() > 1) {
            throw new AssertionError("Card list size expected 0 or 1.");
        }

        return !selectedCardsList.isEmpty();
    }

    /**
     * Navigates the listview to display {@code person}.
     */
    public void navigateToCard(Assignment assignment) {
        if (!getRootNode().getItems().contains(assignment)) {
            throw new IllegalArgumentException("Assignment does not exist.");
        }

        guiRobot.interact(() -> {
            getRootNode().scrollTo(assignment);
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
     * Selects the {@code AssignmentCard} at {@code index} in the list.
     */
    public void select(int index) {
        getRootNode().getSelectionModel().select(index);
    }

    /**
     * Returns the assignment card handle of an assignment associated with the {@code index} in the list.
     * @throws IllegalStateException if the selected card is currently not in the scene graph.
     */
    public AssignmentCardHandle getAssignmentCardHandle(int index) {
        return getAllCardNodes().stream()
                .map(AssignmentCardHandle::new)
                .filter(handle -> handle.equals(getAssignment(index)))
                .findFirst()
                .orElseThrow(IllegalStateException::new);
    }

    private Assignment getAssignment(int index) {
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
     * Remembers the selected {@code AssignmentCard} in the list.
     */
    public void rememberSelectedAssignmentCard() {
        List<Assignment> selectedItems = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedItems.size() == 0) {
            lastRememberedSelectedAssignmentCard = Optional.empty();
        } else {
            lastRememberedSelectedAssignmentCard = Optional.of(selectedItems.get(0));
        }
    }

    /**
     * Returns true if the selected {@code AssignmentCard} is different from the value remembered by the most recent
     * {@code rememberSelectedAssignmentCard()} call.
     */
    public boolean isSelectedAssignmentCardChanged() {
        List<Assignment> selectedItems = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedItems.size() == 0) {
            return lastRememberedSelectedAssignmentCard.isPresent();
        } else {
            return !lastRememberedSelectedAssignmentCard.isPresent()
                    || !lastRememberedSelectedAssignmentCard.get().equals(selectedItems.get(0));
        }
    }

    /**
     * Returns the size of the list.
     */
    public int getListSize() {
        return getRootNode().getItems().size();
    }
}

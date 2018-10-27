package guitests.guihandles;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import javafx.scene.Node;
import javafx.scene.control.ListView;
import seedu.address.model.volunteer.Volunteer;

/**
 * Provides a handle for {@code VolunteerListPanel} containing the list of {@code VolunteerCard}.
 */
public class VolunteerListPanelHandle extends NodeHandle<ListView<Volunteer>> {
    public static final String VOLUNTEER_LIST_VIEW_ID = "#volunteerListView";

    private static final String CARD_PANE_ID = "#cardPane";

    private Optional<Volunteer> lastRememberedSelectedVolunteerCard;

    public VolunteerListPanelHandle(ListView<Volunteer> volunteerListPanelNode) {
        super(volunteerListPanelNode);
    }

    /**
     * Returns a handle to the selected {@code VolunteerCardHandle}.
     * A maximum of 1 item can be selected at any time.
     * @throws AssertionError if no card is selected, or more than 1 card is selected.
     * @throws IllegalStateException if the selected card is currently not in the scene graph.
     */
    public VolunteerCardHandle getHandleToSelectedCard() {
        List<Volunteer> selectedVolunteerList = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedVolunteerList.size() != 1) {
            throw new AssertionError("Volunteer list size expected 1.");
        }

        return getAllCardNodes().stream()
                .map(VolunteerCardHandle::new)
                .filter(handle -> handle.equals(selectedVolunteerList.get(0)))
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
        List<Volunteer> selectedCardsList = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedCardsList.size() > 1) {
            throw new AssertionError("Card list size expected 0 or 1.");
        }

        return !selectedCardsList.isEmpty();
    }

    /**
     * Navigates the listview to display {@code volunteer}.
     */
    public void navigateToCard(Volunteer volunteer) {
        if (!getRootNode().getItems().contains(volunteer)) {
            throw new IllegalArgumentException("Volunteer does not exist.");
        }

        guiRobot.interact(() -> {
            getRootNode().scrollTo(volunteer);
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
     * Selects the {@code VolunteerCard} at {@code index} in the list.
     */
    public void select(int index) {
        getRootNode().getSelectionModel().select(index);
    }

    /**
     * Returns the volunteer card handle of a volunteer associated with the {@code index} in the list.
     * @throws IllegalStateException if the selected card is currently not in the scene graph.
     */
    public VolunteerCardHandle getVolunteerCardHandle(int index) {
        return getAllCardNodes().stream()
                .map(VolunteerCardHandle::new)
                .filter(handle -> handle.equals(getVolunteer(index)))
                .findFirst()
                .orElseThrow(IllegalStateException::new);
    }

    private Volunteer getVolunteer(int index) {
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
     * Remembers the selected {@code VolunteerCard} in the list.
     */
    public void rememberSelectedVolunteerCard() {
        List<Volunteer> selectedItems = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedItems.size() == 0) {
            lastRememberedSelectedVolunteerCard = Optional.empty();
        } else {
            lastRememberedSelectedVolunteerCard = Optional.of(selectedItems.get(0));
        }
    }

    /**
     * Returns true if the selected {@code VolunteerCard} is different from the value remembered by the most recent
     * {@code rememberSelectedVolunteerCard()} call.
     */
    public boolean isSelectedVolunteerCardChanged() {
        List<Volunteer> selectedItems = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedItems.size() == 0) {
            return lastRememberedSelectedVolunteerCard.isPresent();
        } else {
            return !lastRememberedSelectedVolunteerCard.isPresent()
                    || !lastRememberedSelectedVolunteerCard.get().equals(selectedItems.get(0));
        }
    }

    /**
     * Returns the size of the list.
     */
    public int getListSize() {
        return getRootNode().getItems().size();
    }
}

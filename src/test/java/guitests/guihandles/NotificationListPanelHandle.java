package guitests.guihandles;

import javafx.scene.Node;
import javafx.scene.control.ListView;
import seedu.address.model.expense.Expense;
import seedu.address.model.notification.Notification;

import org.assertj.core.condition.Not;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Provides a handle for {@code ExpenseListPanel} containing the list of {@code ExpenseCard}.
 */
public class NotificationListPanelHandle extends NodeHandle<ListView<Notification>> {
    public static final String NOTIFICATION_LIST_VIEW_ID = "#notificationListView";

    private static final String CARD_PANE_ID = "#notificationCardPane";

    private Optional<Notification> lastRememberedSelectedNotificationCard;

    public NotificationListPanelHandle(ListView<Notification> notificationListPanelNode) {
        super(notificationListPanelNode);
    }

    /**
     * Returns a handle to the selected {@code ExpenseCardHandle}.
     * A maximum of 1 item can be selected at any time.
     * @throws AssertionError if no card is selected, or more than 1 card is selected.
     * @throws IllegalStateException if the selected card is currently not in the scene graph.
     */
    public NotificationCardHandle getHandleToSelectedCard() {
        List<Notification> selectedNotificationList = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedNotificationList.size() != 1) {
            throw new AssertionError("Notification list size expected 1");
        }

        return getAllCardNodes().stream()
                .map(NotificationCardHandle::new)
                .filter(handle -> handle.equals(selectedNotificationList.get(0)))
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
        List<Notification> selectedCardsList = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedCardsList.size() > 1) {
            throw new AssertionError("Card list size expected 0 or 1.");
        }

        return !selectedCardsList.isEmpty();
    }

    /**
     * Navigates the listview to display {@code expense}.
     */
    public void navigateToCard(Notification notification) {
        if (!getRootNode().getItems().contains(notification)) {
            throw new IllegalArgumentException("Notification does not exist.");
        }

        guiRobot.interact(() -> {
            getRootNode().scrollTo(notification);
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
     * Selects the {@code ExpenseCard} at {@code index} in the list.
     */
    public void select(int index) {
        getRootNode().getSelectionModel().select(index);
    }

    /**
     * Returns the expense card handle of a expense associated with the {@code index} in the list.
     * @throws IllegalStateException if the selected card is currently not in the scene graph.
     */
    public NotificationCardHandle getNotificationCardHandle(int index) {
        return getAllCardNodes().stream()
                .map(NotificationCardHandle::new)
                .filter(handle -> handle.equals(getNotification(index)))
                .findFirst()
                .orElseThrow(IllegalStateException::new);
    }

    private Notification getNotification(int index) {
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
     * Remembers the selected {@code ExpenseCard} in the list.
     */
    public void rememberSelectedNotificationCard() {
        List<Notification> selectedItems = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedItems.size() == 0) {
            lastRememberedSelectedNotificationCard = Optional.empty();
        } else {
            lastRememberedSelectedNotificationCard = Optional.of(selectedItems.get(0));
        }
    }

    /**
     * Returns true if the selected {@code ExpenseCard} is different from the value remembered by the most recent
     * {@code rememberSelectedExpenseCard()} call.
     */
    public boolean isSelectedNotificationCardChanged() {
        List<Notification> selectedItems = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedItems.size() == 0) {
            return lastRememberedSelectedNotificationCard.isPresent();
        } else {
            return !lastRememberedSelectedNotificationCard.isPresent()
                    || !lastRememberedSelectedNotificationCard.get().equals(selectedItems.get(0));
        }
    }

    /**
     * Returns the size of the list.
     */
    public int getListSize() {
        return getRootNode().getItems().size();
    }
}

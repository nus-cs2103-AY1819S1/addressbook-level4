package guitests.guihandles;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import javafx.scene.Node;
import javafx.scene.control.ListView;
import seedu.jxmusic.model.Track;

/**
 * Provides a handle for {@code TrackListPanel} containing the list of {@code TrackCard}.
 */
public class TrackListPanelHandle extends NodeHandle<ListView<Track>> {
    public static final String TRACK_LIST_VIEW_ID = "#trackListView";

    private static final String CARD_PANE_ID = "#cardPane";

    private Optional<Track> lastRememberedSelectedTrackCard;

    public TrackListPanelHandle(ListView<Track> trackListPanelNode) {
        super(trackListPanelNode);
    }

    /**
     * Returns a handle to the selected {@code TrackCardHandle}.
     * A maximum of 1 item can be selected at any time.
     * @throws AssertionError if no card is selected, or more than 1 card is selected.
     * @throws IllegalStateException if the selected card is currently not in the scene graph.
     */
    public TrackCardHandle getHandleToSelectedCard() {
        List<Track> selectedTrackList = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedTrackList.size() != 1) {
            throw new AssertionError("Track list size expected 1.");
        }

        return getAllCardNodes().stream()
                .map(TrackCardHandle::new)
                .filter(handle -> handle.equals(selectedTrackList.get(0)))
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
        List<Track> selectedCardsList = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedCardsList.size() > 1) {
            throw new AssertionError("Card list size expected 0 or 1.");
        }

        return !selectedCardsList.isEmpty();
    }

    /**
     * Navigates the listview to display {@code playlist}.
     */
    public void navigateToCard(Track track) {
        if (!getRootNode().getItems().contains(track)) {
            throw new IllegalArgumentException("Track does not exist.");
        }

        guiRobot.interact(() -> {
            getRootNode().scrollTo(track);
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
     * Selects the {@code Track} at {@code index} in the list。
     */
    public void select(int index) {
        getRootNode().getSelectionModel().select(index);
    }

    /**
     * Returns the track hard handle of a track associated with the {@code index} in the list.
     */
    public TrackCardHandle getTrackCardHandle(int index) {
        return getAllCardNodes().stream()
                .map(TrackCardHandle::new)
                .filter(handle -> handle.equals(getTrack(index)))
                .findFirst()
                .orElseThrow(IllegalStateException::new);
    }

    private Track getTrack(int index) {
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
     * Remembers the selected {@code TrackCard} in the list.
     */
    public void rememberSelectedTrackCard() {
        List<Track> selectedItems = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedItems.size() == 0) {
            lastRememberedSelectedTrackCard = Optional.empty();
        } else {
            lastRememberedSelectedTrackCard = Optional.of(selectedItems.get(0));
        }
    }

    /**
     * Returns true of the selected {@code TrackCard} is different from the value remembered by the most recent
     * {@code rememberSelectedTrackCard()} call.
     */
    public boolean isSelectedTrackCardChanged() {
        List<Track> selectedItems = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedItems.size() == 0) {
            return lastRememberedSelectedTrackCard.isPresent();
        } else {
            return !lastRememberedSelectedTrackCard.isPresent()
                    || !lastRememberedSelectedTrackCard.get().equals(selectedItems.get(0));
        }
    }

    /**
     * Returns the size of the list.
     */
    public int getListSize() {
        return getRootNode().getItems().size();
    }
}

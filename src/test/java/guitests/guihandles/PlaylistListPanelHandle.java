package guitests.guihandles;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import javafx.scene.Node;
import javafx.scene.control.ListView;
import seedu.jxmusic.model.Playlist;

/**
 * Provides a handle for {@code PlaylistListPanel} containing the list of {@code PlaylistCard}.
 */
public class PlaylistListPanelHandle extends NodeHandle<ListView<Playlist>> {
    public static final String PLAYLIST_LIST_VIEW_ID = "#playlistListView";

    private static final String CARD_PANE_ID = "#cardPane";

    private Optional<Playlist> lastRememberedSelectedPlaylistCard;

    public PlaylistListPanelHandle(ListView<Playlist> playlistListPanelNode) {
        super(playlistListPanelNode);
    }

    /**
     * Returns a handle to the selected {@code PlaylistCardHandle}.
     * A maximum of 1 item can be selected at any time.
     * @throws AssertionError if no card is selected, or more than 1 card is selected.
     * @throws IllegalStateException if the selected card is currently not in the scene graph.
     */
    public PlaylistCardHandle getHandleToSelectedCard() {
        List<Playlist> selectedPlaylistList = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedPlaylistList.size() != 1) {
            throw new AssertionError("Playlist list size expected 1.");
        }

        return getAllCardNodes().stream()
                .map(PlaylistCardHandle::new)
                .filter(handle -> handle.equals(selectedPlaylistList.get(0)))
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
        List<Playlist> selectedCardsList = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedCardsList.size() > 1) {
            throw new AssertionError("Card list size expected 0 or 1.");
        }

        return !selectedCardsList.isEmpty();
    }

    /**
     * Navigates the listview to display {@code playlist}.
     */
    public void navigateToCard(Playlist playlist) {
        if (!getRootNode().getItems().contains(playlist)) {
            throw new IllegalArgumentException("Playlist does not exist.");
        }

        guiRobot.interact(() -> {
            getRootNode().scrollTo(playlist);
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
     * Selects the {@code PlaylistCard} at {@code index} in the list.
     */
    public void select(int index) {
        getRootNode().getSelectionModel().select(index);
    }

    /**
     * Returns the playlist card handle of a playlist associated with the {@code index} in the list.
     * @throws IllegalStateException if the selected card is currently not in the scene graph.
     */
    public PlaylistCardHandle getPlaylistCardHandle(int index) {
        return getAllCardNodes().stream()
                .map(PlaylistCardHandle::new)
                .filter(handle -> handle.equals(getPlaylist(index)))
                .findFirst()
                .orElseThrow(IllegalStateException::new);
    }

    private Playlist getPlaylist(int index) {
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
     * Remembers the selected {@code PlaylistCard} in the list.
     */
    public void rememberSelectedPlaylistCard() {
        List<Playlist> selectedItems = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedItems.size() == 0) {
            lastRememberedSelectedPlaylistCard = Optional.empty();
        } else {
            lastRememberedSelectedPlaylistCard = Optional.of(selectedItems.get(0));
        }
    }

    /**
     * Returns true if the selected {@code PlaylistCard} is different from the value remembered by the most recent
     * {@code rememberSelectedPlaylistCard()} call.
     */
    public boolean isSelectedPlaylistCardChanged() {
        List<Playlist> selectedItems = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedItems.size() == 0) {
            return lastRememberedSelectedPlaylistCard.isPresent();
        } else {
            return !lastRememberedSelectedPlaylistCard.isPresent()
                    || !lastRememberedSelectedPlaylistCard.get().equals(selectedItems.get(0));
        }
    }

    /**
     * Returns the size of the list.
     */
    public int getListSize() {
        return getRootNode().getItems().size();
    }
}

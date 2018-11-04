package guitests.guihandles;

import java.nio.file.Path;
import java.util.Optional;
import java.util.Set;

import javafx.scene.Node;
import javafx.scene.control.ListView;

/**
 * Provides a handle to {@code FilmReel}.
 */
public class FilmReelHandle extends NodeHandle<Node> {

    public static final String IMAGE_LIST_ID = "#imageListView";
    private static final String CARD_PANE_ID = "#cardPane";

    private final ListView<Path> imageListView;

    public FilmReelHandle(Node filmReel) {
        super(filmReel);
        imageListView = getChildNode(IMAGE_LIST_ID);
    }

    private Path getPath(int index) {
        return imageListView.getItems().get(index);
    }

    /**
     * Returns a handle to the selected {@code FilmCardHandle}.
     * A maximum of 1 item can be selected at any time.
     */
    public Optional<FilmCardHandle> getHandleToSelectedCard() {
        int index = imageListView.getSelectionModel().getSelectedIndex();
        System.out.println(index);
        return index >= 0 ? getFilmCardHandle(index) : Optional.empty();
    }

    /**
     * Returns a handle to a {@code FilmCard}.
     */
    public Optional<FilmCardHandle> getFilmCardHandle(int i) {
        return getAllCardNodes().stream()
                .map(FilmCardHandle::new)
                .filter(handle -> handle.equals(getPath(i), i))
                .findFirst();

    }

    /**
     * Returns all card nodes in the scene graph.
     */
    private Set<Node> getAllCardNodes() {
        return guiRobot.lookup(CARD_PANE_ID).queryAll();
    }

    public int getSize() {
        return imageListView.getItems().size();
    }
}

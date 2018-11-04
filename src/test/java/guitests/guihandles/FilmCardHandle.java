package guitests.guihandles;

import java.nio.file.Path;

import javafx.scene.Node;
import javafx.scene.control.Label;

/**
 * Handle to {@code FilmReelCard}.
 */
public class FilmCardHandle extends NodeHandle<Node> {

    private static final String PATH_ID = "#pathName";
    private static final String LABEL_ID = "#name";

    private final Label pathName;
    private final Label name;

    public FilmCardHandle(Node filmReelCard) {
        super(filmReelCard);
        pathName = getChildNode(PATH_ID);
        name = getChildNode(LABEL_ID);
    }

    public String getTitle() {
        return name.getText();
    }

    public String getPath() {
        return pathName.getText();
    }

    /**
     * Returns true if this handle contains {@code Path}.
     */
    public boolean equals(Path path, int index) {
        return getTitle().equals(Integer.toString(index + 1))
                && getPath().equals(path.toString());
    }

    @Override
    public String toString() {
        return pathName.getText() + "" + name.getText();
    }
}

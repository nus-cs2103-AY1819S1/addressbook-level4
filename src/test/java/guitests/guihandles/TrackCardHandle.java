package guitests.guihandles;

import javafx.scene.Node;
import javafx.scene.control.Label;
import seedu.jxmusic.model.Track;

/**
 * Provides a handle to a track card in the track list panel.
 */
public class TrackCardHandle extends NodeHandle<Node> {
    private static final String ID_FIELD_ID = "#id";
    private static final String NAME_FIELD_ID = "#name";

    private final Label idLabel;
    private final Label nameLabel;

    public TrackCardHandle(Node cardNode) {
        super(cardNode);

        idLabel = getChildNode(ID_FIELD_ID);
        nameLabel = getChildNode(NAME_FIELD_ID);
    }

    public String getId() {
        return idLabel.getText();
    }

    public String getName() {
        return nameLabel.getText();
    }

    /**
     * Returns true if this handle contains {@code track}.
     */
    public boolean equals(Track track) {
        return getName().equals(track.getFileNameWithoutExtension());
    }
}


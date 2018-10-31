package guitests.guihandles;

import java.util.List;
import java.util.stream.Collectors;

import com.google.common.collect.ImmutableMultiset;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.jxmusic.model.Playlist;

/**
 * Provides a handle to a playlist card in the playlist list panel.
 */
public class PlaylistCardHandle extends NodeHandle<Node> {
    private static final String ID_FIELD_ID = "#id";
    private static final String NAME_FIELD_ID = "#name";
    private static final String TRACKS_FIELD_ID = "#tracks";

    private final Label idLabel;
    private final Label nameLabel;
    private final List<Label> tracksLabels;

    public PlaylistCardHandle(Node cardNode) {
        super(cardNode);

        idLabel = getChildNode(ID_FIELD_ID);
        nameLabel = getChildNode(NAME_FIELD_ID);

        Region tracksContainer = getChildNode(TRACKS_FIELD_ID);
        tracksLabels = tracksContainer
                .getChildrenUnmodifiable()
                .stream()
                .map(Label.class::cast)
                .collect(Collectors.toList());
    }

    public String getId() {
        return idLabel.getText();
    }

    public String getName() {
        return nameLabel.getText();
    }

    public List<String> getTracks() {
        return tracksLabels
                .stream()
                .map(Label::getText)
                .collect(Collectors.toList());
    }

    /**
     * Returns true if this handle contains {@code playlist}.
     */
    public boolean equals(Playlist playlist) {
        return getName().equals(playlist.getName().nameString)
                && ImmutableMultiset.copyOf(getTracks()).equals(ImmutableMultiset.copyOf(playlist.getTracks().stream()
                        .map(track -> track.getFileNameWithoutExtension())
                        .collect(Collectors.toList())));
    }
}

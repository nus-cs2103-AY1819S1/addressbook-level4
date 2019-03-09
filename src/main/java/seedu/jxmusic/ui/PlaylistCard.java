package seedu.jxmusic.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.jxmusic.model.Playlist;

/**
 * An UI component that displays information of a {@code Playlist}.
 */
public class PlaylistCard extends UiPart<Region> {

    private static final String FXML = "PlaylistListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Playlist playlist;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private FlowPane tracks;

    public PlaylistCard(Playlist playlist, int displayedIndex) {
        super(FXML);
        this.playlist = playlist;
        id.setText(displayedIndex + ". ");
        name.setText(playlist.getName().nameString);
        playlist.getTracks().forEach(track ->
                tracks.getChildren().add(new Label(track.getFileNameWithoutExtension())));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PlaylistCard)) {
            return false;
        }

        //name check
        Playlist playlist1 = ((PlaylistCard) other).playlist;
        return name.getText().equals(playlist1.getName().nameString);
    }
}

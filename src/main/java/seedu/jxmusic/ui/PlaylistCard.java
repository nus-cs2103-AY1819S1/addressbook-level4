package seedu.jxmusic.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.jxmusic.model.Playlist;

/**
 * An UI component that displays information of a {@code Person}.
 */
public class PlaylistCard extends UiPart<Region> {

    private static final String FXML = "PersonListCard.fxml";

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
    private Label phone;
    @FXML
    private Label tracks;
    @FXML
    private Label address;
    @FXML
    private Label email;
    @FXML
    private FlowPane tags;

    public PlaylistCard(Playlist playlist, int displayedIndex) {
        super(FXML);
        this.playlist = playlist;
        id.setText(displayedIndex + ". ");
        name.setText(playlist.getName().nameString);
        tracks.setText(playlist.getTracks().toString());
        //phone.setText(playlist.getPhone().value);
        //address.setText(person.getAddress().value);
        //email.setText(person.getEmail().value);
        //person.getTags().forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
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
        Playlist playlist1 = (Playlist) other;
        return name.getText().equals(playlist1.getName());

        // state check
        /*
        PlaylistCard card = (PlaylistCard) other;
        return id.getText().equals(card.id.getText())
                && person.equals(card.person);
         */
    }
}

package seedu.jxmusic.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.jxmusic.model.Track;

/**
 *  An UI component that displays information of a {@code Track}.
 */
public class TrackCard extends UiPart<Region> {
    private static final String FXML = "TrackCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Track track;

    @FXML
    private HBox trackCardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label duration;

    public TrackCard(Track track, int displayedIndex) {
        super(FXML);
        this.track = track;
        id.setText(displayedIndex + ".");
        name.setText(track.getFileNameWithoutExtension());
        duration.setText(track.getDisplayedFileDuration());
    }

    @Override
    public boolean equals(Object other) {
        //short circuit if same object
        if (other == this) {
            return true;
        }

        //instanceof handles nulls
        if (!(other instanceof TrackCard)) {
            return false;
        }

        //name check
        Track track1 = ((TrackCard) other).track;
        return name.getText().equals(track1.getFileNameWithoutExtension());

        //state check
        /*
        TrackCard card = (TrackCard) other;
        return id.getText().equals(card.id.getText())
                && track.equals(card.track);
         */

    }
}


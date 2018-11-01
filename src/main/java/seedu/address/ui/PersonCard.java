package seedu.address.ui;

import java.io.IOException;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.meeting.Meeting;
import seedu.address.model.person.Person;
import seedu.address.model.person.Picture;

/**
 * An UI component that displays information of a {@code Person}.
 */
public class PersonCard extends UiPart<Region> {
    public static final String NO_PHONE = "No Phone Number";
    public static final String NO_EMAIL = "No Email Address";
    public static final String NO_ADDRESS = "No Address";

    private static final String FXML = "PersonListCard.fxml";
    private static final String[] TAG_COLOR_STYLES = { "teal", "red", "yellow", "blue",
        "orange", "brown", "green", "pink", "black", "grey"};

    private static final Logger logger = LogsCenter.getLogger(PersonCard.class);

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Person person;

    @FXML
    private HBox cardPane;
    @FXML
    private ImageView picture;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label phone;
    @FXML
    private Label address;
    @FXML
    private Label email;
    @FXML
    private Label meeting;
    @FXML
    private FlowPane tags;

    public PersonCard(Person person, int displayedIndex) {
        super(FXML);
        this.person = person;
        id.setText(displayedIndex + ". ");
        name.setText(person.getName().fullName);

        //@@author zioul123
        person.getPhone()
                .ifPresentOrElse(p -> {
                    phone.setText(p.value);
                }, () -> phone.setText(NO_PHONE));
        person.getAddress()
                .ifPresentOrElse(a -> {
                    address.setText(a.value);
                }, () -> address.setText(NO_ADDRESS));
        person.getEmail()
                .ifPresentOrElse(e -> {
                    email.setText(e.value);
                }, () -> email.setText(NO_EMAIL));
        meeting.setText(person.getMeeting().value.equals(Meeting.NO_MEETING) ? Meeting.NO_MEETING_MSG : "Meeting on"
                + person.getMeeting().toString() + "hrs");

        //@@author denzelchung
        // set profile picture
        Image image;

        if (!Picture.isValidPicture(person.getPicture().picture)
            || person.getPicture().picture.equals(Picture.DEFAULT_PICTURE_URL.getPath())) {
            try {
                image = new Image(Picture.DEFAULT_PICTURE_URL.openStream());
            } catch (IOException io) {
                logger.warning("Unable to load default picture");
                image = new Image(Picture.DEFAULT_PICTURE_URL.getPath());
            }
        } else {
            image = new Image("file:" + person.getPicture().picture);
        }

        picture.setImage(image);
        picture.setCache(true);

        //@@author
        initTags(person);

        name.setWrapText(true);
        address.setWrapText(true);
        email.setWrapText(true);
        meeting.setWrapText(true);
    }

    /**
     * Returns the color style for {@code tagName}'s label.
     */
    private String getTagColorStyleFor(String tagName) {
        // we use the hash code of the tag name to generate a random color, so that the color remain consistent
        // between different runs of the program while still making it random enough between tags.
        return TAG_COLOR_STYLES[Math.abs(tagName.hashCode()) % TAG_COLOR_STYLES.length];
    }
    /**
     * Creates the tag labels for {@code person}.
     */
    private void initTags(Person person) {
        person.getTags().forEach(tag -> {
            Label tagLabel = new Label(tag.tagName);
            tagLabel.getStyleClass().add(getTagColorStyleFor(tag.tagName));
            tags.getChildren().add(tagLabel);
        });
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PersonCard)) {
            return false;
        }

        // state check
        PersonCard card = (PersonCard) other;
        return id.getText().equals(card.id.getText())
                && person.equals(card.person);
    }
}

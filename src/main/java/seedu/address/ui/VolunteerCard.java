package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.volunteer.Volunteer;

/**
 * An UI component that displays information of a {@code Volunteer}.
 */
public class VolunteerCard extends UiPart<Region> {

    private static final String FXML = "VolunteerListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Volunteer volunteer;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label volunteerId;
    @FXML
    private Label id;
    @FXML
    private Label phone;
    @FXML
    private Label email;
    @FXML
    private FlowPane tags;

    public VolunteerCard(Volunteer volunteer, int displayedIndex) {
        super(FXML);
        this.volunteer = volunteer;
        id.setText(displayedIndex + ". ");
        name.setText(volunteer.getName().fullName);
        volunteerId.setText(volunteer.getVolunteerId().id);
        phone.setText(volunteer.getPhone().value);
        email.setText(volunteer.getEmail().value);
        volunteer.getTags().forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof VolunteerCard)) {
            return false;
        }

        // state check
        VolunteerCard card = (VolunteerCard) other;
        return id.getText().equals(card.id.getText())
                && volunteer.equals(card.volunteer);
    }
}

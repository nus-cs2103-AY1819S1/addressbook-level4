package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.person.Patient;

/**
 * An UI component that displays information of a {@code Patient}.
 */
public class PersonCard extends UiPart<Region> {

    private static final String FXML = "PersonListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Patient patient;

    private static final String BLUE_CHAS_COLOUR = "blue";
    private static final String ORANGE_CHAS_COLOUR = "orange";
    private static final String PIONEER_CHAS_COLOUR = "red";
    private static final String GREY_COLOUR = "grey";

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label icnumber;
    @FXML
    private Label id;
    @FXML
    private Label phone;
    @FXML
    private Label address;
    @FXML
    private Label email;
    @FXML
    private FlowPane tags;

    public PersonCard(Patient patient, int displayedIndex) {
        super(FXML);
        this.patient = patient;
        id.setText(displayedIndex + ". ");
        name.setText(patient.getName().fullName);
        icnumber.setText(patient.getIcNumber().value);
        phone.setText(patient.getPhone().value);
        address.setText(patient.getAddress().value);
        email.setText(patient.getEmail().value);
        initTags(patient);
    }
    /**
     * Returns the color style for {@code tagName}'s label.
     */
    private String getTagColorStyleFor(String tagName) {
        switch(tagName) {
        case PIONEER_CHAS_COLOUR:
            return PIONEER_CHAS_COLOUR;
        case BLUE_CHAS_COLOUR:
            return BLUE_CHAS_COLOUR;
        case ORANGE_CHAS_COLOUR:
            return ORANGE_CHAS_COLOUR;
        default:
            return GREY_COLOUR;
        }
    }
    /**
     * Creates the tag labels for {@code person}.
     */
    private void initTags(Patient patient) {
        patient.getTags().forEach(tag -> {
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
                && patient.equals(card.patient);
    }
}

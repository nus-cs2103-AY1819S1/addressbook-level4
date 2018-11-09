package seedu.thanepark.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.thanepark.model.ride.Ride;
import seedu.thanepark.model.ride.Status;
import seedu.thanepark.ui.exceptions.AccessibilityException;

/**
 * An UI component that displays information of a {@code Ride}.
 */
public class RideCard extends UiPart<Region> {

    private static final String FXML = "RideListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on ThanePark level 4</a>
     */

    public final Ride ride;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private FlowPane tags;
    @FXML
    private FlowPane rideInfo;
    @FXML
    private Label statusString;

    public RideCard(Ride ride, int displayedIndex) throws AccessibilityException {
        super(FXML);
        this.ride = ride;
        id.setText(displayedIndex + ". ");
        name.setText(ride.getName().fullName);

        ride.getTags().forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));

        rideInfo.getChildren().add(new Label(ride.getZone().value));
        rideInfo.getChildren().add(new Label(ride.getDaysSinceMaintenance().toString()));
        rideInfo.getChildren().add(new Label(ride.getWaitingTime().toString()));

        statusString.setText(ride.getStatus().name());
        if (ride.getStatus().equals(Status.OPEN)) {
            statusString.setStyle("-fx-background-color: #27D858");
        } else if (ride.getStatus().equals(Status.SHUTDOWN)) {
            statusString.setStyle("-fx-background-color: #CC3045");
        } else if (ride.getStatus().equals(Status.MAINTENANCE)) {
            statusString.setStyle("-fx-background-color: #cc6a26");
        //error
        } else {
            throw new AccessibilityException(String.format("Ride status is unknown: %1s", ride.getName().fullName));
        }
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof RideCard)) {
            return false;
        }

        // state check
        RideCard card = (RideCard) other;
        return id.getText().equals(card.id.getText())
                && ride.equals(card.ride);
    }
}

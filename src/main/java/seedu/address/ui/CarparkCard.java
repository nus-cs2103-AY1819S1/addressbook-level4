package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.carpark.Carpark;

/**
 * An UI component that displays information of a {@code Person}.
 */
public class CarparkCard extends UiPart<Region> {

    private static final String FXML = "CarparkListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Carpark carpark;

    @FXML
    private HBox cardPane;
    @FXML
    private Label carparkNumber;
    @FXML
    private Label id;
    @FXML
    private Label lotsAvailable;
    @FXML
    private Label totalLots;
    @FXML
    private Label lotType;
    @FXML
    private Label address;
    @FXML
    private Label email;
    @FXML
    private FlowPane tags;

    public CarparkCard(Carpark carpark, int displayedIndex) {
        super(FXML);
        this.carpark = carpark;
        id.setText(displayedIndex + ". ");
        carparkNumber.setText(carpark.getCarparkNumber().value);
        address.setText(carpark.getAddress().value);
        totalLots.setText("Total Lots: " + carpark.getTotalLots().value);
        lotsAvailable.setText("Lots Available: " + carpark.getLotsAvailable().value);
        carpark.getTags().forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof CarparkCard)) {
            return false;
        }

        // state check
        CarparkCard card = (CarparkCard) other;
        return id.getText().equals(card.id.getText())
                && carpark.equals(card.carpark);
    }
}

package seedu.parking.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.parking.model.carpark.Carpark;
import seedu.parking.model.carpark.PostalCode;

/**
 * An UI component that displays information of a {@code Carpark}.
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
    private Label address;
    @FXML
    private Label coordinate;
    @FXML
    private Label carparkType;
    @FXML
    private Label totalLots;
    @FXML
    private Label lotsAvailable;
    @FXML
    private Label freeParking;
    @FXML
    private Label nightParking;
    @FXML
    private Label shortTerm;
    @FXML
    private Label parkingSystem;
    @FXML
    private Label postalCode;
    @FXML
    private FlowPane tags;

    public CarparkCard(Carpark carpark, int displayedIndex) {
        super(FXML);
        this.carpark = carpark;

        id.setText(displayedIndex + ". ");
        carparkNumber.setText(carpark.getCarparkNumber().toString());
        address.setText(carpark.getAddress().toString());

        if (carpark.getPostalCode().toString().equals(PostalCode.DEFAULT_VALUE)) {
            postalCode.setText("Postal Code: Not Available");
        } else {
            postalCode.setText("Postal Code: " + carpark.getPostalCode().toString());
        }

        carparkType.setText(carpark.getCarparkType().toString());
        coordinate.setText("Coordinate: " + carpark.getCoordinate().toString());

        if (carpark.getTotalLots().toString().equals("0")) {
            totalLots.setText("Total Lots: Not Available");
            lotsAvailable.setText("Lots Available: Not Available");
        } else {
            totalLots.setText("Total Lots: " + carpark.getTotalLots().toString());
            lotsAvailable.setText("Lots Available: " + carpark.getLotsAvailable().toString());
        }

        freeParking.setText("Free Parking: " + carpark.getFreeParking().toString());
        nightParking.setText("Night Parking: " + carpark.getNightParking().toString());
        shortTerm.setText("Short Term Parking: " + carpark.getShortTerm().toString());
        parkingSystem.setText("Parking System: " + carpark.getTypeOfParking());
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

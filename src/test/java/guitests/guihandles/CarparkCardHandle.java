package guitests.guihandles;

import java.util.List;
import java.util.stream.Collectors;

import com.google.common.collect.ImmutableMultiset;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.parking.model.carpark.Carpark;

/**
 * Provides a handle to a carpark card in the carpark list panel.
 */
public class CarparkCardHandle extends NodeHandle<Node> {
    private static final String ID_FIELD_ID = "#id";
    private static final String ADDRESS_FIELD_ID = "#address";
    private static final String CARPARK_NUMBER_FIELD_ID = "#carparkNumber";
    private static final String CARPARK_TYPE_FIELD_ID = "#carparkType";
    private static final String COORDINATE_FIELD_ID = "#coordinate";
    private static final String FREE_PARKING_FIELD_ID = "#freeParking";
    private static final String LOTS_AVAILABLE_FIELD_ID = "#lotsAvailable";
    private static final String NIGHT_PARKING_FIELD_ID = "#nightParking";
    private static final String SHORT_TERM_FIELD_ID = "#shortTerm";
    private static final String TOTAL_LOTS_FIELD_ID = "#totalLots";
    private static final String TYPE_OF_PARKING_FIELD_ID = "#parkingSystem";
    private static final String TAGS_FIELD_ID = "#tags";

    private final Label idLabel;
    private final Label carparkNumberLabel;
    private final Label carparkTypeLabel;
    private final Label coordinateLabel;
    private final Label freeParkingLabel;
    private final Label lotsAvailableLabel;
    private final Label nightParkingLabel;
    private final Label shortTermLabel;
    private final Label totalLotsLabel;
    private final Label typeOfParkingLabel;
    private final Label addressLabel;
    private final List<Label> tagLabels;

    public CarparkCardHandle(Node cardNode) {
        super(cardNode);

        idLabel = getChildNode(ID_FIELD_ID);
        carparkNumberLabel = getChildNode(CARPARK_NUMBER_FIELD_ID);
        carparkTypeLabel = getChildNode(CARPARK_TYPE_FIELD_ID);
        coordinateLabel = getChildNode(COORDINATE_FIELD_ID);
        freeParkingLabel = getChildNode(FREE_PARKING_FIELD_ID);
        lotsAvailableLabel = getChildNode(LOTS_AVAILABLE_FIELD_ID);
        nightParkingLabel = getChildNode(NIGHT_PARKING_FIELD_ID);
        shortTermLabel = getChildNode(SHORT_TERM_FIELD_ID);
        totalLotsLabel = getChildNode(TOTAL_LOTS_FIELD_ID);
        typeOfParkingLabel = getChildNode(TYPE_OF_PARKING_FIELD_ID);
        addressLabel = getChildNode(ADDRESS_FIELD_ID);
        Region tagsContainer = getChildNode(TAGS_FIELD_ID);
        tagLabels = tagsContainer
                .getChildrenUnmodifiable()
                .stream()
                .map(Label.class::cast)
                .collect(Collectors.toList());
    }

    public String getId() {
        return idLabel.getText();
    }

    public String getCarparkNumber() {
        return carparkNumberLabel.getText();
    }

    public String getCarparkType() {
        return carparkTypeLabel.getText();
    }

    public String getCoordinate() {
        return coordinateLabel.getText();
    }

    public String getFreeParking() {
        return freeParkingLabel.getText();
    }

    public String getLotsAvailable() {
        return lotsAvailableLabel.getText();
    }

    public String getNightParking() {
        return nightParkingLabel.getText();
    }

    public String getShortTerm() {
        return shortTermLabel.getText();
    }

    public String getTotalLots() {
        return totalLotsLabel.getText();
    }

    public String getTypeOfParking() {
        return typeOfParkingLabel.getText();
    }

    public String getAddress() {
        return addressLabel.getText();
    }

    public List<String> getTags() {
        return tagLabels
                .stream()
                .map(Label::getText)
                .collect(Collectors.toList());
    }

    /**
     * Returns true if this handle contains {@code carpark}.
     */
    public boolean equals(Carpark carpark) {
        if (carpark.getTotalLots().toString().equals("0")) {
            if (!getTotalLots().equals("Total Lots: Not Available")
                    || !getLotsAvailable().equals("Lots Available: Not Available")) {
                return false;
            }
        } else {
            if (!getTotalLots().equals("Total Lots: " + carpark.getTotalLots().toString())
                    || !getLotsAvailable().equals("Lots Available: " + carpark.getLotsAvailable().toString())) {
                return false;
            }
        }

        return getCarparkNumber().equals(carpark.getCarparkNumber().toString())
                && getAddress().equals(carpark.getAddress().toString())
                && getCarparkType().equals(carpark.getCarparkType().toString())
                && getCoordinate().equals("Coordinate: " + carpark.getCoordinate().toString())
                && getFreeParking().equals("Free Parking: " + carpark.getFreeParking().toString())
                && getNightParking().equals("Night Parking: " + carpark.getNightParking().toString())
                && getShortTerm().equals("Short Term Parking: " + carpark.getShortTerm().toString())
                && getTypeOfParking().equals("Parking System: " + carpark.getTypeOfParking().toString())
                && ImmutableMultiset.copyOf(getTags()).equals(ImmutableMultiset.copyOf(carpark.getTags().stream()
                .map(tag -> tag.tagName)
                .collect(Collectors.toList())));
    }
}

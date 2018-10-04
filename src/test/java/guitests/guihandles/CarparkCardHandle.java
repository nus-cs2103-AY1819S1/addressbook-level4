package guitests.guihandles;

import java.util.List;
import java.util.stream.Collectors;

import com.google.common.collect.ImmutableMultiset;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.address.model.carpark.Carpark;

/**
 * Provides a handle to a person card in the person list panel.
 */
public class CarparkCardHandle extends NodeHandle<Node> {
    private static final String CARPARK_NUMBER_FIELD_ID = "#carparkNumber";
    private static final String CARPARK_TYPE_FIELD_ID = "#carparkType";
    private static final String COORDINATE_FIELD_ID = "#coordinate";
    private static final String FREE_PARKING_FIELD_ID = "#freeParking";
    private static final String LOTS_AVAILABLE_FIELD_ID = "#lotsAvailable";
    private static final String NIGHT_PARKING_FIELD_ID = "#nightParking";
    private static final String SHORT_TERM_FIELD_ID = "#shortTerm";
    private static final String TOTAL_LOTS_FIELD_ID = "#totalLots";
    private static final String TYPE_OF_PARKING_FIELD_ID = "#parkingSystem";
    private static final String ADDRESS_FIELD_ID = "#address";
    private static final String TAGS_FIELD_ID = "#tags";

    private final Label carparkNumberLabel;
    private final Label carparkTypeLabel;
    private final Label coordinateLabel;

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

//    public String getId() {
//        return idLabel.getText();
//    }

//    public String getName() {
//        return nameLabel.getText();
//    }

    public String getAddress() {
        return addressLabel.getText();
    }

//    public String getPhone() {
//        return phoneLabel.getText();
//    }
//
//    public String getEmail() {
//        return emailLabel.getText();
//    }

    public List<String> getTags() {
        return tagLabels
                .stream()
                .map(Label::getText)
                .collect(Collectors.toList());
    }

    /**
     * Returns true if this handle contains {@code person}.
     */
    public boolean equals(Carpark carpark) {
        return getAddress().equals(carpark.getAddress())
                && getCarparkNumber().equals(carpark.getCarparkNumber())
                && getCarparkType().equals(carpark.getCarparkType())
                && getCoordinate().equals(carpark.getCoordinate())
                && getFreeParking().equals((carpark.getFreeParking()))
                && getLotsAvailable().equals(carpark.getLotsAvailable())
                && getNightParking().equals(carpark.getNightParking())
                && getShortTerm().equals(carpark.getShortTerm())
                && getTotalLots().equals(carpark.getTotalLots())
                && getTypeOfParking().equals(carpark.getTypeOfParking())
                && ImmutableMultiset.copyOf(getTags()).equals(ImmutableMultiset.copyOf(carpark.getTags().stream()
                .map(tag -> tag.tagName)
                .collect(Collectors.toList())));
    }
}

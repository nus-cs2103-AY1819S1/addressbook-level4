package guitests.guihandles;

import java.util.List;
import java.util.stream.Collectors;

import com.google.common.collect.ImmutableMultiset;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.address.model.carpark.Carpark;

/**
 * Provides a handle to a carpark card in the carpark list panel.
 */
public class CarparkCardHandle extends NodeHandle<Node> {
    private static final String ADDRESS_FIELD_ID = "#address";
    private static final String CARPARKNUMBER_FIELD_ID = "#carparkNumber";
    private static final String COORDINATE_FIELD_ID = "#coordinate";
    private static final String CARPARKTYPE_FIELD_ID = "#carparkType";
    private static final String FREEPARKING_FIELD_ID = "#freeParking";
    private static final String LOTSAVAILABLE_FIELD_ID = "#lotsAvailable";
    private static final String NIGHTPARKING_FIELD_ID = "#nightParking";
    private static final String SHORTTERM_FIELD_ID = "#shortTerm";
    private static final String TOTALLOTS_FIELD_ID = "#totalLots";
    private static final String TYPEOFPARKING_FIELD_ID = "#typeOfParking";
    private static final String TAGS_FIELD_ID = "#tags";

    private final Label addressLabel;
    private final Label carparkNumberLabel;
    private final Label coordinateLabel;
    private final Label carparkTypeLabel;
    private final Label freeParkingLabel;
    private final Label lotsAvailableLabel;
    private final Label nightParkingLabel;
    private final Label shortTermLabel;
    private final Label totalLotsLabel;
    private final Label typeOfParkingLabel;
    private final List<Label> tagLabels;


    public CarparkCardHandle(Node cardNode) {
        super(cardNode);

        addressLabel = getChildNode(ADDRESS_FIELD_ID);
        carparkNumberLabel = getChildNode(CARPARKNUMBER_FIELD_ID);
        coordinateLabel = getChildNode(COORDINATE_FIELD_ID);
        carparkTypeLabel = getChildNode(CARPARKTYPE_FIELD_ID);
        freeParkingLabel = getChildNode(FREEPARKING_FIELD_ID);
        lotsAvailableLabel = getChildNode(LOTSAVAILABLE_FIELD_ID);
        nightParkingLabel = getChildNode(NIGHTPARKING_FIELD_ID);
        shortTermLabel = getChildNode(SHORTTERM_FIELD_ID);
        totalLotsLabel = getChildNode(TOTALLOTS_FIELD_ID);
        typeOfParkingLabel = getChildNode(TYPEOFPARKING_FIELD_ID);

        Region tagsContainer = getChildNode(TAGS_FIELD_ID);
        tagLabels = tagsContainer
                .getChildrenUnmodifiable()
                .stream()
                .map(Label.class::cast)
                .collect(Collectors.toList());
    }

    public String getAddress() {
        return addressLabel.getText();
    }

    public String getCarparkNumber() {
        return carparkNumberLabel.getText();
    }

    public String getCoordinate() {
        return coordinateLabel.getText();
    }

    public String getCarparkType() {
        return carparkTypeLabel.getText();
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


    public List<String> getTags() {
        return tagLabels
                .stream()
                .map(Label::getText)
                .collect(Collectors.toList());
    }

    /**
     * Returns true if this handle contains {@code carpark}.
     */
    public boolean equals(Carpark Carpark) {
        return getAddress().equals(Carpark.getAddress().value)
                && getCarparkNumber().equals(Carpark.getCarparkNumber().value)
                && getCoordinate().equals(Carpark.getCoordinate().value)
                && getCarparkType().equals(Carpark.getCarparkType().value)
                && getFreeParking().equals(Carpark.getFreeParking().value)
                && getLotsAvailable().equals(Carpark.getLotsAvailable().value)
                && getNightParking().equals(Carpark.getNightParking().value)
                && getShortTerm().equals(Carpark.getShortTerm().value)
                && getTotalLots().equals(Carpark.getTotalLots().value)
                && getTypeOfParking().equals(Carpark.getTypeOfParking().value)
                && ImmutableMultiset.copyOf(getTags()).equals(ImmutableMultiset.copyOf(Carpark.getTags().stream()
                        .map(tag -> tag.tagName)
                        .collect(Collectors.toList())));
    }
}

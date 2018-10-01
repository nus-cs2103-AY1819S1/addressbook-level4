package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.carpark.Address;
import seedu.address.model.carpark.Carpark;
import seedu.address.model.carpark.CarparkNumber;
import seedu.address.model.carpark.CarparkType;
import seedu.address.model.carpark.Coordinate;
import seedu.address.model.carpark.FreeParking;
import seedu.address.model.carpark.LotsAvailable;
import seedu.address.model.carpark.NightParking;
import seedu.address.model.carpark.ShortTerm;
import seedu.address.model.carpark.TotalLots;
import seedu.address.model.carpark.TypeOfParking;
import seedu.address.model.tag.Tag;

/**
 * JAXB-friendly version of the Carpark.
 */
public class XmlAdaptedCarpark {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Carpark's %s field is missing!";

    @XmlElement(required = true)
    private String address;
    @XmlElement(required = true)
    private String carparkNumber;
    @XmlElement(required = true)
    private String carparkType;
    @XmlElement(required = true)
    private String coordinate;
    @XmlElement(required = true)
    private String freeParking;
    @XmlElement(required = true)
    private String lotsAvailable;
    @XmlElement(required = true)
    private String nightParking;
    @XmlElement(required = true)
    private String shortTerm;
    @XmlElement(required = true)
    private String totalLots;
    @XmlElement(required = true)
    private String typeOfParking;

    @XmlElement
    private List<XmlAdaptedTag> tagged = new ArrayList<>();

    /**
     * Constructs an XmlAdaptedCarpark.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedCarpark() {}

    /**
     * Constructs an {@code XmlAdaptedCarpark} with the given carpark details.
     */
    public XmlAdaptedCarpark(String address, String carkparkNumber, String carparkType, String coordinate,
                             String freeParking, String lotsAvailable, String nightParking, String shortTerm,
                             String totalLots, String typeOfParking, List<XmlAdaptedTag> tagged) {
        this.address = address;
        this.carparkNumber = carkparkNumber;
        this.carparkType = carparkType;
        this.coordinate = coordinate;
        this.freeParking = freeParking;
        this.lotsAvailable = lotsAvailable;
        this.nightParking = nightParking;
        this.shortTerm = shortTerm;
        this.totalLots = totalLots;
        this.typeOfParking = typeOfParking;

        if (tagged != null) {
            this.tagged = new ArrayList<>(tagged);
        }
    }

    /**
     * Converts a given Carpark into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedCarpark
     */
    public XmlAdaptedCarpark(Carpark source) {
        this.address = source.getAddress().value;
        this.carparkNumber = source.getCarparkNumber().value;
        this.carparkType = source.getCarparkType().value;
        this.coordinate = source.getCoordinate().value;
        this.freeParking = source.getFreeParking().value;
        this.lotsAvailable = source.getLotsAvailable().value;
        this.nightParking = source.getNightParking().value;
        this.shortTerm = source.getShortTerm().value;
        this.totalLots = source.getTotalLots().value;
        this.typeOfParking = source.getTypeOfParking().value;

        this.tagged = source.getTags().stream()
                .map(XmlAdaptedTag::new)
                .collect(Collectors.toList());
    }

    /**
     * Converts this jaxb-friendly adapted carpark object into the model's Carpark object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted carpark
     */
    public Carpark toModelType() throws IllegalValueException {
        final List<Tag> carparkTags = new ArrayList<>();
        for (XmlAdaptedTag tag : tagged) {
            carparkTags.add(tag.toModelType());
        }

        if (address == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Address.class.getSimpleName()));
        }
        if (!Address.isValidAddress(address)) {
            throw new IllegalValueException(Address.MESSAGE_ADDRESS_CONSTRAINTS);
        }
        final Address modelAddress = new Address(address);

        if (carparkNumber == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    CarparkNumber.class.getSimpleName()));
        }
        if (!CarparkNumber.isValidCarNum(carparkNumber)) {
            throw new IllegalValueException(CarparkNumber.MESSAGE_CAR_NUM_CONSTRAINTS);
        }
        final CarparkNumber modelCarparkNumber = new CarparkNumber(carparkNumber);

        if (carparkType == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    CarparkType.class.getSimpleName()));
        }
        if (!CarparkType.isValidCarType(carparkType)) {
            throw new IllegalValueException(CarparkType.MESSAGE_CAR_TYPE_CONSTRAINTS);
        }
        final CarparkType modelCarparkType = new CarparkType(carparkType);

        if (coordinate == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Coordinate.class.getSimpleName()));
        }
        if (!Coordinate.isValidCoord(coordinate)) {
            throw new IllegalValueException(Coordinate.MESSAGE_COORD_CONSTRAINTS);
        }
        final Coordinate modelCoordinate = new Coordinate(coordinate);

        if (freeParking == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    FreeParking.class.getSimpleName()));
        }
        if (!FreeParking.isValidFreePark(freeParking)) {
            throw new IllegalValueException(FreeParking.MESSAGE_FREE_PARK_CONSTRAINTS);
        }
        final FreeParking modelFreeParking = new FreeParking(freeParking);

        if (lotsAvailable == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    LotsAvailable.class.getSimpleName()));
        }
        if (!LotsAvailable.isValidLotsAvail(lotsAvailable)) {
            throw new IllegalValueException(LotsAvailable.MESSAGE_LOTS_AVAIL_CONSTRAINTS);
        }
        final LotsAvailable modelLotsAvailable = new LotsAvailable(lotsAvailable);

        if (nightParking == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    LotsAvailable.class.getSimpleName()));
        }
        if (!NightParking.isValidNightPark(nightParking)) {
            throw new IllegalValueException(NightParking.MESSAGE_NIGHT_PARK_CONSTRAINTS);
        }
        final NightParking modelNightParking = new NightParking(nightParking);

        if (shortTerm == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    ShortTerm.class.getSimpleName()));
        }
        if (!ShortTerm.isValidShortTerm(shortTerm)) {
            throw new IllegalValueException(ShortTerm.MESSAGE_SHORT_TERM_CONSTRAINTS);
        }
        final ShortTerm modelShortTerm = new ShortTerm(shortTerm);

        if (totalLots == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    TotalLots.class.getSimpleName()));
        }
        if (!TotalLots.isValidTotalLots(totalLots)) {
            throw new IllegalValueException(TotalLots.MESSAGE_TOTAL_LOTS_CONSTRAINTS);
        }
        final TotalLots modelTotalLots = new TotalLots(totalLots);

        if (typeOfParking == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    TypeOfParking.class.getSimpleName()));
        }
        if (!TypeOfParking.isValidTypePark(typeOfParking)) {
            throw new IllegalValueException(TypeOfParking.MESSAGE_TYPE_PARK_CONSTRAINTS);
        }
        final TypeOfParking modelTypeOfParking = new TypeOfParking(typeOfParking);

        final Set<Tag> modelTags = new HashSet<>(carparkTags);
        return new Carpark(modelAddress, modelCarparkNumber, modelCarparkType, modelCoordinate,
                modelFreeParking, modelLotsAvailable, modelNightParking, modelShortTerm, modelTotalLots,
                modelTypeOfParking, modelTags);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedCarpark)) {
            return false;
        }

        XmlAdaptedCarpark otherCarpark = (XmlAdaptedCarpark) other;
        return Objects.equals(address, otherCarpark.address)
                && Objects.equals(carparkNumber, otherCarpark.carparkNumber)
                && Objects.equals(carparkType, otherCarpark.carparkType)
                && Objects.equals(coordinate, otherCarpark.coordinate)
                && Objects.equals(freeParking, otherCarpark.freeParking)
                && Objects.equals(lotsAvailable, otherCarpark.lotsAvailable)
                && Objects.equals(nightParking, otherCarpark.nightParking)
                && Objects.equals(shortTerm, otherCarpark.shortTerm)
                && Objects.equals(totalLots, otherCarpark.totalLots)
                && Objects.equals(typeOfParking, otherCarpark.typeOfParking)
                && tagged.equals(otherCarpark.tagged);
    }
}

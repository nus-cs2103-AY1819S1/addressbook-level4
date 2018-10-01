package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
<<<<<<< HEAD
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
=======
import seedu.address.model.carpark.Carpark;
import seedu.address.model.carpark.CarparkNumber;
import seedu.address.model.carpark.LotType;
import seedu.address.model.carpark.LotsAvailable;
import seedu.address.model.carpark.TotalLots;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;

/**
 * JAXB-friendly version of the Person.
 */
public class XmlAdaptedCarpark {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Person's %s field is missing!";

    @XmlElement(required = true)
    private String carparkNumber;
    @XmlElement(required = true)
    private String lotsAvailable;
    @XmlElement(required = true)
    private String lotType;
    @XmlElement(required = true)
    private String totalLots;
    @XmlElement(required = true)
    private String address;
>>>>>>> master

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
<<<<<<< HEAD
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

=======
    public XmlAdaptedCarpark(String carkparkNumber, String lotsAvailable, String lotType, String totalLots, String address, List<XmlAdaptedTag> tagged) {
        this.carparkNumber = carkparkNumber;
        this.lotsAvailable = lotsAvailable;
        this.lotType = lotType;
        this.totalLots = totalLots;
        this.address = address;
>>>>>>> master
        if (tagged != null) {
            this.tagged = new ArrayList<>(tagged);
        }
    }

    /**
<<<<<<< HEAD
     * Converts a given Carpark into this class for JAXB use.
=======
     * Converts a given Person into this class for JAXB use.
>>>>>>> master
     *
     * @param source future changes to this will not affect the created XmlAdaptedCarpark
     */
    public XmlAdaptedCarpark(Carpark source) {
<<<<<<< HEAD
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

=======
        this.carparkNumber = source.getCarparkNumber().value;
        this.lotsAvailable = source.getLotsAvailable().value;
        this.lotType = source.getLotType().value;
        this.totalLots = source.getTotalLots().value;
        this.address = source.getAddress().value;
>>>>>>> master
        this.tagged = source.getTags().stream()
                .map(XmlAdaptedTag::new)
                .collect(Collectors.toList());
    }

    /**
<<<<<<< HEAD
     * Converts this jaxb-friendly adapted carpark object into the model's Carpark object.
=======
     * Converts this jaxb-friendly adapted carpark object into the model's Person object.
>>>>>>> master
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted carpark
     */
    public Carpark toModelType() throws IllegalValueException {
<<<<<<< HEAD
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
=======
        final List<Tag> personTags = new ArrayList<>();
        for (XmlAdaptedTag tag : tagged) {
            personTags.add(tag.toModelType());
        }

        if (carparkNumber == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Carpark.class.getSimpleName()));
        }
        if (!CarparkNumber.isValidCarParkNumber(carparkNumber)) {
            throw new IllegalValueException(Name.MESSAGE_NAME_CONSTRAINTS);
        }
        final CarparkNumber modelCarparkNumber = new CarparkNumber(carparkNumber);

        if (lotsAvailable == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, LotsAvailable.class.getSimpleName()));
        }
        if (!LotsAvailable.isValidLotsAvailable(lotsAvailable)) {
            throw new IllegalValueException(Phone.MESSAGE_PHONE_CONSTRAINTS);
        }
        final LotsAvailable modelLotsAvailable = new LotsAvailable (lotsAvailable);

        if (lotType == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, LotType.class.getSimpleName()));
        }
        if (!LotType.isValidLotType(lotType)) {
            throw new IllegalValueException(Email.MESSAGE_EMAIL_CONSTRAINTS);
        }
        final LotType modelLotType = new LotType(lotType);

        if (totalLots == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, TotalLots.class.getSimpleName()));
        }
        if (!TotalLots.isValidTotalLots(totalLots)) {
            throw new IllegalValueException(Email.MESSAGE_EMAIL_CONSTRAINTS);
        }
        final TotalLots modelTotalLots = new TotalLots(totalLots);

        if (address == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName()));
        }
        if (!Address.isValidAddress(address)) {
            throw new IllegalValueException(Address.MESSAGE_ADDRESS_CONSTRAINTS);
        }
        final Address modelAddress = new Address(address);

        final Set<Tag> modelTags = new HashSet<>(personTags);
        return new Carpark(modelCarparkNumber,modelTotalLots,modelLotsAvailable,modelLotType, modelAddress, modelTags);
>>>>>>> master
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedCarpark)) {
            return false;
        }

<<<<<<< HEAD
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
=======
        XmlAdaptedCarpark otherPerson = (XmlAdaptedCarpark) other;
        return Objects.equals(carparkNumber, otherPerson.carparkNumber)
                && Objects.equals(totalLots, otherPerson.totalLots)
                && Objects.equals(lotsAvailable, otherPerson.lotsAvailable)
                && Objects.equals(lotType, otherPerson.lotType)
                && Objects.equals(address, otherPerson.address)
                && tagged.equals(otherPerson.tagged);
>>>>>>> master
    }
}

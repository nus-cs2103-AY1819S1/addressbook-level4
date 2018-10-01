package seedu.address.model.carpark;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.tag.Tag;

/**
 * Represents a Carpark in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Carpark {
    // Identity fields
    private final Address address;
    private final CarparkNumber carparkNumber;
    private final Coordinate coordinate;

    // Data fields
    private final CarparkType carparkType;
    private final FreeParking freeParking;
    private final LotsAvailable lotsAvailable;
    private final NightParking nightParking;
    private final ShortTerm shortTerm;
    private final TotalLots totalLots;
    private final TypeOfParking typeOfParking;
    private final Set<Tag> tags = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Carpark(Address address, CarparkNumber carparkNumber, CarparkType carparkType, Coordinate coordinate,
                   FreeParking freeParking, LotsAvailable lotsAvailable, NightParking nightParking,
                   ShortTerm shortTerm, TotalLots totalLots, TypeOfParking typeOfParking, Set<Tag> tags) {
        requireAllNonNull(address, carparkNumber, carparkType, coordinate, freeParking, lotsAvailable,
                nightParking, shortTerm, totalLots, typeOfParking, tags);
        this.address = address;
        this.carparkNumber = carparkNumber;
        this.lotsAvailable = lotsAvailable;
        this.carparkType = carparkType;
        this.coordinate = coordinate;
        this.freeParking = freeParking;
        this.nightParking = nightParking;
        this.shortTerm = shortTerm;
        this.totalLots = totalLots;
        this.typeOfParking = typeOfParking;

        this.tags.addAll(tags);
    }

    public Carpark(String shortTerm, String carparkType, String yCoord, String xCoord, String freeParking,
                   String nightParking, String address, String id, String carparkNumber, String typeOfParking,
                   String totalLots, String lotsAvailable) {

        requireAllNonNull(address, carparkNumber, carparkType, xCoord, yCoord, freeParking,
                nightParking, shortTerm, typeOfParking);
        this.address = new Address(address);
        this.carparkNumber = new CarparkNumber(carparkNumber);
        this.carparkType = new CarparkType(carparkType);
        this.coordinate = new Coordinate(xCoord + ", " + yCoord);
        this.freeParking = new FreeParking(freeParking);
        this.nightParking = new NightParking(nightParking);
        this.shortTerm = new ShortTerm(shortTerm);
        this.typeOfParking = new TypeOfParking(typeOfParking);

        if (totalLots != null) {
            this.totalLots = new TotalLots(totalLots);
        } else {
            this.totalLots = new TotalLots("0");
        }

        if(lotsAvailable != null) {
            this.lotsAvailable = new LotsAvailable(lotsAvailable);
        } else {
            this.lotsAvailable = new LotsAvailable("0");
        }
    }

    public Carpark(String... data) {

        String shortTerm = data[0], carparkType = data[1], yCoord = data[2], xCoord = data[3],
                freeParking = data[4], nightParking = data[5], address = data[6], id = data[6],
                carparkNumber = data[7], typeOfParking = data[8];

        requireAllNonNull(address, carparkNumber, carparkType, xCoord, yCoord, freeParking,
                nightParking, shortTerm, typeOfParking);

        this.address = new Address(address);
        this.carparkNumber = new CarparkNumber(carparkNumber);
        this.carparkType = new CarparkType(carparkType);
        this.coordinate = new Coordinate(yCoord + ", " + xCoord);
        this.freeParking = new FreeParking(freeParking);
        this.nightParking = new NightParking(nightParking);
        this.shortTerm = new ShortTerm(shortTerm);
        this.typeOfParking = new TypeOfParking(typeOfParking);

        this.totalLots = new TotalLots("0");
        this.lotsAvailable = new LotsAvailable("0");
    }

    public Address getAddress() {
        return address;
    }

    public CarparkNumber getCarparkNumber() {
        return carparkNumber;
    }

    public CarparkType getCarparkType() {
        return carparkType;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public FreeParking getFreeParking() { return freeParking; }

    public LotsAvailable getLotsAvailable() {
        return lotsAvailable;
    }

    public NightParking getNightParking() {
        return nightParking;
    }

    public ShortTerm getShortTerm() {
        return shortTerm;
    }

    public TotalLots getTotalLots() {
        return totalLots;
    }

    public TypeOfParking getTypeOfParking() {
        return typeOfParking;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns true if both carparks of the same name have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two carparks.
     */
    public boolean isSameCarpark(Carpark otherCarpark) {
        if (otherCarpark == this) {
            return true;
        }
        return otherCarpark != null
                && otherCarpark.getAddress().equals(getAddress())
                && otherCarpark.getCarparkNumber().equals(getCarparkNumber())
                && otherCarpark.getCoordinate().equals((getCoordinate()));
    }

    /**
     * Returns true if both carparks have the same identity and data fields.
     * This defines a stronger notion of equality between two carparks.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Carpark)) {
            return false;
        }

        Carpark otherCarpark = (Carpark) other;
        return otherCarpark.getAddress().equals(getAddress())
                && otherCarpark.getCarparkNumber().equals(getCarparkNumber())
                && otherCarpark.getCarparkType().equals((getCarparkType()))
                && otherCarpark.getCoordinate().equals((getCoordinate()))
                && otherCarpark.getFreeParking().equals((getFreeParking()))
                && otherCarpark.getLotsAvailable().equals(getLotsAvailable())
                && otherCarpark.getNightParking().equals(getNightParking())
                && otherCarpark.getShortTerm().equals(getShortTerm())
                && otherCarpark.getTotalLots().equals(getTotalLots())
                && otherCarpark.getTypeOfParking().equals(getTypeOfParking())
                && otherCarpark.getTags().equals(getTags());
    }

    @Override
    public int hashCode() {
        return Objects.hash(address, carparkNumber, carparkType, coordinate, freeParking, lotsAvailable,
                nightParking, shortTerm, totalLots, typeOfParking, tags);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getCarparkNumber())
                .append(" Address: ")
                .append(getAddress())
                .append(" Coordinate: ")
                .append(getCoordinate())
                .append(" Type: ")
                .append(getCarparkType())
                .append(" Total Lots: ")
                .append(getTotalLots())
                .append(" Lots Available: ")
                .append(getLotsAvailable())
                .append(" Free Parking: ")
                .append(getFreeParking())
                .append(" Night Parking: ")
                .append(getNightParking())
                .append(" Short Term Parking: ")
                .append(getShortTerm())
                .append(" Parking System: ")
                .append(getTypeOfParking())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }
}

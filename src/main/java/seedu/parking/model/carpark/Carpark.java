package seedu.parking.model.carpark;

import static seedu.parking.commons.util.CollectionUtil.requireAllNonNull;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import com.google.gson.JsonObject;

import seedu.parking.model.tag.Tag;

/**
 * Represents a car park.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Carpark {
    // Identity fields
    private final Address address;
    private final CarparkNumber carparkNumber;
    private final Coordinate coordinate;
    private final LotsAvailable lotsAvailable;
    private final TotalLots totalLots;

    // Data fields
    private final CarparkType carparkType;
    private final FreeParking freeParking;
    private final NightParking nightParking;
    private final ShortTerm shortTerm;
    private final TypeOfParking typeOfParking;

    private final PostalCode postalCode;

    private final Set<Tag> tags = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Carpark(Address address, CarparkNumber carparkNumber, CarparkType carparkType, Coordinate coordinate,
                   FreeParking freeParking, LotsAvailable lotsAvailable, NightParking nightParking,
                   ShortTerm shortTerm, TotalLots totalLots, TypeOfParking typeOfParking, PostalCode postalCode,
                   Set<Tag> tags) {
        requireAllNonNull(address, carparkNumber, carparkType, coordinate, freeParking, lotsAvailable,
                nightParking, shortTerm, totalLots, typeOfParking, postalCode);
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
        this.postalCode = postalCode;

        if (tags != null) {
            this.tags.addAll(tags);
        }
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

    public FreeParking getFreeParking() {
        return freeParking;
    }

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

    public PostalCode getPostalCode() {
        return postalCode;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns true if both car parks of the same name have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two car parks.
     */
    public boolean isSameCarpark (Carpark otherCarpark) {
        if (otherCarpark == this) {
            return true;
        }
        return otherCarpark != null
                && otherCarpark.getAddress().equals(getAddress())
                && otherCarpark.getCarparkNumber().equals(getCarparkNumber())
                && otherCarpark.getCoordinate().equals((getCoordinate()))
                && otherCarpark.getLotsAvailable().equals(getLotsAvailable())
                && otherCarpark.getTotalLots().equals(getTotalLots());
    }

    /**
     * Returns true if both car parks have the same identity and data fields.
     * This defines a stronger notion of equality between two car parks.
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

    /**
     * Returns the car park object in Json format.
     */
    public String toJson () throws UnsupportedEncodingException {
        JsonObject jsonObject = new JsonObject();

        jsonObject.addProperty("address", address.value);
        jsonObject.addProperty("car_park_no", carparkNumber.value);
        jsonObject.addProperty("y_coord", coordinate.getYCoord());
        jsonObject.addProperty("x_coord", coordinate.getXCoord());
        jsonObject.addProperty("lots_available", lotsAvailable.value);
        jsonObject.addProperty("total_lots", totalLots.value);
        jsonObject.addProperty("car_park_type", carparkType.value);
        jsonObject.addProperty("free_parking", freeParking.value);
        jsonObject.addProperty("night_parking", nightParking.value);
        jsonObject.addProperty("short_term", shortTerm.value);
        jsonObject.addProperty("type_of_parking_system", typeOfParking.value);
        jsonObject.addProperty("postal_code", postalCode.value);

        return URLEncoder.encode(jsonObject.toString(), "UTF-8");
    }
}

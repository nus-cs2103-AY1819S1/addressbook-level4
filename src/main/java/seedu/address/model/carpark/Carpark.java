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
    private final CarparkNumber carparkNumber;
    private final Address address;

    // Data fields
    private final TotalLots totalLots;
    private final LotsAvailable lotsAvailable;
    private final Set<Tag> tags = new HashSet<>();

    public Carpark(CarparkNumber carparkNumber, TotalLots totalLots, LotsAvailable lotsAvailable,
                   Address address, Set<Tag> tags) {
        requireAllNonNull(carparkNumber, totalLots, lotsAvailable, address, tags);
        this.carparkNumber = carparkNumber;
        this.totalLots = totalLots;
        this.lotsAvailable = lotsAvailable;
        this.address = address;
        this.tags.addAll(tags);
    }

    public CarparkNumber getCarparkNumber() {
        return carparkNumber;
    }

    public TotalLots getTotalLots() {
        return totalLots;
    }

    public LotsAvailable getLotsAvailable() {
        return lotsAvailable;
    }

    public Address getAddress() {
        return address;
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
                && otherCarpark.getCarparkNumber().equals(getCarparkNumber())
                && otherCarpark.getAddress().equals(getAddress());
    }

    /**
     * Returns true if both carparks have the same identity and data fields.
     * This defines a stronger notion of equality between two carparks.
     */
    @Override
    public boolean equals (Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Carpark)) {
            return false;
        }

        Carpark otherCarpark = (Carpark) other;
        return otherCarpark.getCarparkNumber().equals(getCarparkNumber())
                && otherCarpark.getTotalLots().equals(getTotalLots())
                && otherCarpark.getLotsAvailable().equals(getLotsAvailable())
                && otherCarpark.getAddress().equals(getAddress())
                && otherCarpark.getTags().equals(getTags());
    }

    @Override
    public int hashCode() {
        return Objects.hash(carparkNumber, totalLots, lotsAvailable, address, tags);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getCarparkNumber())
                .append(" Total Lots: ")
                .append(getTotalLots())
                .append(" Lots Available: ")
                .append(getLotsAvailable())
                .append(" Address: ")
                .append(getAddress())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }
}

package seedu.address.model.carpark;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.person.Address;
import seedu.address.model.tag.Tag;

public class Carpark {
  private final CarparkNumber carparkNumber;
  private final TotalLots totalLots;
  private final LotsAvailable lotsAvailable;
  private final LotType lotType;

  private final Address address;
  private final Set<Tag> tags = new HashSet<>();

  public Carpark (CarparkNumber carparkNumber, TotalLots totalLots, LotsAvailable lotsAvailable, LotType lotType, Address address, Set<Tag> tags) {
    this.carparkNumber = carparkNumber;
    this.totalLots = totalLots;
    this.lotsAvailable = lotsAvailable;
    this.lotType = lotType;
    this.address = address;
    this.tags.addAll(tags);
  }

  public CarparkNumber getCarparkNumber() { return carparkNumber; }

  public TotalLots getTotalLots() { return totalLots; }

  public LotsAvailable getLotsAvailable() { return lotsAvailable; }

  public LotType getLotType() { return lotType; }

  public Address getAddress() { return address; }

  public Set<Tag> getTags() { return Collections.unmodifiableSet(tags); }

  public boolean isSameCarpark(Carpark otherCarpark){
    if(otherCarpark == this){
      return true;
    }
    return otherCarpark != null
            && otherCarpark.getCarparkNumber().equals(getCarparkNumber())
            && otherCarpark.getLotType().equals(getLotType())
            && otherCarpark.getAddress().equals(getAddress());
  }

  @Override
  public boolean equals (Object other){
    if(other == this){
      return true;
    }

    if(!(other instanceof Carpark)){
      return false;
    }

    Carpark otherCarpark = (Carpark) other;
    return otherCarpark.getCarparkNumber().equals(getCarparkNumber())
            && otherCarpark.getTotalLots().equals(getTotalLots())
            && otherCarpark.getLotsAvailable().equals(getLotsAvailable())
            && otherCarpark.getLotType().equals(getLotType())
            && otherCarpark.getAddress().equals(getAddress())
            && otherCarpark.getTags().equals(getTags());
  }

  @Override
  public int hashCode () {
    return Objects.hash(carparkNumber, totalLots, lotsAvailable, lotType, address, tags);
  }

  @Override
  public String toString() {
    final StringBuilder builder = new StringBuilder();
    builder.append(getCarparkNumber())
            .append(" Total Lots: ")
            .append(getTotalLots())
            .append(" Lots Available: ")
            .append(getLotsAvailable())
            .append(" Lot Type: ")
            .append(getLotType())
            .append(" Address: ")
            .append(getAddress())
            .append(" Tags: ");
    getTags().forEach(builder::append);
    return builder.toString();
  }

}

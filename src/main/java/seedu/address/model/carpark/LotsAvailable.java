package seedu.address.model.carpark;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

public class LotsAvailable {

  public static final String MESSAGE_LOTS_AVAILABLE_CONSTRAINTS =
          "";

  public static final String LOTS_AVAILABLE_VALIDATION_REGEX = "";
  public final String value;

  public LotsAvailable(String lotsAvailable) {
    requireNonNull(lotsAvailable);
//    checkArgument(isValidLotsAvailable(lotsAvailable), MESSAGE_LOTS_AVAILABLE_CONSTRAINTS);
    this.value = lotsAvailable;
  }

  public static boolean isValidLotsAvailable(String test) { return test.matches(LOTS_AVAILABLE_VALIDATION_REGEX); }

  @Override
  public int hashCode() { return value.hashCode(); }

  @Override
  public boolean equals(Object other) {
    return other == this
            || (other instanceof LotsAvailable
            && value.equals(((LotsAvailable)other).value));
  }

  @Override
  public String toString() { return value; }
}

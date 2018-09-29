package seedu.address.model.carpark;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

public class TotalLots {

  public static final String MESSAGE_TOTAL_LOTS_CONSTRAINTS =
          "";

  public static final String TOTAL_LOTS_VALIDATION_REGEX = "";
  public final String value;

  public TotalLots(String totalLots) {
    requireNonNull(totalLots);
//    checkArgument(isValidTotalLots(totalLots), MESSAGE_TOTAL_LOTS_CONSTRAINTS);
    this.value = totalLots;
  }

  public static boolean isValidTotalLots(String test) { return test.matches(TOTAL_LOTS_VALIDATION_REGEX); }

  @Override
  public int hashCode() { return value.hashCode(); }

  @Override
  public boolean equals(Object other) {
    return other == this // short circuit if same object
            || (other instanceof TotalLots // instanceof handles nulls
            && value.equals(((TotalLots) other).value)); // state check
  }

  @Override
  public String toString() { return value; }
}

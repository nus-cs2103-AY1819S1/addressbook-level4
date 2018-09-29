package seedu.address.model.carpark;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

public class CarparkNumber {
  public static final String MESSAGE_CARPARK_NUMBER_CONSTRAINTS =
          "";
  public static final String CARPARK_NUMBER_VALIDATION_REGEX = "";
  public final String value;

  public CarparkNumber(String carparkNumber) {
    requireNonNull(carparkNumber);
//    checkArgument(isValidCarParkNumber(carparkNumber), MESSAGE_CARPARK_NUMBER_CONSTRAINTS);
    this.value = carparkNumber;
  }

  public static boolean isValidCarParkNumber(String test) { return test.matches(CARPARK_NUMBER_VALIDATION_REGEX); }

  @Override
  public int hashCode() { return value.hashCode(); }

  @Override
  public boolean equals(Object other) {
    return other == this // short circuit if same object
            || (other instanceof CarparkNumber // instanceof handles nulls
            && value.equals(((CarparkNumber) other).value)); // state check
  }

  @Override
  public String toString() { return value; }
}

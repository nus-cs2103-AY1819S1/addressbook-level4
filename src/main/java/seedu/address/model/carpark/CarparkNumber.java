package seedu.address.model.carpark;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

<<<<<<< HEAD
/**
 * Represents a Carpark's number in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidCarNum(String)}
 */
public class CarparkNumber {

    public static final String MESSAGE_CAR_NUM_CONSTRAINTS =
            "Carpark number should only contain alphanumeric characters and spaces, "
                    + "and it should not be blank";

    /*
     * The first character must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String CAR_NUM_VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String value;

    /**
     * Constructs an {@code CarparkNumber}.
     *
     * @param carNum A valid carpark number.
     */
    public CarparkNumber(String carNum) {
        requireNonNull(carNum);
        checkArgument(isValidCarNum(carNum), MESSAGE_CAR_NUM_CONSTRAINTS);
        this.value = carNum;
    }

    public static boolean isValidCarNum(String test) {
        return test.matches(CAR_NUM_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CarparkNumber // instanceof handles nulls
                && value.equals(((CarparkNumber) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
=======
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
>>>>>>> master
}

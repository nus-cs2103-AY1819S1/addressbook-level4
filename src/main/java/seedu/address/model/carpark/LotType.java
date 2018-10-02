package seedu.address.model.carpark;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

public class LotType {

  public final String value;
  public static final String MESSAGE_LOT_TYPE_CONSTRAINTS = "SOMETHING WENT WRONG LOT TYPEEEEE";
  public static final String LOT_TYPE_VALIDATION_REGEX = "^[A-Z]$";

  public LotType(String lotType) {
    requireNonNull(lotType);
//    checkArgument(isValidLotType(lotType), MESSAGE_LOT_TYPE_CONSTRAINTS);
    this.value = lotType;
  }

  public static boolean isValidLotType(String test) { return test.matches(LOT_TYPE_VALIDATION_REGEX); }

  @Override
  public String toString (){
    return value;
  }

  @Override
  public boolean equals (Object other) {
    return other == this
            || (other instanceof LotType
            && value.equals(((LotType) other).value));
  }

  @Override
  public int hashCode () {
    return value.hashCode();
  }
}

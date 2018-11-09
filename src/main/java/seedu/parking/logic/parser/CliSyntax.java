package seedu.parking.logic.parser;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions */
    public static final Prefix PREFIX_ADDRESS = new Prefix("a/");
    public static final Prefix PREFIX_CAR_NUM = new Prefix("cn/");
    public static final Prefix PREFIX_CAR_TYPE = new Prefix("ct/");
    public static final Prefix PREFIX_COORD = new Prefix("c/");
    public static final Prefix PREFIX_FREE_PARK = new Prefix("fp/");
    public static final Prefix PREFIX_LOTS_AVAILABLE = new Prefix("la/");
    public static final Prefix PREFIX_NIGHT_PARK = new Prefix("np/");
    public static final Prefix PREFIX_SHORT_TERM = new Prefix("st/");
    public static final Prefix PREFIX_TOTAL_LOTS = new Prefix("tl/");
    public static final Prefix PREFIX_TYPE_PARK = new Prefix("tp/");
    // following 4 are used by autocomplete mechanism, need to be combined with
    // prefixes above. Please do not remove any of these below.
    public static final Prefix PREFIX_PARKING_TIME = new Prefix("f/");
    public static final Prefix PREFIX_NIGHT_PARKING = new Prefix("n/");
    public static final Prefix PREFIX_AVAILABLE_PARKING = new Prefix("a/");
    public static final Prefix PREFIX_SYSTEM_TYPE = new Prefix("ps/");

    public static final Prefix PREFIX_TAG = new Prefix("t/");
}

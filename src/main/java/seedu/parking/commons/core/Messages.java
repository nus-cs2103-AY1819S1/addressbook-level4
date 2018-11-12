package seedu.parking.commons.core;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";

    public static final String MESSAGE_INVALID_CARPARK_DISPLAYED_INDEX = "The car park index provided is invalid";
    public static final String MESSAGE_CARPARKS_LISTED_OVERVIEW = "%1$d car parks listed!";

    // filter command
    public static final String MESSAGE_FINDCOMMAND_NEEDS_TO_BE_EXECUTED_FIRST = "Please use the find command to find a "
            + "list of car parks within your preferred location first.";
    public static final String MESSAGE_FREEPARKING_HAS_INVALID_PARAMETERS = "Filtering by free parking needs to have the following parameters:  " +
            "f/ [day] [start time] [end time]\n     Example: filter f/ sun 7.30am 8.30pm";
    public static final String MESSAGE_START_OR_END_TIME_HAS_INCORRECT_FORMAT = "Please input a valid start and end time."
            + "\n     Example: 7.30am or 11.30pm";
    public static final String MESSAGE_DAY_IS_INVALID = "Please input a valid day.\n     Example: mon";
    public static final String MESSAGE_CARPARK_TYPE_IS_INVALID = "Please input a valid car park type: SURFACE, BASEMENT"
            + ", MULTISTOREY, MECHANISED, COVERED\n     Example: filter ct/ surface";
    public static final String MESSAGE_PARKINGSYSTEM_TYPE_IS_INVALID = "Please input a valid parking system type: " +
            "COUPON, ELECTRONIC\n     Example: filter ps/ coupon";

    // calculate command
    public static final String MESSAGE_INVALID_CARPARK_NAME = "The car park number input is invalid.";
    public static final String MESSAGE_ERROR_PARSING_CARPARK_INFO = "Error occurred while parsing car park information";
    public static final String MESSAGE_NO_SHORT_TERM_PARKING = "This car park has no short term parking.";
    public static final String MESSAGE_INVALID_START_OR_END_TIME = "Start and end of parking time must be between the s"
            + "tipulated timing.";
    public static final String MESSAGE_COST_OF_PARKING = "Cost of parking is $%1$.2f.";
}

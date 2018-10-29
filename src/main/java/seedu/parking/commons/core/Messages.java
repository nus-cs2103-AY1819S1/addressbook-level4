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
    public static final String MESSAGE_FINDCOMMAND_NEEDS_TO_BE_EXECUTED_FIRST = "Please use the find command to find a list of car parks within your preferred location first.";

    // calculate command
    public static final String MESSAGE_NO_SHORT_TERM_PARKING = "This car park has no short term parking.";
    public static final String MESSAGE_INVALID_START_OR_END_TIME = "Start and end of parking time must be between the stipulated timing.";
    // Todo: add FORMAT to those with %1$
    public static final String MESSAGE_COST_OF_PARKING = "Cost of parking is $%1$.2f.";
}

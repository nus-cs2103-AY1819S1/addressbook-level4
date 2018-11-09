package seedu.parking.commons.core;

/**
 * Container for user visible messages.
 */
public class Messages {

    // CarparkFinderParser
    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_UNCERTAIN_FIND_OR_FILTER_COMMAND =
        "Ambiguous command detected. You can type 'fin' (find [KEYWORD]) or 'fil' (filter [flag/PARAMETER])";
    public static final String MESSAGE_UNCERTAIN_HELP_OR_HISTORY_COMMAND =
        "Ambiguous command detected. You can type 'he' (help) or 'hi'(history)";
    public static final String MESSAGE_UNCERTAIN_CLEAR_OR_CALCULATE_COMMAND =
        "Ambiguous command. You can try 'cl' (clear) or 'ca' (calculate)";

    public static final String MESSAGE_INVALID_CARPARK_DISPLAYED_INDEX = "The car park index provided is invalid";
    public static final String MESSAGE_CARPARKS_LISTED_OVERVIEW = "%1$d car parks listed!";

    // filter command
    public static final String MESSAGE_FINDCOMMAND_NEEDS_TO_BE_EXECUTED_FIRST = "Please use the find command to find a "
            + "list of car parks within your preferred location first.";
    public static final String MESSAGE_START_OR_END_TIME_HAS_INCORRECT_FORMAT = "Please input start / end time in the "
            + "following format: hh.mmAM OR hh.mmPM e.g. 7.30AM";
    public static final String MESSAGE_DAY_IS_INVALID = "Please input a valid day. e.g. MON";
    public static final String MESSAGE_CARPARK_TYPE_IS_INVALID = "Please input a valid car park type: SURFACE, BASEMENT"
            + ", MULTISTOREY, MECHANISED, COVERED";

    // calculate command
    public static final String MESSAGE_NO_SHORT_TERM_PARKING = "This car park has no short term parking.";
    public static final String MESSAGE_INVALID_START_OR_END_TIME = "Start and end of parking time must be between the s"
            + "tipulated timing.";
    // Todo: add FORMAT to those with %1$
    public static final String MESSAGE_COST_OF_PARKING = "Cost of parking is $%1$.2f.";

    // auto-complete
    public static final String MESSAGE_INVALID_COMMAND_FOR_AUTOCOMPLETE =
        "Auto completion failed with this command word.";
}

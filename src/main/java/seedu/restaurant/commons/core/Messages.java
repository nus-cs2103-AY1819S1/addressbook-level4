package seedu.restaurant.commons.core;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command. To view the list of commands, enter `help`";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format!\n%1$s";

    // Account Management
    public static final String MESSAGE_COMMAND_FORBIDDEN = "Please login to execute privileged commands. For more "
            + "information, enter `help`.";

    // Ingredient Management
    public static final String MESSAGE_INVALID_INGREDIENT_DISPLAYED_INDEX = "The ingredient index provided is invalid";
    public static final String MESSAGE_INGREDIENT_NAME_NOT_FOUND = "The ingredient name provided does not exist";
    public static final String MESSAGE_STOCKUP_INGREDIENT_NOT_FOUND = "One or more of the ingredient names provided "
            + "do not exist";

    // Menu Management
    public static final String MESSAGE_INVALID_ITEM_DISPLAYED_INDEX = "The item index provided is invalid";
    public static final String MESSAGE_ITEMS_LISTED_OVERVIEW = "%1$d items listed!";
    public static final String MESSAGE_NAME_NOT_FOUND = "The name provided does not belong to the items in the menu";

    // Sales Management
    public static final String MESSAGE_INVALID_RECORD_DISPLAYED_INDEX = "The sales record index provided is invalid.";

    // Reservation Management
    public static final String MESSAGE_INVALID_RESERVATION_DISPLAYED_INDEX = "The reservation index provided is "
            + "invalid";
}

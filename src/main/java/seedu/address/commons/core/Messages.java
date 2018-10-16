package seedu.address.commons.core;

import seedu.address.logic.commands.EditCommand;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_PERSON_DISPLAYED_INDEX = "The person index provided is invalid";
    public static final String MESSAGE_PERSONS_LISTED_OVERVIEW = "%1$d persons listed!";
    public static final String MESSAGE_PERSON_NOT_FOUND = "The identifier provided does not match anyone in the "
            + "person list!";
    public static final String MESSAGE_MULTIPLE_PERSONS_FOUND = "The identifier provided matches multiple people in"
            + " the person list!\n"
            + "Please use the standard edit command:\n" + EditCommand.MESSAGE_USAGE;
    public static final String MESSAGE_TAGGED_PERSONS_LISTED_OVERVIEW = "%1$d tagged persons listed!";
    public static final String MESSAGE_TAG_DELETED_OVERVIEW = "%1$d persons untagged!";
    public static final String MESSAGE_CLASHING_MEETINGS = "There is already a meeting scheduled at the given time";

}

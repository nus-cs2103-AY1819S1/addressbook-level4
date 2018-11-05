package seedu.address.commons.core;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command!";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_PERSON_DISPLAYED_INDEX = "The patient index provided is invalid.";
    public static final String MESSAGE_PERSONS_LISTED_OVERVIEW = "%1$d persons listed!";
    public static final String MESSAGE_MEDICINES_LISTED_OVERVIEW = "%1$d medicines listed!";
    public static final String MESSAGE_DIFFERENT_BLOOD_TYPE = "Changing blood type of patient is not allowed!";
    public static final String MESSAGE_INVALID_MEDICINE_DISPLAYED_INDEX = "The medicine index provided is invalid!";

    public static final String MESSAGE_PERSON_IN_QUEUE = "%1$s is currently in the queue. "
            + "Remove %1$s from the queue first.";
    public static final String MESSAGE_PATIENT_MODIFIED_WHILE_IN_QUEUE = "Patient %1$s was modified while in queue, "
            + "removing %1$s from the queue.";
}

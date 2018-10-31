package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import seedu.address.model.person.Name;
import seedu.address.model.tag.Tag;

/**
 * Deletes a doctor from health book.
 */
public class DeleteDoctorCommand extends DeletePersonCommand {

    public static final String COMMAND_WORD = "delete-doctor";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the doctor identified by name. "
            + "Parameters: "
            + PREFIX_NAME + "NAME \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "John Doe ";

    /**
     * Creates an DeleteDoctorCommand to add the specified {@code Doctor}
     */
    public DeleteDoctorCommand(Name name) {
        super(name, new Tag("Doctor"));
    }
}

package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;

import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;

/**
 * Deletes a doctor from health book.
 */
public class DeleteDoctorCommand extends DeletePersonCommand {

    public static final String COMMAND_WORD = "delete-doctor";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the doctor identified by name. \n"
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + "[" + PREFIX_PHONE + "PHONE] \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "John Doe ";

    /**
     * Creates an DeleteDoctorCommand to add the specified {@code Doctor}
     */
    public DeleteDoctorCommand(Name name, Phone phone) {
        super(name, phone, new Tag("Doctor"));
    }
}

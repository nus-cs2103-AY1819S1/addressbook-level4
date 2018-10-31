package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import seedu.address.model.person.Name;
import seedu.address.model.tag.Tag;

/**
 * Deletes a patient from health book.
 */
public class DeletePatientCommand extends DeletePersonCommand {

    public static final String COMMAND_WORD = "delete-patient";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the patient identified by name. "
            + "Parameters: "
            + PREFIX_NAME + "NAME \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "John Doe ";

    /**
     * Creates an DeletePatientCommand to add the specified {@code Patient}
     */
    public DeletePatientCommand(Name name) {
        super(name, new Tag("Patient"));
    }
}

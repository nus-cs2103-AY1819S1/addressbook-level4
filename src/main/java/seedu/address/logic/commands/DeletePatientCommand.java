package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;

import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;

/**
 * Deletes a patient from health book.
 */
public class DeletePatientCommand extends DeleteCommand {

    public static final String COMMAND_WORD = "delete-patient";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the patient identified by name and phone. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_PHONE + "PHONE \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "John Doe "
            + PREFIX_PHONE + "98765432 ";

    public DeletePatientCommand(Name name, Phone phone) {
        super(name, phone);
    }
}

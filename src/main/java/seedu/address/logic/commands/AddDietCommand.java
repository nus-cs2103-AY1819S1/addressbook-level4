package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_NRIC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ALLERGY;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;


//@author yuntongzhang
/**
 * Add Diet requirements for a patient.
 *
 * @author yuntongzhang
 */
public class AddDietCommand extends Command {
    // TODO: currently this command only consider allergies, add cultural requirements, physical difficulties

    public static final String COMMAND_WORD = "adddiet";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds dietary requirements for a patient. "
                                               + "Parameters: "
                                               + PREFIX_NRIC + "NRIC"
                                               + PREFIX_ALLERGY + "ALLERGIES "
                                               + "Example: " + COMMAND_WORD + " "
                                               + PREFIX_NRIC + "S1234567M "
                                               + PREFIX_ALLERGY + "Egg ";

    public static final String MESSAGE_SUCCESS = "Added dietary requirements for patient: %1$s";

    private final Person patientToEdit;
    private final Diet dietToAdd;

    /**
     * Creates an AddDietCommand to add Diet to a patient.
     * @param patient The patient to be edited.
     * @param diet The diet to be added to the patient.
     */
    public AddDietCommand(Person patient, Diet diet) {
        patientToEdit = patient;
        dietToAdd = diet;
    }

    // TODO: finish this method
    @override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        return new CommandResult("Our team is still building this functionality.");
    }

    // Todo: finish this method
    @override
    public boolean equals(Object other) {
        return false;
    }
}

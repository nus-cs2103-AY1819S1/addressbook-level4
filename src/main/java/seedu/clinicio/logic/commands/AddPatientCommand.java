package seedu.clinicio.logic.commands;

import static seedu.clinicio.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.clinicio.logic.parser.CliSyntax.PREFIX_ALLERGIES;
import static seedu.clinicio.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.clinicio.logic.parser.CliSyntax.PREFIX_IC;
import static seedu.clinicio.logic.parser.CliSyntax.PREFIX_MEDICAL_PROBLEMS;
import static seedu.clinicio.logic.parser.CliSyntax.PREFIX_MEDICATIONS_LIST;
import static seedu.clinicio.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.clinicio.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.clinicio.logic.parser.CliSyntax.PREFIX_PREFERRED_DOCTOR;

import seedu.clinicio.logic.CommandHistory;
import seedu.clinicio.logic.commands.exceptions.CommandException;

import seedu.clinicio.model.Model;
import seedu.clinicio.model.analytics.Analytics;

//@@author jjlee050
/**
 * Adds a patient to ClinicIO.
 */
public class AddPatientCommand extends Command {

    public static final String COMMAND_WORD = "addpatient";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a patient to the ClinicIO. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_IC + " IC "
            + PREFIX_PHONE + "PHONE "
            + PREFIX_EMAIL + "EMAIL "
            + PREFIX_ADDRESS + "ADDRESS "
            + "[" + PREFIX_MEDICAL_PROBLEMS + "MEDICAL_PROBLEMS]..."
            + "[" + PREFIX_MEDICATIONS_LIST + "MEDICATIONS]..."
            + "[" + PREFIX_ALLERGIES + "ALLERGIES]..."
            + "[" + PREFIX_PREFERRED_DOCTOR + "PREFERRED_DOC]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "John Doe "
            + PREFIX_IC + " S1234567A "
            + PREFIX_PHONE + "98765432 "
            + PREFIX_EMAIL + "johnd@example.com "
            + PREFIX_ADDRESS + "311, Clementi Ave 2, #02-25 "
            + PREFIX_MEDICAL_PROBLEMS + "High Blood Pressure, Asthma ";

    public static final String MESSAGE_SUCCESS = "New patient added: %1$s";

    @Override
    public CommandResult execute(Model model, CommandHistory history, Analytics analytics)
            throws CommandException {
        return new CommandResult(MESSAGE_USAGE);
    }
}

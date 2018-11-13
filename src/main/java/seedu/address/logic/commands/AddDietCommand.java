package seedu.address.logic.commands;

//@@author yuntongzhang

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ALLERGY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CULTURAL_REQUIREMENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NRIC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHYSICAL_DIFFICULTY;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.diet.DietCollection;
import seedu.address.model.person.Nric;
import seedu.address.model.person.Person;

/**
 * Adds diet requirements to a patient.
 *
 * @author yuntongzhang
 */
public class AddDietCommand extends Command {

    public static final String COMMAND_WORD = "adddiet";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds dietary requirements for a patient. "
                                               + "Parameters: "
                                               + PREFIX_NRIC + "NRIC "
                                               + PREFIX_ALLERGY + "ALLERGY1 "
                                               + PREFIX_ALLERGY + "ALLERGY2 "
                                               + PREFIX_CULTURAL_REQUIREMENT + "REQUIREMENT "
                                               + PREFIX_PHYSICAL_DIFFICULTY + "DIFFICULTY\n"
                                               + "Example: " + COMMAND_WORD + " "
                                               + PREFIX_NRIC + "S1234567A "
                                               + PREFIX_ALLERGY + "Egg "
                                               + PREFIX_ALLERGY + "Crab "
                                               + PREFIX_CULTURAL_REQUIREMENT + "Halal "
                                               + PREFIX_PHYSICAL_DIFFICULTY + "Hands cannot move ";

    public static final String MESSAGE_SUCCESS = "Dietary requirements added for patient: %1$s";

    private final DietCollection dietsToAdd;
    private final Nric patientNric;

    /**
     * Creates an AddDietCommand to add dietary requirements to a patient.
     * @param patientNric NRIC of the patient to be updated.
     * @param dietCollection The collection of dietary requirements to be added to a patient.
     */
    public AddDietCommand(Nric patientNric, DietCollection dietCollection) {
        this.patientNric = requireNonNull(patientNric);
        this.dietsToAdd = requireNonNull(dietCollection);
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        Person patientToUpdate = CommandUtil.getPatient(patientNric, model);
        Person updatedPatient = addDietsForPatient(patientToUpdate, dietsToAdd);

        model.updatePerson(patientToUpdate, updatedPatient);
        return new CommandResult(String.format(MESSAGE_SUCCESS, patientNric));
    }

    /**
     * Adds more dietary requirements to a patient.
     *
     * @param patientToUpdate The patient to be updated.
     * @param dietsToAdd The diet requirements to be added to the patient.
     * @return An updated patient with the specified diet requirements added.
     */
    private static Person addDietsForPatient(Person patientToUpdate, DietCollection dietsToAdd) {
        assert patientToUpdate != null;

        DietCollection updatedDiet = new DietCollection(patientToUpdate.getDietCollection()).addMoreDiets(dietsToAdd);
        return patientToUpdate.withDietCollection(updatedDiet);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof AddDietCommand)) {
            return false;
        }

        AddDietCommand otherAddDietCommand = (AddDietCommand) other;
        return otherAddDietCommand.patientNric.equals(patientNric)
                && otherAddDietCommand.dietsToAdd.equals(dietsToAdd);
    }
}

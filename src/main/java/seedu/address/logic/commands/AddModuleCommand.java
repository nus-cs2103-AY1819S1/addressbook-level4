package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Objects;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.module.Module;

/**
 * Adds a module to the transcript.
 */
public class AddModuleCommand extends Command {
    /**
     * Command word for {@code AddModuleCommand}.
     */
    public static final String COMMAND_WORD = "add";

    /**
     * Usage of <b>add</b>.
     * <p>
     * Provides the description and syntax of <b>add</b>.
     */
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": "
            + "Adds a module.\n"
            + "Parameters: "
            + "-m MODULE_CODE "
            + "-y YEAR "
            + "-s SEMESTER "
            + "-c CREDIT "
            + "[-g GRADE]";

    /**
     * Message that informs that new module is added successfully.
     */
    public static final String MESSAGE_ADD_SUCCESS = "New module add: %1$s";

    /**
     * Message that informs that new module already exist.
     */
    public static final String MESSAGE_MODULE_ALREADY_EXIST = "New module"
            + " already exist.";

    /**
     * Module to be added.
     */
    private final Module toAdd;

    /**
     * Constructor that instantiates {@code AddModuleCommand}.
     * @param module
     */
    public AddModuleCommand(Module module) {
        requireNonNull(module);
        toAdd = module;
    }

    /**
     * Adds a module into the module list of transcript.
     * <p>
     * Throws {@code CommandException} when module to be added already exist.
     *
     * @param model {@code Model} that the command operates on.
     * @param history {@code CommandHistory} that the command operates on.
     * @return result of the command
     * @throws CommandException thrown when command cannot be executed
     * successfully
     */
    @Override
    public CommandResult execute(Model model, CommandHistory history)
            throws CommandException {
        // Model cannot be null.
        requireNonNull(model);

        // Throws CommandException if module already exists.
        editedModuleExist(model, toAdd);

        // Add module and commit.
        model.addModule(toAdd);
        model.commitTranscript();

        // Return success message.
        String successMsg = String.format(MESSAGE_ADD_SUCCESS, toAdd);
        return new CommandResult(successMsg);
    }

    /**
     * Throws {@code CommandException} if {@code toAdd} already exist.
     *
     * @param model {@code Model} that the command operates on.
     * @param toAdd module to be added
     * @throws CommandException thrown if {@code toAdd} already exist
     */
    private void editedModuleExist(Model model, Module toAdd)
            throws CommandException {

        if (model.hasModule(toAdd)) {
            throw new CommandException(MESSAGE_MODULE_ALREADY_EXIST);
        }
    }

    /**
     * Returns true if all {@code toAdd} matches.
     *
     * @param other the other object compared against
     * @return true if all field matches
     */
    @Override
    public boolean equals(Object other) {
        // Short circuit if same object.
        if (other == this) {
            return true;
        }

        // instanceof handles nulls.
        if (!(other instanceof AddModuleCommand)) {
            return false;
        }

        // State check.
        AddModuleCommand e = (AddModuleCommand) other;
        return Objects.equals(toAdd, e.toAdd);
    }
}

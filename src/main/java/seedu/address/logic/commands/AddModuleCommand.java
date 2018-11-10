package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.module.Module;

/**
 * Adds a module to the transcript.
 */
public class AddModuleCommand extends Command {
    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": "
            + "Adds a module.\n"
            + "Parameters: "
            + "-m MODULE_CODE "
            + "-y YEAR "
            + "-s SEMESTER "
            + "-c CREDIT "
            + "[-g GRADE]";

    // Constants for CommandException.
    public static final String MESSAGE_ADD_SUCCESS = "New module add: %1$s";
    public static final String MESSAGE_MODULE_ALREADY_EXIST = "New module"
            + " already exist.";

    private final Module toAdd;

    /**
     * Constructor that instantiates {@code AddModuleCommand}.
     * @param module
     */
    public AddModuleCommand(Module module) {
        requireNonNull(module);
        toAdd = module;
    }

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
        String successMsg = String.format(MESSAGE_ADD_SUCCESS, tooAdd);
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

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddModuleCommand // instanceof handles nulls
                && toAdd.equals(((AddModuleCommand) other).toAdd));
    }
}

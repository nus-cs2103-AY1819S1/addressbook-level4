package seedu.address.logic.commands;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.module.Module;

import static java.util.Objects.requireNonNull;

/**
 * Adds a module to the address book.
 */
public class AddModuleCommand extends Command {
    public static final String MESSAGE_SUCCESS = "New module added: %1$s";
    public static final String MESSAGE_DUPLICATE_MODULE = "This person already exists in the address book";

    private final Module toAdd;

    public AddModuleCommand(Module module) {
        this.toAdd = module;
    }
    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

//        if (model.hasEntity(toAdd)) {
//            throw new CommandException(MESSAGE_DUPLICATE_MODULE);
//        }
//
//        model.addEntity(toAdd);
//        model.commitAddressBook();
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }



    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddModuleCommand // instanceof handles nulls
                && toAdd.equals(((AddModuleCommand) other).toAdd));
    }
}

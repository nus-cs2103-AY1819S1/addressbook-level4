package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ACADEMICYEAR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULECODE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULETITLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SEMESTER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.module.Module;



/**
 * Adds a module to the address book.
 */
public class AddModuleCommand extends Command {

    public static final String COMMAND_WORD = "addmodule";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a module to the address book. \n"
            + "Parameters: "
            + PREFIX_MODULECODE + "MODULE_CODE "
            + PREFIX_MODULETITLE + "MODULE_TITLE "
            + PREFIX_ACADEMICYEAR + "ACADEMIC_YEAR "
            + PREFIX_SEMESTER + "SEMESTER"
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_MODULECODE + "CS2103 "
            + PREFIX_MODULETITLE + "SOFTWARE ENGINEERING "
            + PREFIX_ACADEMICYEAR + "1718 "
            + PREFIX_SEMESTER + "1 "
            + PREFIX_TAG + "gg ";

    public static final String MESSAGE_SUCCESS = "New module added: %1$s";
    public static final String MESSAGE_DUPLICATE_MODULE = "This module already exists in the address book";

    private final Module toAdd;

    public AddModuleCommand(Module module) {
        requireNonNull(module);
        this.toAdd = module;
    }
    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (model.hasModule(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_MODULE);
        }

        model.addModule(toAdd);
        model.commitAddressBook();
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }



    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddModuleCommand // instanceof handles nulls
                && toAdd.equals(((AddModuleCommand) other).toAdd));
    }
}

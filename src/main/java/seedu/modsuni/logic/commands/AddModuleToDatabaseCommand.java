package seedu.modsuni.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.modsuni.logic.parser.CliSyntax.PREFIX_MODULE_AVAILABLE;
import static seedu.modsuni.logic.parser.CliSyntax.PREFIX_MODULE_CODE;
import static seedu.modsuni.logic.parser.CliSyntax.PREFIX_MODULE_CREDIT;
import static seedu.modsuni.logic.parser.CliSyntax.PREFIX_MODULE_DEPARTMENT;
import static seedu.modsuni.logic.parser.CliSyntax.PREFIX_MODULE_DESCRIPTION;
import static seedu.modsuni.logic.parser.CliSyntax.PREFIX_MODULE_PREREQ;
import static seedu.modsuni.logic.parser.CliSyntax.PREFIX_MODULE_TITLE;

import seedu.modsuni.logic.CommandHistory;
import seedu.modsuni.logic.commands.exceptions.CommandException;
import seedu.modsuni.model.Model;
import seedu.modsuni.model.module.Module;

/**
 * Adds a new module to database.
 */
public class AddModuleToDatabaseCommand extends Command {
    public static final String COMMAND_WORD = "addModuleDB";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a new module to database. "
            + "Parameters: "
            + PREFIX_MODULE_CODE + "<MODULE_CODE> "
            + PREFIX_MODULE_DEPARTMENT + "<DEPARTMENT> "
            + PREFIX_MODULE_TITLE + "<TITLE> "
            + PREFIX_MODULE_DESCRIPTION + "<DESCRIPTION> "
            + PREFIX_MODULE_CREDIT + "<CREDIT> "
            + PREFIX_MODULE_AVAILABLE + "<Sem1><Sem2><SpecialTerm1><SpecialTerm2> "
            + PREFIX_MODULE_PREREQ + "<PREREQ_STRING> "
            + "Refer to the help window for more information on the format of available/ and prereq/\n"
            + "Example " + COMMAND_WORD + " "
            + PREFIX_MODULE_CODE + "CS2109 "
            + PREFIX_MODULE_DEPARTMENT + "SOC "
            + PREFIX_MODULE_TITLE + "The new module "
            + PREFIX_MODULE_DESCRIPTION + "This is a new module that will be offered starting from this semester "
            + PREFIX_MODULE_CREDIT + "4 "
            + PREFIX_MODULE_AVAILABLE + "1100 "
            + PREFIX_MODULE_PREREQ + "|(|CS1020,CS1020E,CS2020,)(&CS2030,(|CS2040,CS2040C,))";

    public static final String MESSAGE_DUPLICATE_MODULE = "This module already exist in the database";
    public static final String MESSAGE_SUCCESS = "New module added to database";
    public static final String MESSAGE_NOT_ADMIN = "Only an admin user can execute this command";
    public static final String MESSAGE_NOT_LOGGED_IN = "Unable to add, please log in first.";


    private final Module toAdd;

    public AddModuleToDatabaseCommand(Module module) {
        requireNonNull(module);
        toAdd = module;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        if (model.getCurrentUser() == null) {
            throw new CommandException(MESSAGE_NOT_LOGGED_IN);
        }

        if (!model.isAdmin()) {
            throw new CommandException(MESSAGE_NOT_ADMIN);
        }

        if (model.hasModuleInDatabase(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_MODULE);
        }

        model.addModuleToDatabase(toAdd);

        return new CommandResult(String.format(MESSAGE_SUCCESS));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddModuleToDatabaseCommand // instanceof handles nulls
                && toAdd.equals(((AddModuleToDatabaseCommand) other).toAdd));
    }
}

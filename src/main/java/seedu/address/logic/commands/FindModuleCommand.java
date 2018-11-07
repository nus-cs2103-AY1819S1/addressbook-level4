package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.TypeUtil.MODULE;

import java.util.function.Predicate;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.events.ui.ShowModuleRequestEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.module.Module;

/**
 * Finds and lists all modules in address book whose module code, or module title, or academic year, or semester
 * contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindModuleCommand extends Command {
    public static final String COMMAND_WORD = "findmodule";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all modules whose module code, module title, "
            + "academic year, semester contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " mc/cs2103 cs2103t"
            + "Example: " + COMMAND_WORD + " mt/software engineering"
            + "Example: " + COMMAND_WORD + " ay/0102"
            + "Example: " + COMMAND_WORD + " sem/2";

    private final Predicate<Module> predicate;

    public FindModuleCommand(Predicate<Module> predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.updateFilteredModuleList(predicate);
        model.setActiveType(MODULE);
        EventsCenter.getInstance().post(new ShowModuleRequestEvent());
        return new CommandResult(
                String.format(Messages.MESSAGE_MODULES_LISTED_OVERVIEW, model.getFilteredModuleList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindModuleCommand // instanceof handles nulls
                && predicate.equals(((FindModuleCommand) other).predicate)); // state check
    }
}

package ssp.scheduleplanner.logic.commands;

import static java.util.Objects.requireNonNull;

import ssp.scheduleplanner.commons.core.EventsCenter;
import ssp.scheduleplanner.commons.events.ui.ChangeViewEvent;
import ssp.scheduleplanner.logic.CommandHistory;
import ssp.scheduleplanner.logic.commands.exceptions.CommandException;
import ssp.scheduleplanner.logic.parser.CliSyntax;
import ssp.scheduleplanner.model.Model;

/**
 * Clears all existing tags of a category from schedule planner.
 */
public class ClearCategoryCommand extends Command {
    public static final String COMMAND_WORD = "clearcat";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Remove a category from the Schedule Planner. "
            + "Parameters: "
            + CliSyntax.PREFIX_CATEGORY + "CATEGORY \n"
            + "Example: " + COMMAND_WORD + " "
            + CliSyntax.PREFIX_CATEGORY + "Modules \n";

    public static final String MESSAGE_SUCCESS = "Category %1$s has been cleared!";
    public static final String MESSAGE_NONEXISTENT_CATEGORY =
            "This category does not exist your schedule planner T_T";

    private final String categoryName;

    /**
     * Creates an ClearCategoryCommand to add the specified {@code name}
     */
    public ClearCategoryCommand(String name) {
        requireNonNull(name);
        categoryName = name;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (!model.hasCategory(categoryName)) {
            throw new CommandException(MESSAGE_NONEXISTENT_CATEGORY);
        }

        model.clearCategory(categoryName);
        model.commitSchedulePlanner();
        EventsCenter.getInstance().post(new ChangeViewEvent(ChangeViewEvent.View.NORMAL));
        return new CommandResult(String.format(MESSAGE_SUCCESS, categoryName));
    }
}

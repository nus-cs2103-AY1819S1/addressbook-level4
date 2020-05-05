package ssp.scheduleplanner.logic.commands;

import static java.util.Objects.requireNonNull;

import ssp.scheduleplanner.commons.core.EventsCenter;
import ssp.scheduleplanner.commons.events.ui.ChangeViewEvent;
import ssp.scheduleplanner.logic.CommandHistory;
import ssp.scheduleplanner.logic.commands.exceptions.CommandException;
import ssp.scheduleplanner.logic.parser.CliSyntax;
import ssp.scheduleplanner.model.Model;

/**
 * Adds a category to schedule planner.
 */
public class AddCategoryCommand extends Command {
    public static final String COMMAND_WORD = "addcat";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Adds a category to the Schedule Planner. "
            + "Parameters: "
            + CliSyntax.PREFIX_CATEGORY + "CATEGORY \n"
            + "Example: " + COMMAND_WORD + " "
            + CliSyntax.PREFIX_CATEGORY + "SteamList ";

    public static final String MESSAGE_SUCCESS = "New category has been added: %1$s ";
    public static final String MESSAGE_DUPLICATE_CATEGORY =
            "This category exists your schedule planner! Please consider alternative naming ";

    private final String categoryName;

    /**
     * Creates an AddCategoryCommand to add the specified {@code Category}
     */
    public AddCategoryCommand(String name) {
        requireNonNull(name);
        categoryName = name;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (model.hasCategory(categoryName)) {
            throw new CommandException(MESSAGE_DUPLICATE_CATEGORY);
        }

        model.addCategory(categoryName);
        model.commitSchedulePlanner();
        EventsCenter.getInstance().post(new ChangeViewEvent(ChangeViewEvent.View.NORMAL));
        return new CommandResult(String.format(MESSAGE_SUCCESS, categoryName));
    }
}

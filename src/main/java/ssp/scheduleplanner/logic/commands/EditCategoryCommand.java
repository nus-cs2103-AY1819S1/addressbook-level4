package ssp.scheduleplanner.logic.commands;

import static java.util.Objects.requireNonNull;

import ssp.scheduleplanner.commons.core.EventsCenter;
import ssp.scheduleplanner.commons.events.ui.ChangeViewEvent;
import ssp.scheduleplanner.logic.CommandHistory;
import ssp.scheduleplanner.logic.commands.exceptions.CommandException;
import ssp.scheduleplanner.logic.parser.CliSyntax;
import ssp.scheduleplanner.model.Model;

/**
 * Edit the name of a category to schedule planner.
 */
public class EditCategoryCommand extends Command {
    public static final String COMMAND_WORD = "editcat";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Edit the name of a category in the Schedule Planner. "
            + "Parameters: "
            + CliSyntax.PREFIX_CATEGORY + "ORIGINALNAME " + CliSyntax.PREFIX_CATEGORY + "NEWNAME\n"
            + "Example: " + COMMAND_WORD + " "
            + CliSyntax.PREFIX_CATEGORY + "Steam discount list " + CliSyntax.PREFIX_CATEGORY + "Switch discount list";

    public static final String MESSAGE_SUCCESS = "Category %1$s has been changed to: %2$s ";
    public static final String MESSAGE_DUPLICATE_CATEGORY =
            "This category exists your schedule planner! Please consider alternative naming ";
    public static final String MESSAGE_SAME_NAME =
            "The new name given is same as original name. Please consider another name T_T";
    public static final String MESSAGE_DEFAULT_CATEGORY =
            "Default categories (Modules and Others) cannot be changed.";

    private final String originalName;
    private final String newName;

    /**
     * Creates an AddCategoryCommand to add the specified {@code Category}
     */
    public EditCategoryCommand(String original, String changeTo) {
        requireNonNull(changeTo);
        requireNonNull(original);
        originalName = original;
        newName = changeTo;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (model.hasCategory(newName)) {
            throw new CommandException(MESSAGE_DUPLICATE_CATEGORY);
        }

        if (originalName.equals(newName)) {
            throw new CommandException(MESSAGE_SAME_NAME);
        }

        if (originalName.equals("Modules") || originalName.equals("Others")) {
            throw new CommandException((MESSAGE_DEFAULT_CATEGORY));
        }

        model.editCategory(originalName, newName);
        model.commitSchedulePlanner();
        EventsCenter.getInstance().post(new ChangeViewEvent(ChangeViewEvent.View.NORMAL));
        return new CommandResult(String.format(MESSAGE_SUCCESS, originalName, newName));
    }
}

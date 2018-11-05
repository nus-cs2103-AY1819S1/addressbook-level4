package seedu.souschef.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.souschef.commons.core.EventsCenter;
import seedu.souschef.commons.core.Messages;
import seedu.souschef.commons.core.index.Index;
import seedu.souschef.commons.events.ui.JumpToListRequestEvent;
import seedu.souschef.logic.History;
import seedu.souschef.logic.commands.exceptions.CommandException;
import seedu.souschef.model.Model;
import seedu.souschef.model.recipe.Recipe;

/**
 * Selects a recipe identified using it's displayed index from the address book.
 */
public class SelectCommand extends Command {

    public static final String COMMAND_WORD = "select";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Selects the recipe identified by the index number used in the displayed recipe list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1\n\n";

    public static final String MEALPLANNER_MESSAGE_USAGE = COMMAND_WORD
            + ": Selects the recipe at the specified index and meal slot.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "            MEAL SLOT (must be either breakfast, lunch or dinner, and must be non-empty)\n"
            + "Example: " + COMMAND_WORD + " 1 lunch";

    public static final String MESSAGE_SELECT_RECIPE_SUCCESS = "Selected Recipe: %1$s";

    private final Model model;
    private final Index targetIndex;

    public SelectCommand(Model model, Index targetIndex) {
        this.model = model;
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(History history) throws CommandException {
        requireNonNull(model);

        List<Recipe> filteredRecipeList = model.getFilteredList();

        if (targetIndex.getZeroBased() >= filteredRecipeList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_RECIPE_DISPLAYED_INDEX);
        }

        EventsCenter.getInstance().post(new JumpToListRequestEvent(targetIndex));
        return new CommandResult(String.format(MESSAGE_SELECT_RECIPE_SUCCESS, targetIndex.getOneBased()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SelectCommand // instanceof handles nulls
                && targetIndex.equals(((SelectCommand) other).targetIndex)); // state check
    }
}

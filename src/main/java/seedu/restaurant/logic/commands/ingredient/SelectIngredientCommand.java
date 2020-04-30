package seedu.restaurant.logic.commands.ingredient;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.restaurant.commons.core.EventsCenter;
import seedu.restaurant.commons.core.Messages;
import seedu.restaurant.commons.core.index.Index;
import seedu.restaurant.commons.events.ui.ingredient.DisplayIngredientListRequestEvent;
import seedu.restaurant.commons.events.ui.ingredient.JumpToIngredientListRequestEvent;
import seedu.restaurant.logic.CommandHistory;
import seedu.restaurant.logic.commands.Command;
import seedu.restaurant.logic.commands.CommandResult;
import seedu.restaurant.logic.commands.exceptions.CommandException;
import seedu.restaurant.model.Model;
import seedu.restaurant.model.ingredient.Ingredient;

//@@author rebstan97
/**
 * Selects an ingredient identified using its displayed index from the ingredient list.
 */
public class SelectIngredientCommand extends Command {

    public static final String COMMAND_WORD = "select-ingredient";

    public static final String COMMAND_ALIAS = "select-ing";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Selects the ingredient identified by the index number used in the displayed ingredient list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SELECT_INGREDIENT_SUCCESS = "Selected Ingredient: %1$s";

    private final Index targetIndex;

    public SelectIngredientCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        List<Ingredient> filteredIngredientList = model.getFilteredIngredientList();

        if (targetIndex.getZeroBased() >= filteredIngredientList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_INGREDIENT_DISPLAYED_INDEX);
        }
        EventsCenter.getInstance().post(new DisplayIngredientListRequestEvent());
        EventsCenter.getInstance().post(new JumpToIngredientListRequestEvent(targetIndex));
        return new CommandResult(String.format(MESSAGE_SELECT_INGREDIENT_SUCCESS, targetIndex.getOneBased()));

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SelectIngredientCommand // instanceof handles nulls
                    && targetIndex.equals(((SelectIngredientCommand) other).targetIndex)); // state check
    }
}

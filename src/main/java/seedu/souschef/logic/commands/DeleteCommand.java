package seedu.souschef.logic.commands;

import seedu.souschef.commons.core.EventsCenter;
import seedu.souschef.commons.events.model.MealPlanDeletedEvent;
import seedu.souschef.commons.events.model.RecipeDeletedEvent;
import seedu.souschef.logic.History;
import seedu.souschef.logic.parser.Context;
import seedu.souschef.model.Model;
import seedu.souschef.model.UniqueType;
import seedu.souschef.model.planner.Day;
import seedu.souschef.model.recipe.Recipe;

/**
 * Deletes a recipe identified using it's displayed index.
 */
public class DeleteCommand<T extends UniqueType> extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_DELETE_SUCCESS = "Deleted %1$s: %2$s";

    private final Model<T> model;
    private final T toDelete;

    public DeleteCommand(Model<T> model, T toDelete) {
        this.model = model;
        this.toDelete = toDelete;
    }

    @Override
    public CommandResult execute(History history) {
        model.delete(toDelete);
        raiseEvent(history.getContext());
        model.commitAppContent();
        return new CommandResult(String.format(MESSAGE_DELETE_SUCCESS,
                history.getContextString(), toDelete));
    }

    /**
     * Raises a RecipeDeletedEvent or MealPlanDeletedEvent based on the current context.
     * @param context
     */
    private void raiseEvent(Context context) {
        if (context.equals(Context.RECIPE)) {
            EventsCenter.getInstance().post(new RecipeDeletedEvent((Recipe) toDelete));
        } else if (context.equals(Context.MEAL_PLAN)) {
            EventsCenter.getInstance().post(new MealPlanDeletedEvent((Day) toDelete, Context.MEAL_PLAN));
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteCommand // instanceof handles nulls
                && model.equals(((DeleteCommand<T>) other).model)
                && toDelete.equals(((DeleteCommand<T>) other).toDelete)); // state check
    }
}

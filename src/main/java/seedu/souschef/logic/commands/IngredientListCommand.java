package seedu.souschef.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.souschef.model.Model.PREDICATE_SHOW_ALL;

import seedu.souschef.logic.History;
import seedu.souschef.logic.IngredientDateComparator;
import seedu.souschef.model.Model;

/**
 * Sorts all ingredients in ingredient manager by date and list.
 */
public class IngredientListCommand extends Command {
    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "Listed all ingredients.";

    private final Model model;

    public IngredientListCommand(Model model) {
        this.model = model;
    }

    @Override
    public CommandResult execute(History history) {
        requireNonNull(model);
        model.sort(new IngredientDateComparator());
        model.updateFilteredList(PREDICATE_SHOW_ALL);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}

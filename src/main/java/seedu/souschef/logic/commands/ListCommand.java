package seedu.souschef.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.souschef.model.Model.PREDICATE_SHOW_ALL;

import seedu.souschef.logic.History;
import seedu.souschef.model.Model;
import seedu.souschef.model.UniqueType;

/**
 * Lists all contents in the list to the user.
 */
public class ListCommand<T extends UniqueType> extends Command {
    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "Listed all %1$s.";

    private final Model<T> model;

    public ListCommand(Model<T> model) {
        this.model = model;
    }

    @Override
    public CommandResult execute(History history) {
        requireNonNull(model);
        model.updateFilteredList(PREDICATE_SHOW_ALL);
        return new CommandResult(String.format(MESSAGE_SUCCESS, history.getContextString()));
    }
}

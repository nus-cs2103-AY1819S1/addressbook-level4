package seedu.souschef.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.souschef.logic.History;
import seedu.souschef.model.Model;
import seedu.souschef.model.UniqueType;

/**
 * Adds a recipe to the address book.
 */
public class AddCommand<T extends UniqueType> extends Command {

    public static final String COMMAND_WORD = "add";
    public static final String COMMAND_WORD_END = "end";

    public static final String MESSAGE_ADD_SUCCESS = "New %1$s added: %2$s";

    private final Model model;
    private final T toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Recipe}
     */
    public AddCommand(Model model, T toAdd) {
        requireNonNull(toAdd);
        this.model = model;
        this.toAdd = toAdd;
    }

    @Override
    public CommandResult execute(History history) {
        model.add(toAdd);
        model.commitAppContent();
        return new CommandResult(String.format(MESSAGE_ADD_SUCCESS,
                history.getContextString(), toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddCommand // instanceof handles nulls
                && model.equals(((AddCommand<T>) other).model)
                && toAdd.equals(((AddCommand<T>) other).toAdd));
    }
}

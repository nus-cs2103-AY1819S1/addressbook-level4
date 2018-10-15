package seedu.souschef.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.souschef.logic.parser.CliSyntax.PREFIX_COOKTIME;
import static seedu.souschef.logic.parser.CliSyntax.PREFIX_DIFFICULTY;
import static seedu.souschef.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.souschef.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.souschef.logic.CommandHistory;
import seedu.souschef.model.Model;
import seedu.souschef.model.UniqueType;

/**
 * Adds a recipe to the address book.
 */
public class AddCommand<T extends UniqueType> extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a recipe to the address book. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_DIFFICULTY + "DIFFICULTY "
            + PREFIX_COOKTIME + "TIME "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "John Doe "
            + PREFIX_DIFFICULTY + "5 "
            + PREFIX_COOKTIME + "PT1H20M "
            + PREFIX_TAG + "friends "
            + PREFIX_TAG + "owesMoney";

    public static final String MESSAGE_SUCCESS = "New recipe added: %1$s";
    public static final String MESSAGE_DUPLICATE_RECIPE = "This recipe already exists in the address book";

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
    public CommandResult execute(CommandHistory history) {
        model.add(toAdd);
        model.commitAppContent();
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddCommand // instanceof handles nulls
                && model.equals(((AddCommand<T>) other).model)
                && toAdd.equals(((AddCommand<T>) other).toAdd));
    }
}

package seedu.souschef.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.souschef.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.souschef.logic.parser.CliSyntax.PREFIX_AGE;
import static seedu.souschef.logic.parser.CliSyntax.PREFIX_CHEIGHT;
import static seedu.souschef.logic.parser.CliSyntax.PREFIX_CWEIGHT;
import static seedu.souschef.logic.parser.CliSyntax.PREFIX_DURATION;
import static seedu.souschef.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.souschef.logic.parser.CliSyntax.PREFIX_HPNAME;
import static seedu.souschef.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.souschef.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.souschef.logic.parser.CliSyntax.PREFIX_SCHEME;
import static seedu.souschef.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.souschef.logic.parser.CliSyntax.PREFIX_TWEIGHT;

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
            + PREFIX_PHONE + "PHONE "
            + PREFIX_EMAIL + "EMAIL "
            + PREFIX_ADDRESS + "ADDRESS "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "John Doe "
            + PREFIX_PHONE + "98765432 "
            + PREFIX_EMAIL + "johnd@example.com "
            + PREFIX_ADDRESS + "311, Clementi Ave 2, #02-25 "
            + PREFIX_TAG + "friends "
            + PREFIX_TAG + "owesMoney";

    public static final String MESSAGE_SUCCESS = "New recipe added: %1$s";
    public static final String MESSAGE_DUPLICATE_RECIPE = "This recipe already exists in the address book";

    public static final String MESSAGE_USAGE_HEALTHPLAN = COMMAND_WORD + ": Adds a healthplan. "
            + "Parameters: "
            + PREFIX_HPNAME + "HealthPlan Name "
            + PREFIX_AGE + "Age "
            + PREFIX_CHEIGHT + "Current Height(CM) "
            + PREFIX_CWEIGHT + "Current Weight(KG) "
            + PREFIX_TWEIGHT + "Target Weight(KG) "
            + PREFIX_DURATION + "Duration(Days) "
            + PREFIX_SCHEME + "Scheme(GAIN/LOSS/MAINTAIN) \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_HPNAME + "SLIM DOWN "
            + PREFIX_AGE + "25 "
            + PREFIX_CHEIGHT + "170 "
            + PREFIX_CWEIGHT + "70 "
            + PREFIX_TWEIGHT + "60 "
            + PREFIX_DURATION + "10 "
            + PREFIX_SCHEME + "LOSS ";

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

package seedu.inventory.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.inventory.logic.parser.CliSyntax.PREFIX_IMAGE;
import static seedu.inventory.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.inventory.logic.parser.CliSyntax.PREFIX_PRICE;
import static seedu.inventory.logic.parser.CliSyntax.PREFIX_QUANTITY;
import static seedu.inventory.logic.parser.CliSyntax.PREFIX_SKU;
import static seedu.inventory.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.inventory.logic.CommandHistory;
import seedu.inventory.logic.commands.exceptions.CommandException;
import seedu.inventory.model.Model;
import seedu.inventory.model.item.Item;

/**
 * Adds an item to the inventory.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an item to the inventory. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_PRICE + "PRICE "
            + PREFIX_QUANTITY + "QUANTITY "
            + PREFIX_SKU + "SKU "
            + PREFIX_IMAGE + "IMAGE PATH "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "iPhone XR "
            + PREFIX_PRICE + "1500.00 "
            + PREFIX_QUANTITY + "70 "
            + PREFIX_SKU + "apple-iphone-xr "
            + PREFIX_IMAGE + "docs/images/iphone.jpg "
            + PREFIX_TAG + "apple "
            + PREFIX_TAG + "smartphone";

    public static final String MESSAGE_SUCCESS = "New item added: %1$s";
    public static final String MESSAGE_DUPLICATE_ITEM = "This item already exists in the inventory";

    private final Item toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Item}
     */
    public AddCommand(Item item) {
        requireNonNull(item);
        toAdd = item;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (model.hasItem(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_ITEM);
        }

        model.addItem(toAdd);
        model.commitInventory();
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddCommand // instanceof handles nulls
                && toAdd.equals(((AddCommand) other).toAdd));
    }
}

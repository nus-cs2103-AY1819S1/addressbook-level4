package seedu.restaurant.logic.commands.menu;

import static java.util.Objects.requireNonNull;
import static seedu.restaurant.logic.parser.util.CliSyntax.PREFIX_NAME;
import static seedu.restaurant.logic.parser.util.CliSyntax.PREFIX_PRICE;
import static seedu.restaurant.logic.parser.util.CliSyntax.PREFIX_TAG;

import seedu.restaurant.commons.core.EventsCenter;
import seedu.restaurant.commons.events.ui.menu.DisplayItemListRequestEvent;
import seedu.restaurant.logic.CommandHistory;
import seedu.restaurant.logic.commands.Command;
import seedu.restaurant.logic.commands.CommandResult;
import seedu.restaurant.logic.commands.exceptions.CommandException;
import seedu.restaurant.model.Model;
import seedu.restaurant.model.menu.Item;

//@@author yican95
/**
 * Adds an item to the menu.
 */
public class AddItemCommand extends Command {

    public static final String COMMAND_WORD = "add-item";

    public static final String COMMAND_ALIAS = "ai";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an item to the menu. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_PRICE + "PRICE "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "Apple Juice "
            + PREFIX_PRICE + "2 "
            + PREFIX_TAG + "drink";

    public static final String MESSAGE_SUCCESS = "New item added: %1$s";
    public static final String MESSAGE_DUPLICATE_ITEM = "This item already exists in the menu";

    private final Item toAdd;

    /**
     * Creates an AddItemCommand to add the specified {@code Item}
     */
    public AddItemCommand(Item item) {
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
        model.commitRestaurantBook();
        EventsCenter.getInstance().post(new DisplayItemListRequestEvent());
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddItemCommand // instanceof handles nulls
                    && toAdd.equals(((AddItemCommand) other).toAdd));
    }
}

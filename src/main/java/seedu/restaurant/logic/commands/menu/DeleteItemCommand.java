package seedu.restaurant.logic.commands.menu;

import static seedu.restaurant.logic.parser.util.CliSyntax.PREFIX_ENDING_INDEX;

import seedu.restaurant.logic.CommandHistory;
import seedu.restaurant.logic.commands.Command;
import seedu.restaurant.logic.commands.CommandResult;
import seedu.restaurant.logic.commands.exceptions.CommandException;
import seedu.restaurant.model.Model;

//@@author yican95
/**
 * Deletes an item from the menu.
 */
public abstract class DeleteItemCommand extends Command {

    public static final String COMMAND_WORD = "delete-item";

    public static final String COMMAND_ALIAS = "di";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the item identified by the index number used in the displayed item list.\n"
            + "Parameters: INDEX (must be a positive integer, starting index) "
            + "[" + PREFIX_ENDING_INDEX + "INDEX](can not be smaller than the starting index)\n"
            + "or Parameters: NAME\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_ITEM_SUCCESS = "Deleted %1$d items";

    @Override
    public abstract CommandResult execute(Model model, CommandHistory history) throws CommandException;

    @Override
    public abstract boolean equals(Object other);
}

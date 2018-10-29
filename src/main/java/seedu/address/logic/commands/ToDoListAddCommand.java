package seedu.address.logic.commands;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.ui.ToDoList;

/**
 * Adds a String user input to the list. Contains actions that the user may want to take related to their
 * transactions.
 */
public class ToDoListAddCommand extends Command {
    public static final String COMMAND_WORD = "todo";
    public static final String COMMAND_ALIAS = "toDo";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an action to the user's to-do list. "
            + "Example: "
            + COMMAND_WORD + " Buy some eggs.";

    //private final String action;
    private ToDoList tdl;

    public ToDoListAddCommand(String action) {

    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        return new CommandResult(MESSAGE_USAGE);
    }
}

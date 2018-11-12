package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRIORITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TITLE;

import seedu.address.logic.commands.AddToDoCommand;
import seedu.address.model.todolist.ToDoListEvent;

/**
 * A utility class for ToDoListEvent.
 */
public class ToDoListEventUtil {

    /**
     * Returns an add command string for adding the {@code calendarevent}.
     */
    public static String getAddToDoCommand(ToDoListEvent toDoListEvent) {
        return AddToDoCommand.COMMAND_WORD + " " + getToDoListEventDetails(toDoListEvent);
    }

    /**
     * Returns the part of command string for the given {@code todolistevent}'s details.
     */
    public static String getToDoListEventDetails(ToDoListEvent toDoListEvent) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_TITLE + toDoListEvent.getTitle().value + " ");
        sb.append(PREFIX_DESCRIPTION + toDoListEvent.getDescription().value + " ");
        sb.append(PREFIX_PRIORITY + toDoListEvent.getPriority().value + " ");
        return sb.toString();
    }

}


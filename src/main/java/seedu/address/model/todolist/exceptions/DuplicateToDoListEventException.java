package seedu.address.model.todolist.exceptions;

/**
 * Signals that the operation will result in duplicate ToDoList Events (ToDoList Events are considered duplicates if
 * they have the same identity).
 */
public class DuplicateToDoListEventException extends RuntimeException {
    public DuplicateToDoListEventException() {
        super("Operation would result in duplicate ToDoList events");
    }
}


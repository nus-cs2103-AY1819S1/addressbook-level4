package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.todolist.ToDoListEvent;

/**
 * Unmodifiable view of a ToDoList
 */
public interface ReadOnlyToDoList {

    /**
     * Returns an unmodifiable view of the ToDoList.
     * This list will not contain any duplicate ToDoList.
     */
    ObservableList<ToDoListEvent> getToDoList();
}


package seedu.address.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Region;

import seedu.address.commons.core.LogsCenter;
import seedu.address.model.todolist.ToDoListEvent;

/**
 * An UI component that displays selected description of a {@code ToDoListEvent}.
 */
public class DescriptionDisplay extends UiPart<Region> {

    private static final String FXML = "DescriptionDisplay.fxml";
    private final Logger logger = LogsCenter.getLogger(CommandBox.class);
    public final ToDoListEvent toDoListEvent;

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on Scheduler level 4</a>
     */

    @FXML
    private TextArea description;

    @FXML
    private Label id;

    private int selectedIndex;

    public DescriptionDisplay(ToDoListEvent toDoListEvent, int displayedIndex) {
        super(FXML);
        this.selectedIndex = displayedIndex;
        this.toDoListEvent = toDoListEvent;
        id.setText(displayedIndex + 1 + ": ");
        description.setText(toDoListEvent.getDescription().value);
    }

}

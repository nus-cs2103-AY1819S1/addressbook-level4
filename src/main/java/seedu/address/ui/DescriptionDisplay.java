package seedu.address.ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Region;

import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.todolist.ToDoListEvent;

import java.util.logging.Logger;

/**
 * An UI component that displays selected description of a {@code ToDoListEvent}.
 */
public class DescriptionDisplay extends UiPart<Region> {

    private static final String FXML = "DescriptionDisplay.fxml";
    private final Logger logger = LogsCenter.getLogger(CommandBox.class);

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on Scheduler level 4</a>
     */

    public final ToDoListEvent toDoListEvent;

    @FXML
    private TextArea description;

    @FXML
    private Label id;

    private int selectedIndex;

    public DescriptionDisplay(ToDoListEvent toDoListEvent, int displayedIndex) {
        super(FXML);
        this.selectedIndex = displayedIndex;
        this.toDoListEvent = toDoListEvent;
        id.setText(displayedIndex + ": ");
        description.setText(toDoListEvent.getDescription().value);
    }

    @FXML
    public void onOkButtonClicked(ActionEvent event) {
        closeStage(event);
    }

    private void closeStage(javafx.event.ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage stage  = (Stage) source.getScene().getWindow();
        stage.close();
    }
}

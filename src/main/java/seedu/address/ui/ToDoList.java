package seedu.address.ui;

import java.util.ArrayList;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Region;

import seedu.address.commons.core.LogsCenter;



/**
 * The to-do list panel of the App.
 */

public class ToDoList extends UiPart<Region> {

    private static final Logger logger = LogsCenter.getLogger(ToDoList.class);
    private static final String FXML = "ToDoList.fxml";

    @FXML
    private TextArea toDoListDisplay;

    private ArrayList<String> storage = new ArrayList<String>();

    /**
     * Creates a new ToDoList.
     */
    public ToDoList() {
        super(FXML);
    }

    //private List<String> store() {

    //}

}

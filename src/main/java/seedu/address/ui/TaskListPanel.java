package seedu.address.ui;

import java.util.logging.Logger;

import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;


/**
 * Panel containing the list of tasks
 */
public class TaskListPanel extends UiPart<Region> {
    private static final String FXML = "TaskListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(TaskListPanel.class);

    public TaskListPanel() {
        super(FXML);
        registerAsAnEventHandler(this);
    }

}

package seedu.address.ui;

import java.net.URL;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.web.WebView;
import seedu.address.MainApp;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.TaskPanelSelectionChangedEvent;
import seedu.address.model.task.Task;

/**
 * An UI component that displays all information of a {@code Task}.
 */
public class TaskViewPanel extends UiPart<Region> {
    private final Logger logger = LogsCenter.getLogger(TaskViewPanel.class);

    private static final String FXML = "TaskViewPanel.fxml";

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label dueDate;
    @FXML
    private Label remainingTime;
    @FXML
    private Label description;
    @FXML
    private Label priorityValue;
    @FXML
    private Label status;
    @FXML
    private Label hash;
    @FXML
    private Label dependency;
    @FXML
    private FlowPane tags;

    public TaskViewPanel(Task task) {
        super(FXML);
        displayTask(task);
        registerAsAnEventHandler(this);
    }

    private void displayTask(Task task) {
        name.setText(task.getName().fullName);
        dueDate.setText(task.getDueDate().value);
        remainingTime.setText(getRemainingTime(task));
        description.setText(task.getDescription().value);
        priorityValue.setText(task.getPriorityValue().value);
        status.setText(task.getStatus().toString());
        hash.setText(getHashId(task));
        dependency.setText(getDependencies(task));
        tags.getChildren().setAll(task.getLabels().stream().map(t -> new Label(t.labelName)).collect(Collectors.toList()));
    }

    private String getHashId(Task task) {
        return "Dependency id: " + Integer.toString(task.hashCode());
    }

    private String getDependencies(Task task) {
        return "dependencies: " + task.getDependency().toString();
    }

    private String getRemainingTime(Task task) {
        return "remaining time: " + task.getTimeToDueDate();
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof TaskViewPanel)) {
            return false;
        }

        // state check
        TaskViewPanel panel = (TaskViewPanel) other;
        return hash.getText().equals(panel.hash.getText());
    }

    @Subscribe
    private void handleTaskPanelSelectionChangedEvent(TaskPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        Task task = event.getNewSelection();
        displayTask(task);
    }
}

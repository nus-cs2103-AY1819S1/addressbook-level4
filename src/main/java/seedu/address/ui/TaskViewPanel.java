package seedu.address.ui;

import java.net.URL;
import java.util.logging.Logger;

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

    private static final String FXML = "TaskViewPanel.fxml";

    public final Task task;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
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

    public TaskViewPanel(Task task, int displayedIndex) {
        super(FXML);
        this.task = task;
        id.setText(displayedIndex + ". ");
        name.setText(task.getName().fullName);
        dueDate.setText(task.getDueDate().value);
        remainingTime.setText(getRemainingTime());
        description.setText(task.getDescription().value);
        priorityValue.setText(task.getPriorityValue().value);
        status.setText(task.getStatus().toString());
        hash.setText(getHashId());
        dependency.setText(getDependencies());
        task.getLabels().forEach(tag -> tags.getChildren().add(new Label(tag.labelName)));
        registerAsAnEventHandler(this);
    }

    private String getHashId() {
        return "Dependency id: " + Integer.toString(task.hashCode());
    }

    private String getDependencies() {
        return "dependencies: " + task.getDependency().toString();
    }

    private String getRemainingTime() {
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
        return id.getText().equals(panel.id.getText())
                && task.equals(panel.task);
    }

    @Subscribe
    private void handleTaskPanelSelectionChangedEvent(TaskPanelSelectionChangedEvent event) {
//        logger.info(LogsCenter.getEventHandlingLogMessage(event));
    }
}

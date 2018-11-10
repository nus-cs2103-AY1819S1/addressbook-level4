package seedu.address.ui;

import java.net.URL;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.collections.ObservableList;
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
import seedu.address.logic.Logic;
import seedu.address.model.task.Task;

/**
 * An UI component that displays all information of a {@code Task}.
 */
public class TaskViewPanel extends UiPart<Region> {
    private static final String FXML = "TaskViewPanel.fxml";

    private final Logger logger = LogsCenter.getLogger(TaskViewPanel.class);

    private Logic logic;

    // Used for keeping track of "remaining time", that uses a
    // NewResultAvailableEvent (with no Task attached to it)
    private Task latestTask;

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

    public TaskViewPanel(Logic logic) {
        super(FXML);
        this.logic = logic;
        if (logic.getFilteredTaskList().size() > 0) {
            displayTask(logic.getFilteredTaskList().get(0));
        }
        registerAsAnEventHandler(this);
    }

    /**
     * Updates the display to show the information regarding the given task.
     * As a side effect, it updates {@code latestTask}
     *
     * @param task the task whose information the view should display
     */
    private void displayTask(Task task) {
        name.setText(task.getName().fullName);
        dueDate.setText(task.getDueDate().value);
        remainingTime.setText(getRemainingTime(task));
        description.setText(task.getDescription().value);
        priorityValue.setText(task.getPriorityValue().value);
        status.setText(task.getStatus().toString());
        hash.setText(getHashId(task));
        dependency.setText(getDependencies(task));
        tags.getChildren().setAll(task
                .getLabels()
                .stream()
                .map(t -> new Label(t.labelName))
                .collect(Collectors.toList()));
        latestTask = task;
    }

    private String getHashId(Task task) {
        return Integer.toString(task.hashCode());
    }

    private String getDependencies(Task task) {
        ObservableList<Task> tasks = logic.getFilteredTaskList();
        List<String> names = task
                .getDependency()
                .getHashes()
                .stream()
                .map(hash -> tasks.stream().filter(t -> String.valueOf(t.hashCode()).equals(hash)).findFirst().get())
                .map(t -> t.getName().toString())
                .map(str -> str.length() > 20 ? str.substring(0, 12) + "..." : str)
                .collect(Collectors.toList());
        return String.join(", ", names);
    }

    private String getRemainingTime(Task task) {
        return task.getTimeToDueDate();
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

    @Subscribe
    public void handleNewResultEvent(NewResultAvailableEvent abce) {
        Platform.runLater(() -> remainingTime.setText(getRemainingTime(latestTask)));
    }
}

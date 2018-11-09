package seedu.address.ui;

import com.google.common.eventbus.Subscribe;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.commons.events.model.TaskManagerChangedEvent;
import seedu.address.commons.events.ui.NewResultAvailableEvent;
import seedu.address.model.ReadOnlyTaskManager;
import seedu.address.model.task.Task;


/**
 * An UI component that displays information of a {@code Task}.
 */
public class TaskCard extends UiPart<Region> {

    private static final String FXML = "PersonListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on TaskManager level 4</a>
     */

    public final Task task;
    private ReadOnlyTaskManager taskManager;

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
    @FXML
    private Label earliestTimeOfChildren;

    public TaskCard(Task task, int displayedIndex, ReadOnlyTaskManager taskManager) {
        super(FXML);
        this.task = task;
        this.taskManager = taskManager;
        id.setText(displayedIndex + ". ");
        name.setText(task.getName().fullName);
        dueDate.setText(task.getDueDate().value);
        remainingTime.setText(getRemainingTime());
        earliestTimeOfChildren.setText(getChildTime());
        description.setText(task.getDescription().value);
        priorityValue.setText(task.getPriorityValue().value);
        status.setText(task.getStatus().toString());
        hash.setText(getHashId());
        dependency.setText(getDependencies());
        task.getLabels().forEach(tag -> tags.getChildren().add(new Label(tag.labelName)));
        registerAsAnEventHandler(this);
    }
    //Used for testing TaskCard (Not testing earliest time of child)
    public TaskCard(Task task, int displayedIndex) {
        super(FXML);
        this.task = task;
        this.taskManager = null;
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

    private String getChildTime() {
        try {
            return "effective time:" + this.taskManager.getEarliestDependentTimeForNode(this.task).value;
        } catch (Exception e) {
            return "";
        }
    }

    @Subscribe
    public void handleNewResultEvent(NewResultAvailableEvent abce) {
        remainingTime.setText(getRemainingTime());
    }

    @Subscribe
    public void handleTaskManagerChangedEvent(TaskManagerChangedEvent tmce) {
        try {
            earliestTimeOfChildren.setText(tmce.data.getEarliestDependentTimeForNode(this.task).value);
        } catch (Exception e) {
            return;
        }
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof TaskCard)) {
            return false;
        }

        // state check
        TaskCard card = (TaskCard) other;
        return id.getText().equals(card.id.getText())
                && task.equals(card.task);
    }
}

package seedu.address.model.game;

// @@author chikchengyao

import javafx.beans.property.SimpleObjectProperty;
import seedu.address.model.task.Status;
import seedu.address.model.task.Task;

public class GameManager {

    /**
     * Appraises the present XP worth of a particular task.
     * <p>
     * Uncompleted tasks are worth 0XP.
     *
     * @param task The task to be evaluated
     * @return Returns the XP the supplied task is worth at present
     */
    public int appraiseTaskXp(Task task) {
        if (task.isCompleted()) {
            return 50;
        }

        // Task has not been completed
        return 0;
    }

    /**
     * Forecasts the XP an incomplete task will give, if completed.
     * <p>
     * Overdue or completed tasks have a 0XP forecast.
     *
     * @param task The task to be evaluated
     * @return Returns the XP the supplied task is worth at present
     */
    public int forecastTaskXp(Task task) {
        if (task.isCompleted() || task.isOverdue()) {
            return 0;
        }

        Task copy = new Task(task.getName(), task.getDueDate(), task.getPriorityValue(), task.getDescription(),
                task.getLabels(), Status.COMPLETED, task.getDependency());

        return appraiseTaskXp(copy);
    }

    /**
     * Returns the backing object as an {@code SimpleObjectProperty}.
     */
    public SimpleObjectProperty<GameManager> asSimpleObjectProperty() {
        return new SimpleObjectProperty<>(this);
    }
}

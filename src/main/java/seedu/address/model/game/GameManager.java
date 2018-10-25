package seedu.address.model.game;

// @@author chikchengyao

import javafx.beans.property.SimpleObjectProperty;
import seedu.address.model.task.Status;
import seedu.address.model.task.Task;

/**
 * Manages the gamification aspects of the task manager.
 *
 * Varies the amount of XP awarded to the player based on the task and on game settings.
 */
public class GameManager {

    // Stores an instance of the currently chosen game mode.
    GameMode gameMode;


    public GameManager() {
        this.gameMode = new FlatMode();
    }

    public GameManager(GameMode gameMode) {
        this.gameMode = gameMode;
    }

    /**
     * Appraises the present XP worth of a particular task.
     * <p>
     * Uncompleted tasks are worth 0XP.
     *
     * @param task The task to be evaluated
     * @return Returns the XP the supplied task is worth at present
     */
    public int appraiseTaskXp(Task task) {
        return gameMode.appraiseTaskXp(task);
    }

    /**
     * Shows the expected XP gain if the given task is completed.
     *
     * @param task The task to be evaluated
     * @return Returns the XP the supplied task is worth at present
     */
    public int forecastTaskXp(Task task) {

        // Make a copy of the task with COMPLETED status.
        Task copy = new Task(task.getName(), task.getDueDate(), task.getPriorityValue(), task.getDescription(),
                task.getLabels(), Status.COMPLETED, task.getDependency());

        int xpDiff = appraiseTaskXp(copy) - appraiseTaskXp(task);

        return xpDiff;
    }

    /**
     * Returns the backing object as an {@code SimpleObjectProperty}.
     */
    public SimpleObjectProperty<GameManager> asSimpleObjectProperty() {
        return new SimpleObjectProperty<>(this);
    }
}

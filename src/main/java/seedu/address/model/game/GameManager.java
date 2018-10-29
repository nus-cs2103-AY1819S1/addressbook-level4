package seedu.address.model.game;

import seedu.address.model.task.Task;

// @@author chikchengyao

/**
 * Manages the gamification aspects of the task manager.
 *
 * Varies the amount of XP awarded to the player based on the task and on game settings.
 */
public class GameManager {

    // Stores an instance of the currently chosen game mode.
    private GameMode gameMode;

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
     * @param taskFrom The task to be changed
     * @param taskTo    The changed task
     * @return Returns the XP the supplied task is worth at present
     */
    public int appraiseXpChange(Task taskFrom, Task taskTo) {
        if (taskFrom == null || taskTo == null) {
            throw new NullPointerException();
        }

        return gameMode.appraiseXpChange(taskFrom, taskTo);
    }
}

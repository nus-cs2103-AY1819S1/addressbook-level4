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
        this.gameMode = new DecreasingMode();
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

    public void setGameMode(String newGameModeName) {
        if (newGameModeName.equals("flat")) {
            this.gameMode = new FlatMode();
        }

        else {
            this.gameMode = new DecreasingMode();
        }
    }

    /**
     * Checks if the given game mode name is a valid name.
     */
    public static boolean isValidGameMode(String gameModeName) {
        if (gameModeName.equals("flat")) {
            return true;
        }

        if (gameModeName.equals("decreasing")) {
            return true;
        }

        return false;
    }
}

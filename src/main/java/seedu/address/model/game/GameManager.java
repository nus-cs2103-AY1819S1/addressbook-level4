package seedu.address.model.game;

import seedu.address.logic.commands.ModeCommand;
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

    public void setGameMode(String newGameModeName) {
        switch (newGameModeName) {
        case ModeCommand.FLAT_MODE:
            this.gameMode = new FlatMode();
            break;

        case ModeCommand.DECREASING_MODE:
            this.gameMode = new DecreasingMode();
            break;

        case ModeCommand.INCREASING_MODE:
            this.gameMode = new IncreasingMode();
            break;

        case ModeCommand.PRIORITY_MODE:
            this.gameMode = new PriorityMode();
            break;
        }
    }

    public void setGameMode(String newGameModeName, String newGameDifficultyName) {
        int period;
        int low;
        int high;

        switch(newGameDifficultyName) {
        case ModeCommand.EASY_MODE:
            period = 1;
            low = 40;
            high = 50;
            break;

        case ModeCommand.MEDIUM_MODE:
            period = 3;
            low = 30;
            high = 60;
            break;

        case ModeCommand.HARD_MODE:
            period = 7;
            low = 20;
            high = 70;
            break;

        case ModeCommand.EXTREME_MODE:
            period = 10;
            low = 10;
            high = 80;
            break;

        default:
            assert false;
            period = 7;
            low = 25;
            high = 50;
        }

        switch (newGameModeName) {
            case ModeCommand.FLAT_MODE:
                this.gameMode = new FlatMode(low, high);
                break;

            case ModeCommand.DECREASING_MODE:
                this.gameMode = new DecreasingMode(period, low, high);
                break;

            case ModeCommand.INCREASING_MODE:
                this.gameMode = new IncreasingMode(period, low, high);
                break;

            case ModeCommand.PRIORITY_MODE:
                this.gameMode = new PriorityMode();
                break;
        }
    }

    /**
     * Checks if the given game mode name is a valid name.
     */
    public static boolean isValidGameMode(String gameModeName) {
        if (gameModeName.equals(ModeCommand.FLAT_MODE)) {
            return true;
        }

        if (gameModeName.equals(ModeCommand.DECREASING_MODE)) {
            return true;
        }

        if (gameModeName.equals(ModeCommand.INCREASING_MODE)) {
            return true;
        }

        if (gameModeName.equals(ModeCommand.PRIORITY_MODE)) {
            return true;
        }

        return false;
    }

    public static boolean isValidGameDifficulty(String gameDifficultyName) {
        if (gameDifficultyName.equals(ModeCommand.EASY_MODE)) {
            return true;
        }

        if (gameDifficultyName.equals(ModeCommand.MEDIUM_MODE)) {
            return true;
        }

        if (gameDifficultyName.equals(ModeCommand.HARD_MODE)) {
            return true;
        }

        if (gameDifficultyName.equals(ModeCommand.EXTREME_MODE)) {
            return true;
        }

        return false;
    }

}

package seedu.address.model.game;

import seedu.address.model.task.Task;

// @@author chikchengyao

/**
 * A game mode where all completed tasks give the same XP.
 */
public class FlatMode implements GameMode {

    @Override
    public int appraiseTaskXp(Task task) {
        if (task.isCompleted()) {
            return 50;
        }

        // Task has not been completed
        return 0;
    }
}

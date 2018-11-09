package seedu.address.model.game;

import seedu.address.model.task.Task;

// @@author chikchengyao

/**
 *
 * A GameMode awarding XP based on the user-defined priority value.
 */
public class PriorityMode extends GameMode {
    @Override
    int appraiseXpChange(Task taskFrom, Task taskTo) {
        return Integer.parseInt(taskFrom.getPriorityValue().value) * 10;
    }
}

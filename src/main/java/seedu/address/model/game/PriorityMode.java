package seedu.address.model.game;

import seedu.address.model.task.Task;

// @@author chikchengyao

public class PriorityMode extends GameMode {
    @Override
    int appraiseXpChange(Task taskFrom, Task taskTo) {
        return Integer.parseInt(taskFrom.getPriorityValue().value) * 10;
    }
}

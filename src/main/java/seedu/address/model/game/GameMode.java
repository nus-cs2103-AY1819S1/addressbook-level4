package seedu.address.model.game;

import seedu.address.model.task.Task;

public interface GameMode {
    int appraiseXpChange(Task taskFrom, Task taskTo);
}

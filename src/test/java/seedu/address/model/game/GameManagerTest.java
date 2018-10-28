package seedu.address.model.game;

import org.junit.Before;
import org.junit.Test;
import seedu.address.model.task.Status;
import seedu.address.model.task.Task;
import seedu.address.testutil.TaskBuilder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class GameManagerTest {

    private GameManager gm;

    static class GameModeStub extends GameMode {

        @Override
        public int appraiseXpChange(Task taskFrom, Task taskTo) {
//            if (taskTo.isCompleted()) {
//                return 50;
//            }
//
//            // If execution reaches here, the task has not been completed
//            return 0;

            return 50;
        }
    }

    @Before
    public void setUp() {
        // using flat mode as default for predictable behaviour
        gm = new GameManager(new GameModeStub());
    }

    @Test
    public void null_appraiseXpChange() {

        Task task = new TaskBuilder().build();

        assertThrows(NullPointerException.class, () -> gm.appraiseXpChange(null, null));
        assertThrows(NullPointerException.class, () -> gm.appraiseXpChange(task, null));
        assertThrows(NullPointerException.class, () -> gm.appraiseXpChange(null, task));
    }

    @Test
    public void appraiseXpChange() {
        // check that method appraiseTaskXp is callable
        // checking of results is deferred to specific game modes
        Task inProgressTask = new TaskBuilder().withStatus(Status.IN_PROGRESS).build();
        Task completedTask = new TaskBuilder().withStatus(Status.COMPLETED).build();

        assertEquals(gm.appraiseXpChange(inProgressTask, completedTask), 50);
    }
}

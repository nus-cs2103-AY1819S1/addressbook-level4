package seedu.address.model.game;

import org.junit.Before;
import org.junit.Test;
import seedu.address.model.task.Status;
import seedu.address.model.task.Task;
import seedu.address.testutil.TaskBuilder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class GameManagerTest {

    private GameManager gm;

    static class GameModeStub implements GameMode {

        @Override
        public int appraiseTaskXp(Task task) {
            if (task.isCompleted()) {
                return 50;
            }

            // If execution reaches here, the task has not been completed
            return 0;
        }
    }

    @Before
    public void setUp() {
        // using flat mode as default for predictable behaviour
        gm = new GameManager(new GameModeStub());
    }

    @Test
    public void null_appraiseTaskXp() {
        assertThrows(NullPointerException.class, () -> gm.appraiseTaskXp(null));
    }

    @Test
    public void null_forecastTaskXp() {
        assertThrows(NullPointerException.class, () -> gm.forecastTaskXp(null));
    }

    @Test
    public void appraiseTaskXp() {
        // check that method appraiseTaskXp is callable
        // checking of results is deferred to specific game modes
        Task inProgressTask = new TaskBuilder().withStatus(Status.IN_PROGRESS).build();
        Task completedTask = new TaskBuilder().withStatus(Status.COMPLETED).build();

        assertEquals(gm.appraiseTaskXp(inProgressTask), 0);
        assertEquals(gm.appraiseTaskXp(completedTask), 50);
    }

    @Test
    public void forecastTaskXp() {
        // check that method forecastTaskXp is callable
        // checking of results is deferred to specific game modes
        Task inProgressTask = new TaskBuilder().withStatus(Status.IN_PROGRESS).build();
        Task completedTask = new TaskBuilder().withStatus(Status.COMPLETED).build();

        int inProgressXp = gm.forecastTaskXp(inProgressTask);
        int completedXp = gm.forecastTaskXp(completedTask);

        assertNotEquals(inProgressXp, completedXp);

    }
}

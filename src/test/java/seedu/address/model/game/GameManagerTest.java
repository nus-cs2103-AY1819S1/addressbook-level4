package seedu.address.model.game;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import seedu.address.model.task.Status;
import seedu.address.model.task.Task;
import seedu.address.testutil.TaskBuilder;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

//import static org.junit.Assert.*;

public class GameManagerTest {

    private GameManager flatGameManager;

    @Before
    public void setUp() {
        // using flat mode as default for predictable behaviour
        flatGameManager = new GameManager(new FlatMode());
    }

    @Test
    public void null_appraiseTaskXp() {
        assertThrows(NullPointerException.class, () -> flatGameManager.appraiseTaskXp(null));
    }

    @Test
    public void null_forecastTaskXp() {
        assertThrows(NullPointerException.class, () -> flatGameManager.forecastTaskXp(null));
    }

    @Test
    public void appraiseTaskXp() {
        // check that method appraiseTaskXp is callable
        // checking of results is deferred to specific game modes
        Task exampleTask = new TaskBuilder().withStatus(Status.IN_PROGRESS).build();

        flatGameManager.appraiseTaskXp(exampleTask);
    }

    @Test
    public void forecastTaskXp() {
        // check that method forecastTaskXp is callable
        // checking of results is deferred to specific game modes
        Task inProgressTask = new TaskBuilder().withStatus(Status.IN_PROGRESS).build();
        Task completedTask = new TaskBuilder().withStatus(Status.COMPLETED).build();

        int inProgressXp = flatGameManager.forecastTaskXp(inProgressTask);
        int completedXp = flatGameManager.forecastTaskXp(completedTask);

        assertNotEquals(inProgressXp, completedXp);

    }
}

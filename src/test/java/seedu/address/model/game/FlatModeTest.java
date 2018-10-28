package seedu.address.model.game;

import org.junit.Before;
import org.junit.Test;
import seedu.address.model.task.Status;
import seedu.address.model.task.Task;
import seedu.address.testutil.TaskBuilder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class FlatModeTest {
    private GameManager gm;

    @Before
    public void setUp() {
        gm = new GameManager(new FlatMode());
    }

    @Test
    public void null_appraiseTaskXp() {
        assertThrows(NullPointerException.class, () -> gm.appraiseTaskXp(null));
    }

    @Test
    public void appraiseTaskXp() {
        Task inProgressTask = new TaskBuilder().withStatus(Status.IN_PROGRESS).build();
        Task completedTask = new TaskBuilder().withStatus(Status.COMPLETED).build();

        assertEquals(gm.appraiseTaskXp(inProgressTask), 0);
        assertEquals(gm.appraiseTaskXp(completedTask), 50);
    }
}
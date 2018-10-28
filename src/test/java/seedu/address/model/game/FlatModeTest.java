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

    private int overdueXp = 2347;
    private int completedXp = 9362;

    @Before
    public void setUp() {
        gm = new GameManager(new FlatMode(overdueXp, completedXp));
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
        Task inProgressTask = new TaskBuilder().withStatus(Status.IN_PROGRESS).build();
        Task overdueTask = new TaskBuilder().withStatus(Status.OVERDUE).build();
        Task completedTask = new TaskBuilder().withStatus(Status.COMPLETED).build();

        // invalid - no change in status
        assertThrows(XpEvaluationException.class, () -> gm.appraiseXpChange(overdueTask, overdueTask));
        assertThrows(XpEvaluationException.class, () -> gm.appraiseXpChange(overdueTask, overdueTask));
        assertThrows(XpEvaluationException.class, () -> gm. appraiseXpChange(completedTask, completedTask));

        // invalid - regressing status
        assertThrows(XpEvaluationException.class, () -> gm.appraiseXpChange(overdueTask, inProgressTask));
        assertThrows(XpEvaluationException.class, () -> gm.appraiseXpChange(completedTask, inProgressTask));
        assertThrows(XpEvaluationException.class, () -> gm.appraiseXpChange(completedTask, overdueTask));

        // valid
        assertEquals(gm.appraiseXpChange(inProgressTask, overdueTask), 0);
        assertEquals(gm.appraiseXpChange(inProgressTask, completedTask), completedXp);
        assertEquals(gm.appraiseXpChange(overdueTask, completedTask), overdueXp);
    }
}
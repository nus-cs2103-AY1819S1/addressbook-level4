package seedu.address.model.game;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.Before;
import org.junit.Test;

import seedu.address.model.task.Status;
import seedu.address.model.task.Task;
import seedu.address.testutil.TaskBuilder;

// @@author chikchengyao

public class FlatModeTest {
    private GameManager gm;

    private int overdueXp = 2347;
    private int completedXp = 9362;

    @Before
    public void setUp() {
        gm = new GameManager(new FlatMode(overdueXp, completedXp));
    }

    @Test
    public void constructor_called_noException() {
        FlatMode fm1 = new FlatMode();
        FlatMode fm2 = new FlatMode(50, 100);
    }

    @Test
    public void appraiseXpChange_null_raiseNullPointerException() {

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
        assertThrows(XpEvaluationException.class, () -> gm.appraiseXpChange(completedTask, completedTask));

        // invalid - regressing status
        assertThrows(XpEvaluationException.class, () -> gm.appraiseXpChange(overdueTask, inProgressTask));
        assertThrows(XpEvaluationException.class, () -> gm.appraiseXpChange(completedTask, inProgressTask));
        assertThrows(XpEvaluationException.class, () -> gm.appraiseXpChange(completedTask, overdueTask));

        // valid
        assertEquals(0, gm.appraiseXpChange(inProgressTask, overdueTask));
        assertEquals(completedXp, gm.appraiseXpChange(inProgressTask, completedTask));
        assertEquals(overdueXp, gm.appraiseXpChange(overdueTask, completedTask));
    }

}

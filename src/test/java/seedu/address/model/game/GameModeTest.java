package seedu.address.model.game;

import static seedu.address.testutil.Assert.assertThrows;

import org.junit.Before;
import org.junit.Test;

import seedu.address.model.task.Status;
import seedu.address.model.task.Task;
import seedu.address.testutil.TaskBuilder;


public class GameModeTest {

    private GameMode bgm;

    static class BasicGameMode extends GameMode {

        @Override
        int appraiseXpChange(Task taskFrom, Task taskTo) {
            return 0;
        }
    }

    @Before
    public void setUp() {
        bgm = new BasicGameMode();
    }

    @Test
    public void checkValidTasks() {
        Task base = new TaskBuilder().withStatus(Status.IN_PROGRESS).build();

        // Tasks have different name
        Task diffName = new TaskBuilder().withName("other name12345")
                .withStatus(Status.COMPLETED)
                .build();
        assertThrows(XpEvaluationException.class, () -> bgm.checkValidTasks(base, diffName));

        // Tasks have different due date
        Task diffDueDate = new TaskBuilder().withDueDate("12-12-12 0000")
                .withStatus(Status.COMPLETED)
                .build();
        assertThrows(XpEvaluationException.class, () -> bgm.checkValidTasks(base, diffDueDate));

        // Tasks have different priority value
        Task diffPriorityValue = new TaskBuilder().withPriorityValue("897654327")
                .withStatus(Status.COMPLETED)
                .build();
        assertThrows(XpEvaluationException.class, () -> bgm.checkValidTasks(base, diffPriorityValue));

        // Tasks have different description
        Task diffDescription = new TaskBuilder().withDescription("some other description1294982")
                .withStatus(Status.COMPLETED)
                .build();
        assertThrows(XpEvaluationException.class, () -> bgm.checkValidTasks(base, diffDescription));

        // STATUS-RELATED

        // Tasks have same status
        Task sameStatus = new TaskBuilder().withStatus(Status.IN_PROGRESS)
                .build();
        assertThrows(XpEvaluationException.class, () -> bgm.checkValidTasks(base, sameStatus));

        Task inProgressTask = new TaskBuilder().withStatus(Status.IN_PROGRESS).build();
        Task overdueTask = new TaskBuilder().withStatus(Status.OVERDUE).build();
        Task completedTask = new TaskBuilder().withStatus(Status.COMPLETED).build();

        // Tasks regress from completed to overdue
        assertThrows(XpEvaluationException.class, () -> bgm.checkValidTasks(completedTask, overdueTask));

        // Tasks regress from overdue to in-progress
        assertThrows(XpEvaluationException.class, () -> bgm.checkValidTasks(overdueTask, inProgressTask));

        // Tasks regress from completed to in-progress
        assertThrows(XpEvaluationException.class, () -> bgm.checkValidTasks(completedTask, inProgressTask));

        // correct tasks
        bgm.checkValidTasks(inProgressTask, completedTask);
        bgm.checkValidTasks(overdueTask, completedTask);
        bgm.checkValidTasks(inProgressTask, overdueTask);
    }
}

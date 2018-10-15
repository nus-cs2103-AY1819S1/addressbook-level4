package seedu.address.model.task;

import org.junit.Before;
import seedu.address.logic.commands.DependencyCommand;
import seedu.address.testutil.TaskBuilder;

public class DependencyGraphTest {
    private UniqueTaskList tasks = new UniqueTaskList();
    private Task cyclicTask;

    @Before
    public void setUp() {
        //Initializing tasks
        Task a = new TaskBuilder().withName("A").build();
        Task b = new TaskBuilder().withName("B").withDependency(a).build();
        Task c = new TaskBuilder().withName("C").withDependency(b).withDependency(a).build();
        a = DependencyCommand.createDependantTask(a, c);

        tasks.add(a);
        tasks.add(b);
        cyclicTask = c;
    }

    public void isCyclic() {

    }
}

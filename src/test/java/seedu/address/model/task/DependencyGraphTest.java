package seedu.address.model.task;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.commands.DependencyCommand;
import seedu.address.model.DependencyGraph;
import seedu.address.testutil.TaskBuilder;

public class DependencyGraphTest {
    private List<Task> tasks = new ArrayList<>();
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

    @Test
    public void checkCyclic() {
        DependencyGraph graph = new DependencyGraph(tasks);
        assertFalse(graph.checkPresenceOfCycle());
        tasks.add(cyclicTask);
        graph = new DependencyGraph(tasks);
        assertTrue(graph.checkPresenceOfCycle());
    }

}

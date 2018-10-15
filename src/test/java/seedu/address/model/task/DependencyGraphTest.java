package seedu.address.model.task;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.commands.DependencyCommand;
import seedu.address.model.DependencyGraph;
import seedu.address.model.task.exceptions.GraphCycleException;
import seedu.address.testutil.TaskBuilder;

public class DependencyGraphTest {
    private List<Task> preCyclicTasks = new ArrayList<>();
    private Task cyclicTask;
    private List<Task> preSortedTasks = new ArrayList<>();
    private List<Task> sortedTasks = new ArrayList<>();

    @Before
    public void setUp() {
        //Initializing task lists
        Task a = new TaskBuilder().withName("A").build();
        Task b = new TaskBuilder().withName("B").withDependency(a).build();
        Task c = new TaskBuilder().withName("C").withDependency(b).withDependency(a).build();

        //preSortedTasks
        preSortedTasks.add(a);
        preSortedTasks.add(c);
        preSortedTasks.add(b);

        //SortedTasks
        sortedTasks.add(a);
        sortedTasks.add(b);
        sortedTasks.add(c);

        //preCyclicTasks
        a = DependencyCommand.createDependantTask(a, c);
        preCyclicTasks.add(a);
        preCyclicTasks.add(b);
        cyclicTask = c;
    }
    @Test
    public void constructor_invalid_throwsGraphCycleException() {
        preCyclicTasks.add(cyclicTask);
        assertThrows(GraphCycleException.class, () -> new DependencyGraph(preCyclicTasks));
    }

    @Test
    public void checkCyclic() {
        DependencyGraph graph = new DependencyGraph(preCyclicTasks);
        assertFalse(graph.checkPresenceOfCycle());
        preCyclicTasks.add(cyclicTask);
        graph = new DependencyGraph(preCyclicTasks);
        assertTrue(graph.checkPresenceOfCycle());
    }

    @Test
    public void topologicalSort_valid_returnsList() {
        DependencyGraph graph = new DependencyGraph(preSortedTasks);
        List<String> actualHashes = graph.topologicalSort();
        List<String> expectedHashes = sortedTasks.stream().map((task)->Integer.toString(task.hashCode()))
                .collect(Collectors.toList());
        assertEquals(expectedHashes, actualHashes);
    }
}

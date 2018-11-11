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
    private List<Task> cyclicTasks = new ArrayList<>();
    private List<Task> preSortedTasks = new ArrayList<>();
    private List<Task> sortedTasks = new ArrayList<>();

    @Before
    public void setUp() {
        //Initializing task lists
        Task completed1 = new TaskBuilder().withName("E").withStatus(Status.COMPLETED).build();
        Task completed2 = new TaskBuilder().withName("F").withStatus(Status.COMPLETED).build();
        Task a = new TaskBuilder().withName("A").withDependency(completed1).withDependency(completed2).build();
        Task b = new TaskBuilder().withName("B").withDependency(a).build();
        Task c = new TaskBuilder().withName("C").withDependency(b).withDependency(a).build();
        Task completed3 = new TaskBuilder().withName("D").withDependency(c).withStatus(Status.COMPLETED).build();

        //preSortedTasks
        preSortedTasks.add(a);
        preSortedTasks.add(c);
        preSortedTasks.add(b);
        //Should not be contained in final topological sort as they are completed
        preSortedTasks.add(completed1);
        preSortedTasks.add(completed2);
        preSortedTasks.add(completed3);

        //SortedTasks
        sortedTasks.add(a); //a should be first as completed1 and completed2 are completed tasks and dependency removed
        sortedTasks.add(b);
        sortedTasks.add(c);

        //preCyclicTasks
        a = DependencyCommand.createDependantTask(a, c);
        preCyclicTasks.add(a);
        preCyclicTasks.add(b);
        cyclicTask = c;

        //CyclicTasks
        cyclicTasks = new ArrayList<>(preCyclicTasks);
        cyclicTasks.add(cyclicTask);
    }

    @Test
    public void constructor_invalid_throwsGraphCycleException() {
        assertThrows(GraphCycleException.class, () -> new DependencyGraph(cyclicTasks));
    }

    @Test
    public void checkCyclic() {
        DependencyGraph graph = new DependencyGraph(preCyclicTasks);
        assertFalse(graph.checkPresenceOfCycle());
        assertTrue(graph.checkCyclicDependency(cyclicTask));
    }

    @Test
    public void topologicalSort_valid_returnsList() {
        DependencyGraph graph = new DependencyGraph(preSortedTasks);
        List<String> actualHashes = graph.topologicalSort();
        List<String> expectedHashes = sortedTasks.stream().map((task) -> Integer.toString(task.hashCode()))
                .collect(Collectors.toList());
        assertEquals(expectedHashes, actualHashes);
    }
}

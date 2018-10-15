package seedu.address.model;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import seedu.address.model.task.Dependency;
import seedu.address.model.task.Task;
import seedu.address.model.task.UniqueTaskList;

public class DependencyGraph {
    private Map<String, Set<String>> adjacencyList;

    public DependencyGraph(UniqueTaskList taskList) {
        for (Task task: taskList) {
            String hash = Integer.toString(task.hashCode());
            Set<String> edges = task.getDependency().getHashes();
            adjacencyList.put(hash, edges);
        }
    }

}

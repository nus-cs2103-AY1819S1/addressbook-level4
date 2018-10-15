package seedu.address.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import seedu.address.model.task.Task;
import seedu.address.model.task.UniqueTaskList;

/**
 * DependencyGraph models the dependencies of the tasks so that checks such as cyclic dependencies can be performed and
 * useful information such as the topological sort of the dependencies can be determined.
 */
public class DependencyGraph {
    private Map<String, Set<String>> adjacencyList = new HashMap<>();

    public DependencyGraph(UniqueTaskList taskList) {
        for (Task task: taskList) {
            String hash = Integer.toString(task.hashCode());
            Set<String> edges = task.getDependency().getHashes();
            adjacencyList.put(hash, edges);
        }
    }

    /**
     * Checks if there will be a cycle in the graph
     */
    public boolean checkCyclic() {
        Set<String> visited = new HashSet<>();
        Set<String> stack = new HashSet<>();
        for (String node: adjacencyList.keySet()) {
            if (!(visited.contains(node))){
                if (depthFirstSearch(node, visited, stack, adjacencyList)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Performs dfs on graph to check for cycles
     * @param node next node to check
     * @param visited set of visited nodes
     * @param stack set of nodes in current path. Used to check cycles
     * @param adjacencyList
     * @return true is there is a cycle
     */
    private boolean depthFirstSearch(String node, Set<String> visited, Set<String> stack, Map<String,
            Set<String>> adjacencyList) {
        if (stack.contains(node)) {
            return true;
        }
        visited.add(node);
        stack.add(node);
        Set<String> edges = adjacencyList.getOrDefault(node, new HashSet<String>());
        for (String nextNode: edges) {
            if (depthFirstSearch(nextNode, visited, stack, adjacencyList)) {
                return true;
            }
        }
        stack.remove(node);
        return false;
    }
    //TODO: Add toposort function

}

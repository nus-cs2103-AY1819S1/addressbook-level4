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

    /**
     * Checks if there will be a cycle in the graph
     * @param task
     */
    public String checkCyclic() {
        Set<String> visited = new HashSet<>();
        Set<String> stack = new HashSet<>();
        for (String node: adjacencyList.keySet()) {
            if (!(visited.contains(node))){
                String violation = depthFirstSearch(node, visited, stack, adjacencyList);
                if (violation != null) {
                    return violation;
                }
            }
        }
        return null;
    }

    /**
     * Performs dfs on graph to check for cycles
     * @param node next node to check
     * @param visited set of visited nodes
     * @param stack set of nodes in current path. Used to check cycles
     * @param adjacencyList
     * @return String hash of the violating node
     */
    private String depthFirstSearch(String node, Set<String> visited, Set<String> stack, Map<String, Set<String>> adjacencyList) {
        if (stack.contains(node)) {
            return node;
        }
        visited.add(node);
        stack.add(node);
        Set<String> edges = adjacencyList.getOrDefault(node, new HashSet<String>());
        for (String nextNode: edges) {
            String violation = depthFirstSearch(nextNode, visited, stack, adjacencyList);
            if (violation != null) {
                return violation;
            }
        }
        stack.remove(node);
        return null;
    }
    //TODO: Add toposort function

}

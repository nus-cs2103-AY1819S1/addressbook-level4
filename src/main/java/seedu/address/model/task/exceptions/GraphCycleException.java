package seedu.address.model.task.exceptions;

/**
 * Signals that the operation has detected a cyclic dependency in the dependency graph.
 */
public class GraphCycleException extends RuntimeException {
    public GraphCycleException() {
        super("Cycle detected in dependency graph");
    }
}

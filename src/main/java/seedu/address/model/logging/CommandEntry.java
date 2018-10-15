package seedu.address.model.logging;

import java.time.Instant;

/**
 * Represents a command entry inside command history.
 */
public class CommandEntry {
    private final Instant timeOfEntry;
    private final ExecutedCommand executedCommand;

    /**
     * Instantiates a CommandEntry
     */
    public CommandEntry(Instant timeOfEntry, ExecutedCommand executedCommand) {
        this.timeOfEntry = timeOfEntry;
        this.executedCommand = executedCommand;
    }

    public Instant getTimeOfEntry() {
        return timeOfEntry;
    }

    public ExecutedCommand getExecutedCommand() {
        return executedCommand;
    }

}

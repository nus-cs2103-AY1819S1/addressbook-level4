package seedu.address.model.logging;

import java.time.Instant;
import java.util.Objects;

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

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof CommandEntry)) {
            return false;
        }

        CommandEntry otherCommandEntry = (CommandEntry) other;
        return Objects.equals(timeOfEntry, otherCommandEntry.getTimeOfEntry())
                && Objects.equals(executedCommand, otherCommandEntry.getExecutedCommand());
    }
}

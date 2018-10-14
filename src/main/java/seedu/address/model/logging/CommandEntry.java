package seedu.address.model.logging;

import java.time.Instant;

/**
 * Represents a command entry inside command history.
 */
public class CommandEntry {
    private final Instant timeOfEntry;
    private final CommandWord commandWord;
    private final CommandArgs commandArgs;

    /**
     * Instantiates a CommandEntry
     */
    public CommandEntry(Instant timeOfEntry, CommandWord commandWord, CommandArgs commandArgs) {
        this.timeOfEntry = timeOfEntry;
        this.commandWord = commandWord;
        this.commandArgs = commandArgs;
    }

    public Instant getTimeOfEntry() {
        return timeOfEntry;
    }

    public CommandWord getCommandWord() {
        return commandWord;
    }

    public CommandArgs getCommandArgs() {
        return commandArgs;
    }
}
